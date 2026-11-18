package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.Employer;
import model.LiveHouse;
import model.ModelException;

public class MySQLLiveHouseDAO implements LiveHouseDAO {

	@Override
	public boolean save(LiveHouse lH) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO live_house "
				+ " (employer_id, nome_fantasia, email, endereco, capacidade, data_inauguracao, estacionamento) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setInt(1, lH.getEmployer().getId());
		db.setString(2, lH.getNomeFantasia());
		db.setString(3, lH.getEmail());
		db.setString(4, lH.getEndereco());
		db.setInt(5, lH.getCapacidade());
		db.setLocalDateTime(6, lH.getDataInauguracao().atStartOfDay());
		db.setInt(7, lH.isEstacionamento() ? 1 : 0);
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(LiveHouse lH) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE live_house "
				         	+ "SET employer_id = ?, "
				         	+ "nome_fantasia = ?, "
				         	+ "email = ?, "
				         	+ "endereco = ?, "
				         	+ "capacidade = ?, "
				         	+ "data_inauguracao = ?, "
				         	+ "estacionamento = ? "
				         + "WHERE id = ?";
		
		db.prepareStatement(sqlUpdate);
		
		db.setInt(1, lH.getEmployer().getId());
		db.setString(2, lH.getNomeFantasia());
		db.setString(3, lH.getEmail());
		db.setString(4, lH.getEndereco());
		db.setInt(5, lH.getCapacidade());
		db.setLocalDateTime(6, lH.getDataInauguracao().atStartOfDay());
		db.setInt(7, lH.isEstacionamento() ? 1 : 0);
		db.setInt(8, lH.getId());
		
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
		EmployerDAO employerDAO = DAOFactory.createDAO(EmployerDAO.class);
		Employer employer = employerDAO.findById(db.getInt("employer_id"));
		
		return new LiveHouse(
				db.getInt("id"),
				employer,
				db.getString("nome_fantasia"),
				db.getString("email"),
				db.getString("endereco"),
				db.getInt("capacidade"),
				db.getLocalDateTime("data_inauguracao").toLocalDate(),
				db.getInt("estacionamento") == 1);
	}
	
}
