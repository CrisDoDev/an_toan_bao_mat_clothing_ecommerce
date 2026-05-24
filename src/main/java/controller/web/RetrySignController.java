package controller.web;

import dao.OrderDAO;
import model.Order;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RetrySignController", urlPatterns = {"/retry-sign"})
public class RetrySignController extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp?message=login_required");
            return;
        }

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Order order = orderDAO.getOrderById(orderId);

            if (order != null && "Chờ ký số".equals(order.getStatus()) && order.getUserId() == user.getId()) {
                request.setAttribute("orderId", order.getId());
                request.setAttribute("orderHash", order.getOrderHash());
                request.setAttribute("totalMoney", order.getTotalMoney());
                request.getRequestDispatcher("/views/checkout-signature.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("order-history");
    }
}