package com.ra.controller.client;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.BookingRequestDTO;
import com.ra.model.dto.response.BookingResponseDTO;
import com.ra.model.entity.Booking;
import com.ra.model.entity.User;
import com.ra.sercurity.user_principle.UserPrinciple;
import com.ra.service.booking.BookingService;
import com.ra.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class BookingUserController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    @GetMapping("/bookings")
    public ResponseEntity<Page<BookingResponseDTO>> getAllBookingForUser (@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "5") int size,
                                                            @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                            @RequestParam(name = "order", defaultValue = "desc") String order,
                                                                          Authentication authentication) throws CustomException {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findById(userPrinciple.getId());

        Page<BookingResponseDTO> bookingResponseDTOS = bookingService.searchByUser(pageable, user.getId());
        return new ResponseEntity<>(bookingResponseDTOS, HttpStatus.OK);
    }
    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking (@Valid @RequestBody BookingRequestDTO bookingRequestDTO, Authentication authentication) throws CustomException {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findById(userPrinciple.getId());
        BookingResponseDTO booking = bookingService.save(bookingRequestDTO, user);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PatchMapping("/bookings/cancel/{id}")
    public ResponseEntity<?> cancelBooking (@PathVariable("id") Long id) throws CustomException {
        bookingService.changeStatus(id, 2);
        Booking booking = bookingService.findById(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

}
