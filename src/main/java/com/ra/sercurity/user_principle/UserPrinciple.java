
package com.ra.sercurity.user_principle;

import com.ra.model.entity.Booking;
import com.ra.model.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserPrinciple implements UserDetails {
    private Long id;

    private String fullName;

    private String username;

    private String password;

    private String email;

    private Integer age;

    private String phoneNumber;

    private Boolean status;

    private List<Booking> bookings;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetails build (User user) {
        return UserPrinciple.builder().id(user.getId())
                .email(user.getEmail())
                .age(user.getAge())
                .phoneNumber(user.getPhoneNumber())
                .fullName(user.getFullName())
                .username(user.getUserName())
                .password(user.getPassword())
                .bookings(user.getBookings())
                .status(user.getStatus())
                .authorities(user.getRoles().stream().map(item
                        -> new SimpleGrantedAuthority(item.getName())).toList()).build();
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
        return username;
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
        return status;
    }
}

