package logic.dao;

import java.util.List;
import logic.model.Combo;
import logic.util.DAOException;

public interface ComboDAO {

    Combo findById(int id) throws DAOException;
    List<Combo> findAll() throws DAOException;

    void insert(Combo component) throws DAOException;
    void update(Combo component) throws DAOException;
    void delete(int id) throws DAOException;
}