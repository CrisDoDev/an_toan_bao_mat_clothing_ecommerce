package service;

import model.Category;
import java.util.List;
import dao.CategoryDAO;

public class CategoryService {
    // Dependency: Service phụ thuộc vào DAO
    private final CategoryDAO categoryDAO;

    public CategoryService() {
        // Khởi tạo DAO ( DAO sẽ tự động kết nối với DataSource bên trong nó)
        this.categoryDAO = new CategoryDAO();
    }

    public List<Category> getAllCategories() {
       
        return categoryDAO.getAllCategories();
    }
}
