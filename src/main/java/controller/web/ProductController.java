package controller.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseController;
import model.Category;
import model.Product;
import service.CategoryService;
import service.ProductService;

@WebServlet(name = "ProductController", urlPatterns = {"/product"})
public class ProductController extends BaseController {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        setMessages(request);
        
        // Add i18n support
        ResourceBundle msgs = getMessages(request);
        NumberFormat currencyFormat = getCurrencyFormat(request);
        DateFormat dateFormat = getDateFormat(request);
        
        request.setAttribute("msgs", msgs);
        request.setAttribute("currencyFormat", currencyFormat);
        request.setAttribute("dateFormat", dateFormat);
        
        try {
            ProductService productService = new ProductService();
            CategoryService categoryService = new CategoryService();
            
            // Lấy các tham số từ URL
            String pageStr = request.getParameter("page");
            String cid = request.getParameter("cid");
            String priceRange = request.getParameter("price"); // VD: "200000-500000"
            String sort = request.getParameter("sort");        // VD: "price_asc"
            
            //Xử lý Phân trang
            int index = 1;
            if(pageStr != null) try { index = Integer.parseInt(pageStr); } catch(Exception e) {}
            int pageSize = 12;

            //Xử lý Giá (min - max)
            double minPrice = -1;
            double maxPrice = -1;
            if (priceRange != null && !priceRange.isEmpty() && !priceRange.equals("all")) {
                String[] prices = priceRange.split("-");
                if (prices.length == 2) {
                    minPrice = Double.parseDouble(prices[0]);
                    maxPrice = Double.parseDouble(prices[1]);
                } else if (prices.length == 1) { // Trường hợp "Trên 2tr" (2000000-)
                    minPrice = Double.parseDouble(prices[0]);
                }
            }            
            // Xử lý Sắp xếp
            if (sort == null) sort = "default";

            List<Product> listProduct = productService.filterProducts(cid, minPrice, maxPrice, sort, index, pageSize);
            int totalProduct = productService.countProductsByFilter(cid, minPrice, maxPrice);
            
            //Tính tổng số trang
            int endPage = totalProduct / pageSize;
            if (totalProduct % pageSize != 0) endPage++;

            //Gửi dữ liệu về JSP
            request.setAttribute("listProduct", listProduct);
            request.setAttribute("listCategory", categoryService.getAllCategories());
            request.setAttribute("endPage", endPage);
            request.setAttribute("currentPage", index);
            
            request.setAttribute("tag", cid);     
            request.setAttribute("price", priceRange); 
            request.setAttribute("sort", sort);   
            
            request.getRequestDispatcher("/views/product.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}