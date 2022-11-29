package com.konai.kurong.faketee.auth;

import com.konai.kurong.faketee.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

    private final SessionUser sessionUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {

                return sessionUser.getRole().getKey();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {

        return sessionUser.getPassword();
    }

    @Override
    public String getUsername() {

        return sessionUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    public Long getId() {

        return sessionUser.getId();
    }

    public String getName() {

        return sessionUser.getName();
    }

    public String getEmail() {

        return sessionUser.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
