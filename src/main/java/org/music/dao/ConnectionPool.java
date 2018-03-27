package org.music.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {

  private static ConnectionPool pool=null;
  private DataSource dataSource=null;
  private ConnectionPool(){
    try {
      InitialContext initialContext=new InitialContext();
      dataSource=(DataSource)initialContext.lookup("java:/comp/env/jogi");
    } catch (NamingException e) {
      e.printStackTrace();
    }

  }

  public static ConnectionPool getInstance(){
    if(pool==null){
      pool=new ConnectionPool();
    }
    return pool;
  }

  public Connection getConnection(){
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
