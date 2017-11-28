package tengrinews.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {

    Logger log = Logger.getLogger(getClass());
    
    @Override
    public void destroy() {
        
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpReq = (HttpServletRequest) req;
        
        HttpSession session = httpReq.getSession(true);
        
        Object userIdObj = session.getAttribute("userId");
        
//        if (userIdObj == null) {
//            req.getRequestDispatcher("login.jsp").forward(req, resp);
//        } else {
//            log.info("user: " + userIdObj + " -> requesting " + httpReq.getRequestURL());
            chain.doFilter(req, resp);
//        }       
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        
    }

}
