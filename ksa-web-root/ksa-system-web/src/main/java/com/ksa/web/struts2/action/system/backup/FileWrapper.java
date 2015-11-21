package com.ksa.web.struts2.action.system.backup;

import java.io.File;
import java.util.Date;

public class FileWrapper {

    private File file;

    public FileWrapper( File file ) {
        this.file = file;
    }

    public String getName() {
        return file.getName();
    }

    public Date getLastModified() {
        return new Date( file.lastModified() );
    }
    
    public long getSize() {
        return file.length();
    }
}
