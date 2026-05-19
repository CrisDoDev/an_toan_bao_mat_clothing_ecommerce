package controller.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = { "/logout" })
public class LogoutServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false); // Lấy session hiện tại, không tạo mới
		if (session != null) {
			session.invalidate(); // Xóa sạch session
		}

		response.sendRedirect(request.getContextPath() + "/home?status=logged_out");
	}
}
