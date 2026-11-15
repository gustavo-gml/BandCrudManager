package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employer;
import model.ModelException;
import model.dao.DAOFactory;
import model.dao.EmployerDAO;

@WebServlet(urlPatterns = {"/employers", "/employer/form", "/employer/insert", "/employer/delete", "/employer/update"})
public class EmployersController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();

		
		
		switch (action) {
		
		case  "/crud-manager/employers":{
			listEmployers(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/employers.jsp");
			break;
		}
		case "/crud-manager/employer/form": {
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-employer.jsp");			
			break;
		}
		case "/crud-manager/employer/update": {
			String idStr = req.getParameter("employerId");
			int idEmployer = Integer.parseInt(idStr); 
			
			EmployerDAO dao = DAOFactory.createDAO(EmployerDAO.class);
			
			Employer employer = null;
			try {
				employer = dao.findById(idEmployer);
			} catch (ModelException e) {
				e.printStackTrace();
			}
			
			req.setAttribute("action", "update");
			req.setAttribute("employer", employer);
			ControllerUtil.forward(req, resp, "/form-employer.jsp");
			break;
		}
		default:
			
		}
	}
	
	private void listEmployers(HttpServletRequest req) {
		EmployerDAO dao = DAOFactory.createDAO(EmployerDAO.class);
		
		List<Employer> employers = null;
		try {
			employers = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}
		
		if (employers != null)
			req.setAttribute("employers", employers);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/employer/insert": {
			insertEmployer(req, resp);			
			break;
		}
		case "/crud-manager/employer/delete": {
			deleteEmployer(req, resp);
			break;
		}
		case "/crud-manager/employer/update": {
			updateEmployer(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}
		
		ControllerUtil.redirect(resp, req.getContextPath() + "/employers");
	}

	private void updateEmployer(HttpServletRequest req, HttpServletResponse resp) {
		String employerIdStr = req.getParameter("employerId");
		String razaoSocial = req.getParameter("razaoSocial");
		String nomeFantasia = req.getParameter("nomeFantasia");
		String cnpj = req.getParameter("cnpj");
		
		Employer employer = new Employer(Integer.parseInt(employerIdStr));
		employer.setRazaoSocial(razaoSocial);
		employer.setNomeFantasia(nomeFantasia);
		employer.setCNPJ(cnpj);
		
		EmployerDAO dao = DAOFactory.createDAO(EmployerDAO.class);
		
		try {
			if (dao.update(employer)) {
				ControllerUtil.sucessMessage(req, "Empregador '" + 
						employer.getNomeFantasia() + "' atualizado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Empregador '" + 
						employer.getNomeFantasia() + "' não pode ser atualizado.");
			}				
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}		
	}

	private void deleteEmployer(HttpServletRequest req, HttpServletResponse resp) {
		String employerIdParameter = req.getParameter("id");
		
		int employerId = Integer.parseInt(employerIdParameter);
		
		EmployerDAO dao = DAOFactory.createDAO(EmployerDAO.class);
		
		try {
			Employer employer = dao.findById(employerId);
			
			if (employer == null)
				throw new ModelException("Empregador não encontrado para deleção.");
			
			if (dao.delete(employer)) {
				ControllerUtil.sucessMessage(req, "Empregador '" + 
						employer.getNomeFantasia() + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Empregador '" + 
						employer.getNomeFantasia() + "' não pode ser deletado. "
								+ "Há dados relacionados ao empregador.");
			}
		} catch (ModelException e) {
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertEmployer(HttpServletRequest req, HttpServletResponse resp) {
		String razaoSocial = req.getParameter("razaoSocial");
		String nomeFantasia = req.getParameter("nomeFantasia");
		String cnpj = req.getParameter("cnpj");
		
		Employer emp = new Employer();
		emp.setRazaoSocial(razaoSocial);
		emp.setNomeFantasia(nomeFantasia);
		emp.setCNPJ(cnpj);
		
		EmployerDAO dao = DAOFactory.createDAO(EmployerDAO.class);
	
		try {
			if (dao.save(emp)) {
				ControllerUtil.sucessMessage(req, "Empregador '" + emp.getNomeFantasia() 
				+ "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Empregador '" + emp.getNomeFantasia()
				+ "' não pode ser salvo.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}