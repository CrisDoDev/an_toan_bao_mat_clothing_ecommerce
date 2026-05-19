package controller.web;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

import java.util.Locale;
import java.util.ResourceBundle;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseController;
import model.ProductSizes;
import service.ProductService;
import util.FormatUtil;

@WebServlet(name = "ProductSizeController", urlPatterns = {"/product-sizes"})
public class ProductSizeController extends BaseController {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("id");
        
        Locale locale = FormatUtil.getLocale(request);
        ResourceBundle msgs = ResourceBundle.getBundle("messages", locale);
        
        try (PrintWriter out = response.getWriter()) {
            ProductService service = new ProductService();
            List<ProductSizes> listSizes = service.getProductSizesByProductId(id);
            
            out.println("<option value=''>" + msgs.getString("product.selectSize") + "</option>");
            
            for (ProductSizes s : listSizes) {
                if (s.getStockQuantity() > 0) {
                    out.println("<option value='" + s.getSize() + "' data-max='" + s.getStockQuantity() + "'>");
                    out.println("Size " + s.getSize() + " (" + msgs.getString("cart.available") + ": " + s.getStockQuantity() + ")");
                    out.println("</option>");
                } else {
                    out.println("<option disabled>Size " + s.getSize() + " (" + msgs.getString("product.outOfStock") + ")</option>");
                }
            }
            
            if (listSizes.isEmpty()) {
                 out.println("<option value='OneSize' data-max='999'>One Size</option>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}