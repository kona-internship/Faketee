package com.konai.kurong.faketee.account.dto;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.util.Role;
import com.konai.kurong.faketee.account.util.Type;
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
    private Type type;
    private String emailAuthStatus;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(role)
                .type(type)
                .emailAuthStatus(emailAuthStatus)
                .build();
    }

    public void setEncPassword(String encPassword) {

        this.password = encPassword;
    }
}
