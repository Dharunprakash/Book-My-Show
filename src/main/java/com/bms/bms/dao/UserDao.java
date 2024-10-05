package com.bms.bms.dao;

import com.bms.bms.model.User;

import java.util.Optional;

public interface UserDao extends  GenericDao<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
}
