package com.ra.model.dto.response;

import com.ra.model.entity.Booking;
import com.ra.model.entity.Product;
import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponseAllDTO {
    private Long id;
    private String userName;
    private String fullName;
    private String email;
    private Integer age;
    private String phoneNumber;
    private Set<String> roles;
    private List<Booking> bookings;

    public UserResponseAllDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.phoneNumber = user.getPhoneNumber();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        this.bookings = user.getBookings();
    }
}
