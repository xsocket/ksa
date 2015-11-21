package com.ksa.web.struts2.action.system.backup;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.ksa.web.struts2.action.JsonAction;
import com.ksa.web.struts2.action.model.JsonResult;

public class DeleteAction extends ManagerAction implements JsonAction {

    private static final long serialVersionUID = -703652961673191408L;

    private JsonResult result;

    private String filename;

    @Override
    protected String doExecute() throws Exception {
        File file = new File( super.getBackupDirectory(), this.filename );
        if( !file.exists() || file.isDirectory() ) {
            this.addActionError( "名称为 '" + filename + "' 的数据备份文件不存在！" );
        } else {
            FileUtils.deleteQuietly( file );
            this.result = new JsonResult( SUCCESS, "数据备份文件删除成功。" );
        }
        return SUCCESS;
    }

    @Override
    public Object getJsonResult() {
        if( this.hasActionErrors() ) {
            this.result = new JsonResult( ERROR, this.getActionErrors().iterator().next() );
        }
        return this.result;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename( String filename ) {
        this.filename = filename;
    }
}
