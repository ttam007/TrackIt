package trackit.DAL;

import java.util.*;
import java.sql.*;

/**
 * All the methods needed for an object to interact with the database.
 *
 * @param <T> The object in question.
 */
public interface ISQLHelper<T> {

    public ArrayList<T> selectAll()
            throws SQLException;

    public ArrayList<T> selectOne(Integer primaryKey)
            throws SQLException;

    public void insertAll(List<T> aList)
            throws SQLException;

    public void insert(T anObject)
            throws SQLException;

    public void updateAll(List<T> aList)
            throws SQLException;

    public void update(T anObject)
            throws SQLException;

    public void deleteAll(List<Integer> primaryKeys)
            throws SQLException;

    public void delete(Integer primaryKey)
            throws SQLException;
}
