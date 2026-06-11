package logic.dao;

import java.util.List;
import logic.model.Item;
import logic.util.DAOException;

public interface ItemDAO {
    Item findById(int id_item) throws DAOException;
    List<Item> findAll() throws DAOException;
    void insert(Item item) throws DAOException;
    void update(Item item) throws DAOException;
    void delete(int id_item) throws DAOException;
}
