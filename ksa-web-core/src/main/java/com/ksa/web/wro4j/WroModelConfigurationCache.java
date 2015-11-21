package com.ksa.web.wro4j;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Wro4j 配置文件缓存，缓存配置文件的 URL 地址。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public class WroModelConfigurationCache {

    protected static final Map<String, WroModelConfigurationCache> CACHE = new HashMap<String, WroModelConfigurationCache>();

    public static final String DEFAULT_CACHE_KEY = "com.bjsasc.probe.extension.wro4j";

    public static WroModelConfigurationCache get( String key ) {
        if( key == null ) {
            key = DEFAULT_CACHE_KEY;
        }
        if( !CACHE.containsKey( key ) ) {
            CACHE.put( key, new WroModelConfigurationCache() );
        }
        return CACHE.get( key );
    }
    
    public static WroModelConfigurationCache get() {
        return get( DEFAULT_CACHE_KEY );
    }
    
    private Collection<URL> configurations = new HashSet<URL>();
    
    public boolean add( URL url ) {
        return getConfigurations().add( url );
    }
    
    public boolean remove( URL url ) {
        return getConfigurations().remove( url );
    }
    
    public boolean contains( URL url ) {
        return configurations.contains( url );
    }

    public Collection<URL> getConfigurations() {
        return configurations;
    }

}
