package logic.dao;

import java.util.List;

import logic.model.User;
import logic.util.DAOException;

public interface UserDAO {
    User findById(int id_user) throws DAOException;
    List<User> findAll() throws DAOException;
    void insert(User user) throws DAOException;
    void update(User user) throws DAOException;
    void delete(int id_user) throws DAOException;
	User findByUsername(String username);
}
