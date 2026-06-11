package logic.dao;

import java.util.List;
import logic.model.HardDisk;
import logic.util.DAOException;

public interface HardDiskDAO {

    HardDisk findById(int id_component) throws DAOException;
    List<HardDisk> findAll() throws DAOException;

    void insert(HardDisk hardDisk) throws DAOException;
    void update(HardDisk hardDisk) throws DAOException;
    void delete(int id_component) throws DAOException;
}