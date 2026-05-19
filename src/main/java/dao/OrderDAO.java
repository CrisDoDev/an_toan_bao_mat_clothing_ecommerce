package dao;

import model.Cart;
import model.CartItem;
import model.Order;
import model.OrderDetailInfo;
import model.User;
import util.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

	// Trả về Order ID vừa tạo
	// Nhận Connection từ Service để dùng chung Transaction
	public int insertOrder(Connection conn, User user, Cart cart, String address) throws SQLException {

		String query = "INSERT INTO Orders (user_id, total_money, shipping_address, status, order_date) VALUES (?, ?, ?, N'Đã xác nhận', GETDATE())";

		try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, user.getId());
			ps.setDouble(2, cart.getTotalMoney());
			ps.setString(3, address);

			int affectedRows = ps.executeUpdate();

			// Lấy ID sinh ra từ getGeneratedKeys()
			if (affectedRows > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						return rs.getInt(1); // Trả về Order ID vừa tạo
					}
				}
			}
		}
		return -1; // Trả về -1 nếu lỗi
	}

	// Insert Order Detail
	public void insertOrderDetail(Connection conn, int orderId, int productId, int sizeId, int quantity, double price)
			throws SQLException {
		String query = "INSERT INTO OrderDetails (order_id, product_id, size_id, quantity, price) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, orderId);
			ps.setInt(2, productId);
			if (sizeId == -1)
				ps.setNull(3, java.sql.Types.INTEGER);
			else
				ps.setInt(3, sizeId);
			ps.setInt(4, quantity);
			ps.setDouble(5, price);
			ps.executeUpdate();
		}
	}

	// lấy danh sách đơn hàng của 1 user
	public List<Order> getOrdersByUserId(int userId) {
		List<Order> list = new ArrayList<>();
		String query = "SELECT * FROM Orders WHERE user_id = ? ORDER BY order_date DESC"; // Mới nhất lên đầu

		try (Connection conn = DBContext.getDataSource().getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Order o = new Order();
					o.setId(rs.getInt("order_id"));
					o.setUserId(rs.getInt("user_id"));
					o.setTotalMoney(rs.getDouble("total_money"));
					o.setStatus(rs.getString("status"));
					o.setShippingAddress(rs.getString("shipping_address"));
					o.setOrderDate(rs.getTimestamp("order_date"));

					list.add(o);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<OrderDetailInfo> getOrderDetails(int orderId) {
		List<OrderDetailInfo> list = new ArrayList<>();
		// Join để lấy Tên sản phẩm, Ảnh và Tên Size
		String query = "SELECT p.product_name, p.image_url, ps.size_name, d.quantity, d.price " + "FROM OrderDetails d "
				+ "JOIN Products p ON d.product_id = p.product_id " + "JOIN ProductSizes ps ON d.size_id = ps.size_id "
				+ "WHERE d.order_id = ?";

		try (Connection conn = DBContext.getDataSource().getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, orderId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(new OrderDetailInfo(rs.getString("product_name"), rs.getString("image_url"),
							rs.getString("size_name"), rs.getInt("quantity"), rs.getDouble("price")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// [ADMIN] Lấy tất cả đơn hàng
	public List<Order> getAllOrders(String status) {
		List<Order> list = new ArrayList<>();
		String query = "SELECT * FROM Orders";

		if (status != null && !status.equals("all")) {
			query += " WHERE status = ?";
		}
		query += " ORDER BY order_date DESC";

		try (Connection conn = DBContext.getDataSource().getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			if (status != null && !status.equals("all")) {
				ps.setString(1, status);
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Order o = new Order();
					o.setId(rs.getInt("order_id"));
					o.setUserId(rs.getInt("user_id"));
					o.setTotalMoney(rs.getDouble("total_money"));
					o.setStatus(rs.getString("status"));
					o.setShippingAddress(rs.getString("shipping_address"));
					o.setOrderDate(rs.getTimestamp("order_date"));
					list.add(o);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// [ADMIN] Cập nhật trạng thái đơn hàng
	public void updateOrderStatus(int orderId, String status) {
		String query = "UPDATE Orders SET status = ? WHERE order_id = ?";
		try (Connection conn = DBContext.getDataSource().getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, status);
			ps.setInt(2, orderId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Lấy thông tin 1 đơn hàng theo ID
		public Order getOrderById(int orderId) {
			String query = "SELECT * FROM Orders WHERE order_id = ?";
			try (Connection conn = DBContext.getDataSource().getConnection();
				 PreparedStatement ps = conn.prepareStatement(query)) {
				
				ps.setInt(1, orderId);
				
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						Order o = new Order();
						o.setId(rs.getInt("order_id"));
						o.setUserId(rs.getInt("user_id"));
						o.setTotalMoney(rs.getDouble("total_money"));
						o.setStatus(rs.getString("status"));
						o.setShippingAddress(rs.getString("shipping_address"));
						o.setOrderDate(rs.getTimestamp("order_date"));
						return o;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	
	

}