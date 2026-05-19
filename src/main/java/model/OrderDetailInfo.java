package model;

public class OrderDetailInfo {
	private String productName;
	private String productImage;
	private String sizeName;
	private int quantity;
	private double price;

	public OrderDetailInfo(String productName, String productImage, String sizeName, int quantity, double price) {
		this.productName = productName;
		this.productImage = productImage;
		this.sizeName = sizeName;
		this.quantity = quantity;
		this.price = price;
	}

	// Getters
	public String getProductName() {
		return productName;
	}

	public String getProductImage() {
		return productImage;
	}

	public String getSizeName() {
		return sizeName;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public double getTotalPrice() {
		return price * quantity;
	}
}