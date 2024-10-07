package com.bms.bms.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

import java.util.Date;

public class TokenUtil {
    private static final String SECRET_KEY = "EAqgTDXdXWvKvgAeYR+7p+87S0FNQlZlj9IjB0Rwxto=";
    private static final long EXPIRATION_TIME = 1800000;

    public static String generateToken(String userIdRole) {
        return Jwts.builder()
                .setSubject(userIdRole)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}