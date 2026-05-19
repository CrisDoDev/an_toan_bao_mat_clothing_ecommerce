package model;

public class CartItem {
    private Product product;
    private int quantity;
    private String size; 
    private int stockQuantity;

    public CartItem() {
    }

    public CartItem(Product product, int quantity, String size, int stockQuantity) {
        this.product = product;
        this.quantity = quantity;
        this.size = size;
        this.stockQuantity = stockQuantity;
    }


    // Getters Setters
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    
    // Tính tổng tiền của item 
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}