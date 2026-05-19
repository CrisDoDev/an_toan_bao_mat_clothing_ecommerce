package model;

import java.util.ArrayList;
import java.util.List;

public class Product {
	private int product_id;
	private String name;
	private String nameEn;
	private String description;
	private String descriptionEn;
	private double price;
	private String imageUrl;
	private int categoryId;
	private boolean isDeleted;
	private List<ProductSizes> listSizes = new ArrayList<>();

	public Product() {
	}

	public Product(int product_id, String name, String description, double price, String imageUrl, int categoryId) {
		this.product_id = product_id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imageUrl = imageUrl;
		this.categoryId = categoryId;
	}

	public int getId() {
		return product_id;
	}

	public void setId(int product_id) {
		this.product_id = product_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionEn() {
		return descriptionEn;
	}

	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<ProductSizes> getListSizes() {
		return listSizes;
	}

	public void setListSizes(List<ProductSizes> listSizes) {
		this.listSizes = listSizes;
	}

	// Helper methods for localization
	public String getLocalizedName(String lang) {
		return "en".equals(lang) ? nameEn : name;
	}

	public String getLocalizedDescription(String lang) {
		return "en".equals(lang) ? descriptionEn : description;
	}

	@Override
	public String toString() {
		return "Product [id=" + product_id + ", name=" + name + ", price=" + price + "]";
	}
}