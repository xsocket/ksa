package com.ksa.web.struts2.action.bd.partner;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.bd.PartnerService;
import com.ksa.util.StringUtils;

public class PartnerInsertAction extends PartnerAction {

    private static final long serialVersionUID = -1776587314175844741L;

    @Override
    public String doExecute() throws Exception {
        PartnerService service = ServiceContextUtils.getService( PartnerService.class );        
        partner = service.createPartner( partner );
        addActionMessage( String.format( "成功创建合作伙伴信息：'%s'。", partner.getName() ) );
        return SUCCESS;
    }
    
    @Override
    public void validate() {
        super.validate();
        final int basicDataLength = 200;
        final int longDataLength = 2000;
        // 合作伙伴编码
        if( !StringUtils.hasText( partner.getCode() ) ) {
            this.addActionError( "请输入代码信息。" );
        } else {
            if( partner.getCode().length() > basicDataLength ) {
                this.addActionError( "代码信息过长，请控制在 <span class='badge badge-success'>" + basicDataLength + "</span> 个字符之内。" );
            }
        }
        // 名称
        if( !StringUtils.hasText( partner.getName() ) ) {
            this.addActionError( "请输入名称息。" );
        } else {
            if( partner.getName().length() > basicDataLength ) {
                this.addActionError( "名称信息过长，请控制在 <span class='badge badge-success'>" + basicDataLength + "</span> 个字符之内。" );
            }
        }
        
        // 别名
        if( StringUtils.hasText( partner.getAlias() ) && partner.getAlias().length() > longDataLength ) {
            this.addActionError( "抬头信息过长，请控制在 <span class='badge badge-success'>" + longDataLength + "</span> 个字符之内。" );
        }
        
        // 备注
        if( StringUtils.hasText( partner.getNote() ) && partner.getNote().length() > longDataLength ) {
            this.addActionError( "备注信息过长，请控制在 <span class='badge badge-success'>" + longDataLength + "</span> 个字符之内。" );
        }
        
        // 地址
        if( StringUtils.hasText( partner.getAddress() ) && partner.getAddress().length() > longDataLength ) {
            this.addActionError( "地址信息过长，请控制在 <span class='badge badge-success'>" + longDataLength + "</span> 个字符之内。" );
        }
        
        // 付款周期
        if( partner.getPp() <= 0 ) {
            this.addActionError( "付款周期必须大于 <span class='badge badge-success'>0</span> 天。" );
        }
    }
}
