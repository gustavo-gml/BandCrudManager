package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Band;
import model.Employer;
import model.LiveHouse;
import model.ModelException;
import model.Show;
import model.dao.BandDAO;
import model.dao.DAOFactory;
import model.dao.EmployerDAO;
import model.dao.LiveHouseDAO;
import model.dao.ShowDAO;


@WebServlet(urlPatterns = { "/shows", "/shows/form", "/shows/delete", "/shows/insert", "/shows/update"})

public class ShowsController extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/shows":{
			listShows(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/shows.jsp");
			break;
		}
		case "/crud-manager/shows/form": {
			loadSelectLists(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-show.jsp");
			break;
		}
		
		case "/crud-manager/shows/update": {
			Show s = loadShow(req);
			loadSelectLists(req);
			req.setAttribute("show", s);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-show.jsp");
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
		case "/crud-manager/shows/delete":
			deleteShow(req, resp);
			break;
			
		case "/crud-manager/shows/insert": {
			insertShow(req, resp);
			break;
		}
		case "/crud-manager/shows/update": {
			updateShow(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/shows");
	}
	
	private Show loadShow(HttpServletRequest req) {
		String postIdParameter = req.getParameter("showId");
		
		int showId = Integer.parseInt(postIdParameter);
		
		ShowDAO dao = DAOFactory.createDAO(ShowDAO.class);
		
		try {
			Show s = dao.findById(showId);
			
			if (s == null)
				throw new ModelException("Show não encontrado para alteração");
			
			return s;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}

	// Carrega as listas usadas nos selects do formulário
	private void loadSelectLists(HttpServletRequest req) {
		BandDAO bandDao = DAOFactory.createDAO(BandDAO.class);
		EmployerDAO employerDao = DAOFactory.createDAO(EmployerDAO.class);
		LiveHouseDAO liveHouseDao = DAOFactory.createDAO(LiveHouseDAO.class);
		
		try {
			List<Band> bands = bandDao.listAll();
			List<Employer> employers = employerDao.listAll();
			List<LiveHouse> liveHouses = liveHouseDao.listAll();
			
			req.setAttribute("bands", bands);
			req.setAttribute("employers", employers);
			req.setAttribute("liveHouses", liveHouses);
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateShow(HttpServletRequest req, HttpServletResponse resp) {
		Integer id = Integer.parseInt(req.getParameter("showId"));
		Integer bandId = Integer.parseInt(req.getParameter("bandId"));
		Integer employerId = Integer.parseInt(req.getParameter("employerId"));
		Integer liveHouseId = Integer.parseInt(req.getParameter("liveHouseId"));
		Double valorCache = Double.parseDouble(req.getParameter("valorCache"));
		LocalDateTime dataHora = LocalDateTime.parse(req.getParameter("dataHora"));
		Integer tempoShow = Integer.parseInt(req.getParameter("tempoShow"));
	
		Show s = new Show(id, new Band(bandId), new Employer(employerId), new LiveHouse(liveHouseId), valorCache, dataHora, tempoShow);
		
		ShowDAO dao = DAOFactory.createDAO(ShowDAO.class);
		
		try {
			if (dao.update(s)) {
				ControllerUtil.sucessMessage(req, "Show '" + s + "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Show '" + s + "' não pode ser atualizado.");
			}				
		} catch (ModelException e) {
	
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return;
	}

	private void insertShow(HttpServletRequest req, HttpServletResponse resp) {
		
		Integer bandId = Integer.parseInt(req.getParameter("bandId"));
		Integer employerId = Integer.parseInt(req.getParameter("employerId"));
		Integer liveHouseId = Integer.parseInt(req.getParameter("liveHouseId"));
		Double valorCache = Double.parseDouble(req.getParameter("valorCache"));
		LocalDateTime dataHora = LocalDateTime.parse(req.getParameter("dataHora"));
		Integer tempoShow = Integer.parseInt(req.getParameter("tempoShow"));
	
		Show s = new Show(new Band(bandId), new Employer(employerId), new LiveHouse(liveHouseId), valorCache, dataHora, tempoShow);
		
		ShowDAO dao = DAOFactory.createDAO(ShowDAO.class);
		
		try {
			if (dao.save(s)) {
				ControllerUtil.sucessMessage(req, "Show '" + s + "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Show '" + s + "' não pode ser salvo.");
			}
				
		} catch (ModelException e) {
	
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return;
	}

	private void deleteShow(HttpServletRequest req, HttpServletResponse resp) {
		
		Integer id = Integer.parseInt(req.getParameter("id"));
		
		ShowDAO dao = DAOFactory.createDAO(ShowDAO.class);
		
		try {
			Show s = dao.findById(id);
			
			if (s == null)
				throw new ModelException("Show não encontrado para deleção");
			
			if (dao.delete(s)) {
				ControllerUtil.sucessMessage(req, "Show '" + s.toString() + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Show '" + s.toString() + "' não pode ser deletado.");
			}
		} catch (ModelException e) {

			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return;
	}

	private void listShows(HttpServletRequest req) {
		ShowDAO dao = DAOFactory.createDAO(ShowDAO.class);
		
		List<Show> shows = null;
		try {
			shows = dao.listAll();
		} catch (ModelException e) {

			e.printStackTrace();
		}
		
		if (shows != null)
			req.setAttribute("shows", shows);
	}
}