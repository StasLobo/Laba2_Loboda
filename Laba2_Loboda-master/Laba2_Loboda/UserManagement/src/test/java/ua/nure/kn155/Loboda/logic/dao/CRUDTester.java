package ua.nure.kn155.Loboda.logic.dao;

/**
 * 
 * @author Gleb Basic interface for CRUD DB Operations
 */
public interface CRUDTester {

  void testCreate() throws Exception;

  void testDelete() throws Exception;

  void testUpdate() throws Exception;

  void testGetAll() throws Exception;

  void testGetByKey() throws Exception;

}
