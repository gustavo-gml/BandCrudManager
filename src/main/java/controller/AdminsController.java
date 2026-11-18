package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Admin;
import model.ModelException;
import model.dao.AdminDAO;
import model.dao.DAOFactory;
import model.utils.PasswordEncryptor;

@WebServlet(urlPatterns = {"/admins", "/admins/form", "/admins/delete", "/admins/insert", "/admins/update", "/admins/logged"})
public class AdminsController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		
		case "/crud-manager/admins":{
			listAdmins(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/admins.jsp");
			break;
		}
		case "/crud-manager/admins/form": {
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-admin.jsp");
			break;
		}
		case "/crud-manager/admins/update": {
			Admin admin = loadAdmin(req);
			req.setAttribute("admin", admin);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-admin.jsp");
			break;
		}
		
		case "/crud-manager/admins/logged": {
		    Object userLogged = req.getSession().getAttribute("user_logged");

		    if (userLogged instanceof Admin adminLogged) {
		        req.setAttribute("admin", adminLogged);
		        req.setAttribute("action", "update");
		        ControllerUtil.forward(req, resp, "/form-admin.jsp");
		    } else {
		        resp.sendRedirect(req.getContextPath() + "/login.jsp");
		    }
		    break;
		}

		
	
		
		
		default:
			String message = "URL não suportada: " + action +".";
			throw new IllegalArgumentException(message);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		if (action == null || action.equals("") ) {
			ControllerUtil.forward(req, resp, "/index.jsp");
			return;
		}
		
		switch (action) {
		case "/crud-manager/admins/delete":
			deleteAdmin(req, resp);
			break;	
		case "/crud-manager/admins/insert": {
			insertAdmin(req, resp);
			break;
		}
		case "/crud-manager/admins/update": {
			updateAdmin(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
			break;
		}
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/admins");
	}

	private Admin loadAdmin(HttpServletRequest req) {
		String adminIdParameter = req.getParameter("adminId");
		
		int adminId = Integer.parseInt(adminIdParameter);
		
		AdminDAO dao = DAOFactory.createDAO(AdminDAO.class);
		
		try {
			Admin admin = dao.findById(adminId);
			
			if (admin == null)
				throw new ModelException("Usuário não encontrado para alteração");
			
			return admin;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}
	
	private void listAdmins(HttpServletRequest req) {
		AdminDAO dao = DAOFactory.createDAO(AdminDAO.class);
		
		List<Admin> admins = null;
		try {
			admins = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (admins != null)
			req.setAttribute("admins", admins);
	}
	
	private void insertAdmin(HttpServletRequest req, HttpServletResponse resp) {
		String adminName = req.getParameter("name");
		String adminGender = req.getParameter("gender");
		String adminEMail = req.getParameter("mail");
		String userPw = req.getParameter("user_pw");
		
		
		Admin admin = new Admin();
		admin.setName(adminName);
		admin.setGender(adminGender);
		admin.setEmail(adminEMail);
		
		String hashPw = PasswordEncryptor.hashPassword(userPw);
		admin.setPassword(hashPw);
		
		AdminDAO dao = DAOFactory.createDAO(AdminDAO.class);
		
		try {
			if (dao.save(admin)) {
				ControllerUtil.sucessMessage(req, "Usuário '" + admin.getName() + "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Usuário '" + admin.getName() + "' não pode ser salvo.");
			}
				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void updateAdmin(HttpServletRequest req, HttpServletResponse resp) {
		String adminName = req.getParameter("name");
		String adminGender = req.getParameter("gender");
		String adminEMail = req.getParameter("mail");
		String userPw = req.getParameter("user_pw");
		
		Admin admin = loadAdmin(req);
		admin.setName(adminName);
		admin.setGender(adminGender);
		admin.setEmail(adminEMail);
		
		String hashPw = PasswordEncryptor.hashPassword(userPw);
		admin.setPassword(hashPw);
		
		AdminDAO dao = DAOFactory.createDAO(AdminDAO.class);
		
		try {
			if (dao.update(admin)) {
				ControllerUtil.sucessMessage(req, "Usuário '" + admin.getName() + "' atualizado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Usuário '" + admin.getName() + "' não pode ser atualizado.");
			}
				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void deleteAdmin(HttpServletRequest req, HttpServletResponse resp) {
		String adminIdParameter = req.getParameter("id");
		
		int adminId = Integer.parseInt(adminIdParameter);
		
		AdminDAO dao = DAOFactory.createDAO(AdminDAO.class);
		
		try {
			Admin admin = dao.findById(adminId);
			
			if (admin == null)
				throw new ModelException("Usuário não encontrado para deleção.");
			
			if (dao.delete(admin)) {
				ControllerUtil.sucessMessage(req, "Usuário '" + admin.getName() + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Usuário '" + admin.getName() + "' não pode ser deletado. Há dados relacionados ao usuário.");
			}
		} catch (ModelException e) {
			// log no servidor
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
