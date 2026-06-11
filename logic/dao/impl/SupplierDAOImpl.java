package logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.dao.SupplierDAO;
import logic.model.Administration;
import logic.model.Supplier;
import logic.util.Conexion;
import logic.util.DAOException;

public class SupplierDAOImpl implements SupplierDAO {

    private static final String SQL_FIND_ID  =
        "SELECT p.id_person, p.ni, p.name, p.last_name, p.phone, p.address, p.email, p.id_admin, s.delivery_time " +
        "FROM PERSON p " +
        "JOIN SUPPLIER s ON p.id_person = s.id_person " +
        "WHERE p.id_person = ?";

    private static final String SQL_FIND_ALL =
        "SELECT p.id_person, p.ni, p.name, p.last_name, p.phone, p.address, p.email, p.id_admin, s.delivery_time " +
        "FROM PERSON p " +
        "JOIN SUPPLIER s ON p.id_person = s.id_person";

    private static final String SQL_INSERT =
        "INSERT INTO SUPPLIER(id_person, delivery_time) VALUES(?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE SUPPLIER SET delivery_time = ? WHERE id_person = ?";

    private static final String SQL_DELETE =
        "DELETE FROM SUPPLIER WHERE id_person = ?";

    @Override
    public Supplier findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new Supplier(
                    rs.getString("ni"),
                    rs.getString("name"),
                    rs.getString("last_name"),  // apellido separado
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getInt("id_person"),
                    rs.getInt("delivery_time"),
                    new ArrayList<>(), // lista de componentes o productos
                    Administration.AdminId
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding Supplier", e);
        }
    }

    @Override
    public List<Supplier> findAll() throws DAOException {
        List<Supplier> list = new ArrayList<>();
        try (Connection c = Conexion.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SQL_FIND_ALL)) {
            while (rs.next()) {
                list.add(new Supplier(
                    rs.getString("ni"),
                    rs.getString("name"),
                    rs.getString("last_name"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getInt("id_person"),
                    rs.getInt("delivery_time"),
                    new ArrayList<>(),
                    Administration.AdminId
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing Suppliers", e);
        }
        return list;
    }

    @Override
    public void insert(Supplier supplier) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_INSERT)) {
            ps.setInt(1, supplier.getId());
            ps.setInt(2, supplier.getDeliveryTime());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error inserting Supplier", e);
        }
    }

    @Override
    public void update(Supplier supplier) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, supplier.getDeliveryTime());
            ps.setInt(2, supplier.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error updating Supplier", e);
        }
    }

    @Override
    public void delete(int id_person) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id_person);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting Supplier", e);
        }
    }
}
