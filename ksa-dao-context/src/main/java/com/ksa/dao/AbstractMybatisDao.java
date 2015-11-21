package com.ksa.dao;

import org.apache.ibatis.session.SqlSession;

/**
 * 基于 Mybatis 的抽象 Dao 基类。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public abstract class AbstractMybatisDao {
    
    /** Mybaits SqlSession */
    protected SqlSession session;

    public SqlSession getSqlSession() {
        return session;
    }

    public void setSqlSession( SqlSession session ) {
        this.session = session;
    }
}
