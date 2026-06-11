package logic.dao;

import java.util.List;
import logic.model.MicroProcessor;
import logic.util.DAOException;

public interface MicroprocessorDAO {

    MicroProcessor findById(int id_component) throws DAOException;
    List<MicroProcessor> findAll() throws DAOException;

    void insert(MicroProcessor microprocessor) throws DAOException;
    void update(MicroProcessor microprocessor) throws DAOException;
    void delete(int id_component) throws DAOException;
}