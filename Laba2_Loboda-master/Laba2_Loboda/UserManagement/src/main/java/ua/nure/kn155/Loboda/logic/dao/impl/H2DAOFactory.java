package ua.nure.kn155.Loboda.logic.dao.impl;

import ua.nure.kn155.cherepukhin.db.IConnectionManager;
import ua.nure.kn155.cherepukhin.logic.dao.DAOFactory;
import ua.nure.kn155.cherepukhin.logic.dao.UserDAO;

public class H2DAOFactory implements DAOFactory {

  private IConnectionManager connectionManager;

  public H2DAOFactory(IConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
  }

  @Override
  public UserDAO getUserDAO() {
    return null;
  }
}
