package ch.nb.database;

import ch.nb.utils.SimpleLogger;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Helper class for binding parameters to SQL prepared statements.
 * This class handles various data types and their specific binding to SQL queries.
 */
public class StatementHelper {
    /**
     * Binds the given parameters to a prepared statement.
     * This method dynamically binds multiple parameters of various types
     * to a prepared statement in the correct format.
     *
     * @param statement the prepared statement to bind parameters to
     * @param sqlParameters the parameters to bind, in the order they appear in the SQL query
     */
    public static void bindStatementParameters(PreparedStatement statement, Object... sqlParameters) {
        for (int i = 0; i < sqlParameters.length; i++) {
            Object parameter = sqlParameters[i];
            try {
                if (parameter instanceof String) {
                    statement.setString(i + 1, (String) parameter);
                } else if (parameter instanceof Long) {
                    statement.setLong(i + 1, (Long) parameter);
                } else if (parameter instanceof BigDecimal) {
                    statement.setBigDecimal(i + 1, (BigDecimal) parameter);
                } else if (parameter instanceof LocalDate) {
                    statement.setDate(i + 1, Date.valueOf((LocalDate) parameter));
                    // TODO Work in progress
                } else {
                    statement.setObject(i + 1, parameter);
                }
            } catch (SQLException e) {
                SimpleLogger.error("Error while binding statement: " + e.getMessage());
            }
        }
    }
}
