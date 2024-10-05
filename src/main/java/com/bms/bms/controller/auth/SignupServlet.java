package com.bms.bms.controller.auth;


import com.bms.bms.model.User;
import com.bms.bms.service.AuthService;
import com.bms.bms.utils.HttpRequestParser;
import com.bms.bms.utils.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/auth/register")
public class SignupServlet extends HttpServlet {

    private final AuthService authService;

    public SignupServlet() throws SQLException, ClassNotFoundException {
        this.authService = new AuthService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = HttpRequestParser.parse(req, User.class);
        try {
            Optional<User> registeredUser = authService.registerUser(user);
            if (registeredUser.isPresent()) {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Registration successful", registeredUser.get());
            } else {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_BAD_REQUEST, "User already exists", null);
            }
        } catch (RuntimeException e) {
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), null);
        }
    }

    private User parseUserFromJson(String json) {
        // Implement this method to parse JSON string to User object

        return new User(); // Placeholder implementation
    }
}