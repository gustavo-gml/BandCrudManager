package controller;

import java.io.IOException;

import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.LiveHouse;
import model.ModelException;

import model.dao.DAOFactory;
import model.dao.LiveHouseDAO;


@WebServlet(urlPatterns = { "/liveHouses", "/liveHouses/form", "/liveHouses/delete", "/liveHouses/insert", "/liveHouses/update"})

public class LiveHouseController extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/liveHouses":{
			listLiveHouses(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/liveHouses.jsp");
			break;
		}
		case "/crud-manager/liveHouses/form": {
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-live-house.jsp");
			break;
		}
		
		case "/crud-manager/liveHouses/update": {
			LiveHouse lH = loadLiveHouse(req);
			req.setAttribute("liveHouse", lH);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-live-house.jsp");
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
		case "/crud-manager/liveHouses/delete":
			deleteLiveHouse(req, resp);
			break;
			
		case "/crud-manager/liveHouses/insert": {
			insertLiveHouse(req, resp);
			break;
		}
		case "/crud-manager/liveHouses/update": {
			updateLiveHouse(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/liveHouses");
	}
	
	private LiveHouse loadLiveHouse(HttpServletRequest req) {
		String postIdParameter = req.getParameter("liveHouseId");
		
		int liveHouseId = Integer.parseInt(postIdParameter);
		
		LiveHouseDAO dao = DAOFactory.createDAO(LiveHouseDAO.class);
		
		try {
			LiveHouse lH = dao.findById(liveHouseId );
			
			if (lH == null)
				throw new ModelException("Casa de Show não encontrada para alteração");
			
			return lH;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}

	private void updateLiveHouse(HttpServletRequest req, HttpServletResponse resp) {
		Integer id = Integer.parseInt(req.getParameter("liveHouseId"));
		String razaoSocial = req.getParameter("razaoSocial");
		String nomeFantasia = req.getParameter("nomeFantasia");
		String cnpj = req.getParameter("cnpj");
		String endereco = req.getParameter("endereco");
	
		LiveHouse lH = new LiveHouse(id, razaoSocial,  nomeFantasia, cnpj, endereco);
		
		LiveHouseDAO dao = DAOFactory.createDAO(LiveHouseDAO.class);
		
		try {
			if (dao.update(lH)) {
				ControllerUtil.sucessMessage(req, "Casa de show '" + lH + "' salva com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Casa de show '" + lH + "' não pode ser atualizada.");
			}				
		} catch (ModelException e) {
	
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return;
	}

	private void insertLiveHouse(HttpServletRequest req, HttpServletResponse resp) {
		
		String razaoSocial = req.getParameter("razaoSocial");
		String nomeFantasia = req.getParameter("nomeFantasia");
		String cnpj = req.getParameter("cnpj");
		String endereco = req.getParameter("endereco");
	
		LiveHouse lH = new LiveHouse(razaoSocial,  nomeFantasia, cnpj, endereco);
		
		
		
		LiveHouseDAO dao = DAOFactory.createDAO(LiveHouseDAO.class);
		
		try {
			if (dao.save(lH)) {
				ControllerUtil.sucessMessage(req, "Casa de Show '" + lH + "' salva com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Casa de Show'" + lH + "' não pode ser salva.");
			}
				
		} catch (ModelException e) {
	
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return;
	}

	private void deleteLiveHouse(HttpServletRequest req, HttpServletResponse resp) {
	
		Integer id = Integer.parseInt(req.getParameter("liveHouseId"));
		
		LiveHouseDAO dao = DAOFactory.createDAO(LiveHouseDAO.class);
		
		try {
			LiveHouse lH = dao.findById(id);
			
			if (lH == null)
				throw new ModelException("Casa de Show não encontrada para deleção");
			
			if (dao.delete(lH)) {
				ControllerUtil.sucessMessage(req, "Casa de Show '" + lH.toString() + "' deletada com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Casa de Show '" + lH.toString() + "' não pode ser deletada.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return;
	}

	private void listLiveHouses(HttpServletRequest req) {
		LiveHouseDAO dao = DAOFactory.createDAO(LiveHouseDAO.class);
		
		List<LiveHouse> liveHouses = null;
		try {
			liveHouses = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (liveHouses != null)
			req.setAttribute("liveHouses", liveHouses);
	}
}

