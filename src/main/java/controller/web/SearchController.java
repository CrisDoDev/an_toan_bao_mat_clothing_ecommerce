package controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.BaseController; // Kế thừa từ đây
import model.Product;
import service.ProductService;

/**
 * Servlet implementation class SearchController
 */
@WebServlet(name = "SearchController", urlPatterns = { "/search" })
public class SearchController extends BaseController { 
	private static final long serialVersionUID = 1L;

	public SearchController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		// Get current language
		HttpSession session = request.getSession();
		String lang = (String) session.getAttribute("lang");
		if (lang == null) lang = "vi";
		
		Locale locale = (Locale) session.getAttribute("locale");
		if (locale == null) locale = new Locale("vi", "VN");

		String txtSearch = request.getParameter("txt");

		// --- SỬA LỖI TẠI ĐÂY ---		
		// BaseController
		NumberFormat nf = getCurrencyFormat(request);
		ResourceBundle msgs = ResourceBundle.getBundle("messages", locale);

		try (PrintWriter out = response.getWriter()) {
			ProductService service = new ProductService();
			List<Product> list = service.searchByName(txtSearch);

			if (list != null && !list.isEmpty()) {
				for (Product p : list) {
					// Format giá tiền tự động theo ngôn ngữ (VNĐ hoặc USD)
					String price = nf.format(p.getPrice());
					
					// Get localized product name
					String productName = "en".equals(lang) ? p.getNameEn() : p.getName();

					// In ra HTML từng dòng sản phẩm gợi ý
					out.println("<li class='header-cart-item flex-w flex-t m-b-12'>");

					// Ảnh sản phẩm
					out.println("<div class='header-cart-item-img'>");
					out.println("<img src='images/" + p.getImageUrl() + "' alt='IMG'>");
					out.println("</div>");

					// Thông tin
					out.println("<div class='header-cart-item-txt p-t-8'>");
					// Sửa lại đường dẫn detail cho chuẩn (dùng product-detail thay vì detail nếu
					// cần)
					out.println("<a href='product-detail?id=" + p.getId()
							+ "' class='header-cart-item-name m-b-18 hov-cl1 trans-04'>");
					out.println(productName);
					out.println("</a>");
					out.println("<span class='header-cart-item-info'>" + price + "</span>");
					out.println("</div>");

					out.println("</li>");
				}
			} else {
				
				// out.println("<li class='p-t-10 p-b-10 text-center'>" +
				// getMessages(request).getString("message.noSearchResults") + "</li>");
				out.println("<li class='p-t-10 p-b-10 text-center'>" + msgs.getString("message.noSearchResults") + "</li>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}