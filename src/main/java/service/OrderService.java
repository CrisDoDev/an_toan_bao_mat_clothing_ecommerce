package service;

import dao.OrderDAO;
import dao.ProductDAO;
import model.Cart;
import model.CartItem;
import model.Order;
import model.OrderDetailInfo;
import model.User;
import util.DBContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();
    private final ProductDAO productDAO = new ProductDAO();

    //Xử lý Transaction mua hàng
    public int placeOrder(User user, Cart cart, String address) {
        Connection conn = null;
        try {
            conn = DBContext.getDataSource().getConnection();
            conn.setAutoCommit(false); 

            // Insert đơn hàng và lấy ID
            int orderId = orderDAO.insertOrder(conn, user, cart, address);
            
            if (orderId == -1) throw new SQLException("Loi Order.");

            // Duyệt giỏ hàng
            for (model.CartItem item : cart.getItems()) {
                int productId = item.getProduct().getId();
                int sizeId = productDAO.getSizeId(productId, item.getSize());
                
                orderDAO.insertOrderDetail(conn, orderId, productId, sizeId, item.getQuantity(), item.getProduct().getPrice());
                
                if (!productDAO.decreaseStock(conn, sizeId, item.getQuantity())) {
                    throw new SQLException("Hết hàng sản phẩm: " + item.getProduct().getName());
                }
            }

            conn.commit();
            return orderId; 

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return -1; 
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    
    }
    
 // (ADMIN) Lấy danh sách đơn hàng
    public List<model.Order> getAllOrders(String status) {
        return orderDAO.getAllOrders(status);
    }

    // (ADMIN) Cập nhật trạng thái
    public void updateOrderStatus(int orderId, String status) {
        orderDAO.updateOrderStatus(orderId, status);
    }
    
    public List<OrderDetailInfo> getOrderDetails(int orderId) {
        return orderDAO.getOrderDetails(orderId);
    }
    
    public Order getOrderById(int orderId) {
    	return orderDAO.getOrderById(orderId);
    }
}