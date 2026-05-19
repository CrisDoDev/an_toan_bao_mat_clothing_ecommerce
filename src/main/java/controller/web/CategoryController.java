package controller.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseController;

@WebServlet(name = "CategoryController", urlPatterns = {"/category"})
public class CategoryController extends BaseController {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Add i18n support
        ResourceBundle msgs = getMessages(request);
        NumberFormat currencyFormat = getCurrencyFormat(request);
        DateFormat dateFormat = getDateFormat(request);
        
        request.setAttribute("msgs", msgs);
        request.setAttribute("currencyFormat", currencyFormat);
        request.setAttribute("dateFormat", dateFormat);
        
        String id = request.getParameter("cid");
        
        
        if (id != null) {
            response.sendRedirect("product?cid=" + id);
        } else {
            response.sendRedirect("product");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}