package controller.auth;

import model.User;
import service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import controller.BaseController;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends BaseController {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, 	IOException {
        
        // Thiết lập ngôn ngữ và các định dạng cho trang Login
        setMessages(request);
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("username");
        String pass = request.getParameter("password");

        UserService service = new UserService();
        User user = service.login(email, pass);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60);
            
            if (user.getRoleId() == 1) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
        } else {
            // Khi đăng nhập lỗi, thiết lập lại i18n để hiển thị thông báo lỗi và giữ ngôn ngữ trang
            setMessages(request);
            String errorMsg = getMessages(request).getString("login.error");
            request.setAttribute("errorMessage", errorMsg);
            
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}