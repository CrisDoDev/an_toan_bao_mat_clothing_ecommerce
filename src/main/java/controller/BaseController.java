package controller;

import javax.servlet.http.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class BaseController extends HttpServlet {
    
    protected Locale getLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute("locale");
        
        if (locale == null) {
            locale = new Locale("vi", "VN");
            session.setAttribute("locale", locale);
        }
        
        return locale;
    }
    
    protected ResourceBundle getMessages(HttpServletRequest request) {
        Locale locale = getLocale(request);
        return ResourceBundle.getBundle("messages", locale);
    }
    
    protected NumberFormat getCurrencyFormat(HttpServletRequest request) {
        Locale locale = getLocale(request);
        return NumberFormat.getCurrencyInstance(locale);
    }
    
    protected DateFormat getDateFormat(HttpServletRequest request) {
        Locale locale = getLocale(request);
        return DateFormat.getDateInstance(DateFormat.LONG, locale);
    }
    
    protected DateFormat getDateTimeFormat(HttpServletRequest request) {
        Locale locale = getLocale(request);
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale);
    }
    
    protected void setMessages(HttpServletRequest request) {
        request.setAttribute("msgs", getMessages(request));
        request.setAttribute("currencyFormat", getCurrencyFormat(request));
        request.setAttribute("dateFormat", getDateFormat(request));
        request.setAttribute("dateTimeFormat", getDateTimeFormat(request));
    }
}