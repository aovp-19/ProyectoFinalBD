package logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.dao.ComboDAO;
import logic.dao.ComponentDAO;
import logic.model.Combo;
import logic.model.Component;
import logic.util.Conexion;
import logic.util.DAOException;

public class ComboDAOImpl implements ComboDAO {

    private static final String SQL_FIND_ID =
        "SELECT id_combo, description, discounts " +
        "FROM COMBO WHERE id_combo = ?";

    private static final String SQL_FIND_ALL =
    	"SELECT id_combo, description, discounts " +
        "FROM COMBO";

    private static final String SQL_FIND_COMPS =
        "SELECT id_component " +
        "FROM COMBO_COMPONENT " +
        "WHERE id_combo = ?";

    private static final String SQL_INSERT_COMBO =
        "INSERT INTO COMBO(description, discounts) VALUES(?,?)";

    private static final String SQL_UPDATE_COMBO =
        "UPDATE COMBO SET description = ?, discounts = ? " +
        "WHERE id_combo = ?";

    private static final String SQL_DELETE_COMBO =
        "DELETE FROM COMBO WHERE id_combo = ?";

    private static final String SQL_DELETE_COMPC =
        "DELETE FROM COMBO_COMPONENT WHERE id_combo = ?";

    private static final String SQL_INSERT_COMPC =
        "INSERT INTO COMBO_COMPONENT(id_combo, id_component, quantity) VALUES(?,?,?)";

    // DAO auxiliar para recuperar componentes uno a uno
    private final ComponentDAO componentDAO = new ComponentDAOImpl();

    @Override
    public Combo findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                String desc    = rs.getString("description");
                int    discount= rs.getInt("discounts");

                // recupera lista de componentes
                List<Component> comps = new ArrayList<>();
                try (PreparedStatement ps2 = c.prepareStatement(SQL_FIND_COMPS)) {
                    ps2.setInt(1, id);
                    try (ResultSet rs2 = ps2.executeQuery()) {
                        while (rs2.next()) {
                            int compId = rs2.getInt("id_component");
                            Component comp = componentDAO.findById(compId);
                            if (comp != null) comps.add(comp);
                        }
                    }
                }

                return new Combo(id, desc, discount, new ArrayList<>(comps));
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding Combo", e);
        }
    }

    @Override
    public List<Combo> findAll() throws DAOException {
        List<Combo> list = new ArrayList<>();
        try (Connection c = Conexion.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SQL_FIND_ALL)) {

            while (rs.next()) {
                int    id      = rs.getInt("id_combo");
                String desc    = rs.getString("description");
                float    discount= rs.getInt("discounts");

                // reutiliza findById para traer la lista de componentes
                list.add(findById(id));
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing Combos", e);
        }
        return list;
    }

    @Override
    public void insert(Combo combo) throws DAOException {
        try (Connection c = Conexion.getConnection()) {
            c.setAutoCommit(false);

            // 1) inserta en COMBO y obtiene id generado
            try (PreparedStatement ps = c.prepareStatement(
                     SQL_INSERT_COMBO, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, combo.getName());
                ps.setInt(2, combo.getDiscount());
                ps.executeUpdate();
                try (ResultSet gk = ps.getGeneratedKeys()) {
                    if (!gk.next()) throw new DAOException("No se genero id_combo", null);
                    combo.setId(gk.getInt(1));
                }
            }

            // 2) inserta las relaciones en COMBO_COMPONENT
            try (PreparedStatement ps = c.prepareStatement(SQL_INSERT_COMPC)) {
                for (Component comp : combo.getComboComp()) {
                    ps.setInt(1, combo.getId());
                    ps.setInt(2, comp.getId());
                    ps.setInt(3, 1);  // cantidad fija = 1
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Error inserting Combo", e);
        }
    }

    @Override
    public void update(Combo combo) throws DAOException {
        try (Connection c = Conexion.getConnection()) {
            c.setAutoCommit(false);

            // 1) actualiza tabla COMBO
            try (PreparedStatement ps = c.prepareStatement(SQL_UPDATE_COMBO)) {
                ps.setString(1, combo.getName());
                ps.setInt(2, combo.getDiscount());
                ps.setInt(3, combo.getId());
                ps.executeUpdate();
            }

            // 2) borra las relaciones anteriores
            try (PreparedStatement ps = c.prepareStatement(SQL_DELETE_COMPC)) {
                ps.setInt(1, combo.getId());
                ps.executeUpdate();
            }

            // 3) inserta la nueva lista de componentes
            try (PreparedStatement ps = c.prepareStatement(SQL_INSERT_COMPC)) {
                for (Component comp : combo.getComboComp()) {
                    ps.setInt(1, combo.getId());
                    ps.setInt(2, comp.getId());
                    ps.setInt(3, 1);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Error updating Combo", e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection c = Conexion.getConnection()) {
            c.setAutoCommit(false);

            // 1) elimina relaciones
            try (PreparedStatement ps = c.prepareStatement(SQL_DELETE_COMPC)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            // 2) elimina combo
            try (PreparedStatement ps = c.prepareStatement(SQL_DELETE_COMBO)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Error deleting Combo", e);
        }
    }
}
