package com.bms.bms.service;


import com.bms.bms.dao.UserDao;
import com.bms.bms.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {

    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> registerUser(User user) {


        // Hash password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        user.setPassword(hashedPassword);

        // Save user in the database
        return Optional.of(userDao.save(user));
    }

    public Optional<User> loginUser(User user) {
        // Find user by email or phone
        Optional<User> userOpt = userDao.findByEmail(user.getEmail())
                .or(() -> userDao.findByPhone(user.getPhone()));

        if (userOpt.isEmpty() || !BCrypt.checkpw(user.getPassword(), userOpt.get().getPassword())) {
            return Optional.empty();  // Invalid login
        }

        return userOpt;  // Return the authenticated user
    }
}