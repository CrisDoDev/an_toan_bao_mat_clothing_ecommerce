package controller.web;

import dao.UserKeyDAO;
import model.User;
import model.UserKey;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserKeyController", urlPatterns = {"/user-key"})
public class UserKeyController extends HttpServlet {

    private final UserKeyDAO keyDAO = new UserKeyDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp?message=login_required");
            return;
        }

        // Lấy danh sách khóa cũ để hiển thị
        List<UserKey> userKeys = keyDAO.getAllKeysByUserId(user.getId());
        request.setAttribute("userKeys", userKeys);
        
        // Kiểm tra xem có khóa active không
        UserKey activeKey = keyDAO.getLatestActiveKey(user.getId());
        request.setAttribute("hasActiveKey", activeKey != null);

        request.getRequestDispatcher("/views/user-key.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp?message=login_required");
            return;
        }

        String action = request.getParameter("action");

        if ("upload".equals(action)) {
            String publicKeyText = request.getParameter("publicKey");
            if (publicKeyText != null && !publicKeyText.trim().isEmpty()) {
                // Hủy khóa cũ (nếu có muốn chỉ giữ 1 khóa 1 lúc)
                keyDAO.revokeKey(user.getId());
                
                // Lưu khóa mới
                UserKey newKey = new UserKey();
                newKey.setUserId(user.getId());
                newKey.setPublicKeyText(publicKeyText.trim());
                keyDAO.insertKey(newKey);
                
                request.setAttribute("successMessage", "Cập nhật Public Key mới thành công!");
            }
        } else if ("revoke".equals(action)) {
            keyDAO.revokeKey(user.getId());
            request.setAttribute("successMessage", "Đã báo mất và vô hiệu hóa khóa thành công!");
        }

        // Tải lại list
        List<UserKey> userKeys = keyDAO.getAllKeysByUserId(user.getId());
        request.setAttribute("userKeys", userKeys);
        UserKey activeKey = keyDAO.getLatestActiveKey(user.getId());
        request.setAttribute("hasActiveKey", activeKey != null);

        request.getRequestDispatcher("/views/user-key.jsp").forward(request, response);
    }
}