package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.RoomRequestDTO;
import com.ra.model.dto.response.RoomResponseDTO;
import com.ra.service.room.RoomService;
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
public class RoomController {
    @Autowired
    private RoomService roomService;
    @GetMapping("/rooms")
    public ResponseEntity<Page<RoomResponseDTO>> getAll (@RequestParam(name = "page", defaultValue = "0") int page,
                                                         @RequestParam(name = "size", defaultValue = "5") int size,
                                                         @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                         @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<RoomResponseDTO> roomResponseDTOS = roomService.findAll(pageable);
        return new ResponseEntity<>(roomResponseDTOS, HttpStatus.OK);
    }
    @GetMapping("/rooms/search")
    public ResponseEntity<Page<RoomResponseDTO>> search (@RequestParam(name = "page", defaultValue = "0") int page,
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
        Page<RoomResponseDTO> roomResponseDTOS = roomService.searchByName(pageable, search);
        return new ResponseEntity<>(roomResponseDTOS, HttpStatus.OK);
    }

    @PostMapping("/rooms")
    public ResponseEntity<RoomResponseDTO> create (@Valid @ModelAttribute("room") RoomRequestDTO roomRequestDTO) throws CustomException {
        RoomResponseDTO room = roomService.saveOrUpdate(roomRequestDTO);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<RoomResponseDTO> updateHotel (@Valid @PathVariable("id") Long id,
                                                         @ModelAttribute("room") RoomRequestDTO roomRequestDTO ) throws CustomException {
        RoomResponseDTO roomNew = roomService.findById(id);

        roomRequestDTO.setId(roomNew.getId());
        RoomResponseDTO roomUpdate = roomService.saveOrUpdate(roomRequestDTO);

        return new ResponseEntity<>(roomUpdate, HttpStatus.OK);
    }

    @PatchMapping("/rooms/{id}")
    public ResponseEntity<?> changeStatus (@PathVariable("id") Long id) throws CustomException {
        roomService.changeStatusById(id);
        RoomResponseDTO room = roomService.findById(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
}
