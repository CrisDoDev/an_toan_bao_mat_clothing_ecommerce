package controller.web;

import model.Cart;
import model.Order;
import model.OrderDetailInfo;
import model.User;
import service.OrderService;
import util.EmailUtility;

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
                // LẤY THÔNG TIN ĐỂ GỬI MAIL (Trước khi xóa giỏ hàng)
                Order newOrder = orderService.getOrderById(orderId);
                List<OrderDetailInfo> details = orderService.getOrderDetails(orderId);
                
                //  Gửi mail ngầm (Dùng Thread để không làm chậm trang web người dùng)
                new Thread(() -> {
                    EmailUtility.sendOrderConfirmation(user, newOrder, details);
                }).start();

                //  Xóa giỏ và thông báo
                session.removeAttribute("cart");
                response.sendRedirect("home?status=order_success");
            } else {
                request.setAttribute("errorMessage", "Đặt hàng thất bại! Có thể do hết hàng.");
                request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("home");
        }
    }
}