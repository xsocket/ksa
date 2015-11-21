package com.ksa.web.wro4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.isdc.wro.model.WroModel;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.factory.XmlModelFactory;

/**
 * WroModelFactory 的具体实现。基于多个 xml 文件来创建 WroModel 对象，每个 xml 配置文件均配置了各自的 groups。
 * <p>
 * WroModelFactory 创建 WroModel 所需的多个 xml 配置文件是从 WroModelConfigurationCache 中获取的。 也就是说在创建 WroModel 前，所有配置文件应该均已获取并存放在
 * WroModelConfigurationCache 中。
 * <p>
 * <b>注意：</b>目前的解析方式无法解读 xml 配置之间的依赖关系，因而所提供的 xml 配置文件应该是项目独立的。
 * 
 * @author 麻文强
 * 
 * @since v0.0.1
 * 
 * @see <code>com.bjsasc.probe.extension.wro4j.WroModelConfigurationCache</code>
 */
public class MultiXmlWroModelFactory implements WroModelFactory {

    private static final Logger log = LoggerFactory.getLogger( MultiXmlWroModelFactory.class );

    private String configurationCacheKey;

    /**
     * 使用 WroModelConfigurationCache 默认实例来获取 WroModel 配置文件并解析。
     */
    public MultiXmlWroModelFactory() {
        this( WroModelConfigurationCache.DEFAULT_CACHE_KEY );
    }

    /**
     * 使用指定的 WroModelConfigurationCache 实例来获取 WroModel 配置文件并解析。
     * 
     * @param configurationCacheKey
     *        存放配置文件的 WroModelConfigurationCache 实例名称。
     */
    public MultiXmlWroModelFactory( String configurationCacheKey ) {
        this.configurationCacheKey = configurationCacheKey;
    }

    @Override
    public WroModel create() {
        WroModel finalModel = new WroModel();
        Collection<URL> configs = WroModelConfigurationCache.get( configurationCacheKey ).getConfigurations();
        for( URL url : configs ) {
            try {
                WroModel model = getParseProxy( url ).create();
                finalModel.merge( model );
            } catch( Throwable e ) {
                log.warn( "Fail to parse wro4j configuration '" + url + "'.", e );
            }
            log.debug( "Parsed wro4j configuration '{}'.", url );
        }
        return finalModel;
    }

    @Override
    public void destroy() {

    }

    private XmlModelFactory getParseProxy( final URL wroXMLUrl ) {
        return new XmlModelFactory() {

            @Override
            protected InputStream getModelResourceAsStream() throws IOException {
                return wroXMLUrl.openStream();
            }
        };
    }
}
