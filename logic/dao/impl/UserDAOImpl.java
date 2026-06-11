package logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.dao.UserDAO;
import logic.model.Administration;
import logic.model.User;
import logic.util.Conexion;
import logic.util.DAOException;

public class UserDAOImpl implements UserDAO {

    private static final String SQL_FIND_ID  =
      "SELECT id_user, type, username, password, id_admin FROM USERS WHERE id_user = ?";
    private static final String SQL_FIND_ALL =
      "SELECT id_user, type, username, password, id_admin FROM USERS";
    private static final String SQL_INSERT   =
      "INSERT INTO USERS(type, username, password, id_admin) VALUES(?,?,?,?)";
    private static final String SQL_UPDATE   =
      "UPDATE USERS SET type = ?, username = ?, password = ? WHERE id_user = ?";
    private static final String SQL_DELETE   =
      "DELETE FROM USERS WHERE id_user = ?";

    @Override
    public User findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new User(
                	rs.getInt("id_user"),
                    rs.getString("type"),
                    rs.getString("username"),
                    rs.getString("password"),
                    1
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding User", e);
        }
    }

    @Override
    public List<User> findAll() throws DAOException {
        List<User> list = new ArrayList<>();
        try (Connection c = Conexion.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SQL_FIND_ALL)) {
            while (rs.next()) {
                list.add(new User(
                		rs.getInt("id_user"),
                		rs.getString("type"),
                        rs.getString("username"),
                        rs.getString("password"),
                        1
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing Users", e);
        }
        return list;
    }

    @Override
    public void insert(User u) throws DAOException {
        final String SQL_INS_USER =
            "INSERT INTO USERS(type, username, password, id_admin) VALUES(?,?,?,?)";

        try (Connection c = Conexion.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = 
                     c.prepareStatement(SQL_INS_USER, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, u.getType());
                ps.setString(2, u.getUsername());
                ps.setString(3, u.getPassword());
                ps.setInt(4, 1); //id del admin
                ps.executeUpdate();

                // 3) leemos el id generado
                try (ResultSet gk = ps.getGeneratedKeys()) {
                    if (!gk.next()) {
                        throw new DAOException("No se generó id_user", null);
                    }
                    u.setId(gk.getInt(1));
                }
            }

            // 4) confirmamos la transacción
            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Error inserting User", e);
        }
    }


    @Override
    public void update(User u) throws DAOException {
        final String SQL_UPDATE =
            "UPDATE USERS "
          + "SET type = ?, username = ?, password = ? "
          + "WHERE id_user = ?";

        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_UPDATE)) {

            c.setAutoCommit(false);
            ps.setString(1, u.getType());      
            ps.setString(2, u.getUsername()); 
            ps.setString(3, u.getPassword()); 
            ps.setInt(4, u.getId());

            ps.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            throw new DAOException("Error updating User", e);
        }
    }


    @Override
    public void delete(int id_user) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id_user);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting User", e);
        }
    }

	@Override
	public User findByUsername(String username) throws DAOException {
	    final String SQL_FIND_BY_USERNAME = 
	        "SELECT id_user, type, username, password, id_admin FROM USERS WHERE username = ?";
	    try (Connection c = Conexion.getConnection();
	         PreparedStatement ps = c.prepareStatement(SQL_FIND_BY_USERNAME)) {
	        ps.setString(1, username);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (!rs.next()) return null;
	            User user = new User(
	            	rs.getInt("id_user"), 
	                rs.getString("type"),
	                rs.getString("username"),
	                rs.getString("password"),
	                1
	            );
	            user.setId(rs.getInt("id_user"));
	            return user;
	        }
	    } catch (SQLException e) {
	        throw new DAOException("Error finding User by username", e);
	    }
	}

}
