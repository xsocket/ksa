package com.ksa.web.struts2.action.system.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DownloadAction extends ManagerAction {

    private static final long serialVersionUID = 6136996078231813304L;
    
    private String filename;
    private File file;


    @Override
    protected String doExecute() throws Exception {
        
        this.file = new File( super.getBackupDirectory(), this.filename );
        if( !file.exists() || file.isDirectory() ) {
            return ERROR;
        }
        return SUCCESS;
    }
    
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream( this.file );
    }
    
    public long getFilesize() {
        return this.file.length();
    }
    
    public String getFilename() {
        return filename;
    }

    public void setFilename( String filename ) {
        this.filename = filename;
    }
}
