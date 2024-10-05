package com.bms.bms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.bms.bms.model.User;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SessionUserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;

    public static SessionUserDTO fromUser(User user) {
        return SessionUserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .phone(phone)
                .build();
    }
}