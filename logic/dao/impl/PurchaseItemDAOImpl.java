package logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logic.dao.PurchaseItemDAO;
import logic.model.PurchaseItem;
import logic.util.Conexion;
import logic.util.DAOException;

public class PurchaseItemDAOImpl implements PurchaseItemDAO {

    private static final String SQL_FIND_BY_PURCHASE   =
        "SELECT id_purchase, id_item, quantity FROM PURCHASE_ITEM WHERE id_purchase = ?";
    private static final String SQL_INSERT             =
        "INSERT INTO PURCHASE_ITEM(id_purchase, id_item, quantity) VALUES(?,?,?)";
    private static final String SQL_DELETE_BY_PURCHASE =
        "DELETE FROM PURCHASE_ITEM WHERE id_purchase = ?";

    @Override
    public List<PurchaseItem> findByPurchaseId(int idPurchase) throws DAOException {
        List<PurchaseItem> lista = new ArrayList<>();
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_BY_PURCHASE)) {
            ps.setInt(1, idPurchase);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new PurchaseItem(
                        rs.getInt("id_purchase"),
                        rs.getInt("id_item"),
                        rs.getInt("quantity")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(
                "Error finding purchase items for purchase " + idPurchase, e);
        }
        return lista;
    }

    @Override
    public void insert(PurchaseItem pi) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_INSERT)) {
            ps.setInt(1, pi.getPurchaseId());
            ps.setInt(2, pi.getItemId());
            ps.setInt(3, pi.getQuantity());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error inserting PurchaseItem", e);
        }
    }

    @Override
    public void deleteByPurchaseId(int idPurchase) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE_BY_PURCHASE)) {
            ps.setInt(1, idPurchase);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(
                "Error deleting PurchaseItems for purchase " + idPurchase, e);
        }
    }
}
