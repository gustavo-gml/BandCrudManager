package model.dao;

import java.util.List;

import model.ModelException;
import model.Employer;

public interface EmployerDAO {
	boolean save(Employer company) throws ModelException;
	boolean update(Employer company) throws ModelException;
	boolean delete(Employer company) throws ModelException;
	List<Employer> listAll() throws ModelException;
	Employer findById(int id) throws ModelException;
}
