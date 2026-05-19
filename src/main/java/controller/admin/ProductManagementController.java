package controller.admin;

import model.Category;
import model.Product;
import service.CategoryService;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import controller.BaseController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductManagementController", urlPatterns = {"/admin/products"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, 
    maxFileSize = 1024 * 1024 * 10,     
    maxRequestSize = 1024 * 1024 * 50    
)
public class ProductManagementController extends BaseController {

    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        // Sử dụng logic i18n tập trung từ BaseController
        setMessages(request);

        String action = request.getParameter("action");
        
        try {
            if ("delete".equals(action)) {
                String id = request.getParameter("id");
                productService.softDeleteProduct(Integer.parseInt(id)); 
                response.sendRedirect("products?message=soft_deleted");
                
            } else if ("destroy".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));                
                boolean isDeleted = productService.permanentlyDeleteProduct(id);                
                if (isDeleted) {
                    response.sendRedirect("products?message=destroyed");
                } else {
                    response.sendRedirect("products?message=cannot_delete");
                }
                
            } else if ("restore".equals(action)) {
                String id = request.getParameter("id");
                productService.restoreProduct(Integer.parseInt(id));
                response.sendRedirect("products?message=restored");
                
            } else {
                List<Product> listProduct = productService.getAllProductsForAdmin();
                List<Category> listCategory = categoryService.getAllCategories();            
                
                request.setAttribute("listProduct", listProduct);
                request.setAttribute("listCategory", listCategory);           
                request.getRequestDispatcher("/views/admin/product-manager.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("products?error=fail");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<String> errors = new ArrayList<>();        
        try {
            String action = request.getParameter("action"); 
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String categoryIdStr = request.getParameter("categoryId");

            if (name == null || name.trim().isEmpty()) {
                errors.add("Tên sản phẩm không được để trống.");
            }

            double price = 0;
            try {
                price = Double.parseDouble(priceStr);
                if (price < 0) errors.add("Giá sản phẩm không được âm.");
            } catch (NumberFormatException e) {
                errors.add("Giá sản phẩm phải là số hợp lệ.");
            }

            int categoryId = 1;
            try {
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (NumberFormatException e) {
                errors.add("Danh mục không hợp lệ.");
            }

            Part filePart = request.getPart("image");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String oldImage = request.getParameter("old_image");
            
            String finalImageName = null;

            if (fileName != null && !fileName.isEmpty()) {
                if (!fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".png") && !fileName.toLowerCase().endsWith(".jpeg")) {
                    errors.add("Chỉ chấp nhận file ảnh (JPG, PNG).");
                } else {
                    String tomcatPath = getServletContext().getRealPath("") + File.separator + "images";
                    saveFile(filePart, tomcatPath, fileName);                       
                    
                    String projectPath = "D:\\Java\\Java Web Programming\\eclipse\\Workspace\\clothing-ecommerce-project\\src\\main\\webapp\\images"; 
                    saveFile(filePart, projectPath, fileName);
                    
                    finalImageName = fileName;
                }
            } else {
                if ("update".equals(action)) {
                    finalImageName = oldImage; 
                } else {
                    errors.add("Vui lòng chọn hình ảnh cho sản phẩm mới.");
                }
            }

            String[] quantities = request.getParameterValues("size_quantities");
            if (quantities != null) {
                for (String qty : quantities) {
                    try {
                        int q = Integer.parseInt(qty);
                        if (q < 0) {
                            errors.add("Số lượng size không được âm.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        errors.add("Số lượng size phải là số nguyên.");
                        break;
                    }
                }
            }

            if (!errors.isEmpty()) {
                request.setAttribute("error", String.join("<br>", errors)); 
                request.setAttribute("listProduct", productService.getAllProducts()); 
                request.setAttribute("listCategory", new CategoryService().getAllCategories());
                request.getRequestDispatcher("/views/admin/product-manager.jsp").forward(request, response);
                return; 
            }

            if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Product p = new Product();
                p.setId(id);
                p.setName(name);
                p.setPrice(price);
                p.setDescription(description);
                p.setCategoryId(categoryId);
                p.setImageUrl(finalImageName);                
                productService.updateProduct(p);
                handleProductSizes(request, id, true); 

                response.sendRedirect("products?message=updated");
            } 
            else {
                Product p = new Product();
                p.setName(name);
                p.setPrice(price);
                p.setDescription(description);
                p.setCategoryId(categoryId);
                p.setImageUrl(finalImageName);                                
                
                int newProductId = productService.insertProduct(p);               
                
                if (newProductId != -1) {
                    handleProductSizes(request, newProductId, false); 
                    response.sendRedirect("products?message=success");
                } else {
                    response.sendRedirect("products?error=insert_failed");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("/views/admin/product-manager.jsp").forward(request, response);
        }
    }

    private void handleProductSizes(HttpServletRequest request, int productId, boolean isUpdate) {
        List<model.ProductSizes> currentDbSizes = new ArrayList<>();
        if (isUpdate) {
            currentDbSizes = productService.getProductSizesByProductId(String.valueOf(productId));
        }

        String[] sizeIds = request.getParameterValues("size_ids");
        String[] sizes = request.getParameterValues("size_names");
        String[] quantities = request.getParameterValues("size_quantities");
        List<Integer> keptSizeIds = new ArrayList<>();

        if (sizes != null && quantities != null) {
            for (int i = 0; i < sizes.length; i++) {
                String sizeName = sizes[i].trim();
                if (!sizeName.isEmpty()) {
                    int qty = 0;
                    try { qty = Integer.parseInt(quantities[i]); } catch (Exception e) {}                             
                    
                    int sizeId = 0;
                    if (isUpdate && sizeIds != null && i < sizeIds.length && !sizeIds[i].isEmpty()) {
                        try { 
                            sizeId = Integer.parseInt(sizeIds[i]); 
                            keptSizeIds.add(sizeId);
                        } catch (Exception e) {}
                    }

                    if (sizeId > 0) {
                        productService.updateProductSize(sizeId, sizeName, qty);
                    } else {
                        productService.insertProductSize(productId, sizeName, qty);
                    }
                }
            }
        }      
        if (isUpdate && currentDbSizes != null) {
            for (model.ProductSizes dbSize : currentDbSizes) {
                if (!keptSizeIds.contains(dbSize.getId())) {
                    productService.deleteProductSize(dbSize.getId());
                }
            }
        }
    }
    
    private void saveFile(Part filePart, String path, String fileName) throws IOException {
        File uploadDir = new File(path);
        if (!uploadDir.exists()) uploadDir.mkdirs();     
        try (InputStream input = filePart.getInputStream()) {
            java.nio.file.Files.copy(input, new File(path + File.separator + fileName).toPath(), 
                java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}