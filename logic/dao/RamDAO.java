package logic.dao;

import java.util.List;
import logic.model.RAM;
import logic.util.DAOException;

public interface RamDAO {

    RAM findById(int id_component) throws DAOException;
    List<RAM> findAll() throws DAOException;

    void insert(RAM ram) throws DAOException;
    void update(RAM ram) throws DAOException;
    void delete(int id_component) throws DAOException;
}