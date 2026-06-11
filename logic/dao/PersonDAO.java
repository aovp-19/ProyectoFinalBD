package logic.dao;

import java.util.List;

import logic.model.Person;
import logic.util.DAOException;

public interface PersonDAO {
	
	Person findById(int id) throws DAOException;
	List<Person> findAll() throws DAOException;
	
	void insert(Person person) throws DAOException;
	void update(Person person) throws DAOException;
	void delete(int id_person) throws DAOException;

}
