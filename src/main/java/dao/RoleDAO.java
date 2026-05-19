package dao;

import model.Role;
import util.DBContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

	private final DataSource dataSource;

	public RoleDAO() {
		this.dataSource = DBContext.getDataSource();
	}

	// Lấy tất cả danh sách quyền dùng để hiển thị trong trang Admin quản lý
	public List<Role> getAllRoles() {
		List<Role> list = new ArrayList<>();
		String query = "SELECT * FROM Roles";

		try (Connection conn = this.dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(new Role(rs.getInt("role_id"), rs.getString("role_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Lấy tên Role dựa vào ID
	public String getRoleNameById(int roleId) {
		String query = "SELECT role_name FROM Roles WHERE role_id = ?";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, roleId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getString("role_name");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}