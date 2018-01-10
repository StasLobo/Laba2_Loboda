package ua.nure.kn155.Loboda.logic.dao;

import java.io.Serializable;
import java.util.List;

import ua.nure.kn155.cherepukhin.db.DatabaseException;
import ua.nure.kn155.cherepukhin.logic.bean.User;

/**
 * 
 * @author Gleb
 *
 * @param <K> representing the Key type ind database table. Compund keys not supported(!!!)
 * @param <E> representing the java type for entity - DTO(Data Transfer Object)
 */
public interface AbstractDAO<K extends Serializable, E> {

  /**
   * 
   * @return list of entities from table {@code [ENTITY_NAME]}
   * @throws DatabaseException if something went wrong
   */
  public abstract List<E> getAll() throws DatabaseException;

  /**
   * 
   * @param id as primary key in database table
   * @return fetched entity, otherwise null
   * @throws DatabaseException if something went wrong
   */
  public abstract E getById(K id) throws DatabaseException;

  /**
   * 
   * @param entity to add in DB, see impl package for further info id must be null.
   * @return user with assigned id
   * @exception DatabaseException if any trouble appears during operation
   */
  public abstract User create(E entity) throws DatabaseException;

  /**
   * 
   * @param entity to update in DB, entity should be persistent i.e. exist in database
   * @return true if entity was updated
   * @throws DatabaseException if something went wrong: Connection was lost, entity doesn't exist
   */
  public abstract boolean update(E entity) throws DatabaseException;

  /**
   * 
   * @param entity to delete. Generally its's necessary for the entity object to have an id value
   * @return boolean result whether the operation was successful or not
   * @throws DatabaseException if something went wrong: Connection was lost, entity doesn't exist
   */
  public abstract boolean delete(E entity) throws DatabaseException;

}
