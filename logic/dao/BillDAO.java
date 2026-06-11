package logic.dao;

import java.util.List;

import logic.model.Bill;
import logic.util.DAOException;

public interface BillDAO {
    Bill findById(int id_bill) throws DAOException;
    List<Bill> findAll() throws DAOException;
    void insert(Bill bill) throws DAOException;
    void update(Bill bill) throws DAOException;
    void delete(int id_bill) throws DAOException;
	double[] getMonthlyProfits();
}
