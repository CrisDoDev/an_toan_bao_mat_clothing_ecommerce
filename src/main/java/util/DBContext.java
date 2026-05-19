package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource; // Import chuẩn của Java

public class DBContext {
	// Biến static để giữ Connection Pool duy nhất
	private static HikariDataSource dataSource;

	static {
		try {
			HikariConfig config = new HikariConfig();

			// --- Cấu hình SQL Server
			config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			config.setJdbcUrl(
					"jdbc:sqlserver://localhost:1433;databaseName=ECommerceDB;encrypt=true;trustServerCertificate=true;");
			config.setUsername("sa");
			config.setPassword("123456");

			// --- Cấu hình HikariCP
			config.setMaximumPoolSize(10); // Tối đa 10 kết nối
			config.setMinimumIdle(2); // Giữ ít nhất 2 kết nối rảnh
			config.setIdleTimeout(30000); // 30s không dùng sẽ thu hồi
			config.setPoolName("ProjectPool");

			dataSource = new HikariDataSource(config);

		} catch (Exception e) {
			throw new RuntimeException("Không thể khởi tạo HikariCP!", e);
		}
	}

	// Constructor private để chặn việc "new DBContext()" lung tung
	private DBContext() {
	}

	// Phương thức static để các DAO lấy nguồn dữ liệu
	public static DataSource getDataSource() {
		return dataSource;
	}
}

