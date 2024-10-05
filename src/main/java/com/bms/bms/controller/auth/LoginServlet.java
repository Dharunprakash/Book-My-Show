package com.bms.bms.controller.auth;

import com.bms.bms.dto.SessionUserDTO;
import com.bms.bms.model.User;
import com.bms.bms.service.AuthService;
import com.bms.bms.utils.HttpRequestParser;
import com.bms.bms.utils.ResponseUtil;
import com.bms.bms.utils.TokenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

    private final AuthService authService;

    public LoginServlet() throws SQLException, ClassNotFoundException {
        this.authService = new AuthService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = HttpRequestParser.parse(req, User.class);

        Optional<User> userOpt = authService.loginUser(user);
        System.out.println("userOpt: " + userOpt);
        if (userOpt.isEmpty()) {
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials", null);
            return;
        }
        var sessionUser = SessionUserDTO.fromUser(userOpt.get());

        // Generate token
        String token = TokenUtil.generateToken(sessionUser.getId().toString()+"-"+sessionUser.getRole().name());

        System.out.println("token: " + token);

        // Set token as cookie
        Cookie tokenCookie = new Cookie("AUTH_TOKEN", token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(30 * 60); // 30 minutes expiration
        System.out.println("tokenCookie: " + tokenCookie.getMaxAge());
        resp.addCookie(tokenCookie);
        ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Login successful", sessionUser);
    }
}