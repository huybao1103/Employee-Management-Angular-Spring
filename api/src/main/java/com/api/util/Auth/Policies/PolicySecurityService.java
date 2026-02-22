package com.api.util.Auth.Policies;

import com.api.util.Auth.Policies.Employee.EmployeePoliciesEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component("Policy")
public class PolicySecurityService {
    public boolean hasAny(Authentication authentication, Collection<String> required) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Set<String> userAuthorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return required.stream().anyMatch(userAuthorities::contains);
    }

    public boolean hasAll(Authentication authentication, Collection<String> required) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Set<String> userAuthorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return required.stream().anyMatch(userAuthorities::contains);
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface HasAllClaims {
        String[] claims();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface HasAnyClaims {
        String[] claims();
    }
}
