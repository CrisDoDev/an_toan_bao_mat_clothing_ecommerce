package service;

import dao.UserDAO;

import model.*;


import org.mindrot.jbcrypt.BCrypt;

public class UserService {

	private final UserDAO userDAO;

	public UserService() {
		userDAO = new UserDAO();
	}

	// Xử lý Đăng nhập
	public User login(String email, String password) {
		// Lấy user từ DB
		User user = userDAO.getUserByEmail(email);

		// Nếu user tồn tại, kiểm tra pass hash
		if (user != null) {
			boolean checkPass = BCrypt.checkpw(password, user.getPassword());
			if (checkPass) {
				return user;
			}
		}
		return null; // Sai email hoặc sai pass
	}

	// Xử lý Đăng ký
	public boolean register(String fullName, String email, String password, String phone) {
		// Kiểm tra email trùng
		if (userDAO.checkEmailExist(email)) {
			return false;
		}

		// Mã hóa mật khẩu
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
		
		// Tạo user và lưu xuống DB
		User newUser = new User(fullName, email, hashedPassword, phone, Role.ID_CUSTOMER);
		userDAO.register(newUser);

		return true;
	}
}