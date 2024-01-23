package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.response.BookingResponseDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.entity.Booking;
import com.ra.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/bookings")
    public ResponseEntity<Page<BookingResponseDTO>> getAll (@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "5") int size,
                                                            @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                            @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<BookingResponseDTO> bookingResponseDTOS = bookingService.findAll(pageable);
        return new ResponseEntity<>(bookingResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/bookings/search")
    public ResponseEntity<Page<BookingResponseDTO>> search (@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "5") int size,
                                                            @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                            @RequestParam(name = "order", defaultValue = "desc") String order,
                                                            @RequestParam(name = "search") String search) throws CustomException {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<BookingResponseDTO> bookingResponseDTOS = bookingService.searchByUser(pageable, Long.valueOf(search));
        return new ResponseEntity<>(bookingResponseDTOS, HttpStatus.OK);
    }

    @PatchMapping("/bookings/accept/{id}")
    public ResponseEntity<?> acceptBooking (@PathVariable("id") String id) throws CustomException {

        // Kiểm tra giá trị nhập vào
        if (id != null && !id.trim().isEmpty()) {
            try {
                Long parsedId = Long.valueOf(id);
                bookingService.changeStatus(parsedId, 1);
                Booking booking = bookingService.findById(Long.valueOf(id));
                return new ResponseEntity<>(booking, HttpStatus.OK);
            } catch (NumberFormatException e) {
                throw new CustomException("Không đúng định dạng số, hãy nhập lại!!!");
            }
        } else {
            return new ResponseEntity<>("Không được để trống hoặc khoảng trắng", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/bookings/cancel/{id}")
    public ResponseEntity<?> cancelBooking (@PathVariable("id") Long id) throws CustomException {
        bookingService.changeStatus(id, 2);
        Booking booking = bookingService.findById(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping("/bookings/count")
    public ResponseEntity<?> countBooking (@ModelAttribute("year") Integer year) {
        Integer count = bookingService.countBooking(year);
        return new ResponseEntity<>("Tổng đơn đặt phòng trong năm: " + count, HttpStatus.OK);
    }
}
