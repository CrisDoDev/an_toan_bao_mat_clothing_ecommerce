package dao;

import model.User;
import util.DBContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

	private final DataSource dataSource;

	public UserDAO() {
		this.dataSource = DBContext.getDataSource();
	}

	// Lấy thông tin User theo Email
	public User getUserByEmail(String email) {
		String query = "SELECT * FROM Users WHERE email = ?";
		User user = null;

		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, email);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					user = mapRowToUser(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	// Kiểm tra Email đã tồn tại hay chưa
	public boolean checkEmailExist(String email) {
		String query = "SELECT COUNT(*) FROM Users WHERE email = ?";

		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, email);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Đăng ký tài khoản mới
	public void register(User user) {
		String query = "INSERT INTO Users (full_name, email, password, phone, role_id) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, user.getFullName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getPhone());
			ps.setInt(5, user.getRoleId()); // Thường là 2 (Customer)

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Helper method
	private User mapRowToUser(ResultSet rs) throws SQLException {
		return new User(rs.getInt("user_id"), rs.getString("full_name"), rs.getString("email"),
				rs.getString("password"), rs.getString("phone"), rs.getString("address"), rs.getInt("role_id"));
	}
}