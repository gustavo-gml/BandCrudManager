package model.dao;

import java.util.List;

import model.ModelException;
import model.Admin;

public interface AdminDAO {
	boolean save(Admin user) throws ModelException ;
	boolean update(Admin  user) throws ModelException;
	boolean delete(Admin  user) throws ModelException;
	List<Admin> listAll() throws ModelException;
	Admin  findById(int id) throws ModelException;
}
