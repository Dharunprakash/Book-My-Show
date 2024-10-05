package com.bms.bms.utils;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.bms.bms.utils.QueryResult.toSnakeCase;

public class QueryBuilderUtil {

    /**
     * Creates an insert query for the given object.
     * By default, fields are excluded if they are null.
     *
     * @param tableName the name of the table
     * @param obj the object to insert
     * @return the query result containing the query string and parameters
     * @throws IllegalAccessException if the object's fields are not accessible
     */
    public QueryResult createInsertQuery(String tableName, Object obj) throws IllegalAccessException {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        List<Object> params = new ArrayList<>();

        buildColumnsAndValues(obj, columns, values, params);

        query.append(columns).append(") VALUES (").append(values).append(");");
        return new QueryResult(query.toString(), params);  // Return query and parameters
    }

    /**
     * Creates an update query for the given object.
     * By default, fields are excluded if they are null.
     *
     * @param tableName the name of the table
     * @param obj the object to update
     * @param conditionColumn the column used for the condition
     * @param conditionValue the value of the condition column
     * @param ignoreConditionColumn whether to ignore the condition column
     * @return the query result containing the query string and parameters
     * @throws IllegalAccessException if the object's fields are not accessible
     */
    public QueryResult createUpdateQuery(String tableName, Object obj, String conditionColumn, Object conditionValue, boolean ignoreConditionColumn) throws IllegalAccessException {
        StringBuilder setClause = new StringBuilder();
        StringBuilder query = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        List<Object> params = new ArrayList<>();

        if (ignoreConditionColumn) {
            ObjectManipulatorUtil.nullifyField(obj, conditionColumn);  // Nullify the condition column if ignoreConditionColumn is true
        }

        buildSetClause(obj, setClause, params);

        query.append(setClause).append(" WHERE ").append(conditionColumn).append(" = ?;");
        params.add(conditionValue);  // Add the condition value

        return new QueryResult(query.toString(), params);  // Return query and parameters
    }

    /**
     * Creates an update query for the given object.
     * By default, fields are excluded if they are null.
     *
     * @param tableName the name of the table
     * @param obj the object to update
     * @param conditionColumn the column used for the condition
     * @param conditionValue the value of the condition column
     * @return the query result containing the query string and parameters
     * @throws IllegalAccessException if the object's fields are not accessible
     */
    public QueryResult createUpdateQuery(String tableName, Object obj, String conditionColumn, Object conditionValue) throws IllegalAccessException {
        return createUpdateQuery(tableName, obj, conditionColumn, conditionValue, true);
    }

    /**
     * Creates a select query for the given object.
     * By default, fields are excluded if they are null.
     *
     * @param tableName the name of the table
     * @param obj the object to select
     * @return the query result containing the query string and parameters
     * @throws IllegalAccessException if the object's fields are not accessible
     */
    public QueryResult createSelectQuery(String tableName, Object obj) throws IllegalAccessException {
        StringBuilder whereClause = new StringBuilder("SELECT * FROM ").append(tableName).append(" WHERE ");
        List<Object> params = new ArrayList<>();
        boolean firstCondition = true;

        buildWhereClause(obj, whereClause, params, firstCondition);

        whereClause.append(";");
        return new QueryResult(whereClause.toString(), params);  // Return query and parameters
    }

