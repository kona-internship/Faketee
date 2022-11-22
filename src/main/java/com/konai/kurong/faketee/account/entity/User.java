package com.konai.kurong.faketee.account.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@SequenceGenerator(
        name = "USR_SEQUENCE_GENERATOR",
        sequenceName = "USR_SEQUENCE",
        initialValue = 1,
        allocationSize = 1)
@Table(name="USR")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "USR_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private long id;

    private String email;

    @Column(name = "PSWD")
    private String password;

    private String name;

    private String role;

    @Column(name = "CRE_DTTM")
    private LocalDateTime createdDateTime;

    @Column(name = "UPD_DTTM")
    private LocalDateTime updatedDateTime;

    @Column(name = "CRE_ID")
    private String createdId;

    @Column(name = "CRE_ID")
    private String updatedId;

    @Builder
    public User(long id, String email, String password, String name, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}
