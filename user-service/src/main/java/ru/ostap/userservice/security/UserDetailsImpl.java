package ru.ostap.userservice.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.ostap.userservice.models.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Service
public class UserDetailsImpl implements UserDetails {

    private final User user;

    @Autowired
    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        this.user.getRoles().forEach(role -> role.getAuthorities().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getAuthority()))));

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.getEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.getEnabled();
    }

    @Override
    public boolean isEnabled() {
        return this.user.getEnabled();
    }
}