package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.TypeRoomRequestDTO;
import com.ra.model.dto.response.TypeRoomResponseDTO;
import com.ra.service.type_room.TypeRoomService;
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
public class TypeRoomController {
    @Autowired
    private TypeRoomService typeRoomService;
    @GetMapping("/type_rooms")
    public ResponseEntity<Page<TypeRoomResponseDTO>> getAll (@RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "5") int size,
                                                             @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                             @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<TypeRoomResponseDTO> typeRoomResponseDTOS = typeRoomService.findAll(pageable);
        return new ResponseEntity<>(typeRoomResponseDTOS, HttpStatus.OK);
    }
    @GetMapping("/type_rooms/search")
    public ResponseEntity<Page<TypeRoomResponseDTO>> search (@RequestParam(name = "page", defaultValue = "0") int page,
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
        Page<TypeRoomResponseDTO> typeRoomResponseDTOS = typeRoomService.searchByName(pageable, search);
        return new ResponseEntity<>(typeRoomResponseDTOS, HttpStatus.OK);
    }

    @PostMapping("/type_rooms")
    public ResponseEntity<TypeRoomResponseDTO> create (@Valid @RequestBody TypeRoomRequestDTO typeRoomRequestDTO) throws CustomException {
        TypeRoomResponseDTO typeRoom = typeRoomService.saveOrUpdate(typeRoomRequestDTO);
        return new ResponseEntity<>(typeRoom, HttpStatus.CREATED);
    }

    @PutMapping("/type_rooms/{id}")
    public ResponseEntity<TypeRoomResponseDTO> updateHotel (@Valid @PathVariable("id") Long id,
                                                            @RequestBody TypeRoomRequestDTO typeRoomRequestDTO ) throws CustomException {
        TypeRoomResponseDTO typeRoomNew = typeRoomService.findById(id);

        typeRoomRequestDTO.setId(typeRoomNew.getId());
        TypeRoomResponseDTO typeRoomUpdate = typeRoomService.saveOrUpdate(typeRoomRequestDTO);

        return new ResponseEntity<>(typeRoomUpdate, HttpStatus.OK);
    }

    @PatchMapping("/type_rooms/{id}")
    public ResponseEntity<?> changeStatus (@PathVariable("id") Long id) throws CustomException {
        typeRoomService.changeStatusById(id);
        TypeRoomResponseDTO typeRoom = typeRoomService.findById(id);
        return new ResponseEntity<>(typeRoom, HttpStatus.OK);
    }
}
