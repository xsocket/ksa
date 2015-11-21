package com.ksa.dao.bd;

import com.ksa.model.bd.Partner;
import com.ksa.model.bd.PartnerType;

/**
 * 合作伙伴数据访问接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface PartnerDao {
    int insertPartner( Partner partner ) throws RuntimeException;
    int updatePartner( Partner data ) throws RuntimeException;
    int deletePartner( Partner data ) throws RuntimeException;
    Partner selectPartnerById( String id ) throws RuntimeException;
    Partner selectPartnerByCode( String id ) throws RuntimeException;
    
    int insertPartnerType( Partner partner, PartnerType type ) throws RuntimeException;
    int deletePartnerType( Partner partner, PartnerType type ) throws RuntimeException;
    
    int insertPartnerExtra( Partner partner, String extra ) throws RuntimeException;
    int deletePartnerExtra( Partner partner, String extra ) throws RuntimeException;
}
