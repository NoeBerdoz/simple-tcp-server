package ch.nb.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Mapper<T> {

    Optional<T> selectById(Long id) throws SQLException;

    List<T> selectAll() throws SQLException;

    void insert(T t) throws SQLException;

    T mapRowToObject(ResultSet resultSet) throws SQLException;
}
