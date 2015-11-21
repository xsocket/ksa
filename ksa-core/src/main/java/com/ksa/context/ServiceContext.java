package com.ksa.context;


public interface ServiceContext {
    /**
     * 根据服务名称获取一个业务服务。
     * 
     * @param name 服务名称
     * @return 业务服务对象
     * @throws ContextException
     *         所需业务服务不存在
     */
    Object getService( String name ) throws ContextException;

    /**
     * 根据服务名称获取特定类型的一个业务服务。
     * 
     * @param name
     *        服务名称
     * @param requiredType
     *        服务类型
     * @return 业务服务对象
     * @throws ContextException
     *         业务服务获取不存在或类型不符合要求
     */
    <T> T getService( String name, Class<T> requiredType ) throws ContextException;

    /**
     * 获取特定类型的一个业务服务。
     * 
     * @param requiredType 服务类型
     * @return 业务服务对象
     * @throws ContextException
     *         特定类型的业务服务不存在
     */
    <T> T getService( Class<T> requiredType ) throws ContextException;

    /**
     * 判断特定名称的服务是否存在。
     * 
     * @param name 服务名称
     * @return true 对应名称的服务存在；否则返回 false
     */
    boolean containsService( String name );
}
