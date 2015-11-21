package com.ksa.web.struts2.action.logistics;

import java.io.UnsupportedEncodingException;

import com.ksa.model.logistics.BaseLogisticsModel;


public abstract class LogisticsModelDownloadAction<T extends BaseLogisticsModel>  extends LogisticsModelAction<T> {

    private static final long serialVersionUID = -1214135495100416203L;
    
    protected String customFilename;
    
    /** 导出类型与导出文件名后缀的映射 */
/*    protected static Map<String, String> typeExtMap = new HashMap<String, String>(); 
    static {
        typeExtMap.put( "word", ".doc" );
        typeExtMap.put( "word2003", ".doc" );
        typeExtMap.put( "excel", ".xls" );
        typeExtMap.put( "excel2003", ".xls" );
    }*/
    
    /** 导出文档的格式，默认为 word */
    protected String type = "word";

    
    public String getType() {
        return type;
    }
    
    public void setType( String type ) {
        this.type = type;
    }
    
    public String getFilename() {
        String filename = doGetFilename();
        try {
            String t = type.toLowerCase();
            if( t.contains( "word" ) ) {
                filename += ".doc";
            } else if( t.contains( "excel" ) ) {
                filename += ".xls";
            }
            return new String(filename.getBytes("GBK"), "ISO8859-1");    // 修复下载文件名为乱码的bug
        } catch( UnsupportedEncodingException e ) {
            return filename;
        }
    }
    
    /** 获取导出文件的文件名（不含后缀） */
    protected abstract String doGetFilename();
    
    public void setFilename( String filename ) {
        customFilename = filename;
    }

}

