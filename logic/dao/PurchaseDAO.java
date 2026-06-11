package logic.dao;

import java.util.List;
import logic.model.Purchase;
import logic.util.DAOException;

public interface PurchaseDAO {
    Purchase findById(int id_purchase) throws DAOException;
    List<Purchase> findAll() throws DAOException;
    void insert(Purchase purchase) throws DAOException;
    void update(Purchase purchase) throws DAOException;
    void delete(int id_purchase) throws DAOException;
}
