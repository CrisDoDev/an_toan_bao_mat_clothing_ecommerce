package dao;

import model.Cart;
import model.CartItem;
import model.Order;
import model.OrderDetail;
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
					
					// Ánh xạ thêm các trường chữ ký số
					o.setKeyId((Integer) rs.getObject("key_id"));
					o.setDigitalSignature(rs.getString("digital_signature"));
					o.setOrderHash(rs.getString("order_hash"));
					o.setSignedAt(rs.getTimestamp("signed_at"));

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
		String query = "SELECT o.*, k.public_key_text FROM Orders o LEFT JOIN UserKeys k ON o.key_id = k.key_id";

		if (status != null && !status.equals("all")) {
			query += " WHERE o.status = ?";
		}
		query += " ORDER BY o.order_date DESC";

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
					
					// Ánh xạ thêm các trường chữ ký số
					o.setKeyId((Integer) rs.getObject("key_id"));
					o.setDigitalSignature(rs.getString("digital_signature"));
					o.setOrderHash(rs.getString("order_hash"));
					o.setSignedAt(rs.getTimestamp("signed_at"));
					o.setPublicKeyText(rs.getString("public_key_text"));
					
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
						
						// Ánh xạ thêm các trường chữ ký số
						o.setKeyId((Integer) rs.getObject("key_id"));
						o.setDigitalSignature(rs.getString("digital_signature"));
						o.setOrderHash(rs.getString("order_hash"));
						o.setSignedAt(rs.getTimestamp("signed_at"));
						
						return o;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	
	
	// Lấy nguyên bản Detail để thực hiện sinh/kiểm tra mã băm
	public List<OrderDetail> getRawDetailsForHash(int orderId) {
		List<OrderDetail> list = new ArrayList<>();
		String query = "SELECT product_id, quantity, price FROM OrderDetails WHERE order_id = ? ORDER BY product_id ASC";
		
		try (Connection conn = DBContext.getDataSource().getConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setInt(1, orderId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					OrderDetail d = new OrderDetail();
					d.setProductId(rs.getInt("product_id"));
					d.setQuantity(rs.getInt("quantity"));
					d.setPrice(rs.getDouble("price"));
					list.add(d);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Update mã băm và chuyển trạng thái 
	public void updateOrderHashAndStatus(int orderId, String hash, String status) {
		String query = "UPDATE Orders SET order_hash = ?, status = ? WHERE order_id = ?";
		try (Connection conn = DBContext.getDataSource().getConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, hash);
			ps.setString(2, status);
			ps.setInt(3, orderId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Update khi lưu chữ ký số hợp lệ
	public void updateOrderSignature(int orderId, int keyId, String signature, String status) {
		String query = "UPDATE Orders SET key_id = ?, digital_signature = ?, signed_at = GETDATE(), status = ? WHERE order_id = ?";
		try (Connection conn = DBContext.getDataSource().getConnection();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, keyId);
			ps.setString(2, signature);
			ps.setString(3, status);
			ps.setInt(4, orderId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}