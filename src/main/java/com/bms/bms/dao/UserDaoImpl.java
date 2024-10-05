package com.bms.bms.dao;

import com.bms.bms.model.User;
import com.bms.bms.utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private  final Connection connection;
    private final QueryBuilderUtil queryBuilderUtil = new QueryBuilderUtil();

    public UserDaoImpl() {
        this.connection = DataBaseUtil.getConnection();
    }

    @Override
    public User save(User user) {
        try {
            QueryResult queryResult = queryBuilderUtil.createInsertQuery("users", user);
            Long id = queryBuilderUtil.executeDynamicQuery(connection, queryResult, Long.class);
            user.setId(id);
            return user;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try{
            QueryResult queryResult = queryBuilderUtil.createSelectQuery("users", User.builder().id(id).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            User user = null;
            if(rs.next()){
                user = ResultSetMapper.mapResultSetToObject(rs, User.class);
                System.out.println("User "+user);
            }
            System.out.println("DOne "+user);
            return Optional.ofNullable(user);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User update(User entity) {

        try {
            QueryResult queryResult = queryBuilderUtil.createUpdateQuery("users", entity, "id", entity.getId());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
            return entity;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Long id) {

        try {
            QueryResult queryResult = queryBuilderUtil.createDeleteQuery("users", User.builder().id(id).build());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try{
            QueryResult queryResult = queryBuilderUtil.createSelectQuery("users", User.builder().email(email).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            User user = null;
            if(rs.next()){
                user = ResultSetMapper.mapResultSetToObject(rs, User.class);
            }
            return Optional.ofNullable(user);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        try{
            QueryResult queryResult = queryBuilderUtil.createSelectQuery("users", User.builder().phone(phone).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            User user = null;
            if(rs.next()){
                user = ResultSetMapper.mapResultSetToObject(rs, User.class);
            }
            return Optional.ofNullable(user);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
