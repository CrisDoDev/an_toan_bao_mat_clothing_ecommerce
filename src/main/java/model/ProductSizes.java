package model;

public class ProductSizes {
    private int sizeId;
    private int productId;
    private String size;
    private int stockQuantity;

    public ProductSizes() {
    }

    public ProductSizes(int sizeId, int productId, String size, int stockQuantity) {
        this.sizeId = sizeId;
        this.productId = productId;
        this.size = size;
        this.stockQuantity = stockQuantity;
    }

	public int getId() {
		return sizeId;
	}

	public void setId(int sizeId) {
		this.sizeId = sizeId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
}