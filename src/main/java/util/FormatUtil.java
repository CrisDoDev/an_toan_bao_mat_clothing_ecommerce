package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtil {
    
    /**
     * Định dạng tiền tệ dựa trên Locale lưu trong Session
     * @param request HttpServletRequest để lấy session
     * @param amount Số tiền cần định dạng
     * @return Chuỗi tiền tệ đã định dạng (ví dụ: 100.000 ₫ hoặc $100.00)
     */
    public static String formatCurrency(HttpServletRequest request, double amount) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute("locale");
        
        if (locale == null) {
            locale = new Locale("vi", "VN");
        }
        
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        return nf.format(amount);
    }
    
    /**
     * Lấy Locale hiện tại từ Session
     * @param request HttpServletRequest để lấy session
     * @return Locale hiện tại (mặc định vi-VN nếu chưa có)
     */
    public static Locale getLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute("locale");
        
        if (locale == null) {
            locale = new Locale("vi", "VN");
        }
        
        return locale;
    }
}