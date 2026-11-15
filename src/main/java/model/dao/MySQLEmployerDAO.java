package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.Employer;
import model.ModelException;

public class MySQLEmployerDAO implements EmployerDAO {

	@Override
	public boolean save(Employer employer) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO employers VALUES (DEFAULT, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		
		db.setString(1, employer.getRazaoSocial());
		db.setString(2, employer.getNomeFantasia());
		db.setString(3, employer.getCNPJ());
		
		return db.executeUpdate() > 0;	
	}

	@Override
	public boolean update(Employer employer) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE employers "
				+ " SET razao_social = ?, "
				+ " nome_fantasia = ?, "
				+ " CNPJ = ? "
				+ " WHERE id = ?;";
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, employer.getRazaoSocial());
		db.setString(2, employer.getNomeFantasia());
		db.setString(3, employer.getCNPJ());
		db.setInt(4, employer.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Employer employer) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = "DELETE FROM employers WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, employer.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Employer> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		
		List<Employer> employers = new ArrayList<Employer>();
			
		String sqlQuery = "SELECT * FROM employers;";
		
		db.createStatement();
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Employer employer = new Employer(db.getInt("id"));
			employer.setRazaoSocial(db.getString("razao_social"));
			employer.setNomeFantasia(db.getString("nome_fantasia"));
			employer.setCNPJ(db.getString("cnpj"));
			
			employers.add(employer);
		}
		
		return employers;
	}

	@Override
	public Employer findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sql = "SELECT * FROM employers WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Employer employer = null;
		while (db.next()) {
			employer = new Employer(id);
			employer.setRazaoSocial(db.getString("razao_social"));
			employer.setNomeFantasia(db.getString("nome_fantasia"));
			employer.setCNPJ(db.getString("cnpj"));
			
			break;
		}
		
		return employer;
	}
}