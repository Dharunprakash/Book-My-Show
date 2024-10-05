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
        Optional<User> userOpt = userDao.findByEmail(user.getEmail());
        if (userOpt.isEmpty()) {
            userOpt = userDao.findByPhone(user.getPhone());
        }

        if (userOpt.isPresent()) {
            User foundUser = userOpt.get();

            // Verify password
            if (BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
                return Optional.of(foundUser);
            }
        }

        return Optional.empty();
    }

    public Optional<User> getUserById(Long id) {
        return userDao.findById(id);
    }
}