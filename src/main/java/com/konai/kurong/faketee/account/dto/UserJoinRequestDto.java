package com.konai.kurong.faketee.account.dto;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.util.Role;
import lombok.*;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserJoinRequestDto {

    private String email;
    private String password;

    private String name;
    private Role role;

    //    UserJoinRequestDto -> User
    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    public void setEncPassword(String encPassword){

        this.password = encPassword;
    }
}
