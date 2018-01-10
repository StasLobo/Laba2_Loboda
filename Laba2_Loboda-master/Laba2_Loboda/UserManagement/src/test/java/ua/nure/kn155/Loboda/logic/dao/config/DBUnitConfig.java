package ua.nure.kn155.Loboda.logic.dao.config;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.builder.DataRowBuilder;
import org.dbunit.dataset.builder.DataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import ua.nure.kn155.cherepukhin.db.ConnectionManager;
import ua.nure.kn155.cherepukhin.db.IConnectionManager;

public abstract class DBUnitConfig<E> extends DBTestCase {

  protected DataSetBuilder dsBuilder;
  protected IDatabaseTester tester;
  protected static Properties properties;
  protected IDataSet beforeData;
  protected static IConnectionManager connectionManager;

  static {
    properties = new Properties();
    try {
      properties.load(Thread.currentThread().getContextClassLoader()
          .getResourceAsStream("db.config.properties"));
      Class.forName("org.h2.Driver");
      System.out.println("DRIVER REGISTRED!");
      connectionManager = ConnectionManager.getInstance(properties.getProperty("db.url"),
          properties.getProperty("db.username"), properties.getProperty("db.password"),
          properties.getProperty("db.driver"));

      Connection connection = connectionManager.getConnection();
      Statement statement = connection.createStatement();
      statement.execute(
          "CREATE TABLE USER (id BIGINT IDENTITY, first_name VARCHAR NOT NULL,last_name VARCHAR NOT NULL, date_birth DATE NOT NULL)");
      statement.close();
      connection.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  @Before
  public void setUp() throws Exception {
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
        properties.getProperty("db.driver"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
        properties.getProperty("db.url"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
        properties.getProperty("db.username"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
        properties.getProperty("db.password"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "");
    dsBuilder = new DataSetBuilder();
    tester = new JdbcDatabaseTester(
        properties.getProperty("db.driver"),
        properties.getProperty("db.url"),
        properties.getProperty("db.username"),
        properties.getProperty("db.password"));

    /*
     * try (Connection c = DriverManager.getConnection(properties.getProperty("db.url"),
     * properties.getProperty("db.username"), properties.getProperty("db.password"))) {
     * c.createStatement().execute("SET REFERENTIAL_INTEGRITY FALSE;"); }
     */
  }

  public DBUnitConfig(String name) {
    super(name);
  }

  @Override
  protected IDataSet getDataSet() throws Exception {
    return beforeData;
  }

  @Override
  protected DatabaseOperation getTearDownOperation() throws Exception {
    return DatabaseOperation.DELETE_ALL;
  }

  @After
  public void clearDown() throws Exception {
    /*
     * try (Connection c = DriverManager.getConnection(properties.getProperty("db.url"),
     * properties.getProperty("db.username"), properties.getProperty("db.password"))) {
     * c.createStatement().execute("SET REFERENTIAL_INTEGRITY TRUE;"); }
     */
    
  }

  public IDataSet buildDataSetOfList(List<E> list, Class<E> clazz) throws DataSetException {
    List<Field> fields = getFields(clazz);

    list.forEach(entity -> {
      DataRowBuilder drBuilder = dsBuilder.newRow(clazz.getSimpleName());
      fields.forEach(field -> {
        field.setAccessible(true);
        try {
          drBuilder.with(properties.getProperty(field.getName()), field.get(entity));
        } catch (IllegalArgumentException | IllegalAccessException e) {
          e.printStackTrace();
          throw new RuntimeException(e);
        }
      });
      try {
        drBuilder.add();
      } catch (DataSetException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    });
    return dsBuilder.build();
  }

  private List<Field> getFields(Class<E> clazz) {
    return Arrays.asList((clazz.getDeclaredFields()));
  }
}
