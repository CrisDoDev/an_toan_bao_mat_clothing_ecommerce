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
import model.ProductSizes;
import service.CategoryService;
import service.ProductService;

/**
 * Servlet implementation class ProductDetailController
 */
@WebServlet(name = "ProductDetailController", urlPatterns = {"/detail"})
public class ProductDetailController extends BaseController {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		
		String id = request.getParameter("pid");
		
		ProductService productService = new ProductService();
		CategoryService categoryService = new CategoryService();
		
		Product product = productService.getProductById(id);
		List<Category> listCategory = categoryService.getAllCategories();
		request.setAttribute("product", product);
		request.setAttribute("listCategory", listCategory);
		
		List<ProductSizes> listSizes = productService.getProductSizesByProductId(id);
		request.setAttribute("listSizes", listSizes);
		
		request.getRequestDispatcher("/views/product-detail.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
