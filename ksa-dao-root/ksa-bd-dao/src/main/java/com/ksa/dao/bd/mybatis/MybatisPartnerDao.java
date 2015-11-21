package com.ksa.dao.bd.mybatis;

import java.util.HashMap;
import java.util.Map;

import com.ksa.dao.AbstractMybatisDao;
import com.ksa.dao.bd.PartnerDao;
import com.ksa.model.bd.Partner;
import com.ksa.model.bd.PartnerType;

/**
 * 基于 Mybaits 的合作伙伴数据访问接口实现。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class MybatisPartnerDao extends AbstractMybatisDao implements PartnerDao {

    @Override
    public int insertPartner( Partner partner ) throws RuntimeException {
        return this.session.insert( "insert-bd-partner", partner );
    }

    @Override
    public int updatePartner( Partner partner ) throws RuntimeException {
        return this.session.update( "update-bd-partner", partner );
    }

    @Override
    public int deletePartner( Partner partner ) throws RuntimeException {
        return this.session.delete( "delete-bd-partner", partner );
    }

    @Override
    public Partner selectPartnerById( String id ) throws RuntimeException {
        return this.session.selectOne( "select-bd-partner-byid", id );
    }
    
    @Override
    public Partner selectPartnerByCode( String code ) throws RuntimeException {
        return this.session.selectOne( "select-bd-partner-bycode", code );
    }

    @Override
    public int insertPartnerType( Partner partner, PartnerType type ) throws RuntimeException {
        Map<String, String> paras = new HashMap<String, String>();
        paras.put( "partneId", partner.getId() );
        paras.put( "typeId", type.getId() );
        return this.session.insert( "insert-bd-partnertype", paras );
    }

    @Override
    public int deletePartnerType( Partner partner, PartnerType type ) throws RuntimeException {
        Map<String, String> paras = new HashMap<String, String>();
        paras.put( "partneId", partner.getId() );
        paras.put( "typeId", type.getId() );
        return this.session.delete( "delete-bd-partnertype", paras );
    }
    
    @Override
    public int insertPartnerExtra( Partner partner, String extra ) throws RuntimeException {
        Map<String, String> paras = new HashMap<String, String>();
        paras.put( "partneId", partner.getId() );
        paras.put( "extra", extra );
        return this.session.insert( "insert-bd-partnertextra", paras );
    }

    @Override
    public int deletePartnerExtra( Partner partner, String extra ) throws RuntimeException {
        Map<String, String> paras = new HashMap<String, String>();
        paras.put( "partneId", partner.getId() );
        paras.put( "extra", extra );
        return this.session.delete( "delete-bd-partnertextra", paras );
    }

}
