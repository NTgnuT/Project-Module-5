
package com.ra.model.dto.response;

import com.ra.model.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponseDTO {
    private String token;
    private String userName;
    private String email;
    private Integer age;
    private String phoneNumber;
    private Set<String> roles;
    private List<Booking> bookings;
}

