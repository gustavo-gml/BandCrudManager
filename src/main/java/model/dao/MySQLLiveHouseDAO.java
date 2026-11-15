package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.LiveHouse;
import model.ModelException;

public class MySQLLiveHouseDAO implements LiveHouseDAO {

	@Override
	public boolean save(LiveHouse lH) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO live_house VALUES "
				+ " (DEFAULT, ?, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, lH.getRazaoSocial());
		db.setString(2, lH.getNomeFantasia());
		db.setString(3, lH.getCnpj());
		db.setString(4, lH.getEndereco()); 
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(LiveHouse lH) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE live_house "
				         	+ "SET razao_social = ?, "
				         	+ "nome_fantasia = ?, "
				         	+ "CNPJ = ?, "
				         	+ "endereco = ? "
				         + "WHERE id = ?";
		
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, lH.getRazaoSocial());
		db.setString(2, lH.getNomeFantasia());
		db.setString(3, lH.getCnpj());
		db.setString(4, lH.getEndereco()); 
		db.setInt(5, lH.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(LiveHouse lH) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM live_house "
		                 + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, lH.getId());
		
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
	public List<LiveHouse> listAll() throws ModelException {
	
		DBHandler db = new DBHandler();
		
		List<LiveHouse> lHs = new ArrayList<LiveHouse>();
			
		// Declara um instrução SQL
		String sqlQuery = "SELECT * FROM live_house";
		
		db.createStatement();
	
		db.executeQuery(sqlQuery);

		while (db.next()) {
			LiveHouse lh = createLiveHouse(db);
			lHs.add(lh);
		}
		
		return lHs;
	}

	@Override
	public LiveHouse findById(int id) throws ModelException {
		
		DBHandler db = new DBHandler();
				
		String sql = "SELECT * FROM live_house WHERE id = ?";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		LiveHouse lH = null;
		while (db.next()) {
			lH = createLiveHouse(db);
			break;
		}
		return lH;
	}
	
	private LiveHouse createLiveHouse(DBHandler db) throws ModelException {
		LiveHouse lh = new LiveHouse(db.getInt("id"), db.getString("razao_social"), db.getString("nome_fantasia"), db.getString("CNPJ"), db.getString("endereco"));
		return lh;
	}
	
}
