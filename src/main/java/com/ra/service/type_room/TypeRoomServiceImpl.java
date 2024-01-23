package com.ra.service.type_room;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.TypeRoomRequestDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.dto.response.TypeRoomResponseDTO;
import com.ra.model.entity.Product;
import com.ra.model.entity.TypeRoom;
import com.ra.repository.TypeRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeRoomServiceImpl implements TypeRoomService{
    @Autowired
    private TypeRoomRepository typeRoomRepository;
    @Override
    public Page<TypeRoomResponseDTO> findAll(Pageable pageable) {
        Page<TypeRoom> typeRoomPage = typeRoomRepository.findAll(pageable);
        return typeRoomPage.map(TypeRoomResponseDTO::new);
    }

    @Override
    public Page<TypeRoomResponseDTO> findAllByStatus(Pageable pageable) {
        Page<TypeRoom> typeRoomPage = typeRoomRepository.findAllByStatus(pageable, true);
        return typeRoomPage.map(TypeRoomResponseDTO::new);
    }

    @Override
    public Page<TypeRoomResponseDTO> searchByName(Pageable pageable, String name) {
        Page<TypeRoom> typeRoomPage = typeRoomRepository.findAllByTypeContainingIgnoreCase(pageable, name);
        return typeRoomPage.map(TypeRoomResponseDTO::new);
    }

    @Override
    public TypeRoomResponseDTO saveOrUpdate(TypeRoomRequestDTO typeRoomRequestDTO) throws CustomException {
        TypeRoom typeRoomNew;
        if (typeRoomRequestDTO.getId() == null) {
            if (typeRoomRepository.existsByType(typeRoomRequestDTO.getType())) {
                throw new CustomException("Kiểu phòng này có rồi, đặt tên kiểu khác đi!!!");
            }
            typeRoomNew = new TypeRoom();
            typeRoomNew.setType(typeRoomRequestDTO.getType());
            typeRoomNew.setStatus(typeRoomRequestDTO.getStatus());
        } else {
            typeRoomNew = typeRoomRepository.findById(typeRoomRequestDTO.getId())
                    .orElseThrow(() -> new CustomException("Không tìm thấy kiểu phòng này!!!"));
            if (!typeRoomNew.getType().equals(typeRoomRequestDTO.getType())) {
                if (typeRoomRepository.existsByType(typeRoomRequestDTO.getType())) {
                    throw new CustomException("Kiểu phòng này có rồi, đặt tên kiểu khác đi!!!");
                }
                typeRoomNew.setType(typeRoomRequestDTO.getType());
            }
            typeRoomNew.setStatus(typeRoomRequestDTO.getStatus());
        }
        typeRoomRepository.save(typeRoomNew);
        return new TypeRoomResponseDTO(typeRoomNew);
    }

    @Override
    public TypeRoomResponseDTO findById(Long id) {
        Optional<TypeRoom> typeRoomOptional = typeRoomRepository.findById(id);
        return typeRoomOptional.map(typeRoom -> new TypeRoomResponseDTO(typeRoom)).orElse(null);
    }

    @Override
    public void changeStatusById(Long id) throws CustomException {
        TypeRoom typeRoom = typeRoomRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy kiểu phòng!!!"));
        typeRoom.setStatus(!typeRoom.getStatus());
        typeRoomRepository.save(typeRoom);
    }
}
