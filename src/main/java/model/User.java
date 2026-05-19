package model;

public class User {
	private int id;
	private String fullName;
	private String email;
	private String password;
	private String phone;
	private String address;
	private int roleId;


	public User() {
	}

	// Constructor đầy đủ
	public User(String fullName, String email, String password, String phone, int roleId) {
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.roleId = roleId;
	}

	// Constructor dùng khi map từ DB lên (có ID)
	public User(int id, String fullName, String email, String password, String phone, String address, int roleId) {
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.roleId = roleId;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}