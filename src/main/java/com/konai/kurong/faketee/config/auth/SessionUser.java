package com.konai.kurong.faketee.config.auth;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.util.Role;
import lombok.Getter;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private static final long serialVersionID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private Long id;
    private String email;
    private String password;
    private String name;
    private Role role;

    public SessionUser(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.role = user.getRole();
    }
}
