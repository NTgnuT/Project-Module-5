package com.ra.service.email;

import com.ra.model.dto.response.BookingResponseDTO;
import com.ra.model.entity.Booking;
import com.ra.model.entity.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public String sendMail(User userLogin, BookingResponseDTO booking) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("tung28111998@gmail.com");
            helper.setTo(booking.getEmail());
            helper.setSubject("Cảm ơn bạn đã đặt phòng.");

            // Tạo nội dung email
            String emailContent = createEmailContent(booking);

            helper.setText(emailContent, true);

            javaMailSender.send(message);

            return "Gửi mail thành công";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String createEmailContent(BookingResponseDTO booking) {
        StringBuilder emailContentBuilder = new StringBuilder();
//        emailContentBuilder.append("<html><body>");
//        emailContentBuilder.append("<h2 style=\"color: #4caf50;\">Cảm ơn bạn đã đặt phòng!</h2>");
//        emailContentBuilder.append("<p>Dưới đây là chi tiết đặt phòng của bạn:</p>");
//        emailContentBuilder.append("<table style=\"border-collapse: collapse; width: 100%;\" border='1' cellpadding='10'>");
//        emailContentBuilder.append("<tr style=\"background-color: #f2f2f2;\"><th style=\"text-align: left;\">Tài khoản đặt phòng</th><td>").append(booking.getUserName()).append("</td></tr>");
//        emailContentBuilder.append("<tr><th style=\"background-color: #f2f2f2; text-align: left;\">Ngày đặt phòng</th><td>").append(booking.getCheckIn()).append("</td></tr>");
//        emailContentBuilder.append("<tr style=\"background-color: #f2f2f2;\"><th style=\"text-align: left;\">Số ngày đặt</th><td>").append(booking.getDays()).append("</td></tr>");
//        emailContentBuilder.append("<tr><th style=\"background-color: #f2f2f2; text-align: left;\">Tên khách sạn</th><td>").append(booking.getRoom().getHotel().getHotelName()).append("</td></tr>");
//        emailContentBuilder.append("<tr style=\"background-color: #f2f2f2;\"><th style=\"text-align: left;\">Loại phòng</th><td>").append(booking.getRoom().getTypeRoom().getType()).append("</td></tr>");
//        emailContentBuilder.append("<tr><th style=\"background-color: #f2f2f2; text-align: left;\">Số phòng</th><td>").append(booking.getRoom().getRoomNo()).append("</td></tr>");
//        emailContentBuilder.append("<tr style=\"background-color: #f2f2f2;\"><th style=\"text-align: left;\">Loại dịch vụ</th><td>").append(booking.getProducts()).append("</td></tr>");
//        emailContentBuilder.append("</table>");
//        emailContentBuilder.append("</body></html>");

        emailContentBuilder.append("<html><body style=\"font-family: Arial, sans-serif;\">");
        emailContentBuilder.append("<div style=\"background-color: #4caf50; color: #fff; padding: 20px; text-align: center;\">");
        emailContentBuilder.append("<h2 style=\"margin: 0;\">Cảm ơn bạn đã đặt phòng!</h2>");
        emailContentBuilder.append("</div>");
        emailContentBuilder.append("<div style=\"padding: 20px;\">");
        emailContentBuilder.append("<h3 style=\"color: #4caf50;\">Chi tiết đặt phòng:</h3>");
        emailContentBuilder.append("<div style=\"border: 1px solid #ccc; border-radius: 8px; overflow: hidden;\">");
        emailContentBuilder.append("<table style=\"width: 100%; border-collapse: collapse;\">");
        emailContentBuilder.append("<tr style=\"background-color: #4caf50; color: #fff;\"><th style=\"padding: 10px;\">Thông tin</th><th style=\"padding: 10px;\">Chi tiết</th></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Tài khoản đặt phòng</td><td style=\"padding: 10px;\">").append(booking.getUserName()).append("</td></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Ngày đặt phòng</td><td style=\"padding: 10px;\">").append(booking.getCheckIn()).append("</td></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Số ngày đặt</td><td style=\"padding: 10px;\">").append(booking.getDays()).append("</td></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Tên khách sạn</td><td style=\"padding: 10px;\">").append(booking.getRoom().getHotel().getHotelName()).append("</td></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Loại phòng</td><td style=\"padding: 10px;\">").append(booking.getRoom().getTypeRoom().getType()).append("</td></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Số phòng</td><td style=\"padding: 10px;\">").append(booking.getRoom().getRoomNo()).append("</td></tr>");
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Loại dịch vụ</td><td style=\"padding: 10px;\">").append(booking.getProducts()).append("</td></tr>");

        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedAmount = decimalFormat.format(booking.getTotal());
        emailContentBuilder.append("<tr><td style=\"padding: 10px;\">Tổng tiền</td><td style=\"padding: 10px;\">").append(formattedAmount).append("</td></tr>");

        emailContentBuilder.append("</table>");
        emailContentBuilder.append("</div>");
        emailContentBuilder.append("</div>");
        emailContentBuilder.append("</body></html>");

        return emailContentBuilder.toString();
    }
}
