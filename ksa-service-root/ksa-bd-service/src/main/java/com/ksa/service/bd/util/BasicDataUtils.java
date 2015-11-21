package com.ksa.service.bd.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ksa.context.ContextException;
import com.ksa.context.ServiceContextUtils;
import com.ksa.dao.bd.BasicDataDao;
import com.ksa.model.bd.BasicData;


public class BasicDataUtils {
    
    private static BasicDataDao dao;
    private static boolean initialized = false;

    // 保存所有的基础数据缓存
    private static Map<String, BasicData> allBasicDataCache = new HashMap<String, BasicData>();
    // 按照基础数据类型，分类保存基础数据 key: typeId
    private static Map<String, Map<String, BasicData>> typedBasicDataCache = 
            new HashMap<String, Map<String, BasicData>>();
    
    static {
        try {
            BasicDataDao d = ServiceContextUtils.getService( BasicDataDao.class );
            init( d );
        } catch( ContextException e ) {
            // 忽略
        }
    }
    
    public static void init( BasicDataDao basicDataDao ) {
        if( ! initialized && basicDataDao != null ) {
            dao = basicDataDao;
            List<BasicData> allData = dao.selectAllBasicData();
            for( BasicData data : allData ) {
                addToCache( data );
            }
            initialized = true;
        }
    }
    
    public static BasicData getDataFromName( String name ) {
        return getDataFromName( name, allBasicDataCache.values() );
    }
    
    public static BasicData getDataFromName( String name, String... typeIds ) {
        for( String typeId : typeIds ) {
            if( typedBasicDataCache.containsKey( typeId ) ) {
                BasicData data = getDataFromName( name, typedBasicDataCache.get( typeId ).values() );
                if( data != null ) {
                    return data;
                }
            }
        }
        return null;
    }
    
    private static BasicData getDataFromName( String name, Collection<BasicData> data ) {
        for( BasicData d : data ) {
            if( d.getName().equals( name ) ) {
                return d;
            }
        }
        return null;
    }
    
    public static BasicData getData( String id ) {
       if( allBasicDataCache.containsKey( id ) ) {
           return allBasicDataCache.get( id );
       } else {
           BasicData d = dao.selectBasicDataById( id );
           if( d != null ) {
               addToCache( d );
               return d;
           } else {
               return new BasicData();
           }
       }
    }
    
    public static void updateData( String id ) {
        BasicData d = dao.selectBasicDataById( id );
        if( d != null ) {
            // 更新
            addToCache( d );
        } else {
            // 删除
            BasicData data = allBasicDataCache.remove( id );
            if( data != null ) {
                String typeId = data.getType().getId();
                if( typedBasicDataCache.containsKey( typeId ) ) {
                    typedBasicDataCache.get( typeId ).remove( id );
                }
            }
        }
    }
    
    public static void clearCache() {
        clearCache( null );
    }
    
    public static void clearCache( String typeId ) {
        if( typeId != null ) {
            Map<String, BasicData> typedData = typedBasicDataCache.remove( typeId );
            for( String key : typedData.keySet() ) {
                allBasicDataCache.remove( key );
            }
        } else {
            typedBasicDataCache.clear();
            allBasicDataCache.clear();
        }
    }
    
    private static void addToCache( BasicData data ) {
        String id = data.getId();
        allBasicDataCache.put( id, data );
        String typeId = data.getType().getId();
        if( typedBasicDataCache.containsKey( typeId ) ) {
            typedBasicDataCache.get( typeId ).put( id, data );
        } else {
            Map<String, BasicData> map = new HashMap<String, BasicData>();
            map.put( id, data );
            typedBasicDataCache.put( typeId, map );
        }
    }
    
    
}
