package com.bms.bms.dto;

import com.bms.bms.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;

    public User toUser() {
        return User.builder()
                .id(id)
                .name(name)
                .phone(phone)
                .email(email)
                .build();
    }

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }
}
