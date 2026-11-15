package model.dao;

import java.util.List;

import model.ModelException;
import model.Show;

public interface ShowDAO {
	boolean save(Show show) throws ModelException;
	boolean update(Show show) throws ModelException;
	boolean delete(Show show) throws ModelException;
	List<Show> listAll() throws ModelException;
	Show findById(int id) throws ModelException;
}
