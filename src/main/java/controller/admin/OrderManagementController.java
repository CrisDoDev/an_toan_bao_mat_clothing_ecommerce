package controller.admin;

import model.Order;
import model.OrderDetailInfo; 
import service.OrderService;
import service.ProductService; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "OrderManagementController", urlPatterns = {"/admin/orders"})
public class OrderManagementController extends BaseController {
    
    private final OrderService orderService = new OrderService();
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        // Sử dụng phương thức gộp từ BaseController
        setMessages(request);

        String action = request.getParameter("action");
        
        // Xem chi tiết
        if ("view_detail".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                List<OrderDetailInfo> details = orderService.getOrderDetails(id);
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                
                // Tự build JSON thủ công
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < details.size(); i++) {
                    OrderDetailInfo d = details.get(i);
                    json.append("{");
                    json.append("\"productName\": \"").append(d.getProductName().replace("\"", "\\\"")).append("\",");
                    json.append("\"productImage\": \"").append(d.getProductImage()).append("\",");
                    json.append("\"sizeName\": \"").append(d.getSizeName() == null ? "N/A" : d.getSizeName()).append("\",");
                    json.append("\"price\": ").append(d.getPrice()).append(",");
                    json.append("\"quantity\": ").append(d.getQuantity());
                    json.append("}");
                    
                    if (i < details.size() - 1) json.append(",");
                }
                json.append("]");
                
                out.print(json.toString());
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        // Xem danh sách
        else {
            String status = request.getParameter("status");
            List<Order> listOrder = orderService.getAllOrders(status);
            request.setAttribute("listOrder", listOrder);
            request.getRequestDispatcher("/views/admin/order-manager.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        // --- CẬP NHẬT TRẠNG THÁI ---
        if ("updateStatus".equals(action)) {
            try {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                String newStatus = request.getParameter("status");
                
                // 1. Lấy thông tin đơn hàng hiện tại để kiểm tra trạng thái cũ
                Order currentOrder = orderService.getOrderById(orderId);
                
                // 2. LOGIC HOÀN KHO:
                if ("Đã hủy".equals(newStatus) && !"Đã hủy".equals(currentOrder.getStatus())) {
                    productService.restoreStockForOrder(orderId);
                }

                // 3. Cập nhật trạng thái mới cho đơn hàng
                orderService.updateOrderStatus(orderId, newStatus);
                
                response.sendRedirect("orders?message=updated");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("orders?error=fail");
            }
        }
    }
}