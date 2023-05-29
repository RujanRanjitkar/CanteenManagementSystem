package com.example.canteenmgmtsys.customer.model;

import com.example.canteenmgmtsys.customer.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@Entity
public class Customer implements UserDetails {
    private static AtomicLong counter = new AtomicLong(1);
    private static final String prefix = "I";
    @Id
    private String customerId;
    private String customerName;
    private String email;
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    private void generateUniqueId() {
        long sequentialNumber = counter.getAndIncrement();
        this.customerId = String.format("%s%d", prefix, sequentialNumber);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
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
