// Para RAM
package logic.dao.impl;

import java.sql.*;
import java.util.*;
import logic.dao.RamDAO;
import logic.model.RAM;
import logic.util.Conexion;
import logic.util.DAOException;

public class RamDAOImpl implements RamDAO {

    private static final String SQL_FIND_ID  =
          "SELECT c.id_component, c.brand, c.price, c.units, c.series, "
        + "r.capacity, r.type "
        + "FROM COMPONENT c "
        + "  JOIN RAM r ON c.id_component = r.id_component "
        + "WHERE c.id_component = ?";

    private static final String SQL_FIND_ALL =
          "SELECT c.id_component, c.brand, c.price, c.units, c.series, "
        + "r.capacity, r.type "
        + "FROM COMPONENT c "
        + "  JOIN RAM r ON c.id_component = r.id_component";

    private static final String SQL_INSERT = "INSERT INTO RAM(id_component, capacity, type) VALUES(?,?,?)";
    private static final String SQL_UPDATE = "UPDATE RAM SET capacity = ?, type = ? WHERE id_component = ?";
    private static final String SQL_DELETE = "DELETE FROM RAM WHERE id_component = ?";

    @Override
    public RAM findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection(); PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new RAM(
                    rs.getInt("id_component"),
                    rs.getString("brand"),
                    rs.getDouble("price"),
                    rs.getInt("units"),
                    rs.getInt("series"),
                    rs.getInt("capacity"),
                    rs.getString("type"),
                    0
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding RAM", e);
        }
    }

    @Override
    public List<RAM> findAll() throws DAOException {
        List<RAM> list = new ArrayList<>();
        try (Connection c = Conexion.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(SQL_FIND_ALL)) {
            while (rs.next()) {
                list.add(new RAM(
                    rs.getInt("id_component"),
                    rs.getString("brand"),
                    rs.getDouble("price"),
                    rs.getInt("units"),
                    rs.getInt("series"),
                    rs.getInt("capacity"),
                    rs.getString("type"),
                    0
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing RAMs", e);
        }
        return list;
    }

    @Override
    public void insert(RAM ram) throws DAOException {
        String SQL_INS_COMP  = "INSERT INTO COMPONENT(brand, price, units, series, id_person) VALUES(?,?,?,?,?)";
        try (Connection c = Conexion.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps1 = c.prepareStatement(SQL_INS_COMP, Statement.RETURN_GENERATED_KEYS)) {
            	ps1.setString(1, ram.getBrand());
                ps1.setDouble(2, ram.getPrice());
                ps1.setInt(   3, ram.getUnits());
                ps1.setInt(   4, ram.getSerie());
                ps1.setInt(5, ram.getIdSupplier());
                ps1.executeUpdate();

                try (ResultSet gen = ps1.getGeneratedKeys()) {
                    if (!gen.next()) throw new DAOException("No se generó id_component", null);
                    ram.setId(gen.getInt(1));
                }
            }

            try (PreparedStatement ps2 = c.prepareStatement(SQL_INSERT)) {
                ps2.setInt(1, ram.getId());
                ps2.setInt(2, ram.getCapacity());
                ps2.setString(3, ram.getType());
                ps2.executeUpdate();
            }
            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Error inserting RAM", e);
        }
    }

    @Override
    public void update(RAM ram) throws DAOException {
        try (Connection c = Conexion.getConnection(); PreparedStatement ps = c.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, ram.getCapacity());
            ps.setString(2, ram.getType());
            ps.setInt(3, ram.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error updating RAM", e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection c = Conexion.getConnection(); PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting RAM", e);
        }
    }
}

// Puedes replicar este patrón también para HardDisk y MicroProcessor ya que tus DAOs están 90% correctos.
// Solo valida bien los parámetros de SQL_UPDATE y SQL_INSERT para cada caso.

// Si deseas que te cree las clases modelo completas (RAM.java, HardDisk.java, MicroProcessor.java) con sus constructores, dímelo y te las genero ahora mismo también.
