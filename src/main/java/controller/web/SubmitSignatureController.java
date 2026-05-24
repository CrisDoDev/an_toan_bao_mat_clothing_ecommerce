package controller.web;

import dao.OrderDAO;
import dao.UserKeyDAO;
import model.User;
import model.UserKey;
import util.SignatureUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SubmitSignatureController", urlPatterns = {"/submit-signature"})
public class SubmitSignatureController extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();
    private final UserKeyDAO keyDAO = new UserKeyDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp?message=login_required");
            return;
        }

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String orderHash = request.getParameter("orderHash");
            String signature = request.getParameter("signature");

            // Lấy pub key đang xài
            UserKey activeKey = keyDAO.getLatestActiveKey(user.getId());
            
            if (activeKey == null) {
                // Khong co pub key -> quay lai bat ky tiep
                request.setAttribute("errorMessage", "Bạn chưa thiết lập Public Key. Vui lòng vào Cài đặt tài khoản để cập nhật!");
                request.setAttribute("orderId", orderId);
                request.setAttribute("orderHash", orderHash);
                request.getRequestDispatcher("/views/checkout-signature.jsp").forward(request, response);
                return;
            }

            // Xac thuc
            boolean isIntact = SignatureUtil.verifySignature(orderHash, signature, activeKey.getPublicKeyText());

            if (isIntact) {
                // UPDATE VAO DB -> Đã xác thực
                orderDAO.updateOrderSignature(orderId, activeKey.getKeyId(), signature, "Đã xác thực");
                response.sendRedirect("home?status=order_success");
            } else {
                request.setAttribute("errorMessage", "Chữ ký số sai lệch hoặc không trùng khớp với tài khoản, vui lòng thử lại!");
                request.setAttribute("orderId", orderId);
                request.setAttribute("orderHash", orderHash);
                request.getRequestDispatcher("/views/checkout-signature.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("home?status=order_error");
        }
    }
}