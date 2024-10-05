package com.bms.bms.service;


import com.bms.bms.dao.UserDao;
import com.bms.bms.dao.UserDaoImpl;
import com.bms.bms.model.User;
import com.bms.bms.utils.DataBaseUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {

    private final UserDao userDao = new UserDaoImpl();

    public Optional<User> registerUser(User user) {


        // Hash password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        user.setPassword(hashedPassword);

        // Save user in the database
        return Optional.of(userDao.save(user));
    }

    public Optional<User> loginUser(User user) {
        // Find user by email or phone


    }
}