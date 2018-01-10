package ua.nure.kn155.Loboda.logic.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.logic.bean.User;
import ua.nure.kn155.cherepukhin.logic.dao.config.DBUnitConfig;
import ua.nure.kn155.cherepukhin.logic.dao.impl.H2UserDAO;

public class H2UserDAOEmptyDBTest extends DBUnitConfig<User> {

  public H2UserDAO userDAO;

  public H2UserDAOEmptyDBTest(String name) {
    super(name);
    userDAO = new H2UserDAO(connectionManager);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    beforeData = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("entities/user-data-empty.xml"));
    tester.setDataSet(beforeData);
    tester.onSetup();
  }

  @Test
  public void testSelectAllOnEmptyTable() throws Exception {
    List<User> fetchedUsers = userDAO.getAll();
    assertNotNull(fetchedUsers);
    assertEquals(0, fetchedUsers.size());
  }

  @Test
  public void testSelectByKeyOnEmptyTable() throws Exception {
    // given-when-then
    try {
      User fetchedUser = userDAO.getById(999L);
      fail();
    } catch (DatabaseException e) {
    }
  }
  
  public void testUpdateOnEmptyTable() throws Exception{
 // given
    User userToUpdate = new User();
    userToUpdate.setId(999L);
    userToUpdate.setFirstName("aaa");
    userToUpdate.setLastName("bbb");
    userToUpdate.setDateBirth(new SimpleDateFormat("dd.MM.yyyy").parse("18.09.2015"));

    IDataSet expectedData = new FlatXmlDataSetBuilder().build(Thread.currentThread()
        .getContextClassLoader().getResourceAsStream("entities/user-data-empty.xml"));

    // when
    boolean resultOfupdateOperation = userDAO.update(userToUpdate);

    // then
    assertEquals(false, resultOfupdateOperation);
    IDataSet actualData = tester.getConnection().createDataSet();
    Assertion.assertEquals(expectedData, actualData);
  }

}
