package com.api.util;

import com.api.entities.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private User user;

//    @Override
//    public UserDetails loadByUsername(String username) {
//        return this;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole().getRoleClaims()
                .stream()
                .map(rc -> new SimpleGrantedAuthority(rc.getClaim()))
                .collect(Collectors.toSet());
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getuserName();
    }

    public String getId() {
        return user.getId().toString();
    }


}
