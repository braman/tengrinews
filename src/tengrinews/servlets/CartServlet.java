package tengrinews.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tengrinews.beans.INewsLocal;

@WebServlet(urlPatterns = "/cart/add")
public class CartServlet extends HttpServlet {

    @EJB INewsLocal newsLocal;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        
        
    }
    
    
    
}
