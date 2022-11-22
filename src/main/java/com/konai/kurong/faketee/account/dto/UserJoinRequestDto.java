package com.konai.kurong.faketee.account.dto;

import com.konai.kurong.faketee.account.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserJoinRequestDto {
    private String email;

    private String password;

    private String name;

    private String role;

//    UserJoinRequestDto -> User
    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}
