package ua.nure.kn155.Loboda.db;

import java.sql.SQLException;

public class DatabaseException extends Exception {

  public DatabaseException() {
  }
  
  public DatabaseException(SQLException e) {
    super(e);
  }

}
