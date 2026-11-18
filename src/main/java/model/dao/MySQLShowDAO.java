package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.Band;
import model.LiveHouse;
import model.ModelException;
import model.Show;

public class MySQLShowDAO implements ShowDAO {
	
	@Override
	public boolean save(Show s) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO shows "
				+ " (band_id, live_house_id, valor_cache, data_hora, tempo_de_show) "
				+ " VALUES (?, ?, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setInt(1, s.getBand().getId());
		db.setInt(2, s.getLiveHouse().getId());
		db.setDouble(3, s.getValorCache()); 
		db.setLocalDateTime(4, s.getDataHora());
		db.setInt(5, s.getTempoShow());
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Show s) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE shows "
				         	+ "SET band_id = ?, "
				         	+ "live_house_id = ?, "
				         	+ "valor_cache = ?, "
				         	+ "data_hora = ?, "
				         	+ "tempo_de_show = ? "
				         + "WHERE id = ?";
		
		db.prepareStatement(sqlUpdate);
		
		db.setInt(1, s.getBand().getId());
		db.setInt(2, s.getLiveHouse().getId());
		db.setDouble(3, s.getValorCache()); 
		db.setLocalDateTime(4, s.getDataHora());
		db.setInt(5, s.getTempoShow());
		db.setInt(6, s.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Show s) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM shows "
		                 + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, s.getId());
		
		try {
			
			return db.executeUpdate() > 0;
			
		} catch (ModelException e) {
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				return false;
			}
			
			throw e; 
		}
	}

	@Override
	public List<Show> listAll() throws ModelException {
	
		DBHandler db = new DBHandler();
		
		List<Show> shows = new ArrayList<Show>();
			
		String sqlQuery = "SELECT * FROM shows";
		
		db.createStatement();
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Show s = createShow(db);
			shows.add(s);
		}
		
		return shows;
	}

	@Override
	public Show findById(int id) throws ModelException {
		
		DBHandler db = new DBHandler();
				
		String sql = "SELECT * FROM shows WHERE id = ?";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Show s = null;
		while (db.next()) {
			s = createShow(db);
			break;
		}
		return s;
	}
	
	private Show createShow(DBHandler db) throws ModelException {
		BandDAO bandDAO = DAOFactory.createDAO(BandDAO.class);
		LiveHouseDAO liveHouseDAO = DAOFactory.createDAO(LiveHouseDAO.class);
		
		Band b = bandDAO.findById(db.getInt("band_id"));
		LiveHouse lH = liveHouseDAO.findById(db.getInt("live_house_id"));
		
		return new Show(db.getInt("id"), b, lH, db.getDouble("valor_cache"), db.getLocalDateTime("data_hora"), db.getInt("tempo_de_show"));
	}
}
