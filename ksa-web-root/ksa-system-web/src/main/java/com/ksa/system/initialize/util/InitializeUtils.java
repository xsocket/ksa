package com.ksa.system.initialize.util;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;

import com.ksa.system.initialize.convert.BusinessConverter;
import com.ksa.system.initialize.convert.PartnerConverter;
import com.ksa.system.initialize.convert.UserConverter;
import com.ksa.util.ResourceUtils;

public class InitializeUtils {

    public static void convertDatabase( DataSource ds ) throws Exception {
        SqlSession session = getSqlSession( ds );
        
        // 执行用户数据的迁移
        UserConverter.doConvert( session );

        // 执行客户数据的迁移
        PartnerConverter.doConvert( session );
        
        // 执行业务数据的迁移（业务数据：托单+费用+结算单）
        BusinessConverter.doConvert( session );
        
    }

    private static SqlSession getSqlSession( DataSource ds ) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource( ds );
        factory.setMapperLocations( ResourceUtils.getResources( "classpath*:mybatis/initialize/*.xml" ) );
        return new SqlSessionTemplate( factory.getObject() );
    }

}
