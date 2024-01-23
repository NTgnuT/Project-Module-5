package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.HotelRequestDTO;
import com.ra.model.dto.response.HotelResponseDTO;
import com.ra.service.hotel.HotelService;
import jakarta.validation.Valid;
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
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @GetMapping("/hotels")
    public ResponseEntity<Page<HotelResponseDTO>> getAll (@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "5") int size,
                                                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                          @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<HotelResponseDTO> hotelResponseDTOS = hotelService.findAll(pageable);
        return new ResponseEntity<>(hotelResponseDTOS, HttpStatus.OK);
    }
    @GetMapping("/hotels/search")
    public ResponseEntity<Page<HotelResponseDTO>> search (@RequestParam(name = "page", defaultValue = "0") int page,
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
        Page<HotelResponseDTO> hotelResponseDTOS = hotelService.searchByHotelName(pageable, search);
        return new ResponseEntity<>(hotelResponseDTOS, HttpStatus.OK);
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelResponseDTO> create (@Valid @ModelAttribute("hotel") HotelRequestDTO hotelRequestDTO) throws CustomException {
        HotelResponseDTO hotel = hotelService.saveOrUpdate(hotelRequestDTO);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    @PutMapping("/hotels/{id}")
    public ResponseEntity<HotelResponseDTO> updateHotel (@Valid @PathVariable("id") Long id,
                                                         @ModelAttribute("hotel") HotelRequestDTO hotelRequestDTO ) throws CustomException {
        HotelResponseDTO hotelNew = hotelService.findById(id);

        hotelRequestDTO.setId(hotelNew.getId());
        HotelResponseDTO hotelUpdate = hotelService.saveOrUpdate(hotelRequestDTO);

        return new ResponseEntity<>(hotelUpdate, HttpStatus.OK);
    }

    @PatchMapping("/hotels/{id}")
    public ResponseEntity<?> changeStatus (@PathVariable("id") Long id) throws CustomException {
        hotelService.changeStatusById(id);
        HotelResponseDTO hotel = hotelService.findById(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

}
