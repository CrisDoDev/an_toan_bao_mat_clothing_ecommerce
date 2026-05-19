package dao;

import model.Product;
import model.ProductSizes;
import util.DBContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

	// Khai báo DataSource để quản lý pool
	private final DataSource dataSource;

	// Constructor nhận nguồn dữ liệu từ DBContext
	public ProductDAO() {
		this.dataSource = DBContext.getDataSource();
	}

	// Lấy tất cả sản phẩm
	public List<Product> getAllProducts() {
		List<Product> list = new ArrayList<>();
		String query = "SELECT * FROM Products WHERE is_deleted = 0";

		// 3. Dùng try-with-resources để tự động đóng conn, ps, rs
		try (Connection conn = this.dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(mapRowToProduct(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Lấy sản phẩm có phân trang
	public List<Product> getProductsByPage(int index, int size) {
		List<Product> list = new ArrayList<>();
		String query = "SELECT * FROM Products WHERE is_deleted = 0 ORDER BY product_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, (index - 1) * size);
			ps.setInt(2, size);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRowToProduct(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Đếm tổng số sản phẩm
	public int countTotalProducts() {
		String query = "SELECT COUNT(*) FROM Products WHERE is_deleted = 0";
		try (Connection conn = this.dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Lấy chi tiết 1 sản phẩm theo ID
	public Product getProductById(String id) {
		String query = "SELECT * FROM Products WHERE product_id = ?";
		Product product = null;
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					product = mapRowToProduct(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	// Lấy sản phẩm theo Danh mục
	public List<Product> getProductsByCategoryId(String cid) {
		List<Product> list = new ArrayList<>();
		String query = "SELECT * FROM Products WHERE category_id = ? AND is_deleted = 0";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, cid);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRowToProduct(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Lấy danh sách các size của 1 sản phẩm
	public List<ProductSizes> getProductSizesByProductId(int productId) {
		List<ProductSizes> list = new ArrayList<>();
		String query = "SELECT * FROM ProductSizes WHERE product_id = ?";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, productId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(new ProductSizes(rs.getInt("size_id"), rs.getInt("product_id"), rs.getString("size_name"),
							rs.getInt("stock_quantity")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// --- PHẦN FILTER ---
	// Đếm số lượng sản phẩm sau khi lọc (để chia trang)
	public int countProductsByFilter(String cid, double minPrice, double maxPrice) {
		String sql = "SELECT COUNT(*) FROM Products WHERE 1=1 AND is_deleted = 0";

		if (cid != null && !cid.isEmpty()) {
			sql += " AND category_id = " + cid;
		}

		if (minPrice >= 0)
			sql += " AND price >= " + minPrice;
		if (maxPrice > 0)
			sql += " AND price <= " + maxPrice;

		try (Connection conn = this.dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Phương thức lọc sản phẩm chính
	public List<Product> filterProducts(String cid, double minPrice, double maxPrice, String sortType, int index,
			int size) {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT * FROM Products WHERE 1=1 AND is_deleted = 0";

		if (cid != null && !cid.isEmpty()) {
			sql += " AND category_id = " + cid;
		}

		if (minPrice >= 0)
			sql += " AND price >= " + minPrice;
		if (maxPrice > 0)
			sql += " AND price <= " + maxPrice;

		switch (sortType) {
		case "newest":
			sql += " ORDER BY created_date DESC";
			break;
		case "price_asc":
			sql += " ORDER BY price ASC";
			break;
		case "price_desc":
			sql += " ORDER BY price DESC";
			break;
		default:
			sql += " ORDER BY product_id ASC";
			break;
		}

		sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, (index - 1) * size);
			ps.setInt(2, size);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRowToProduct(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Tìm kiếm sản phẩm theo tên
	public List<Product> searchByName(String s) {
		List<Product> list = new ArrayList<>();
		String query = "SELECT * FROM Products WHERE product_name COLLATE SQL_Latin1_General_CP1_CI_AI LIKE ? AND is_deleted = 0";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, "%" + s + "%");

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRowToProduct(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Helper method để map dữ liệu
	private Product mapRowToProduct(ResultSet rs) throws SQLException {
		Product p = new Product(rs.getInt("product_id"), rs.getString("product_name"), rs.getString("description"),
				rs.getDouble("price"), rs.getString("image_url"), rs.getInt("category_id"));
		
		// Add English columns
		try {
			p.setNameEn(rs.getString("product_name_en"));
		} catch (SQLException e) {
			p.setNameEn(null);
		}
		
		try {
			p.setDescriptionEn(rs.getString("description_en"));
		} catch (SQLException e) {
			p.setDescriptionEn(null);
		}
		
		try {
			p.setDeleted(rs.getBoolean("is_deleted"));
		} catch (SQLException e) {
			p.setDeleted(false);
		}
		return p;
	}

	public int getStockBySize(int productId, String sizeName) {
		String query = "SELECT stock_quantity FROM ProductSizes WHERE product_id = ? AND size_name = ?";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, productId);
			ps.setString(2, sizeName);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Hàm lấy ID của Size dựa vào ProductID và Tên Size
	public int getSizeId(int productId, String sizeName) {
		String query = "SELECT size_id FROM ProductSizes WHERE product_id = ? AND size_name = ?";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, productId);
			ps.setString(2, sizeName);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // Không tìm thấy
	}

	// Hàm trừ tồn kho
	public boolean decreaseStock(Connection conn, int sizeId, int quantityToDecrease) throws SQLException {
		String query = "UPDATE ProductSizes SET stock_quantity = stock_quantity - ? WHERE size_id = ? AND stock_quantity >= ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, quantityToDecrease);
			ps.setInt(2, sizeId);
			ps.setInt(3, quantityToDecrease);

			int rows = ps.executeUpdate();
			return rows > 0; 
		}
	}

	// Hoàn trả tồn kho khi hủy đơn hàng
	public void restoreStockForOrder(int orderId) {
		String query = "UPDATE ps " + "SET ps.stock_quantity = ps.stock_quantity + od.quantity "
				+ "FROM ProductSizes ps " + "JOIN OrderDetails od ON ps.size_id = od.size_id "
				+ "WHERE od.order_id = ?";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, orderId);
			int rows = ps.executeUpdate();
			System.out.println("Đã hoàn trả kho cho đơn hàng " + orderId + ". Số dòng ảnh hưởng: " + rows);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// (ADMIN) Thêm sản phẩm mới
	public int insertProduct(Product p) {
		String query = "INSERT INTO Products (product_name, description, price, image_url, category_id, created_date, is_deleted) VALUES (?, ?, ?, ?, ?, GETDATE(), 0)";
		int generatedId = -1;
		try (Connection conn = this.dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, p.getName());
			ps.setString(2, p.getDescription());
			ps.setDouble(3, p.getPrice());
			ps.setString(4, p.getImageUrl());
			ps.setInt(5, p.getCategoryId());

			int affectedRows = ps.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						generatedId = rs.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return generatedId;
	}

	public void insertProductSize(int productId, String sizeName, int quantity) {
		String query = "INSERT INTO ProductSizes (product_id, size_name, stock_quantity) VALUES (?, ?, ?)";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, productId);
			ps.setString(2, sizeName);
			ps.setInt(3, quantity);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// (ADMIN) Cập nhật sản phẩm
	public void updateProduct(Product p) {
		String query = "UPDATE Products SET product_name=?, description=?, price=?, category_id=? WHERE product_id=?";
		if (p.getImageUrl() != null && !p.getImageUrl().isEmpty()) {
			query = "UPDATE Products SET product_name=?, description=?, price=?, category_id=?, image_url=? WHERE product_id=?";
		}
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, p.getName());
			ps.setString(2, p.getDescription());
			ps.setDouble(3, p.getPrice());
			ps.setInt(4, p.getCategoryId());

			if (p.getImageUrl() != null && !p.getImageUrl().isEmpty()) {
				ps.setString(5, p.getImageUrl());
				ps.setInt(6, p.getId());
			} else {
				ps.setInt(5, p.getId());
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// (ADMIN) Xóa sản phẩm
	public void deleteProduct(int id) {
		String query = "DELETE FROM Products WHERE product_id = ?";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// (ADMIN) Cập nhật số lượng tồn kho cho một Size cụ thể
	public void updateStock(int sizeId, int newQuantity) {
		String query = "UPDATE ProductSizes SET stock_quantity = ? WHERE size_id = ?";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, newQuantity);
			ps.setInt(2, sizeId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// (ADMIN) Lấy tất cả sản phẩm (Lấy cả sản phẩm đã xóa để quản lý)
	public List<Product> getAllProductsForAdmin() {
		List<Product> list = new ArrayList<>();
		String query = "SELECT * FROM Products ORDER BY is_deleted ASC, product_id";

		try (Connection conn = this.dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Product p = mapRowToProduct(rs);
				List<ProductSizes> sizes = getSizesByProductId(p.getId());
				p.setListSizes(sizes);

				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Cập nhật thông tin Size
	public void updateProductSize(int sizeId, String sizeName, int quantity) {
		String query = "UPDATE ProductSizes SET size_name = ?, stock_quantity = ? WHERE size_id = ?";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, sizeName);
			ps.setInt(2, quantity);
			ps.setInt(3, sizeId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<ProductSizes> getSizesByProductId(int productId) {
		List<ProductSizes> list = new ArrayList<>();
		String query = "SELECT * FROM ProductSizes WHERE product_id = ?";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, productId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(new ProductSizes(rs.getInt("size_id"), rs.getInt("product_id"), rs.getString("size_name"),
							rs.getInt("stock_quantity")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void deleteProductSize(int sizeId) {
		String query = "DELETE FROM ProductSizes WHERE size_id = ?";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, sizeId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// --- CÁC HÀM XỬ LÝ XÓA/KHÔI PHỤC ---

	// Xóa mềm
	public void softDeleteProduct(int id) {
		String query = "UPDATE Products SET is_deleted = 1 WHERE product_id = ?";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Khôi phục
	public void restoreProduct(int id) {
		String query = "UPDATE Products SET is_deleted = 0 WHERE product_id = ?";
		try (Connection conn = this.dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Xóa vĩnh viễn
	public boolean permanentlyDeleteProduct(int id) {
        String checkQuery = "SELECT COUNT(*) FROM OrderDetails WHERE product_id = ?";
        try (Connection conn = this.dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(checkQuery)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        String sqlSizes = "DELETE FROM ProductSizes WHERE product_id = ?";
        String sqlProduct = "DELETE FROM Products WHERE product_id = ?";
        
        try (Connection conn = this.dataSource.getConnection()) {
            conn.setAutoCommit(false); 
            
            try (PreparedStatement ps1 = conn.prepareStatement(sqlSizes);
                 PreparedStatement ps2 = conn.prepareStatement(sqlProduct)) {
                ps1.setInt(1, id);
                ps1.executeUpdate();
                ps2.setInt(1, id);
                ps2.executeUpdate();              
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback(); 
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}