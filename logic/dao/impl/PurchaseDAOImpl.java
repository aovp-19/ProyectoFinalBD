// logic/dao/impl/PurchaseDAOImpl.java
package logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.dao.PurchaseDAO;
import logic.model.Purchase;
import logic.util.Conexion;
import logic.util.DAOException;

public class PurchaseDAOImpl implements PurchaseDAO {

    private static final String SQL_FIND_ID  =
      "SELECT id_purchase, date_sold, id_admin, id_person, id_user, total_price "
    + "FROM PURCHASE WHERE id_purchase = ?";
    private static final String SQL_FIND_ALL =
      "SELECT id_purchase, date_sold, id_admin, id_person, id_user, total_price FROM PURCHASE";
    private static final String SQL_INSERT   =
      "INSERT INTO PURCHASE(date_sold, id_admin, id_person, id_user, total_price) VALUES(?,?,?,?,?)";
    private static final String SQL_UPDATE   =
      "UPDATE PURCHASE SET date_sold = ?, id_admin = ?, id_person = ?, id_user = ?, total_price = ? "
    + "WHERE id_purchase = ?";
    private static final String SQL_DELETE   =
      "DELETE FROM PURCHASE WHERE id_purchase = ?";

    @Override
    public Purchase findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new Purchase(
                    rs.getInt("id_purchase"),
                    rs.getDate("date_sold"),
                    rs.getInt("id_admin"),
                    rs.getInt("id_person"),
                    rs.getInt("id_user"), null
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding Purchase", e);
        }
    }

    @Override
    public List<Purchase> findAll() throws DAOException {
        List<Purchase> list = new ArrayList<>();
        try (Connection c = Conexion.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SQL_FIND_ALL)) {
            while (rs.next()) {
                list.add(new Purchase(
                		rs.getInt("id_purchase"),
                        rs.getDate("date_sold"),
                        rs.getInt("id_admin"),
                        rs.getInt("id_person"),
                        rs.getInt("id_user"), null
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing Purchases", e);
        }
        return list;
    }

    @Override
    public void insert(Purchase p) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_INSERT)) {
            ps.setDate( 1, new java.sql.Date(p.getDateSold().getTime()));
            ps.setInt(   2, p.getIdAdmin());
            ps.setInt(   3, p.getIdPerson());
            ps.setInt(   4, p.getIdUser());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error inserting Purchase", e);
        }
    }

    @Override
    public void update(Purchase p) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_UPDATE)) {
            ps.setDate( 1, new java.sql.Date(p.getDateSold().getTime()));
            ps.setInt(   2, p.getIdAdmin());
            ps.setInt(   3, p.getIdPerson());
            ps.setInt(   4, p.getIdUser());
            ps.setInt(   6, p.getIdPurchase());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error updating Purchase", e);
        }
    }

    @Override
    public void delete(int id_purchase) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id_purchase);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting Purchase", e);
        }
    }
}
