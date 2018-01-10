package ua.nure.kn155.Loboda.db;

import java.sql.Connection;


public interface IConnectionManager extends AutoCloseable{

  Connection getConnection() throws DatabaseException;

}
