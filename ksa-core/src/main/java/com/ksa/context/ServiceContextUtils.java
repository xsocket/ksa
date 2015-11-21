package com.ksa.context;

/**
 * 业务逻辑服务配置管理工具。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class ServiceContextUtils {
    
    private static ServiceContext instance;

    /**
     * 初始化业务服务配置实例。
     */
    public static void init( ServiceContext instance ) {
        ServiceContextUtils.instance = instance;
    }
    
    /**
     * 根据服务名称获取一个业务服务。
     * 
     * @param name
     *        服务名称
     * @return 业务服务对象
     * @throws ContextException
     *         所需业务服务不存在
     */
    public static Object getService( String name ) throws ContextException {
        return instance.getService( name );
    }

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
    public static <T> T getService( String name, Class<T> requiredType ) throws ContextException {
        return instance.getService( name, requiredType );
    }

    /**
     * 获取特定类型的一个业务服务。
     * 
     * @param requiredType
     *        服务类型
     * @return 业务服务对象
     * @throws ContextException
     *         特定类型的业务服务不存在
     */
    public static <T> T getService( Class<T> requiredType ) throws ContextException {
        return instance.getService( requiredType );
    }

    /**
     * 判断特定名称的服务是否存在。
     * 
     * @param name
     *        服务名称
     * @return true 对应名称的服务存在；否则返回 false
     */
    public static boolean containsService( String name ) {
        return instance.containsService( name );
    }
}
