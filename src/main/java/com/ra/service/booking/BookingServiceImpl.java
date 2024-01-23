package com.ra.service.booking;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.BookingRequestDTO;
import com.ra.model.dto.response.BookingResponseDTO;
import com.ra.model.entity.*;
import com.ra.repository.*;
import com.ra.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BookingServiceImpl implements BookingService{
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EmailService emailService;
    @Override
    public Page<BookingResponseDTO> findAll(Pageable pageable) {
        Page<Booking> bookingPage = bookingRepository.findAll(pageable);
        return bookingPage.map(BookingResponseDTO::new);
    }

    @Override
    public Page<BookingResponseDTO> searchByUser(Pageable pageable, Long id) throws CustomException {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy người dùng này!!!"));
        Page<Booking> bookingPage = bookingRepository.findAllByUser(pageable, user);
        return bookingPage.map(BookingResponseDTO::new);
    }

    @Override
    public BookingResponseDTO save(BookingRequestDTO bookingRequestDTO, User userLogin) throws CustomException {
        Room room = roomRepository.findById(bookingRequestDTO.getRoomId()).orElseThrow(() -> new CustomException("Không tìm thấy phòng!!!"));
        if (!room.getStatus()) {
            throw new CustomException("Phòng này không còn trống vui lòng chọn phòng khác!!!");
        }
                Set<Product> products = new HashSet<>();

        Hotel hotel = hotelRepository.findById(room.getHotel().getId()).orElseThrow(() -> new CustomException("Không tìm thấy khách sạn!!!"));
        if (!hotel.getStatus()) {
            throw new CustomException("Khách sạn này đang tạm dừng hoạt động, vui lòng chọn khách sạn khác!!!");
        }
        for (Long id : bookingRequestDTO.getProductId()) {
            Product product = productRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy dịch vụ!!!"));
            if (!product.getStatus()) {
                throw new CustomException("Dịch vụ " + product.getId() + " đang tạm ngừng kinh doanh!!!");
            }
            products.add(product);
        }

//        if (bookingRequestDTO.getRoomId() == null) {
//            throw new CustomException("Hãy chọn phòng bạn muốn đặt");
//        }

        Booking booking = Booking.builder()
                .days(bookingRequestDTO.getDays())
                .note(bookingRequestDTO.getNote())
                .checkIn(bookingRequestDTO.getCheckIn())
                .room(room)
                .products(products)
                .user(userLogin)
                .status(bookingRequestDTO.getStatus())
                .build();
        room.setStatus(false);
        roomRepository.save(room);

        bookingRepository.save(booking);
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO(booking);
        emailService.sendMail(userLogin, bookingResponseDTO);
        return bookingResponseDTO;
    }

    @Override
    public Booking findById(Long id) throws CustomException {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.orElseThrow(() -> new CustomException("Không tìm thấy booking !!!"));
    }

    @Override
    public void changeStatus(Long id, int status) throws CustomException {
        Booking booking = findById(id);
        if (booking.getStatus() == 0) {
            booking.setStatus(status);
            bookingRepository.save(booking);
        } else {
            throw new CustomException("Booking đã thay đổi trạng thái, không sửa được nữa!!!");
        }
    }

    @Override
    public Integer countBooking(Integer year) {
        // Lấy ngày đầu tiên của năm
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = new Date(calendar.getTimeInMillis());

        // Lấy ngày cuối cùng của năm
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        Date endDate = new Date(calendar.getTimeInMillis());

        return bookingRepository.countAllByCheckInBetween(startDate, endDate);
    }
}
