package controller.web;

import model.Cart;
import model.Order;
import model.OrderDetail;
import model.OrderDetailInfo;
import model.User;
import service.OrderService;
import util.EmailUtility;
import util.SignatureUtil;
import dao.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import controller.BaseController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;

@WebServlet(name = "CheckoutController", urlPatterns = {"/checkout"})
public class CheckoutController extends BaseController {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setMessages(request);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        // Chưa đăng nhập -> Bắt đăng nhập
        if (user == null) {
            response.sendRedirect("login.jsp?message=login_required");
            return;
        }

        // Giỏ hàng trống -> Về trang chủ
        if (cart == null || cart.getItems().isEmpty()) {
        	response.sendRedirect("product?message=cart_empty");
            return;
        }
              
        // Hiển thị trang checkout
        request.setAttribute("user", user);
        request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        if (user != null && cart != null) {
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String fullShippingInfo = address + " - SĐT: " + phone;

            // Thực hiện đặt hàng và nhận về orderId
            int orderId = orderService.placeOrder(user, cart, fullShippingInfo);

            if (orderId > 0) {
                Order newOrder = orderService.getOrderById(orderId);
                
                // 1. Tạo chuỗi mã băm từ các sản phẩm được gom (Đã sort product_id ASC để tránh lệch hash)
                OrderDAO orderDAO = new OrderDAO();
                List<OrderDetail> rawDetails = orderDAO.getRawDetailsForHash(orderId);
                StringBuilder detailStrBuilder = new StringBuilder();
                for (int i = 0; i < rawDetails.size(); i++) {
                    OrderDetail d = rawDetails.get(i);
                    detailStrBuilder.append("pid").append(d.getProductId())
                            .append("_q").append(d.getQuantity())
                            .append("_p").append((long)d.getPrice());
                    if (i < rawDetails.size() - 1) detailStrBuilder.append("|");
                }
                
                String orderHash = SignatureUtil.buildOrderHash(orderId, user.getId(), newOrder.getTotalMoney(), detailStrBuilder.toString());
                
                // 2. Chuyển trạng thái đơn hàng thành Chờ ký số và lưu hash
                orderDAO.updateOrderHashAndStatus(orderId, orderHash, "Chờ ký số");

                // LẤY THÔNG TIN ĐỂ GỬI MAIL (Trước khi xóa giỏ hàng)
                List<OrderDetailInfo> details = orderService.getOrderDetails(orderId);
                
                //  Gửi mail ngầm (Dùng Thread để không làm chậm trang web người dùng)
                new Thread(() -> {
                    EmailUtility.sendOrderConfirmation(user, newOrder, details);
                }).start();

                //  Xóa giỏ và ĐIỀU HƯỚNG SANG TRANG KÝ SỐ
                session.removeAttribute("cart");
                
                // Set attributes cho View
                request.setAttribute("orderId", orderId);
                request.setAttribute("orderHash", orderHash);
                request.setAttribute("totalMoney", newOrder.getTotalMoney());
                request.getRequestDispatcher("/views/checkout-signature.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("errorMessage", "Đặt hàng thất bại! Có thể do hết hàng.");
                request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("home");
        }
    }
}