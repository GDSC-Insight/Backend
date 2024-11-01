package com.example.GDSC_insight.config.auth.domain;

import com.example.GDSC_insight.domain.Corporate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CorporatePrincipalDetails implements UserDetails {

    private final Corporate corporate;

    public CorporatePrincipalDetails(Corporate corporate) {
        this.corporate = corporate;
    }

    public Corporate getUser(){
        return corporate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> corporate.getRole());
        return authorities;
    }

    @Override
    public String getPassword() {
        return corporate.getPassword();
    }

    @Override
    public String getUsername() {
        return corporate.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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

    public Corporate getCorporate() {
        return corporate;
    }
}
