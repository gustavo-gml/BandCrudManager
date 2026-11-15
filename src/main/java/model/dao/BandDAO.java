package model.dao;

import java.util.List;

import model.ModelException;
import model.Band;

public interface BandDAO {
	boolean save(Band band) throws ModelException;
	boolean update(Band band) throws ModelException;
	boolean delete(Band band) throws ModelException;
	List<Band> listAll() throws ModelException;
	Band findById(int id) throws ModelException;
}
