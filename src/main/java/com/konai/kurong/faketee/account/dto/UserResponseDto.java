package com.konai.kurong.faketee.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDto {

    private long id;
    private String email;
    private String password;
    private String name;

    //  User -> UserUpdateResponseDto
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
    }
}
