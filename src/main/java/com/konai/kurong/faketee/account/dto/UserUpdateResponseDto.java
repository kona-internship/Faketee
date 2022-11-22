package com.konai.kurong.faketee.account.dto;

import com.konai.kurong.faketee.account.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateResponseDto {
    private long id;

    private String email;

    private String password;

    private String name;

    //  User -> UserUpdateResponseDto
    public UserUpdateResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
    }
}
