package model.dao;

import java.util.List;

import model.LiveHouse;
import model.ModelException;

public interface LiveHouseDAO {
	boolean save(LiveHouse liveHouse) throws ModelException;
	boolean update(LiveHouse liveHouse) throws ModelException;
	boolean delete(LiveHouse liveHouse) throws ModelException;
	List<LiveHouse> listAll() throws ModelException;
	LiveHouse findById(int id) throws ModelException;
}
