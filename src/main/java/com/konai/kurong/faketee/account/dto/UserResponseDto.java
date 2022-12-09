package com.konai.kurong.faketee.account.dto;

import com.konai.kurong.faketee.account.entity.User;
import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
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

    public static UserResponseDto convertToDto(User user){

        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .build();
    }
}
