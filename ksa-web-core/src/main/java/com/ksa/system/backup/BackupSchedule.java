package com.ksa.system.backup;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContext;

import com.ksa.context.web.RuntimeConfiguration;


public class BackupSchedule {

    private static BackupSchedule instance;
    public static BackupSchedule getInstance( ServletContext sc ) {
        if( instance == null ) {
            instance = new BackupSchedule( sc );
        }
        return instance;
    }

    public static String KEY_BACKUP_AUTO_ON = "backup.auto.on";
    public static String KEY_BACKUP_AUTO_NEXT = "backup.auto.next";
    public static String KEY_BACKUP_AUTO_INTERVAL = "backup.auto.interval";
    
    public static boolean DEFAULT_BACKUP_AUTO_ON = false;
    public static long DEFAULT_BACKUP_AUTO_NEXT = 0;
    public static int DEFAULT_BACKUP_AUTO_INTERVAL = 7;
    
    private Timer timer = new Timer();
    private ServletContext context;
    
    public Timer getTimer() {
        return timer;
    }
    
    public void cancel() {
        if( timer != null ) {
            timer.cancel();
            timer = null;
        }
    }
    
    public void schedule() {
        cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate( new BackupTask( this, context ), getNextExecuteDate(), ( (long)getIntervalDay() ) * 24L * 60L * 60L * 1000L );
    }
        
    protected BackupSchedule( ServletContext sc) {
        this.context = sc;
    }
    
    public boolean isAutoBackupOn() {
        return RuntimeConfiguration.getConfiguration().getBoolean( KEY_BACKUP_AUTO_ON, DEFAULT_BACKUP_AUTO_ON ); 
    }
    
    public void setAutoBackupOn( boolean on ) {
        RuntimeConfiguration.getConfiguration().setProperty( KEY_BACKUP_AUTO_ON, on ); 
    }
    
    public void setNextExecuteDate( Date next ) {
        long nextTicks = next.getTime();
        if( nextTicks < new Date().getTime() ) {
            nextTicks = getTommorrowTicks();
        }
        RuntimeConfiguration.getConfiguration().setProperty( KEY_BACKUP_AUTO_NEXT, nextTicks ); 
    }
    
    public Date getNextExecuteDate() {
        Date now = new Date();
        long next = RuntimeConfiguration.getConfiguration().getLong( KEY_BACKUP_AUTO_NEXT, DEFAULT_BACKUP_AUTO_NEXT );
        if( next <= 0L ) {
            next = getTommorrowTicks();
        }
        long nowTime = now.getTime();
        long interval = (long) getIntervalDay();
        while( next < nowTime ) {
            next += interval * 24L * 60L * 60L * 1000L;
        }
        return new Date( next );
    }
    
    protected long getTommorrowTicks() {
        Calendar c = Calendar.getInstance();
        c.setTime( new Date() );
        c.add( Calendar.DATE, 1 );
        c.set( Calendar.HOUR, 0 );
        c.set( Calendar.MINUTE, 1 );
        c.set( Calendar.SECOND, 0 );
        return c.getTimeInMillis();
    }
    
    public void setIntervalDay( int interval ) {
        RuntimeConfiguration.getConfiguration().setProperty( KEY_BACKUP_AUTO_INTERVAL, interval <= 0 ? DEFAULT_BACKUP_AUTO_INTERVAL : interval );
    }
    
    public int getIntervalDay() {
        return RuntimeConfiguration.getConfiguration().getInt( KEY_BACKUP_AUTO_INTERVAL, DEFAULT_BACKUP_AUTO_INTERVAL );
    }
    
    public void save() {
        RuntimeConfiguration.save();
    }
}
