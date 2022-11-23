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

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(role)
                .build();
    }

    public void setEncPassword(String encPassword) {

        this.password = encPassword;
    }
}
