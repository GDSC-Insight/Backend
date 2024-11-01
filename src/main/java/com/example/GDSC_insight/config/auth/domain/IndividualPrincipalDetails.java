package com.example.GDSC_insight.config.auth.domain;

import com.example.GDSC_insight.domain.Individual;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class IndividualPrincipalDetails implements UserDetails {

    private final Individual individual;

    public IndividualPrincipalDetails(Individual individual) {
        this.individual = individual;
    }

    public Individual getUser(){
        return individual;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(individual::getRole);
        return authorities;
    }

    @Override
    public String getPassword() {
        return individual.getPassword();
    }

    @Override
    public String getUsername() {
        return individual.getLoginId();
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
}