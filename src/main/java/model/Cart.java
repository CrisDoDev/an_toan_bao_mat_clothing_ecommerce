package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    // Thêm sản phẩm vào giỏ
    public void addItem(CartItem newItem) {
        // Kiểm tra xem sản phẩm + size này đã có trong giỏ chưa
        for (CartItem item : items) {
            if (item.getProduct().getId() == newItem.getProduct().getId() 
                && item.getSize().equals(newItem.getSize())) {
                
                // Nếu có rồi thì cộng dồn số lượng
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        //  Nếu chưa có thì thêm mới
        items.add(newItem);
    }

    // Xóa sản phẩm khỏi giỏ
    public void removeItem(int productId, String size) {
        items.removeIf(item -> item.getProduct().getId() == productId 
                               && item.getSize().equals(size));
    }

    // Cập nhật số lượng
    public void updateItem(int productId, String size, int newQuantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId 
                && item.getSize().equals(size)) {
                
                item.setQuantity(newQuantity);
                return;
            }
        }
    }

    // Tính tổng tiền cả giỏ hàng
    public double getTotalMoney() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }
    
    // Đếm tổng số lượng item 
    public int getTotalQuantity() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getQuantity();
        }
        return total;
    }
}