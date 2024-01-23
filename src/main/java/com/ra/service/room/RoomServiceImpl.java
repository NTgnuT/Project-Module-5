package com.ra.service.room;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.RoomRequestDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.dto.response.RoomResponseDTO;
import com.ra.model.entity.Hotel;
import com.ra.model.entity.Product;
import com.ra.model.entity.Room;
import com.ra.model.entity.TypeRoom;
import com.ra.repository.HotelRepository;
import com.ra.repository.RoomRepository;
import com.ra.repository.TypeRoomRepository;
import com.ra.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private TypeRoomRepository typeRoomRepository;

    @Override
    public Page<RoomResponseDTO> findAll(Pageable pageable) {
        Page<Room> roomPage = roomRepository.findAll(pageable);
        return roomPage.map(RoomResponseDTO::new);
    }

    @Override
    public Page<RoomResponseDTO> findAllByStatus(Pageable pageable) {
        Page<Room> roomPage = roomRepository.findAllByStatus(pageable, true);
        return roomPage.map(RoomResponseDTO::new);
    }

    @Override
    public Page<RoomResponseDTO> searchByName(Pageable pageable, String name) {
        Page<Room> roomPage = roomRepository.findAllByRoomNoContainingIgnoreCase(pageable, name);
        return roomPage.map(RoomResponseDTO::new);
    }

    @Override
    public RoomResponseDTO saveOrUpdate(RoomRequestDTO roomRequestDTO) throws CustomException {
        Room room;
        if (roomRepository.existsByRoomNo(roomRequestDTO.getRoomNo())) {
            throw new CustomException("Tên phòng đã tồn tại, đặt tên khác đi!!!");
        }

        if (roomRequestDTO.getId() == null) {
            room = new Room();
            room.setRoomNo(roomRequestDTO.getRoomNo());
            room.setPrice(roomRequestDTO.getPrice());
            room.setDescription(roomRequestDTO.getDescription());
            room.setStatus(roomRequestDTO.getStatus());

            Hotel hotel = hotelRepository.findById(roomRequestDTO.getHotelId())
                    .orElseThrow(() -> new CustomException("Không tìm thấy khách sạn này để thêm phòng!!!"));
            room.setHotel(hotel);

            TypeRoom typeRoom = typeRoomRepository.findById(roomRequestDTO.getTypeRoomId())
                    .orElseThrow(() -> new CustomException("Không tìm thấy kiểu phòng này!!!"));
            room.setTypeRoom(typeRoom);

            String fileName = uploadService.uploadImage(roomRequestDTO.getFile());
            room.setImage(fileName);


        } else {
            room = roomRepository.findById(roomRequestDTO.getId()).orElseThrow(() -> new CustomException("Không tìm thấy phòng!!!"));
            String fileName;
            if (roomRequestDTO.getFile() != null && !roomRequestDTO.getFile().isEmpty()) {
                fileName = uploadService.uploadImage(roomRequestDTO.getFile());
            } else {
                fileName = room.getImage();
            }
            Hotel hotel = hotelRepository.findById(roomRequestDTO.getHotelId())
                    .orElseThrow(() -> new CustomException("Không tìm thấy khách sạn này để thêm phòng!!!"));

            TypeRoom typeRoom = typeRoomRepository.findById(roomRequestDTO.getTypeRoomId())
                    .orElseThrow(() -> new CustomException("Không tìm thấy kiểu phòng này!!!"));

            if (!room.getRoomNo().equals(roomRequestDTO.getRoomNo())) {
                if (roomRepository.existsByRoomNo(roomRequestDTO.getRoomNo())) {
                    throw new CustomException("Tên phòng đã tồn tại, đặt tên khác đi!!!");
                }
                room.setRoomNo(roomRequestDTO.getRoomNo());
            }
            room.setPrice(roomRequestDTO.getPrice());
            room.setDescription(roomRequestDTO.getDescription());
            room.setStatus(roomRequestDTO.getStatus());
            room.setHotel(hotel);
            room.setTypeRoom(typeRoom);
            room.setImage(fileName);

        }
        roomRepository.save(room);
        return new RoomResponseDTO(room);
    }

    @Override
    public RoomResponseDTO findById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.map(RoomResponseDTO::new).orElse(null);
    }

    @Override
    public void changeStatusById(Long id) throws CustomException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy tên phòng!!!"));
        room.setStatus(!room.getStatus());
        roomRepository.save(room);
    }
}
