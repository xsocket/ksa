package com.ksa.web.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ksa.context.web.SpringServiceContextListener;
import com.ksa.system.backup.BackupSchedule;


public class BackupScheduleListener extends SpringServiceContextListener implements ServletContextListener {

    @Override
    public void contextInitialized( ServletContextEvent sce ) {
        super.contextInitialized( sce );
        BackupSchedule schedule = BackupSchedule.getInstance( sce.getServletContext() );
        if( schedule.isAutoBackupOn() ) {
            // 启动自动备份
            schedule.schedule();
        }
        
    }

    @Override
    public void contextDestroyed( ServletContextEvent sce ) {
        BackupSchedule.getInstance( sce.getServletContext() ).cancel();
        super.contextDestroyed( sce );
    }

}
