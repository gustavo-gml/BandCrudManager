package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.Admin;
import model.ModelException;


public class MySQLAdminDAO implements AdminDAO {

	@Override
	public boolean save(Admin user) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO admins VALUES "
				+ " (DEFAULT, ?, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, user.getName());
		db.setString(2, user.getEmail());
		db.setString(3, user.getGender());
		db.setString(4, "1234");  // TODO: Implementar hash depois 
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Admin user) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE admins "
				         	+ "SET nome = ?, "
				         	+ "email = ?, "
				         	+ "sexo = ?, "
				         	+ "senha = ? "
				         + "WHERE id = ?";
		
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, user.getName());
		db.setString(2, user.getEmail());
		db.setString(3, user.getGender());
		db.setString(4, "1234"); //TODO hash
		db.setInt(5, user.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Admin user) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM admins "
		                 + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, user.getId());
		
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
	public List<Admin> listAll() throws ModelException {
	
		DBHandler db = new DBHandler();
		
		List<Admin> users = new ArrayList<Admin>();
			
		// Declara um instrução SQL
		String sqlQuery = "SELECT * FROM admins";
		
		db.createStatement();
	
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Admin u = createAdmin(db);
			users.add(u);
		}
		
		return users;
	}

	@Override
	public Admin findById(int id) throws ModelException {
		
		DBHandler db = new DBHandler();
				
		String sql = "SELECT * FROM admins WHERE id = ?";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Admin u = null;
		while (db.next()) {
			u = createAdmin(db);
			break;
		}
		
		return u;
	}
	
	private Admin createAdmin(DBHandler db) throws ModelException {
		Admin u = new Admin(db.getInt("id"));
		u.setName(db.getString("nome"));
		u.setGender(db.getString("sexo"));
		u.setEmail(db.getString("email"));
		u.setPassword("1234"); //TODO: hash
		
		return u;
	}
}
