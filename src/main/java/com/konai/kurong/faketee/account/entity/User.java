package com.konai.kurong.faketee.account.entity;

import com.konai.kurong.faketee.account.dto.UserUpdateRequestDto;
import com.konai.kurong.faketee.account.util.Role;
import com.konai.kurong.faketee.util.exception.BaseTimeEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
public class User extends BaseTimeEntity {
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

    @Column(name = "CRE_ID")
    private String createdId;
    @Column(name = "UPD_ID")

    private String updatedId;

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
