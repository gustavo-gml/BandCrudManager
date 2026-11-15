package controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import model.Admin;
import model.ModelException;
import model.dao.AdminDAO;
import model.dao.DAOFactory;

public class CommonsController {
	
	public static void listUsers(HttpServletRequest req) {
		AdminDAO dao = DAOFactory.createDAO(AdminDAO.class);
		
		List<Admin> listUsers = null;
		try {
			listUsers = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (listUsers != null)
			req.setAttribute("users", listUsers);		
	}
}
