package com.ksa.service.bd.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ksa.dao.bd.PartnerDao;
import com.ksa.model.ModelUtils;
import com.ksa.model.bd.Partner;
import com.ksa.model.bd.PartnerType;
import com.ksa.service.bd.PartnerService;


public class PartnerServiceImpl implements PartnerService {
    
    private PartnerDao partnerDao;

    @Override
    public Partner loadPartnerById( String id ) throws RuntimeException {
        Partner partner = partnerDao.selectPartnerById( id );
        if( partner == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的合作伙伴不存在。", id ) );
        }
        return partner;
    }

    @Override
    public Partner loadPartnerByCode( String code ) throws RuntimeException {
        Partner partner = partnerDao.selectPartnerByCode( code );
        if( partner == null ) {
            throw new IllegalArgumentException( String.format( "代码为 '%s' 的合作伙伴不存在。", code ) );
        }
        return partner;
    }

    @Override
    public Partner createPartner( Partner partner ) throws RuntimeException {
        // 保证合作伙伴的代码全局唯一
        Partner temp = partnerDao.selectPartnerByCode( partner.getCode() );
        if( temp != null ) {
            throw new IllegalStateException( String.format( "代码为 '%s' 的合作伙伴已经存在。", partner.getCode() ) );
        }

        // 设置 id
        partner.setId( ModelUtils.generateRandomId() );
        
        int length = partnerDao.insertPartner( partner );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "新增合作伙伴发生异常，期望新增 1 条数据，实际新增了 %d 条数据。", length ) );
        }
        
        // 插入类型
        Collection<PartnerType> types = buildMap( partner.getTypes() ).values();    // 防止重复数据被插入
        for( PartnerType type : types ) {
            partnerDao.insertPartnerType( partner, type );
        }
        
        // 插入附加提单信息
        Set<String> extras = buildSet( partner.getExtras() );
        for( String extra : extras ) {
            partnerDao.insertPartnerExtra( partner, extra );
        }
        
        return partner;
    }

    @Override
    public Partner modifyPartner( Partner partner ) throws RuntimeException {
        Partner temp = partnerDao.selectPartnerById( partner.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的合作伙伴不存在。", partner.getId() ) );
        } else if( !temp.getCode().equals( partner.getCode() ) ) {   // 修改了 code
            temp = partnerDao.selectPartnerByCode( partner.getCode() );
            if( temp != null ) {
                throw new IllegalStateException( String.format( "代码为 '%s' 的合作伙伴已经存在。", partner.getCode() ) );
            }
        }
        
        int length = partnerDao.updatePartner( partner );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "更新合作伙伴发生异常，期望更新 1 条数据，实际更新了 %d 条数据。", length ) );
        }
        
        // 更新类型信息
        Map<String, PartnerType> oldTypes = buildMap( temp.getTypes() );
        Map<String, PartnerType> newTypes = buildMap( partner.getTypes() );
        for( PartnerType type : oldTypes.values() ) {
            if( !newTypes.containsKey( type.getId() ) ) {
                partnerDao.deletePartnerType( partner, type );
            }
        }
        for( PartnerType type : newTypes.values() ) {
            if( !oldTypes.containsKey( type.getId() ) ) {
                partnerDao.insertPartnerType( partner, type );
            }
        }
        
        // 更新附加提单信息
        Set<String> oldExtras = buildSet( temp.getExtras() );
        Set<String> newExtras = buildSet( partner.getExtras() );
        for( String extra : oldExtras ) {
            if( !newExtras.contains( extra ) ) {
                partnerDao.deletePartnerExtra( partner, extra );
            }
        }
        for( String extra : newExtras ) {
            if( !oldExtras.contains( extra ) ) {
                partnerDao.insertPartnerExtra( partner, extra );
            }
        }
        
        return partner;
    }
    
    private Map<String, PartnerType> buildMap( PartnerType[] types ) {
        Map<String, PartnerType> map = new HashMap<String, PartnerType>();
        if( types != null && types.length > 0 ) {
            for( PartnerType type : types ) {
                map.put( type.getId(), type );
            }
        }
        return map;
    }
    
    private Set<String> buildSet( String[] extras ) {
        Set<String> set = new HashSet<String>();
        if( extras != null && extras.length > 0 ) {
            for( String extra : extras ) {
                set.add( extra );
            }
        }
        return set;
    }

    @Override
    public Partner removePartner( Partner partner ) throws RuntimeException {
        Partner temp = partnerDao.selectPartnerById( partner.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的合作伙伴不存在。", partner.getId() ) );
        }
        
        int length = partnerDao.deletePartner( temp );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "删除合作伙伴发生异常，期望删除 1 条数据，实际删除了 %d 条数据。", length ) );
        }
        return temp;
    }
    
    public void setPartnerDao( PartnerDao partnerDao ) {
        this.partnerDao = partnerDao;
    }

}