    /**
     * Executes a dynamic query and returns the generated ID.
     *
     * @param connection the database connection
     * @param queryResult the query result containing the query string and parameters
     * @param idType the type of the generated ID
     * @param <T> the type of the generated ID
     * @return the generated ID
     * @throws SQLException if a database access error occurs
     */
    public <T> T executeDynamicQuery(Connection connection, QueryResult queryResult, Class<T> idType) throws SQLException {
        String query = queryResult.getQuery();
        List<Object> params = queryResult.getParameters();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setParameters(stmt, params);
            stmt.executeUpdate();
            System.out.printf("Execution successful: %s\n", query);
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Object generatedId = generatedKeys.getObject(1);
                    System.out.printf("Generated ID: %s\n and of type: %s\n", generatedId, generatedId.getClass());
                    if (idType.isInstance(generatedId)) {
                        System.out.printf("Generated ID is of the expected type: %s\n", idType);
                        return idType.cast(generatedId);  // Return the generated ID
                    }
                    switch (generatedId) {
                        case BigInteger bigInteger -> generatedId = bigInteger.longValue();
                        case BigDecimal bigDecimal -> generatedId = bigDecimal.doubleValue();
                        case Number number -> generatedId = number.longValue();
                        default -> {
                            throw new SQLException("Generated ID is not of the expected type.");
                        }
                    }
                    return idType.cast(generatedId);  // Return the generated ID
                }
                throw new SQLException("Creating entity failed, no ID obtained.");
            }
        }
    }

    /**
     * create dynamic delete query
     *
     * @param tableName
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public QueryResult createDeleteQuery(String tableName, Object obj) throws IllegalAccessException {
        StringBuilder whereClause = new StringBuilder("DELETE FROM ").append(tableName).append(" WHERE ");
        List<Object> params = new ArrayList<>();
        boolean firstCondition = true;

        buildWhereClause(obj, whereClause, params, firstCondition);


        whereClause.append(";");
        return new QueryResult(whereClause.toString(), params);  // Return query and parameters
    }




    /**
     * Executes a dynamic query.
     *
     * @param connection the database connection
     * @param queryResult the query result containing the query string and parameters
     * @throws SQLException if a database access error occurs
     */
    public void executeDynamicQuery(Connection connection, QueryResult queryResult) throws SQLException {
        String query = queryResult.getQuery();
        List<Object> params = queryResult.getParameters();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            stmt.executeUpdate();
        }
    }

    /**
     * Executes a dynamic select query and returns the result set.
     *
     * @param connection the database connection
     * @param queryResult the query result containing the query string and parameters
     * @return the result set of the query
     * @throws SQLException if a database access error occurs
     */
    public ResultSet executeDynamicSelectQuery(Connection connection, QueryResult queryResult) throws SQLException {
        String query = queryResult.getQuery();
        List<Object> params = queryResult.getParameters();

        PreparedStatement stmt = connection.prepareStatement(query);
        setParameters(stmt, params);
        System.out.println("Executing query: " + stmt);
        return stmt.executeQuery();
    }

    // Helper Methods to Modularize Code
    private void setParameters(PreparedStatement stmt, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            if (param instanceof Enum<?>) {
                stmt.setObject(i + 1, ((Enum<?>) param).name());
            } else {
                stmt.setObject(i + 1, param);  // Set parameters dynamically
            }
        }
    }

    private void buildColumnsAndValues(Object obj, StringBuilder columns, StringBuilder values, List<Object> params) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(obj);

            if (value != null) {  // Only include non-null fields
                if (columns.length() > 0) {
                    columns.append(", ");
                    values.append(", ");
                }
                columns.append(toSnakeCase(field.getName()));  // Convert to snake_case
                values.append("?");
                params.add(value);  // Add the parameter value
            }
        }
    }

    private void buildSetClause(Object obj, StringBuilder setClause, List<Object> params) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(obj);

            if (value != null) {  // Only include non-null fields
                if (setClause.length() > 0) {
                    setClause.append(", ");
                }
                setClause.append(toSnakeCase(field.getName())).append(" = ?");
                params.add(value);  // Add the parameter value
            }
        }
    }

    private void buildWhereClause(Object obj, StringBuilder whereClause, List<Object> params, boolean firstCondition) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(obj);

            if (value != null) {  // Only include non-null fields
                if (!firstCondition) {
                    whereClause.append(" AND ");
                }
                whereClause.append(toSnakeCase(field.getName())).append(" = ?");
                params.add(value);  // Add the parameter value
                firstCondition = false;
            }
        }
    }
}
