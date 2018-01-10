package ua.nure.kn155.Loboda.logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.h2.jdbc.JdbcSQLException;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.db.IConnectionManager;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.logic.dao.AbstractDAO;
import ua.nure.kn155.cherepukhin.logic.dao.UserDAO;

public class H2UserDAO implements UserDAO {

  private static final String SELECT_ALL_QUERRY = "SELECT * FROM USER";
  private static final String SELECT_BY_KEY_QUERRY = "SELECT * FROM USER WHERE USER.id = ?";
  private static final String INSERT_QUERRY =
      "INSERT INTO USER(first_name,last_name,date_birth) VALUES(?,?,?)";
  private static final String UPDATE_QUERRY =
      "UPDATE USER SET first_name = ?, last_name = ?,date_birth = ? WHERE USER.id = ?";
  private static final String DELETE_QUERRY = "DELETE FROM USER WHERE USER.id = ?";
  private IConnectionManager connectionManager;

  public H2UserDAO(IConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
  }

  @Override
  public List<User> getAll() throws DatabaseException {
    try (Connection connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERRY)) {
      List<User> resultList = new ArrayList<>(resultSet.getFetchSize());
      while (resultSet.next()) {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setDateBirth(resultSet.getDate("date_birth"));
        resultList.add(user);
      }
      return resultList;
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  @Override
  public User getById(Long id) throws DatabaseException {
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_KEY_QUERRY)) {
      statement.setLong(1, id);
      ResultSet fetchedUser = statement.executeQuery();
      fetchedUser.next();
      User user = new User();
      user.setId(fetchedUser.getLong("id"));
      user.setFirstName(fetchedUser.getString("first_name"));
      user.setLastName(fetchedUser.getString("last_name"));
      user.setDateBirth(fetchedUser.getDate("date_birth"));
      return user;
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  @Override
  public User create(User entity) throws DatabaseException {
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement statement =
            connection.prepareStatement(INSERT_QUERRY, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, entity.getFirstName());
      statement.setString(2, entity.getLastName());
      statement.setDate(3, new java.sql.Date(entity.getDateBirth().getTime()));
      statement.execute();

      ResultSet keys = statement.getGeneratedKeys();
      if (keys.next()) {
        entity.setId(keys.getLong(1));
      } else {
        throw new SQLException("No rows affected for create querry!");
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
    return entity;
  }

  @Override
  public boolean update(User entity) throws DatabaseException {
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_QUERRY)) {
      statement.setString(1, entity.getFirstName());
      statement.setString(2, entity.getLastName());
      statement.setDate(3, new java.sql.Date(entity.getDateBirth().getTime()));
      statement.setLong(4, entity.getId());
      return statement.executeUpdate() != 0;
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  @Override
  public boolean delete(User entity) throws DatabaseException {
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_QUERRY)) {
      statement.setLong(1, entity.getId());
      return statement.executeUpdate() != 0;
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

}
