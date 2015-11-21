package com.ksa.web.struts2.action.bd.data;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.bd.BasicDataService;
import com.ksa.util.StringUtils;

public class BasicDataInsertAction extends BasicDataAction {

    private static final long serialVersionUID = 4601199504765579351L;

    @Override
    public String doExecute() throws Exception {
        BasicDataService service = ServiceContextUtils.getService( BasicDataService.class );
        data = service.createBasicData( data );
        addActionMessage( String.format( "成功创建基本代码：'%s'。", data.getName() ) );
        return SUCCESS;
    }
    
    @Override
    public void validate() {
        super.validate();
        final int basicDataLength = 200;
        // 基本代码编码
        if( !StringUtils.hasText( data.getCode() ) ) {
            this.addActionError( "请输入基本代码。" );
        } else {
            if( data.getName().length() > basicDataLength ) {
                this.addActionError( "基本代码过长，请控制在 <span class='badge badge-success'>" + basicDataLength + "</span> 个字符之内。" );
            }
        }
        // 名称
        if( !StringUtils.hasText( data.getName() ) ) {
            this.addActionError( "请输入基本代码名称。" );
        } else {
            if( data.getName().length() > basicDataLength ) {
                this.addActionError( "基本代码名称过长，请控制在 <span class='badge badge-success'>" + basicDataLength + "</span> 个字符之内。" );
            }
        }
        // 别名
        if( StringUtils.hasText( data.getAlias() ) && data.getAlias().length() > basicDataLength ) {
            this.addActionError( "基本代码别名过长，请控制在 <span class='badge badge-success'>" + basicDataLength + "</span> 个字符之内。" );
        }
        // 备注
        if( StringUtils.hasText( data.getNote() ) && data.getNote().length() > 2000 ) {
            this.addActionError( "基本代码备注过长，请控制在 <span class='badge badge-success'>2000</span> 个字符之内。" );
        }
        // 附加值
        if( StringUtils.hasText( data.getExtra() ) && data.getExtra().length() > basicDataLength ) {
            this.addActionError( "基本代码附加信息过长，请控制在 <span class='badge badge-success'>" + basicDataLength + "</span> 个字符之内。" );
        }
    }
}
