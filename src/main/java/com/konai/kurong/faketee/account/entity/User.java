package com.konai.kurong.faketee.account.entity;

import com.konai.kurong.faketee.account.dto.UserUpdateRequestDto;
import com.konai.kurong.faketee.account.util.BaseUserEntity;
import com.konai.kurong.faketee.account.util.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@SequenceGenerator(
        name = "USR_SEQUENCE_GENERATOR",
        sequenceName = "USR_SEQUENCE",
        initialValue = 1,
        allocationSize = 1)
@Table(name="USR")
@Data
@Entity
public class User extends BaseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "USR_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;

    private String email;

    @Column(name = "PSWD")
    private String password;

    private String name;

    private Role role;

    @Builder
    public User(long id, String email, String password, String name, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public void updatePassword(UserUpdateRequestDto requestDto) {

        this.password = requestDto.getNewPassword();
    }
}