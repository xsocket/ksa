package com.ksa.web.struts2.action.system.backup;

import java.io.File;
import java.io.FilenameFilter;

import com.ksa.service.security.util.SecurityUtils;
import com.ksa.system.backup.BackupTask;
import com.ksa.web.struts2.action.DefaultActionSupport;


public class ManagerAction extends DefaultActionSupport {

    private static final long serialVersionUID = -1604839033990532880L;    

    @Override
    public String execute() throws Exception {
        if( !SecurityUtils.isPermitted( "system:backup" ) ) {
            return NO_PERMISSION;
        }
        return super.execute();
    }
    
    protected File getBackupDirectory() {
        return BackupTask.getBackupDirectory();
    }
    
    protected String generateBackupFilename() {
        return BackupTask.generateBackupFilename();
    }
    
    /**
     * 备份文件名过滤器。
     *
     * @author 麻文强
     *
     * @since v0.0.1
     */
    static class BackupFilenameFilter implements FilenameFilter {
        @Override
        public boolean accept( File dir, String name ) {
            return name.startsWith( "ksa-" ) && name.endsWith( ".bak" );
        }
    }
}
