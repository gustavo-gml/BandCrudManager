package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Band;
import model.ModelException;
import model.dao.BandDAO;
import model.dao.DAOFactory;

@SuppressWarnings("serial")

@WebServlet(urlPatterns = { "/bands", "/bands/form", "/bands/delete", "/bands/insert", "/bands/update"})
public class BandsController extends HttpServlet {
	
	// Sobrescreve o método doGet, sendo capaz de responder métodos HTTP GET
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/bands":{
			listBands(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/bands.jsp");
			break;
		}
		case "/crud-manager/bands/form": {
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-band.jsp");
			break;
		}
		
		case "/crud-manager/bands/update": {
			Band b = loadBand(req);
			req.setAttribute("band", b);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-band.jsp");
			break;
		}
		default:
			String message = "URL não suportada: " + action +".";
			throw new IllegalArgumentException(message);
		}
	}
	
	// Sobrescreve o método doPost, sendo capaz de responder métodos HTTP POST
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		if (action == null || action.equals("") ) {
			ControllerUtil.forward(req, resp, "/index.jsp");
			return;
		}
		
		switch (action) {
		case "/crud-manager/bands/delete":
			deleteBand(req, resp);
			break;
			
		case "/crud-manager/bands/insert": {
			insertBand(req, resp);
			break;
		}
		case "/crud-manager/bands/update": {
			updateBand(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/bands");
	}
	
	private Band loadBand(HttpServletRequest req) {
		String postIdParameter = req.getParameter("bandId");
		
		int bandId = Integer.parseInt(postIdParameter);
		
		BandDAO dao = DAOFactory.createDAO(BandDAO.class);
		
		try {
			Band b = dao.findById(bandId);
			
			if (b == null)
				throw new ModelException("Banda não encontrada para alteração");
			
			return b;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}

	private void updateBand(HttpServletRequest req, HttpServletResponse resp) {
		Integer id = Integer.parseInt(req.getParameter("bandId"));
		String name = req.getParameter("name");
		String musicGenre = req.getParameter("musicGenre");
		Integer members = Integer.parseInt(req.getParameter("members"));
		String fundationDateStr = req.getParameter("fundationDate");
		
		Band band = new Band(id, name, members, LocalDate.parse(fundationDateStr), musicGenre);
		
		BandDAO dao = DAOFactory.createDAO(BandDAO.class);
		
		try {
			if (dao.update(band)) {
				ControllerUtil.sucessMessage(req, "Banda '" + band + "' salva com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Banda '" + band + "' não pode ser atualizada.");
			}				
		} catch (ModelException e) {
	
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return;
	}

	private void insertBand(HttpServletRequest req, HttpServletResponse resp) {
		
		String name = req.getParameter("name");
		String musicGenre = req.getParameter("musicGenre");
		Integer members = Integer.parseInt(req.getParameter("members"));
		String fundationDateStr = req.getParameter("fundationDate");
		
		Band band = new Band(name, members, LocalDate.parse(fundationDateStr), musicGenre);
		
		
		BandDAO dao = DAOFactory.createDAO(BandDAO.class);
		
		try {
			if (dao.save(band)) {
				ControllerUtil.sucessMessage(req, "Banda '" + band + "' salva com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Banda '" + band + "' não pode ser salva.");
			}
				
		} catch (ModelException e) {
	
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return;
	}

	private void deleteBand(HttpServletRequest req, HttpServletResponse resp) {
	
		String bandIdParameter = req.getParameter("id");
		
		int bandId = Integer.parseInt(bandIdParameter);
		
		BandDAO dao = DAOFactory.createDAO(BandDAO.class);
		
		try {
			Band p = dao.findById(bandId);
			
			if (p == null)
				throw new ModelException("Banda não encontrada para deleção");
			
			if (dao.delete(p)) {
				ControllerUtil.sucessMessage(req, "Banda '" + p.toString() + "' deletada com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Banda '" + p.toString() + "' não pode ser deletada.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return;
	}

	private void listBands(HttpServletRequest req) {
		BandDAO dao = DAOFactory.createDAO(BandDAO.class);
		
		List<Band> bands = null;
		try {
			bands = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (bands != null)
			req.setAttribute("bands", bands);
	}
}
