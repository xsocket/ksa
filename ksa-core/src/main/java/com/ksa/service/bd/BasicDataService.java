package com.ksa.service.bd;

import com.ksa.model.bd.BasicData;

/**
 * 基础数据管理服务接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface BasicDataService {
    /**
     * 根据基础数据标识获取基础数据。
     * @param dataId 用户标识
     * @return 对应标识的基础数据
     * @throws RuntimeException 对应标识的用户不存在
     */
    BasicData loadBasicDataById( String dataId ) throws RuntimeException;

    /**
     * 创建新基础数据。
     * @param data 将要被创建的基础数据
     * @return 创建成功后的基础数据
     * @throws RuntimeException
     */
    BasicData createBasicData( BasicData data ) throws RuntimeException;
    
    /**
     * 更新基础数据。
     * @param data 新的基础数据
     * @return 更新成功后的基础数据
     * @throws RuntimeException
     */
    BasicData modifyBasicData( BasicData data ) throws RuntimeException;
    
    /**
     * 移除基础数据。
     * @param data 需要被移除的基础数据
     * @return 移除成功后的基础数据
     * @throws RuntimeException
     */
    BasicData removeBasicData( BasicData data ) throws RuntimeException;
}
