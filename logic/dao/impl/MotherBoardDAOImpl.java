// MotherboardDAOImpl.java
package logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.dao.MotherboardDAO;
import logic.model.MotherBoard;
import logic.util.Conexion;
import logic.util.DAOException;

public class MotherBoardDAOImpl implements MotherboardDAO {
	
	private static ArrayList<String> parseConns(String dbValue) {
	    ArrayList<String> list = new ArrayList<>();
	    if (dbValue != null && !dbValue.trim().isEmpty()) {
	        for (String s : dbValue.split("\\s*,\\s*")) {
	            list.add(s);
	        }
	    }
	    return list;
	}

    private static final String SQL_FIND_ID  =
          "SELECT c.id_component, c.brand, c.price, c.units, c.series, "
        + "m.model, m.socket, m.typeRAM, m.hardDiskCon "
        + "FROM COMPONENT c "
        + "  JOIN MOTHERBOARD m ON c.id_component = m.id_component "
        + "WHERE c.id_component = ?";
    private static final String SQL_FIND_ALL =
          "SELECT c.id_component, c.brand, c.price, c.units, c.series, " + 
          "m.model, m.socket, m.typeRAM, m.hardDiskCon "
        + "FROM COMPONENT c "
        + "JOIN MOTHERBOARD m ON c.id_component = m.id_component";
    private static final String SQL_INSERT   = "INSERT INTO MOTHERBOARD(model, socket, typeRAM, hardDiskCon) VALUES(?,?,?,?)";
    private static final String SQL_UPDATE   = "UPDATE MOTHERBOARD SET model = ?, socket = ?, typeRAM = ?, hardDiskCon = ? WHERE id_component = ?";
    private static final String SQL_DELETE   = "DELETE FROM MOTHERBOARD WHERE id_component = ?";

    @Override
    public MotherBoard findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new MotherBoard(
                		rs.getInt("id_component"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getInt("units"),
                        rs.getInt("series"),
                        rs.getString("model"),
                        rs.getString("socket"),
                        rs.getString("typeRAM"),
                        parseConns(rs.getString("hardDiskCon")),
                        0
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding Motherboard", e);
        }
    }

    @Override
    public List<MotherBoard> findAll() throws DAOException {
        List<MotherBoard> list = new ArrayList<>();
        try (Connection c = Conexion.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SQL_FIND_ALL)) {

            while (rs.next()) {
                list.add(new MotherBoard(
                		rs.getInt("id_component"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getInt("units"),
                        rs.getInt("series"),
                        rs.getString("model"),
                        rs.getString("socket"),
                        rs.getString("typeRAM"),
                        parseConns(rs.getString("hardDiskCon")),
                        0
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing Motherboards", e);
        }
        return list;
    }

    @Override
    public void insert(MotherBoard m) throws DAOException {
    	String SQL_INS_COMP =
    			  "INSERT INTO COMPONENT(brand, price, units, series, id_person) VALUES(?,?,?,?,?)";
    	        String SQL_INS_MOTHER = 
    	          "INSERT INTO MOTHERBOARD(id_component, model, socket, typeRAM, hardDiskCon) VALUES(?,?,?,?,?)";

    	        try (Connection c = Conexion.getConnection()) {
    	            c.setAutoCommit(false);
    	            
    	            try (PreparedStatement ps1 = 
    	                     c.prepareStatement(SQL_INS_COMP, Statement.RETURN_GENERATED_KEYS)) {
    	                ps1.setString(1, m.getBrand());
    	                ps1.setDouble(2, m.getPrice());
    	                ps1.setInt(   3, m.getUnits());
    	                ps1.setInt(   4, m.getSerie());
    	                ps1.setInt(5, m.getIdSupplier());
    	                ps1.executeUpdate();

    	                try (ResultSet gen = ps1.getGeneratedKeys()) {
    	                    if (!gen.next()) {
    	                        throw new DAOException("No se generó id_component", null);
    	                    }
    	                    int newId = gen.getInt(1);
    	                    m.setId(newId);
    	                }
    	            }

    	           
    	            try (PreparedStatement ps2 = c.prepareStatement(SQL_INS_MOTHER)) {
    	                ps2.setInt(   1, m.getId());
    	                ps2.setString(2, m.getModel());
    	                ps2.setString(3, m.getSocket());
    	                ps2.setString(4, m.getTypeRAM());
    	                String conns = (m.getHardDiskCon() == null || m.getHardDiskCon().isEmpty())
    	                		? "" : String.join(",", m.getHardDiskCon()); //que hace join y como se llama esa forma del condicional 
    	                ps2.setString(5, conns);
    	                ps2.executeUpdate();
    	            }

    	            c.commit();

    	        } catch (SQLException e) {
    	            throw new DAOException("Error inserting MotherBoard", e);
    	        }
    }

    @Override
    public void update(MotherBoard m) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, m.getModel());
            ps.setString(2, m.getSocket());
            ps.setString(3, m.getTypeRAM());
            String conns = (m.getHardDiskCon() == null || m.getHardDiskCon().isEmpty())
                    ? "" : String.join(",", m.getHardDiskCon()); //join toma una coleccion o array y la convierte en una cadena con un separador que tu indiques.
            ps.setString(4, conns); // signo de interrogacion, si es verdadero, se cumple la primera, sino se cumple la segunda.
            
            ps.setInt(5, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error updating Motherboard", e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting Motherboard", e);
        }
    }
}
