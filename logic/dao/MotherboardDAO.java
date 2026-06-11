package logic.dao;

import java.util.List;
import logic.model.MotherBoard;
import logic.util.DAOException;

public interface MotherboardDAO {

    MotherBoard findById(int id_component) throws DAOException;
    List<MotherBoard> findAll() throws DAOException;

    void insert(MotherBoard motherboard) throws DAOException;
    void update(MotherBoard motherboard) throws DAOException;
    void delete(int id_component) throws DAOException;
}