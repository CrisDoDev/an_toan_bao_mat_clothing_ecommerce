package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Role;
import model.User;

@WebFilter(filterName = "AuthorizationFilter", urlPatterns = { "/admin/*" })
public class AuthorizationFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // Lấy session hiện tại, không tạo mới nếu chưa có

        // Lấy thông tin User và đường dẫn hiện tại
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        String requestURI = req.getRequestURI();
        
        // Loại bỏ context path để lấy đường dẫn tương đối (ví dụ: /checkout, /login)
        String path = requestURI.substring(req.getContextPath().length());

        
        
        // Nhóm trang chỉ dành cho người CHƯA đăng nhập (Guest)
        boolean isGuestPage = path.equals("/login") || path.equals("/register");
        
        // Nhóm trang bắt buộc phải đăng nhập (User)
        boolean isUserPage = path.equals("/checkout") || path.startsWith("/account") || path.equals("/order-history");
        
        // Nhóm trang Admin
        boolean isAdminPage = path.startsWith("/admin");
        
        // XỬ LÝ LOGIC CHẶN

        // Đã đăng nhập mà cố vào trang Login/Register -> Đuổi về Home
        if (user != null && isGuestPage) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return; 
        }

        // Chưa đăng nhập mà cố vào trang Checkout/User -> Bắt Login
        if (user == null && isUserPage) {
            resp.sendRedirect(req.getContextPath() + "/login?message=login_required");
            return;
        }

        // Vào trang Admin 
        if (isAdminPage) {
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            } else if (user.getRoleId() != 1) { // Giả sử 1 là Admin
                resp.sendRedirect(req.getContextPath() + "/access-denied"); // Hoặc về Home
                return;
            }
        }
        chain.doFilter(request, response);
	}

	// Dọn dẹp tài nguyên
	@Override
	public void destroy() {
	}
}