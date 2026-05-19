package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Locale;

/**
 * Filter xử lý đa ngôn ngữ (i18n) cho toàn bộ hệ thống.
 * Tự động nhận diện ngôn ngữ từ trình duyệt hoặc theo lựa chọn của người dùng.
 */
@WebFilter("/*")
public class LocaleFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        
        // Kiểm tra nếu người dùng thay đổi ngôn ngữ thủ công qua URL (?lang=en hoặc ?lang=vi)
        String lang = req.getParameter("lang");
        
        if (lang != null && !lang.isEmpty()) {
            Locale locale = new Locale(lang);
            session.setAttribute("locale", locale);
            session.setAttribute("lang", lang);
        } 
        // Nếu chưa có ngôn ngữ trong Session, tự động lấy theo trình duyệt
        else if (session.getAttribute("locale") == null) {
            Locale browserLocale = req.getLocale();
            String langCode = browserLocale.getLanguage();
            
            // Mặc định là tiếng Việt nếu trình duyệt không phải là tiếng Anh
            if (!"en".equals(langCode)) {
                langCode = "vi";
            }
            
            Locale locale = new Locale(langCode);
            session.setAttribute("locale", locale);
            session.setAttribute("lang", langCode);
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void destroy() {
       
    }
}