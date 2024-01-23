package com.ra.controller.home;

import com.ra.model.dto.response.*;
import com.ra.service.hotel.HotelService;
import com.ra.service.product.ProductService;
import com.ra.service.room.RoomService;
import com.ra.service.type_room.TypeRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class PermitAllController {
    @Autowired
    private ProductService productService;
    @Autowired
    private TypeRoomService typeRoomService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;
    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProductByStatus (@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "5") int size,
                                                            @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                            @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<ProductResponseDTO> productResponseDTOS = productService.findAllByStatus(pageable);
        return new ResponseEntity<>(productResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/type_rooms")
    public ResponseEntity<Page<TypeRoomResponseDTO>> getAllTypeRoomByStatus (@RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "5") int size,
                                                             @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                             @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<TypeRoomResponseDTO> typeRoomResponseDTOS = typeRoomService.findAllByStatus(pageable);
        return new ResponseEntity<>(typeRoomResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/hotels")
    public ResponseEntity<Page<HotelResponseDTO>> getAllHotelByStatus (@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "5") int size,
                                                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                          @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<HotelResponseDTO> hotelResponseDTOS = hotelService.findAllByStatus(pageable);
        return new ResponseEntity<>(hotelResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/hotels/search")
    public ResponseEntity<Page<HotelUserResponseDTO>> searchHotelForUser (@RequestParam(name = "page", defaultValue = "0") int page,
                                                              @RequestParam(name = "size", defaultValue = "5") int size,
                                                              @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                              @RequestParam(name = "order", defaultValue = "desc") String order,
                                                              @RequestParam(name = "search") String search) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<HotelUserResponseDTO> hotel = hotelService.searchByNameForUser(pageable, search);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping("/rooms")
    public ResponseEntity<Page<RoomResponseDTO>> getAllRoomByStatus (@RequestParam(name = "page", defaultValue = "0") int page,
                                                         @RequestParam(name = "size", defaultValue = "5") int size,
                                                         @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                         @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<RoomResponseDTO> roomResponseDTOS = roomService.findAllByStatus(pageable);
        return new ResponseEntity<>(roomResponseDTOS, HttpStatus.OK);
    }


}
