package logic.dao;

import java.util.List;

import logic.model.Component;
import logic.util.DAOException;

public interface ComponentDAO {

    Component findById(int id) throws DAOException;
    List<Component> findAll() throws DAOException;

    void insert(Component component) throws DAOException;
    void update(Component component) throws DAOException;
    void delete(int id) throws DAOException;
	int countByType(String type);
}