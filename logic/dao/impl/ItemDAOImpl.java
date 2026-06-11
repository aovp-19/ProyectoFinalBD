package logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.dao.ItemDAO;
import logic.model.Item;
import logic.util.Conexion;
import logic.util.DAOException;

public class ItemDAOImpl implements ItemDAO {

    private static final String SQL_FIND_ID  =
      "SELECT id_item, id_component, id_combo, price, id_admin, quantity FROM ITEM WHERE id_item = ?";
    private static final String SQL_FIND_ALL =
      "SELECT id_item, id_component, id_combo, price, id_admin, quantity FROM ITEM";
    private static final String SQL_INSERT   =
      "INSERT INTO ITEM(id_component, id_combo, price, id_admin) VALUES(?,?,?,?)";
    private static final String SQL_UPDATE   =
      "UPDATE ITEM SET id_component = ?, id_combo = ?, price = ?, id_admin = ? WHERE id_item = ?";
    private static final String SQL_DELETE   =
      "DELETE FROM ITEM WHERE id_item = ?";

    @Override
    public Item findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new Item(
                    rs.getInt("id_component"),
                    rs.getInt("id_combo"),
                    rs.getDouble("price"),
                    rs.getInt("id_admin"),
                    rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding Item", e);
        }
    }

    @Override
    public List<Item> findAll() throws DAOException {
        List<Item> list = new ArrayList<>();
        try (Connection c = Conexion.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SQL_FIND_ALL)) {
            while (rs.next()) {
                list.add(new Item(
                        rs.getInt("id_component"),
                        rs.getInt("id_combo"),
                        rs.getDouble("price"),
                        rs.getInt("id_admin"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing Items", e);
        }
        return list;
    }

    @Override
    public void insert(Item item) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_INSERT)) {
            ps.setInt(1, item.getIdComponent());
            ps.setInt(2, item.getIdCombo());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getIdAdmin());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error inserting Item", e);
        }
    }

    @Override
    public void update(Item item) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, item.getIdComponent());
            ps.setInt(2, item.getIdCombo());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getIdAdmin());
            ps.setInt(5, item.getIdItem()); // Este estaba faltando
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new DAOException("No se encontró Item con id: " + item.getIdItem(), null);
            }
        } catch (SQLException e) {
            throw new DAOException("Error updating Item", e);
        }
    }

    @Override
    public void delete(int id_item) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id_item);
            int deleted = ps.executeUpdate();
            if (deleted == 0) {
                throw new DAOException("No se encontró Item con id: " + id_item, null);
            }
        } catch (SQLException e) {
            throw new DAOException("Error deleting Item", e);
        }
    }
}
