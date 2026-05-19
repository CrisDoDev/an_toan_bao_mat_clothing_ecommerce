package controller.web;

import model.Cart;
import model.CartItem;
import model.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import controller.BaseController;
import dao.ProductDAO;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;

@WebServlet(name = "CartController", urlPatterns = { "/cart" })
public class CartController extends BaseController {

	private final ProductService productService = new ProductService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		setMessages(request);

		String action = request.getParameter("action");

		// Lấy giỏ hàng từ Session
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}

		// Xử lý Xóa item
		if ("remove".equals(action)) {
			int id = Integer.parseInt(request.getParameter("id"));
			String size = request.getParameter("size");
			cart.removeItem(id, size);

			response.sendRedirect("cart");
			return;
		}

		// Mặc định: Chuyển sang trang xem giỏ hàng
		request.getRequestDispatcher("views/shoping-cart.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");

		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}

		// THÊM VÀO GIỎ
		if ("add".equals(action)) {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				String size = request.getParameter("size");

				Product product = productService.getProductById(String.valueOf(id));

				ProductDAO productDAO = new ProductDAO();
				int currentStock = productDAO.getStockBySize(id, size);

				if (product != null) {
					// Truyền thêm currentStock vào Constructor
					CartItem item = new CartItem(product, quantity, size, currentStock);
					cart.addItem(item);
				}
				response.sendRedirect("cart");
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("home");
			}
		}

		// CẬP NHẬT GIỎ HÀNG
		else if ("update".equals(action) || "checkout".equals(action)) {
			// Duyệt qua tất cả tham số được gửi lên từ form
			Enumeration<String> paramNames = request.getParameterNames();

			while (paramNames.hasMoreElements()) {
				String paramName = paramNames.nextElement();

				if (paramName.startsWith("num_product_")) {
					try {

						String[] parts = paramName.split("_");

						if (parts.length >= 4) {
							int productId = Integer.parseInt(parts[2]);
							String size = parts[3];

							int newQuantity = Integer.parseInt(request.getParameter(paramName));
							// Check lại tồn kho
							int currentStock = new ProductDAO().getStockBySize(productId, size);

							if (newQuantity > currentStock) {
								newQuantity = currentStock; // Nếu nhập lố, ép về max
							}

							if (newQuantity > 0) {
								cart.updateItem(productId, size, newQuantity);
							} else {
								cart.removeItem(productId, size);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if ("checkout".equals(action)) {
		        // Nếu bấm nút Thanh toán -> Update xong thì chuyển sang Checkout
		        response.sendRedirect("checkout");
		    } else {
		        // Nếu bấm nút Cập nhật -> Reload lại trang giỏ hàng
		        response.sendRedirect("cart");
		    }
		}
	}
}