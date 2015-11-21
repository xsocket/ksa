package com.ksa.web.struts2.action.system.backup;

import java.util.Date;

import org.apache.struts2.ServletActionContext;

import com.ksa.system.backup.BackupSchedule;


public class StrategyView extends ManagerAction {

    private static final long serialVersionUID = 5988314421967742595L;
    
    protected boolean on = false;
    protected Date next;
    protected int interval;
    protected boolean doJustNow = false;
    
    @Override
    protected String doExecute() throws Exception {
        BackupSchedule instance = BackupSchedule.getInstance( ServletActionContext.getServletContext() );
        this.on = instance.isAutoBackupOn();
        this.next = instance.getNextExecuteDate();
        this.interval = instance.getIntervalDay();
        this.doJustNow = true;
        
        return SUCCESS;
    }
    
    
    public boolean isOn() {
        return on;
    }
    
    public void setOn( boolean on ) {
        this.on = on;
    }
    
    public Date getNext() {
        return next;
    }
    
    public void setNext( Date next ) {
        this.next = next;
    }
    
    public int getInterval() {
        return interval;
    }
    
    public void setInterval( int interval ) {
        this.interval = interval;
    }
    
    public boolean isDoJustNow() {
        return doJustNow;
    }
    
    public void setDoJustNow( boolean doJustNow ) {
        this.doJustNow = doJustNow;
    }
    
    
 

}
