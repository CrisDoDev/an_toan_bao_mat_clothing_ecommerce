package dao;

import model.Category;
import util.DBContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

	// Khai báo DataSource
	private final DataSource dataSource;

	// Constructor: Lấy DataSource từ DBContext ngay khi DAO được khởi tạo
	public CategoryDAO() {
		this.dataSource = DBContext.getDataSource();
	}

	public List<Category> getAllCategories() {
		List<Category> list = new ArrayList<>();
		String sql = "SELECT * FROM Categories"; 

		// --- TRY-WITH-RESOURCES
		// Tự động đóng conn, ps, rs theo thứ tự ngược lại khi block kết thúc
		try (Connection conn = this.dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
	            Category c = new Category();
	            c.setId(rs.getInt("category_id"));
	            c.setName(rs.getString("category_name"));
	            
	            
	            c.setNameEn(rs.getString("category_name_en")); 
	            
	            list.add(c);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
}
}
