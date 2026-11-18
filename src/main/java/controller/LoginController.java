package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Admin;
import model.ModelException;
import model.dao.AdminDAO;
import model.dao.DAOFactory;
import model.utils.PasswordEncryptor;

@WebServlet(urlPatterns= {"/login", "/logout"} )
public class LoginController extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		HttpSession session = req.getSession(false);
		if(session != null) session.invalidate();
		
		resp.sendRedirect("/crud-manager/login.jsp");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		String userEmail = req.getParameter("user_login");
		String userPw = req.getParameter("user_pw");
		
		if (userEmail == null || userEmail.isBlank() || userPw == null || userPw.isBlank()) {
			resp.sendRedirect("/crud-manager/login.jsp?erro=true");
			return;
		}
		
		// Carregar o usuario do banco pelo email
		AdminDAO dao = DAOFactory.createDAO(AdminDAO.class);
		Admin userToLogin = null;
		
		try {
			userToLogin = dao.findByEmail(userEmail);
		} catch (ModelException e) {
			e.printStackTrace();
		}
		
		if (userToLogin == null) {
			resp.sendRedirect("/crud-manager/login.jsp?erro=true");
			return;
		}
		
		
		
		
		if(PasswordEncryptor.checkPassword(userPw, userToLogin.getPassword()) == true) {
			req.getSession().setAttribute("user_logged", userToLogin);
			resp.sendRedirect("/crud-manager/");
		} else {
			resp.sendRedirect("/crud-manager/login.jsp?erro=true");
		}
		
	}
	
}
