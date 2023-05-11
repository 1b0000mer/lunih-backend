package com.gsquad.lunih.securities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUserDetails implements UserDetails {

    private final String fullName;

    private final String email;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private final boolean accountStatus;

    public JwtUserDetails(String fullName, String email, String password, Collection<? extends GrantedAuthority> authorities, boolean accountStatus) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.accountStatus = accountStatus;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountStatus;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return accountStatus;
    }

    public String getFullName() {
        return fullName;
    }
}
