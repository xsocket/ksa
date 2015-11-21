package com.ksa.web.wro4j;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.ksa.util.ResourceUtils;
import com.ksa.util.StringUtils;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.config.Context;
import ro.isdc.wro.config.WroConfigurationChangeListener;
import ro.isdc.wro.config.factory.PropertiesAndFilterConfigWroConfigurationFactory;
import ro.isdc.wro.config.jmx.WroConfiguration;
import ro.isdc.wro.http.HttpHeader;
import ro.isdc.wro.manager.CacheChangeCallbackAware;
import ro.isdc.wro.manager.WroManagerFactory;
import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.group.DefaultGroupExtractor;
import ro.isdc.wro.model.group.GroupExtractor;
import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.processor.factory.ConfigurableProcessorsFactory;
import ro.isdc.wro.util.ObjectFactory;
import ro.isdc.wro.util.WroUtil;


public class WroFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger( WroFilter.class );

    // 设置 headers 时，必须返回英文，所以 Locale 要设置为 English
    private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance( "E, dd MMM yyyy HH:mm:ss z",
            TimeZone.getTimeZone( "GMT" ), Locale.ENGLISH );
    
    public static final String DEFAULT_WRO_CONFIG = "/WEB-INF/wro.xml";
    
    public static final String DEFAULT_WRO_CONFIG_PATTERN = "classpath*:wro.xml";

    public static final String DEFAULT_PRE_PROCESSORS = "cssImport,cssUrlRewriting,semicolonAppender";

    public static final String DEFAULT_POST_PROCESSORS = "cssMinJawr,jsMin";

    /**
     * Default value used by Cache-control header.
     */
    private static final String DEFAULT_CACHE_CONTROL_VALUE = "public, max-age=315360000";

    /**
     * wro API mapping path. If request uri contains this, exposed API method will be invoked.
     */
    public static final String PATH_API = "wroAPI";

    /**
     * API - reload cache method call
     */
    public static final String API_RELOAD_CACHE = PATH_API + "/reloadCache";

    /**
     * API - reload model method call
     */
    public static final String API_RELOAD_MODEL = PATH_API + "/reloadModel";

    /**
     * Filter config.
     */
    protected FilterConfig filterConfig;

    /**
     * Wro configuration.
     */
    protected WroConfiguration wroConfiguration;

    /**
     * WroManagerFactory. The brain of the optimizer.
     */
    protected WroManagerFactory wroManagerFactory;

    /**
     * Map containing header values used to control caching. The keys from this values are trimmed and lower-cased when put, in
     * order to avoid duplicate keys. This is done, because according to RFC 2616 Message Headers field names are
     * case-insensitive.
     */
    @SuppressWarnings( "serial" )
    protected final Map<String, String> headersMap = new LinkedHashMap<String, String>() {

        @Override
        public String put( final String key, final String value ) {
            return super.put( key.trim().toLowerCase(), value );
        }

        @Override
        public String get( final Object key ) {
            return super.get( ( (String)key ).toLowerCase() );
        }
    };

    /**
     * {@inheritDoc}
     */
    public final void init( final FilterConfig config )
            throws ServletException {
        this.filterConfig = config;
        wroConfiguration = newWroConfigurationFactory().create();
        initWroManagerFactory();
        initHeaderValues();
        registerChangeListeners();
        doInit( config );
    }

    /**
     * @return implementation of {@link WroConfigurationFactory} used to create a {@link WroConfiguration} object.
     */
    protected ObjectFactory<WroConfiguration> newWroConfigurationFactory() {
        return new PropertiesAndFilterConfigWroConfigurationFactory( filterConfig );
    }

    /**
     * Initialize {@link WroManagerFactory}.
     */
    private void initWroManagerFactory() {
        this.wroManagerFactory = getWroManagerFactory();
        if( wroManagerFactory instanceof CacheChangeCallbackAware ) {
            // register cache change callback -> when cache is changed, update headers values.
            ( (CacheChangeCallbackAware)wroManagerFactory ).registerCallback( new PropertyChangeListener() {

                public void propertyChange( final PropertyChangeEvent evt ) {
                    // update header values
                    initHeaderValues();
                }
            } );
        }
    }

    /**
     * Register property change listeners.
     */
    private void registerChangeListeners() {
        wroConfiguration.registerCacheUpdatePeriodChangeListener( new PropertyChangeListener() {

            public void propertyChange( final PropertyChangeEvent event ) {
                // reset cache headers when any property is changed in order to avoid browser caching
                initHeaderValues();
                if( wroManagerFactory instanceof WroConfigurationChangeListener ) {
                    ( (WroConfigurationChangeListener)wroManagerFactory ).onCachePeriodChanged();
                }
            }
        } );
        wroConfiguration.registerModelUpdatePeriodChangeListener( new PropertyChangeListener() {

            public void propertyChange( final PropertyChangeEvent event ) {
                initHeaderValues();
                if( wroManagerFactory instanceof WroConfigurationChangeListener ) {
                    ( (WroConfigurationChangeListener)wroManagerFactory ).onModelPeriodChanged();
                }
            }
        } );
        log.debug( "Cache & Model change listeners were registered" );
    }

    /**
     * Initialize header values.
     */
    private void initHeaderValues() {
        // put defaults
        if( !wroConfiguration.isDebug() ) {
            final Long timestamp = new Date().getTime();
            final Calendar cal = Calendar.getInstance();
            cal.roll( Calendar.YEAR, 1 );
            headersMap.put( HttpHeader.CACHE_CONTROL.toString(), DEFAULT_CACHE_CONTROL_VALUE );
            headersMap.put( HttpHeader.LAST_MODIFIED.toString(), DATE_FORMAT.format( timestamp ) );
            headersMap.put( HttpHeader.EXPIRES.toString(), DATE_FORMAT.format( cal.getTimeInMillis() ) );
        }
        final String headerParam = wroConfiguration.getHeader();
        if( StringUtils.hasLength( headerParam ) ) {
            try {
                if( headerParam.contains( "|" ) ) {
                    final String[] headers = headerParam.split( "[|]" );
                    for( final String header : headers ) {
                        parseHeader( header );
                    }
                } else {
                    parseHeader( headerParam );
                }
            } catch( final Exception e ) {
                throw new WroRuntimeException( "Invalid header init-param value: " + headerParam
                        + ". A correct value should have the following format: "
                        + "<HEADER_NAME1>: <VALUE1> | <HEADER_NAME2>: <VALUE2>. " + "Ex: <look like this: "
                        + "Expires: Thu, 15 Apr 2010 20:00:00 GMT | cache-control: public", e );
            }
        }
        log.debug( "Header Values: " + headersMap );
    }

    /**
     * Parse header value & puts the found values in headersMap field.
     * 
     * @param header
     *        value to parse.
     */
    private void parseHeader( final String header ) {
        log.debug( "parseHeader: " + header );
        final String headerName = header.substring( 0, header.indexOf( ":" ) );
        if( !headersMap.containsKey( headerName ) ) {
            headersMap.put( headerName, header.substring( header.indexOf( ":" ) + 1 ) );
        }
    }

    protected void doInit( FilterConfig config ) throws ServletException {
        // 设置自定义的默认配置
        if( wroConfiguration.isDebug() ) {
            wroConfiguration.setDisableCache( true ); // debug 模式下不进行缓存
        } else {
            wroConfiguration.setDisableCache( false ); // 正式环境下 必须缓存
        }
    }

    /**
     * {@inheritDoc}
     */
    public final void doFilter( final ServletRequest req, final ServletResponse res, final FilterChain chain )
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest)req;
        final HttpServletResponse response = (HttpServletResponse)res;
        try {
            // add request, response & servletContext to thread local
            Context.set( Context.webContext( request, response, filterConfig ), wroConfiguration );

            if( shouldReloadCache( request ) ) {
                Context.get().getConfig().reloadCache();
                WroUtil.addNoCacheHeaders( response );
                // set explicitly status OK for unit testing
                response.setStatus( HttpServletResponse.SC_OK );
            } else if( shouldReloadModel( request ) ) {
                Context.get().getConfig().reloadModel();
                WroUtil.addNoCacheHeaders( response );
                // set explicitly status OK for unit testing
                response.setStatus( HttpServletResponse.SC_OK );
            } else {
                processRequest( request, response );
                onRequestProcessed();
            }
        } catch( final RuntimeException e ) {
            onRuntimeException( e, response, chain );
        } finally {
            Context.unset();
        }
    }

    /**
     * Useful for unit tests to check the post processing.
     */
    protected void onRequestProcessed() {}

    /**
     * @return true if reload model must be triggered.
     */
    private boolean shouldReloadModel( final HttpServletRequest request ) {
        return Context.get().getConfig().isDebug() && matchesUrl( request, API_RELOAD_MODEL );
    }

    /**
     * @return true if reload cache must be triggered.
     */
    private boolean shouldReloadCache( final HttpServletRequest request ) {
        return Context.get().getConfig().isDebug() && matchesUrl( request, API_RELOAD_CACHE );
    }

    /**
     * Check if the request path matches the provided api path.
     */
    private boolean matchesUrl( final HttpServletRequest request, final String apiPath ) {
        final Pattern pattern = Pattern.compile( ".*" + apiPath + "[/]?", Pattern.CASE_INSENSITIVE );
        final Matcher m = pattern.matcher( request.getRequestURI() );
        return m.matches();
    }

    /**
     * Perform actual processing.
     */
    private void processRequest( final HttpServletRequest request, final HttpServletResponse response )
            throws ServletException, IOException {
        setResponseHeaders( response );
        // process the uri using manager
        wroManagerFactory.create().process();
    }

    /**
     * Invoked when a {@link RuntimeException} is thrown. Allows custom exception handling. The default implementation redirects
     * to 404 for a specific {@link WroRuntimeException} exception when in DEPLOYMENT mode.
     * 
     * @param e
     *        {@link RuntimeException} thrown during request processing.
     */
    protected void onRuntimeException( final RuntimeException e, final HttpServletResponse response,
            final FilterChain chain ) {
        log.debug( "RuntimeException occured", e );
        try {
            log.debug( "Cannot process. Proceeding with chain execution." );
            chain.doFilter( Context.get().getRequest(), response );
        } catch( final Exception ex ) {
            // should never happen
            log.error( "Error while chaining the request: " + HttpServletResponse.SC_NOT_FOUND );
        }
    }

    /**
     * Method called for each request and responsible for setting response headers, used mostly for cache control. Override this
     * method if you want to change the way headers are set.<br>
     * 
     * @param response
     *        {@link HttpServletResponse} object.
     */
    protected void setResponseHeaders( final HttpServletResponse response ) {
        // prevent caching when in development mode
        if( wroConfiguration.isDebug() ) {
            WroUtil.addNoCacheHeaders( response );
        } else {
            // Force resource caching as best as possible
            for( final Map.Entry<String, String> entry : headersMap.entrySet() ) {
                response.setHeader( entry.getKey(), entry.getValue() );
            }
        }
    }

    /**
     * Factory method for {@link WroManagerFactory}. Override this method, in order to change the way filter use factory.
     * 
     * @return {@link WroManagerFactory} object.
     */
    protected WroManagerFactory getWroManagerFactory() {
        if( !StringUtils.hasLength( wroConfiguration.getWroManagerClassName() ) ) {
            // If no context param was specified we return the default factory
            return newWroManagerFactory();
        } else {
            // Try to find the specified factory class
            Class<?> factoryClass = null;
            try {
                factoryClass = Thread.currentThread().getContextClassLoader().loadClass(
                        wroConfiguration.getWroManagerClassName() );
                // Instantiate the factory
                return (WroManagerFactory)factoryClass.newInstance();
            } catch( final Exception e ) {
                throw new WroRuntimeException( "Exception while loading WroManagerFactory class", e );
            }
        }
    }

    protected WroManagerFactory newWroManagerFactory() {
        return new ConfigurableWroManagerFactory() {

            @Override
            protected GroupExtractor newGroupExtractor() {
                return new DefaultGroupExtractor() {

                    // 改变原有压缩的逻辑
                    // 当debug == true 时，不压缩
                    // 当 request 的参数 minimize=false 时，不压缩
                    @Override
                    public boolean isMinimized( HttpServletRequest request ) {
                        if( request == null ) {
                            throw new IllegalArgumentException( "Request cannot be NULL!" );
                        }
                        final String minimizeAsString = request.getParameter( PARAM_MINIMIZE );
                        if( Context.get().getConfig().isDebug() ) {
                            return false; // debug 模式不压缩
                        }
                        // request 的参数 minimize=false 时，不压缩
                        return !( "false".equalsIgnoreCase( minimizeAsString ) );
                    }
                    
                    @Override
                    public String getGroupName( HttpServletRequest request ) {
                        return super.getGroupName( new HttpServletRequestWrapper( request ) );
                    }
                    
                    @Override
                    public ResourceType getResourceType( HttpServletRequest request ) {
                        return super.getResourceType( new HttpServletRequestWrapper( request ) );
                    }
                    
                };
            }

            @Override
            protected WroModelFactory newModelFactory() {
                final String KEY = "__probe_default_wro_config__";
                WroModelConfigurationCache cache = WroModelConfigurationCache.get( KEY );
                try {
                    final ServletContext servletContext = Context.get().getServletContext();
                    //Don't allow NPE, throw a more detailed exception
                    if ( servletContext != null ) {
                        URL defaultWro = servletContext.getResource( DEFAULT_WRO_CONFIG );
                        if( defaultWro != null ) {
                            log.debug( "Load default wro config resource '{}'.", DEFAULT_WRO_CONFIG );
                            cache.add( defaultWro );
                        }
                    }
                    Resource[] resources = ResourceUtils.getResources( DEFAULT_WRO_CONFIG_PATTERN );
                    if( resources != null ) {
                        log.debug( "Load '{}' wro config resources ( '{}' ) in each bundles.", resources.length, DEFAULT_WRO_CONFIG_PATTERN );
                        for( Resource resource : resources ) {
                            cache.add( resource.getURL() );
                        }
                    }
                    return new MultiXmlWroModelFactory( KEY );
                } catch( IOException e ) {
                    log.warn( "Fail to load wro.xml config files.", e );
                    return super.newModelFactory();
                }
            }

            @Override
            protected Properties newConfigProperties() {
                // default location is /WEB-INF/wro.properties
                final Properties props = new Properties();
                // 加入自定义的默认处理器
                props.put( ConfigurableProcessorsFactory.PARAM_PRE_PROCESSORS, DEFAULT_PRE_PROCESSORS );
                props.put( ConfigurableProcessorsFactory.PARAM_POST_PROCESSORS, DEFAULT_POST_PROCESSORS );
                try {
                    props.load( PropertiesAndFilterConfigWroConfigurationFactory
                            .defaultConfigPropertyStream( Context.get().getFilterConfig() ) );

                } catch( final Exception e ) {
                    log.debug( "No configuration property file found." );
                }
                return props;
            }
        };
    }

    /**
     * @return the {@link WroConfiguration} associated with this filter instance.
     */
    public final WroConfiguration getWroConfiguration() {
        return this.wroConfiguration;
    }

    /**
     * {@inheritDoc}
     */
    public void destroy() {
        wroConfiguration.destroy();
        Context.destroy();
        wroManagerFactory.destroy();
    }
}
