package controller.auth;

import service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import controller.BaseController;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = { "/register" })
public class RegisterServlet extends BaseController {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Sử dụng phương thức tập trung để thiết lập đa ngôn ngữ
        setMessages(request);
        
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String pass = request.getParameter("password");
        String confirmPass = request.getParameter("confirmPassword");
        
        // Kiểm tra số điện thoại (Ví dụ: 10 số, bắt đầu bằng 0)
        
        if (phone == null || !phone.matches("^0\\d{9}$")) {
            handleError(request, response, "register.errorPhone", fullName, email, phone);
            return; 
        }

        // Kiểm tra khớp mật khẩu
        
        if (!pass.equals(confirmPass)) {
            handleError(request, response, "register.errorPassword", fullName, email, phone);
            return;
        }

        UserService service = new UserService();
        boolean isSuccess = service.register(fullName, email, pass, phone);

        if (isSuccess) {
            // Đăng ký thành công, chuyển hướng sang trang đăng nhập
            response.sendRedirect("login.jsp?status=success");
        } else {
            // Email đã tồn tại hoặc lỗi hệ thống
            
            handleError(request, response, "register.errorEmail", fullName, email, phone);
        }
    }

    /**
     * Hàm hỗ trợ xử lý lỗi đăng ký để tránh lặp code
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, 
                             String errorKey, String fullName, String email, String phone) 
            throws ServletException, IOException {
        
        setMessages(request); 
        
        // Lấy thông báo lỗi từ ResourceBundle dựa trên key
        String errorMsg = "";
        try {
            errorMsg = getMessages(request).getString(errorKey);
        } catch (Exception e) {
            // Fallback nếu quên khai báo key
            errorMsg = "Error: " + errorKey; 
        }

        request.setAttribute("errorMessage", errorMsg);
        
        // Giữ lại các giá trị user đã nhập
        request.setAttribute("fullname", fullName);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}