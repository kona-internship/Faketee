package com.konai.kurong.faketee.account.entity;

import com.konai.kurong.faketee.account.dto.UserUpdateRequestDto;
import com.konai.kurong.faketee.account.util.Type;
import com.konai.kurong.faketee.util.jpa_auditing.BaseUserEntity;
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

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PSWD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Builder
    public User(Long id, String email, String password, String name, Role role, Type type) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.type = type;
    }

    public void updatePassword(UserUpdateRequestDto requestDto) {

        this.password = requestDto.getNewPassword();
    }
}
