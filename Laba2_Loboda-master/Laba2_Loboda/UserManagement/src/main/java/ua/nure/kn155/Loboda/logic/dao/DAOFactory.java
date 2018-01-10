package ua.nure.kn155.Loboda.logic.dao;

import ua.nure.kn155.cherepukhin.db.IConnectionManager;
import ua.nure.kn155.cherepukhin.logic.dao.impl.H2DAOFactory;

public interface DAOFactory {

  public static final int H2_FACTORY = 0;

  static DAOFactory getFactory(int what, IConnectionManager connectionManager) {
    switch (what) {
      case H2_FACTORY: {
        return new H2DAOFactory(connectionManager);
      }
      default:
        return null;
    }
  }
  
  UserDAO getUserDAO();

}
