package controller.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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

/**
 * Servlet implementation class HomeController
 */
@WebServlet(name = "HomeController", urlPatterns = {"/home", ""})
public class HomeController extends BaseController {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		setMessages(request);

		ProductService productService = new ProductService();
		CategoryService categoryService = new CategoryService();
		
		//Xử lý phân trang
		String indexPage = request.getParameter("page");
		if(indexPage == null) {
			indexPage = "1";
		}
		int index = Integer.parseInt(indexPage);
		int pageSize = 8; // Số sản phẩm hiện ở mỗi trang
		
		//Lấy toàn bộ sản phẩm, danh mục sản phẩm cho trang hiện tại
		
		//Danh sách product lấy từ DB
		List<Product> listProduct = productService.getProductsByPage(index, pageSize);
		List<Category> listCategory = categoryService.getAllCategories();
		
		//Tính toán số lượng trang 
		int totalProduct = productService.countTotalProducts();
		int endPage = totalProduct / pageSize;
		if(totalProduct % pageSize != 0) {
			endPage++;
		}
		 request.setAttribute("listProduct", listProduct);
		 request.setAttribute("listCategory", listCategory);
		 request.setAttribute("endPage", endPage);
		 request.setAttribute("currentPage", index);
		 
		 request.getRequestDispatcher("/views/home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
