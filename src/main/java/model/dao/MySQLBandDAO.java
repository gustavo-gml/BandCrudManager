package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Band;


public class MySQLBandDAO implements BandDAO {

	@Override
	public boolean save(Band band) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO bands (nome, integrantes, fundacao, genero) VALUES "
				+ " (?, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, band.getName());
		db.setInt(2, band.getMembers());
		db.setLocalDate(3, band.getFundationDate());
		db.setString(4, band.getMusicGenre());
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Band band) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE bands "
						 + " SET nome = ?,"
						 + " integrantes = ?,"
						 + " fundacao = ?,"
						 + " genero = ?"
						 + " WHERE id = ?";
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, band.getName());
		db.setInt(2, band.getMembers());
		db.setLocalDate(3, band.getFundationDate());
		db.setString(4, band.getMusicGenre());
		db.setInt(5, band.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Band band) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM bands "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, band.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Band> listAll() throws ModelException {
	
		DBHandler db = new DBHandler();
		
		List<Band> bands = new ArrayList<Band>();
			
		String sqlQuery = "SELECT * FROM bands";
				      
		
		db.createStatement();
	
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Band p = createBand(db);
			
			bands.add(p);
		}
		
		return bands;
	}

	@Override
	public Band findById(int id) throws ModelException {
		
		DBHandler db = new DBHandler();
				
		String sql = "SELECT * FROM bands WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Band p = null;
		while (db.next()) {
			p = createBand(db);
			break;
		}
		
		return p;
	}
	
	private Band createBand(DBHandler db) throws ModelException {
		Band p = new Band(db.getInt("id"));
		p.setName(db.getString("nome"));
		p.setMembers(db.getInt("integrantes"));
		p.setFundationDate(db.getLocalDate("fundacao"));
		p.setMusicGenre(db.getString("genero"));
		return p;
	}
}
