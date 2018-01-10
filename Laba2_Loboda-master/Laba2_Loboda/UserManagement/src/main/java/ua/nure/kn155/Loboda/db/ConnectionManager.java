package ua.nure.kn155.Loboda.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.Server;

public class ConnectionManager implements IConnectionManager {

  private static IConnectionManager manager;
  private String url;
  private String user;
  private String password;
  private String driverName;
  private static Server server;

  private ConnectionManager(String url, String user, String password, String driverName) {
    this.url = url;
    this.user = user;
    this.password = password;
    this.driverName = driverName;
  }

  /**
   * 
   * @param url of database
   * @param user: name of user for database
   * @param password; password to database
   * @param driverName: full  JDBCDriver class name
   * @return current Implementation of connection manager
   */
  public static IConnectionManager getInstance(String url, String user, String password,
      String driverName) {
    if (manager == null) {
      try {
        Class.forName(driverName);
        server = Server.createTcpServer().start();
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      manager = new ConnectionManager(url, user, password, driverName);
    }
    return manager;
  }

  /**
   * Short procedure of obtaining connection manager
   * @return instance(associated with database) or(!!!) {@code null} if no instance present
   */
  public static IConnectionManager getInstance() {
    return manager;
  }

  @Override
  public Connection getConnection() throws DatabaseException {
    try {
      return DriverManager.getConnection(url, user, password);
    } catch (SQLException ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public void close() throws Exception {
    server.stop();
    manager = null;
  }
}
