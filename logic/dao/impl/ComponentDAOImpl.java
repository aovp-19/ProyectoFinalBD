package logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.dao.ComponentDAO;
import logic.dao.HardDiskDAO;
import logic.dao.MicroprocessorDAO;
import logic.dao.MotherboardDAO;
import logic.dao.RamDAO;
import logic.model.Component;
import logic.model.HardDisk;
import logic.model.MicroProcessor;
import logic.model.MotherBoard;
import logic.model.RAM;
import logic.util.Conexion;
import logic.util.DAOException;

public class ComponentDAOImpl implements ComponentDAO {

    private static final String SQL_FIND_ID  =
        "SELECT id_component, price, units, brand, series, id_person " +
        "FROM COMPONENT WHERE id_component = ?";
    private static final String SQL_FIND_ALL =
        "SELECT id_component, price, units, brand, series, id_person FROM COMPONENT";
    private static final String SQL_INSERT   =
        "INSERT INTO COMPONENT(price, units, brand, series, id_person) VALUES(?,?,?,?,?)";
    private static final String SQL_UPDATE   =
        "UPDATE COMPONENT SET price = ?, units = ?, brand = ?, series = ?, id_person = ? " +
        "WHERE id_component = ?";
    private static final String SQL_DELETE   =
        "DELETE FROM COMPONENT WHERE id_component = ?";

    @Override
    public Component findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new Component(
                    rs.getInt("id_component"),
                    rs.getString("brand"),
                    rs.getDouble("price"),
                    rs.getInt("units"),
                    rs.getInt("series"),
                    rs.getInt("id_person")
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding Component", e);
        }
    }

 // ComponentDAOImpl.findAll()

    public List<Component> findAll() throws DAOException {
        List<Component> list = new ArrayList<>();

        // 1. Obtener RAM
        RamDAOImpl ramDAO = new RamDAOImpl();
        list.addAll(ramDAO.findAll());

        // 2. Obtener HardDisks
        HardDiskDAOImpl hdDAO = new HardDiskDAOImpl();
        list.addAll(hdDAO.findAll());

        // 3. Obtener MicroProcessors
        MicroProcessorDAOImpl mpDAO = new MicroProcessorDAOImpl();
        list.addAll(mpDAO.findAll());

        // 4. Obtener Motherboards
        MotherBoardDAOImpl mbDAO = new MotherBoardDAOImpl();
        list.addAll(mbDAO.findAll());

        return list;
    }



    @Override
    public void insert(Component component) throws DAOException {
        if (component instanceof MotherBoard) {
            new MotherBoardDAOImpl().insert((MotherBoard) component);
        } else if (component instanceof RAM) {
            new RamDAOImpl().insert((RAM) component);
        } else if (component instanceof HardDisk) {
            new HardDiskDAOImpl().insert((HardDisk) component);
        } else if (component instanceof MicroProcessor) {
            new MicroProcessorDAOImpl().insert((MicroProcessor) component);
        } else {
            // Si no es un tipo conocido, hacer inserción genérica como ahora (opcional)
            try (Connection c = Conexion.getConnection();
                 PreparedStatement ps = c.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

                c.setAutoCommit(false);
                ps.setDouble(1, component.getPrice());
                ps.setInt(2, component.getUnits());
                ps.setString(3, component.getBrand());
                ps.setInt(4, component.getSerie());
                ps.setInt(5, component.getIdSupplier());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        component.setId(rs.getInt(1));
                    }
                }
                c.commit();
            } catch (SQLException e) {
                throw new DAOException("Error inserting generic Component", e);
            }
        }
    }


    @Override
    public void update(Component component) throws DAOException {
        if (component instanceof MotherBoard) {
            new MotherBoardDAOImpl().update((MotherBoard) component);
        } else if (component instanceof RAM) {
            new RamDAOImpl().update((RAM) component);
        } else if (component instanceof HardDisk) {
            new HardDiskDAOImpl().update((HardDisk) component);
        } else if (component instanceof MicroProcessor) {
            new MicroProcessorDAOImpl().update((MicroProcessor) component);
        } else {
            try (Connection c = Conexion.getConnection();
                 PreparedStatement ps = c.prepareStatement(SQL_UPDATE)) {

                c.setAutoCommit(false);
                ps.setDouble(1, component.getPrice());
                ps.setInt(2, component.getUnits());
                ps.setString(3, component.getBrand());
                ps.setInt(4, component.getSerie());
                ps.setInt(5, component.getIdSupplier());
                ps.setInt(6, component.getId());
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                throw new DAOException("Error updating generic Component", e);
            }
        }
    }


    @Override
    public void delete(int id_component) throws DAOException {
        // Para saber a qué DAO delegar, puedes usar una consulta que obtenga el tipo si guardas el tipo
        // en la base de datos. Si no lo haces, puedes directamente eliminar de las subtablas primero.

        try {
            new MotherBoardDAOImpl().delete(id_component);
            new RamDAOImpl().delete(id_component);
            new HardDiskDAOImpl().delete(id_component);
            new MicroProcessorDAOImpl().delete(id_component);
        } catch (DAOException e) {
            // Si alguna lanza error, continúa con el genérico
        }

        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id_component);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting Component", e);
        }
    }


    @Override
    public int countByType(String type) throws DAOException {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM COMPONENT WHERE type = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error counting components by type: " + type, e);
        }
        return count;
    }
}
