package logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.dao.ComboComponentDAO;
import logic.model.ComboComponent;
import logic.util.Conexion;
import logic.util.DAOException;

public class ComboComponentDAOImpl implements ComboComponentDAO {

    private static final String SQL_INSERT =
        "INSERT INTO COMBO_COMPONENT (id_combo, id_component, quantity) VALUES (?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE COMBO_COMPONENT SET quantity = ? WHERE id_combo = ? AND id_component = ?";

    private static final String SQL_DELETE =
        "DELETE FROM COMBO_COMPONENT WHERE id_combo = ? AND id_component = ?";

    private static final String SQL_FIND =
        "SELECT id_combo, id_component, quantity FROM COMBO_COMPONENT WHERE id_combo = ? AND id_component = ?";

    private static final String SQL_FIND_BY_COMBO =
        "SELECT id_combo, id_component, quantity FROM COMBO_COMPONENT WHERE id_combo = ?";

    @Override
    public void insert(ComboComponent cc) throws DAOException {
        try {
            // Verificar si ya existe la relación para evitar duplicados
            if (find(cc.getIdCombo(), cc.getIdComponent()) != null) {
                throw new DAOException("La relación Combo-Component ya existe. No se puede insertar duplicado.", null);
            }

            try (Connection conn = Conexion.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
                ps.setInt(1, cc.getIdCombo());
                ps.setInt(2, cc.getIdComponent());
                ps.setInt(3, cc.getQuantity());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("Error inserting ComboComponent", e);
        }
    }

    @Override
    public void update(ComboComponent cc) throws DAOException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, cc.getQuantity());
            ps.setInt(2, cc.getIdCombo());
            ps.setInt(3, cc.getIdComponent());
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new DAOException("No existe la asociación para actualizar", null);
            }
        } catch (SQLException e) {
            throw new DAOException("Error updating ComboComponent", e);
        }
    }

    @Override
    public void delete(int idCombo, int idComponent) throws DAOException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, idCombo);
            ps.setInt(2, idComponent);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting ComboComponent", e);
        }
    }

    @Override
    public ComboComponent find(int idCombo, int idComponent) throws DAOException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND)) {
            ps.setInt(1, idCombo);
            ps.setInt(2, idComponent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ComboComponent(
                        rs.getInt("id_combo"),
                        rs.getInt("id_component"),
                        rs.getInt("quantity")
                    );
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding ComboComponent", e);
        }
        return null;
    }

    @Override
    public List<ComboComponent> findByCombo(int idCombo) throws DAOException {
        List<ComboComponent> list = new ArrayList<>();
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_COMBO)) {
            ps.setInt(1, idCombo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ComboComponent(
                        rs.getInt("id_combo"),
                        rs.getInt("id_component"),
                        rs.getInt("quantity")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding ComboComponents by combo", e);
        }
        return list;
    }
}
