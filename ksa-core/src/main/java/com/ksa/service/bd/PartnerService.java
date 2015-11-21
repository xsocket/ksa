package com.ksa.service.bd;

import com.ksa.model.bd.Partner;

/**
 * 合作伙伴管理服务接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface PartnerService {
    
    /**
     * 根据合作伙伴数据标识获取合作伙伴数据。
     * @param id 合作伙伴标识
     * @return 对应标识的合作伙伴数据
     * @throws RuntimeException 对应标识的合作伙伴不存在
     */
    Partner loadPartnerById( String id ) throws RuntimeException;
    
    /**
     * 根据合作伙伴数据代码获取合作伙伴数据。
     * @param code 合作伙伴代码
     * @return 对应代码的合作伙伴数据
     * @throws RuntimeException 对应代码的合作伙伴不存在
     */
    Partner loadPartnerByCode( String code ) throws RuntimeException;

    /**
     * 创建新合作伙伴数据。
     * @param partner 将要被创建的合作伙伴数据
     * @return 创建成功后的合作伙伴数据
     * @throws RuntimeException
     */
    Partner createPartner( Partner partner ) throws RuntimeException;
    
    /**
     * 更新合作伙伴数据。
     * @param partner 新的合作伙伴数据
     * @return 更新成功后的合作伙伴数据
     * @throws RuntimeException
     */
    Partner modifyPartner( Partner partner ) throws RuntimeException;
    
    /**
     * 移除合作伙伴数据。
     * @param partner 需要被移除的合作伙伴数据
     * @return 移除成功后的合作伙伴数据
     * @throws RuntimeException
     */
    Partner removePartner( Partner partner ) throws RuntimeException;
}
