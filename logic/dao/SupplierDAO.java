package logic.dao;

import java.util.List;
import logic.model.Supplier;
import logic.util.DAOException;

public interface SupplierDAO {
    Supplier findById(int id_person) throws DAOException;
    List<Supplier> findAll() throws DAOException;
    void insert(Supplier supplier) throws DAOException;
    void update(Supplier supplier) throws DAOException;
    void delete(int id_person) throws DAOException;
}
