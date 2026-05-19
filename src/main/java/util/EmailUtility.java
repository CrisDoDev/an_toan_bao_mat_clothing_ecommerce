package util;

import model.OrderDetailInfo;
import model.User;
import model.Order;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.List;
import java.util.Properties;
import java.text.NumberFormat;
import java.util.Locale;

public class EmailUtility {

    // THÔNG TIN CẤU HÌNH 
    private static final String HOST = "smtp.gmail.com";
    private static final String PORT = "587";
    private static final String SENDER_EMAIL = "dominhphuoc68@gmail.com"; // Email 
    private static final String SENDER_PASS = "xafo zqvt buqx losr";     // Mật khẩu ứng dụng Gmail

    public static void sendOrderConfirmation(User recipient, Order order, List<OrderDetailInfo> details) {
        // Cấu hình Properties cho Session
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.quitwait", "false"); // Ngăn lỗi SSLException với Gmail

        // Tạo Session với xác thực
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASS);
            }
        });

        try {
            // Soạn thảo tin nhắn 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient.getEmail()));
            message.setSubject("Xác nhận đơn hàng #" + order.getId() + " - Shop Thời Trang");

            // Tạo nội dung HTML 
            StringBuilder htmlContent = new StringBuilder();
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            htmlContent.append("<h2 style='color: #333;'>Cảm ơn bạn đã mua hàng, ").append(recipient.getFullName()).append("!</h2>");
            htmlContent.append("<p>Đơn hàng <strong>#").append(order.getId()).append("</strong> của bạn đã được hệ thống xác nhận.</p>");
            htmlContent.append("<table border='1' cellpadding='10' cellspacing='0' style='width: 100%; border-collapse: collapse; font-family: sans-serif;'>");
            htmlContent.append("<tr style='background-color: #f8f8f8;'><th>Sản phẩm</th><th>Size</th><th>Số lượng</th><th>Giá</th><th>Thành tiền</th></tr>");

            for (OrderDetailInfo item : details) {
                htmlContent.append("<tr>")
                        .append("<td>").append(item.getProductName()).append("</td>")
                        .append("<td align='center'>").append(item.getSizeName()).append("</td>")
                        .append("<td align='center'>").append(item.getQuantity()).append("</td>")
                        .append("<td>").append(nf.format(item.getPrice())).append("</td>")
                        .append("<td>").append(nf.format(item.getTotalPrice())).append("</td>")
                        .append("</tr>");
            }
            htmlContent.append("</table>");
            htmlContent.append("<h3>Tổng thanh toán: <span style='color: #c0392b;'>").append(nf.format(order.getTotalMoney())).append("</span></h3>");
            htmlContent.append("<p>Địa chỉ nhận hàng: ").append(order.getShippingAddress()).append("</p>");
            htmlContent.append("<hr/><p>Chúng tôi sẽ sớm liên hệ với bạn để giao hàng. Chúc bạn một ngày tốt lành!</p>");

            message.setContent(htmlContent.toString(), "text/html; charset=UTF-8");

            // 5. Gửi email
            Transport.send(message);
            System.out.println("Email sent successfully to: " + recipient.getEmail());

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}