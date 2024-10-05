package com.bms.bms.model;

import com.bms.bms.annotations.RelationField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Long id;
    private String name;
    private String password;
    private String phone;
    private String email;
    @RelationField("bookings")
    private List<Booking> bookings; // List of bookings associated with the user

}


