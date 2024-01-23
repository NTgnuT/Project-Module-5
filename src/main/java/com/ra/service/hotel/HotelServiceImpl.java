package com.ra.service.hotel;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.HotelRequestDTO;
import com.ra.model.dto.response.HotelResponseDTO;
import com.ra.model.dto.response.HotelUserResponseDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.entity.Hotel;
import com.ra.model.entity.Product;
import com.ra.repository.HotelRepository;
import com.ra.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UploadService uploadService;

    @Override
    public Page<HotelResponseDTO> findAll(Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);
        return hotelPage.map(HotelResponseDTO::new);
    }

    @Override
    public Page<HotelResponseDTO> findAllByStatus(Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepository.findAllByStatus(pageable, true);
        return hotelPage.map(HotelResponseDTO::new);
    }

    @Override
    public Page<HotelResponseDTO> searchByHotelName(Pageable pageable, String name) {
        Page<Hotel> hotelPage = hotelRepository.findAllByHotelNameContainingIgnoreCase(pageable, name);
        return hotelPage.map(HotelResponseDTO::new);
    }

    @Override
    public Page<HotelUserResponseDTO> searchByNameForUser(Pageable pageable, String name) {
        Page<Hotel> hotelPage = hotelRepository.findAllByHotelNameContainingIgnoreCaseAndStatus(pageable, name, true);
        return hotelPage.map(HotelUserResponseDTO::new);
    }

    @Override
    public HotelResponseDTO saveOrUpdate(HotelRequestDTO hotelRequestDTO) throws CustomException {
        Hotel hotel;
        if (hotelRequestDTO.getId() == null) {
            if (hotelRepository.existsByHotelName(hotelRequestDTO.getHotelName())) {
                throw new CustomException("Tên khách sạn này có rồi, đặt tên khác đi!!!");
            }
            hotel = new Hotel();
            hotel.setHotelName(hotelRequestDTO.getHotelName());
            hotel.setAddress(hotelRequestDTO.getAddress());
            hotel.setDescription(hotelRequestDTO.getDescription());
            hotel.setStatus(hotelRequestDTO.getStatus());

            String fileName = uploadService.uploadImage(hotelRequestDTO.getImage());
            hotel.setImage(fileName);

        } else {
            hotel = hotelRepository.findById(hotelRequestDTO.getId()).orElseThrow(() -> new CustomException("Không tìm thấy khách sạn!!!"));

            String fileName;
            if (hotelRequestDTO.getImage() != null && !hotelRequestDTO.getImage().isEmpty()) {
                fileName = uploadService.uploadImage(hotelRequestDTO.getImage());
            } else {
                fileName = hotel.getImage();
            }

            if (!hotel.getHotelName().equals(hotelRequestDTO.getHotelName())) {
                if (hotelRepository.existsByHotelName(hotelRequestDTO.getHotelName())) {
                    throw new CustomException("Tên khách sạn này có rồi, đặt tên khác đi!!!");
                }
                hotel.setHotelName(hotelRequestDTO.getHotelName());
            }
            hotel.setImage(fileName);
            hotel.setAddress(hotelRequestDTO.getAddress());
            hotel.setDescription(hotelRequestDTO.getDescription());
            hotel.setStatus(hotelRequestDTO.getStatus());
        }
        hotelRepository.save(hotel);
        return new HotelResponseDTO(hotel);
    }

    @Override
    public HotelResponseDTO findById(Long id) throws CustomException {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        return hotel.map(HotelResponseDTO::new).orElseThrow(() -> new CustomException("Không tìm thấy khách sạn!!!"));
    }

    @Override
    public Boolean changeStatusById(Long id) throws CustomException {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy khách sạn!!!"));
        hotel.setStatus(!hotel.getStatus());
        hotelRepository.save(hotel);
        return true;
    }
}
