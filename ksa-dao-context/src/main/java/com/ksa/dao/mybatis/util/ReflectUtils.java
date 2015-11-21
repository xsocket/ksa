package com.ksa.dao.mybatis.util;

import java.lang.reflect.Field;

/**
 * 反射工具类。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 */
public class ReflectUtils {

    /**
     * 获取给定 obj 对象名称为 fieldName 的 Field。
     * 
     * @param obj
     *        获取 Field 的目标对象
     * @param fieldName
     *        Field 对象的名称
     * @return Field 对象
     */
    public static Field getField( Object obj, String fieldName ) {
        for( Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass() ) {
            try {
                return superClass.getDeclaredField( fieldName );
            } catch( NoSuchFieldException e ) { /* 忽略 */}
        }
        return null;
    }
    
    /**
     * 通过反射方式获取给定 obj 对象 field 字段的值。
     * @param obj 获取所需字段值的目标对象
     * @param field 目标字段
     * @return 指定字段的值
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getFieldValue( Object obj, Field field ) throws IllegalArgumentException, IllegalAccessException {
        Object value = null;
        if( field != null ) {
            if( field.isAccessible() ) {
                value = field.get( obj );
            } else {
                field.setAccessible( true );
                value = field.get( obj );
                field.setAccessible( false );
            }
        }
        return value;
    }

    /**
     * 通过反射方式获取给定 obj 对象名称为 fieldName 的字段值。
     * 
     * @param obj 获取所需字段值的目标对象
     * @param fieldName 目标字段名称
     * @return 指定字段的值
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getFieldValue( Object obj, String fieldName ) throws IllegalArgumentException, IllegalAccessException {
        return getFieldValue( obj, getField( obj, fieldName ) );
    }
    
    /**
     * 通过反射方式将给定 obj 对象的 field 字段值设置为 value。
     * @param obj 设置所需字段值的目标对象
     * @param field 目标字段
     * @param value 目标字段值
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setFieldValue( Object obj, Field field, Object value ) throws IllegalArgumentException, IllegalAccessException {
        if( field == null ) {
            throw new IllegalArgumentException( "Field argument could not be null." );  //$NON-NLS-1$ 
        }
        if( field.isAccessible() ) {
            field.set( obj, value );
        } else {
            field.setAccessible( true );
            field.set( obj, value );
            field.setAccessible( false );
        }
    }
    
    /**
     * 通过反射方式将给定 obj 对象名称为 field 的字段值设置为 value。
     * @param obj 设置所需字段值的目标对象
     * @param fieldName 目标字段名称
     * @param value 目标字段值
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setFieldValue( Object obj, String fieldName, Object value ) throws IllegalArgumentException, IllegalAccessException {
        setFieldValue( obj, getField( obj, fieldName ), value);
    }
}
