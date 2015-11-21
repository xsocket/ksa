package com.ksa.model;

import java.util.UUID;

import com.ksa.util.StringUtils;


/**
 * 业务模型工具类。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 */
public class ModelUtils {
    
    /** 纯工具类，禁止实例化。  */
    private ModelUtils() {}

    /**
     * 新建业务对象的标识。
     */
    public static final String FRESH_OBJECT_ID = "";

    /**
     * 随机生成业务对象的唯一标识。
     * 
     * @return 唯一标识。
     */
    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    /* --------------------- 业务对象状态判断函数 --------------------- */

    /**
     * 判断业务对象是否未被持久化（即：是一个新建对象）。
     * <p>
     * 对象被持久化表示对象在数据库等数据存储区存在副本。
     * 
     * @param obj
     *        业务对象
     * @return 如果业务对象是一个新建对象未被持久化，则返回 true；否则返回 false。
     */
    public static boolean isFreshObject( BaseModel obj ) {
        return ! StringUtils.hasText( obj.getId() );
    }

    /**
     * 判断业务对象是否已被持久化。
     * <p>
     * 对象被持久化表示对象在数据库等数据存储区存在副本。
     * 
     * @param obj
     *        业务对象
     * @return 如果业务对象已经被持久化，则返回 true；否则返回 false。
     */
    public static boolean isPersistentObject( BaseModel obj ) {
        return !isFreshObject( obj );
    }

    /**
     * 判断业务对象是否为系统保留对象。
     * <p>
     * 系统保留对象时处理业务逻辑时必不可少的对象，不能对其进行修改。
     * <p>
     * 当前默认实现为对业务对象的 id 进行判断，如果是系统保留对象，则其 id 为特定意义的唯一标识； 否则对象 id 是通过 generateRandomId 方法随机生成的 UUID 字符串。
     * 
     * @param obj
     *        业务对象
     * @return 如果业务对象是系统保留对象，则返回 true；否则返回 false。
     */
    public static boolean isReservedObject( BaseModel obj ) {
        if( obj == null ) {
            return false;
        }
        String id = obj.getId();
        try {
            UUID.fromString( id );
            return false;
        } catch( IllegalArgumentException e ) {
            // 不是uuid标识，说明是系统保留对象。
            return true;
        }

    }

}
