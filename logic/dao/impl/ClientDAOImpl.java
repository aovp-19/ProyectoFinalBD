package logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.dao.ClientDAO;
import logic.model.Client;
import logic.model.Person;
import logic.util.Conexion;
import logic.util.DAOException;

public class ClientDAOImpl implements ClientDAO {

    private static final String SQL_FIND_ID  =
        "SELECT p.id_person, p.ni, p.name, p.last_name, p.phone, p.address, p.email, p.id_admin "
      + "FROM PERSON p "
      + "JOIN CLIENT c ON p.id_person = c.id_person "
      + "WHERE p.id_person = ?";

    private static final String SQL_FIND_ALL =
        "SELECT p.id_person, p.ni, p.name, p.last_name, p.phone, p.address, p.email, p.id_admin "
      + "FROM PERSON p "
      + "JOIN CLIENT c ON p.id_person = c.id_person";

    private static final String SQL_INSERT =
    	    "INSERT INTO PERSON(ni, name, last_name, phone, address, email, id_admin) VALUES(?,?,?,?,?,?,?)";

    private static final String SQL_UPDATE = "UPDATE CLIENT SET id_person = ? WHERE id_person = ?";

    private static final String SQL_DELETE = "DELETE FROM CLIENT WHERE id_person = ?";

    @Override
    public Client findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
            	if (!rs.next()) return null;
            	Client c1 = new Client(
            	    rs.getString("ni"),
            	    rs.getString("name"),
            	    rs.getString("last_name"),
            	    rs.getString("phone"),
            	    rs.getString("address"),
            	    rs.getString("email"),
            	    0,
            	    rs.getInt("id_admin")
            	);
            	c1.setId(rs.getInt("id_person"));  
            	return c1;
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding Client", e);
        }
    }

    @Override
    public List<Client> findAll() throws DAOException {
        List<Client> list = new ArrayList<>();
        try (Connection c = Conexion.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SQL_FIND_ALL)) {
        	while (rs.next()) {
        	    Client c1 = new Client(
        	        rs.getString("ni"),
        	        rs.getString("name"),
        	        rs.getString("last_name"),
        	        rs.getString("phone"),
        	        rs.getString("address"),
        	        rs.getString("email"),
        	        0,            
        	        rs.getInt("id_admin")
        	    );
        	    c1.setId(rs.getInt("id_person")); 
        	    list.add(c1);
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing Clients", e);
        }
        return list;
    }

    @Override
    public void insert(Client client) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement("INSERT INTO CLIENT(id_person) VALUES(?)")) {
            ps.setInt(1, client.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error inserting Client", e);
        }
    }




    @Override
    public void update(Client client) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, client.getId());
            ps.setInt(2, client.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error updating Client", e);
        }
    }

    @Override
    public void delete(int id_person) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id_person);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting Client", e);
        }
    }

}
