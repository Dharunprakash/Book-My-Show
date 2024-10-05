package com.bms.bms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private long id;
    private String username;
    private String password;
    private String phone;
    private List<Booking> bookings; // List of bookings associated with the user
}

