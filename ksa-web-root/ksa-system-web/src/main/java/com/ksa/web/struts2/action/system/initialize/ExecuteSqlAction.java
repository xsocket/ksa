package com.ksa.web.struts2.action.system.initialize;

import java.sql.Connection;

import org.apache.commons.dbcp.BasicDataSource;

import com.ksa.context.ServiceContextUtils;
import com.ksa.service.security.util.SecurityUtils;
import com.ksa.web.struts2.action.DefaultActionSupport;

public class ExecuteSqlAction extends DefaultActionSupport {

  private static final long serialVersionUID = -6928426403666068348L;
  private String sql;
  
  @Override
  protected String doExecute() throws Exception {
    
    if(!"administrator".equals(SecurityUtils.getCurrentUser().getId())) {
      return NO_PERMISSION;
    }
    
    BasicDataSource db = ServiceContextUtils.getService( BasicDataSource.class );
    Connection conn = db.getConnection();
    try {
      conn.createStatement().executeUpdate(sql);
    } catch(Exception ex) {
      conn.close();
      throw ex;
    }
    
    return NONE;
  }

  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }
}
