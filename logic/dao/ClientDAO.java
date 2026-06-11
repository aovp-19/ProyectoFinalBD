package logic.dao;

import java.util.List;
import logic.model.Client;
import logic.model.Person;
import logic.util.DAOException;

public interface ClientDAO {
    Client findById(int id_person) throws DAOException;
    List<Client> findAll() throws DAOException;
    void update(Client client) throws DAOException;
    void delete(int id_person) throws DAOException;
	void insert(Client p) throws DAOException;
}
