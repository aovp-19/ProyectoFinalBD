package logic.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import logic.dao.HardDiskDAO;
import logic.model.HardDisk;
import logic.util.Conexion;
import logic.util.DAOException;

public class HardDiskDAOImpl implements HardDiskDAO {

    private static final String SQL_FIND_ID =
        "SELECT c.id_component, c.brand, c.price, c.units, c.series, " +
        "h.model, h.connectionType, h.capacity " +
        "FROM COMPONENT c JOIN HARDDISK h ON c.id_component = h.id_component WHERE c.id_component = ?";

    private static final String SQL_FIND_ALL =
        "SELECT c.id_component, c.brand, c.price, c.units, c.series, " +
        "h.model, h.connectionType, h.capacity " +
        "FROM COMPONENT c JOIN HARDDISK h ON c.id_component = h.id_component";

    private static final String SQL_INSERT_COMP =
        "INSERT INTO COMPONENT(brand, price, units, series, id_person) VALUES(?,?,?,?,?)";

    private static final String SQL_INSERT_HD =
        "INSERT INTO HARDDISK(id_component, model, connectionType, capacity) VALUES(?,?,?,?)";

    private static final String SQL_UPDATE =
        "UPDATE HARDDISK SET model = ?, connectionType = ?, capacity = ? WHERE id_component = ?";

    private static final String SQL_DELETE =
        "DELETE FROM HARDDISK WHERE id_component = ?";

    @Override
    public HardDisk findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                return new HardDisk(
                    rs.getInt("id_component"),
                    rs.getString("brand"),
                    rs.getDouble("price"),
                    rs.getInt("units"),
                    rs.getInt("series"),
                    rs.getString("model"),
                    rs.getInt("capacity"),
                    rs.getString("connectionType"),
                    0 // id_supplier
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding HardDisk", e);
        }
    }

    @Override
    public List<HardDisk> findAll() throws DAOException {
        List<HardDisk> list = new ArrayList<>();
        try (Connection c = Conexion.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SQL_FIND_ALL)) {
            
            while (rs.next()) {
                list.add(new HardDisk(
                    rs.getInt("id_component"),
                    rs.getString("brand"),
                    rs.getDouble("price"),
                    rs.getInt("units"),
                    rs.getInt("series"),
                    rs.getString("model"),
                    rs.getInt("capacity"),
                    rs.getString("connectionType"),
                    0
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing HardDisks", e);
        }
        return list;
    }

    @Override
    public void insert(HardDisk h) throws DAOException {
        try (Connection c = Conexion.getConnection()) {
            c.setAutoCommit(false);

            try (PreparedStatement ps1 = c.prepareStatement(SQL_INSERT_COMP, Statement.RETURN_GENERATED_KEYS)) {
            	ps1.setString(1, h.getBrand());
                ps1.setDouble(2, h.getPrice());
                ps1.setInt(   3, h.getUnits());
                ps1.setInt(   4, h.getSerie());
                ps1.setInt(5, h.getIdSupplier());
                ps1.executeUpdate();

                try (ResultSet gen = ps1.getGeneratedKeys()) {
                    if (!gen.next()) throw new DAOException("No se generó id_component", null);
                    h.setId(gen.getInt(1));
                }
            }

            try (PreparedStatement ps2 = c.prepareStatement(SQL_INSERT_HD)) {
                ps2.setInt(1, h.getId());
                ps2.setString(2, h.getModel());
                ps2.setString(3, h.getConnectionType());
                ps2.setInt(4, h.getCapacity());
                ps2.executeUpdate();
            }

            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Error inserting HardDisk", e);
        }
    }

    @Override
    public void update(HardDisk h) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, h.getModel());
            ps.setString(2, h.getConnectionType());
            ps.setInt(3, h.getCapacity());
            ps.setInt(4, h.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error updating HardDisk", e);
        }
    }

    @Override
    public void delete(int id_component) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id_component);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting HardDisk", e);
        }
    }
}
