package logic.dao;

import java.util.List;

import logic.model.ComboComponent;
import logic.util.DAOException;

public interface ComboComponentDAO {
    void insert(ComboComponent cc) throws DAOException;
    void update(ComboComponent cc) throws DAOException;
    void delete(int idCombo, int idComponent) throws DAOException;
    ComboComponent find(int idCombo, int idComponent) throws DAOException;
    List<ComboComponent> findByCombo(int idCombo) throws DAOException;
}
