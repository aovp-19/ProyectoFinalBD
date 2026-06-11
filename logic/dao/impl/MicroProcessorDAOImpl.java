// MicroprocessorDAOImpl.java
package logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.dao.MicroprocessorDAO;
import logic.model.MicroProcessor;
import logic.util.Conexion;
import logic.util.DAOException;

public class MicroProcessorDAOImpl implements MicroprocessorDAO {

	private static final String SQL_FIND_ID =
		    "SELECT "
		  + "  c.id_component, c.brand, c.price, c.units, c.series, "  
		  + "  m.model, m.socket, m.processing_speed "   
		  + "FROM COMPONENT AS c "  
		  + "JOIN MICROPROCESSOR AS m " 
		  + "  ON c.id_component = m.id_component "  
		  + "WHERE c.id_component = ?";   
    private static final String SQL_FIND_ALL =
    	    "SELECT "
    	  + "  c.id_component, c.brand, c.price, c.units, c.series, "  
    	  + "  m.model, m.socket, m.processing_speed "                           
    	  + "FROM COMPONENT AS c "                                    
    	  + "JOIN MICROPROCESSOR AS m "                              
    	  + "  ON c.id_component = m.id_component";    
    private static final String SQL_INSERT   = "INSERT INTO MICROPROCESSOR(model, socket) VALUES(?,?)";
    private static final String SQL_UPDATE   = "UPDATE MICROPROCESSOR SET model = ?, socket = ? WHERE id_component = ?";
    private static final String SQL_DELETE   = "DELETE FROM MICROPROCESSOR WHERE id_component = ?";

    @Override
    public MicroProcessor findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new MicroProcessor(
                    rs.getInt("id_component"),
                    rs.getString("brand"),
                    rs.getDouble("price"),
                    rs.getInt("units"),
                    rs.getInt("series"),
                    rs.getString("model"),
                    rs.getString("socket"),
                    rs.getInt("processing_speed"), 0
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding Microprocessor", e);
        }
    }

    @Override
    public List<MicroProcessor> findAll() throws DAOException {
        List<MicroProcessor> list = new ArrayList<>();
        try (Connection c = Conexion.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SQL_FIND_ALL)) {

            while (rs.next()) {
                list.add(new MicroProcessor(
                		rs.getInt("id_component"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getInt("units"),
                        rs.getInt("series"),
                        rs.getString("model"),
                        rs.getString("socket"),
                        rs.getInt("processing_speed"), 0
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing Microprocessors", e);
        }
        return list;
    }

    @Override
    public void insert(MicroProcessor m) throws DAOException {
        String SQL_INS_COMP  = 
          "INSERT INTO COMPONENT(brand, price, units, series, id_person) VALUES(?,?,?,?,?)";
        String SQL_INS_MICRO = 
          "INSERT INTO MICROPROCESSOR(id_component, model, socket, processing_speed) VALUES(?,?,?,?)";

        try (Connection c = Conexion.getConnection()) {
            c.setAutoCommit(false);

            // 1) Inserto en COMPONENT y recupero el ID generado
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

            // 2) Inserto en MICROPROCESSOR usando ese mismo ID
            try (PreparedStatement ps2 = c.prepareStatement(SQL_INS_MICRO)) {
                ps2.setInt(   1, m.getId());
                ps2.setString(2, m.getModel());
                ps2.setString(3, m.getSocket());
                ps2.setFloat(   4, m.getProcessingSpeed());
                ps2.executeUpdate();
            }

            c.commit();

        } catch (SQLException e) {
            throw new DAOException("Error inserting Microprocessor", e);
        }
    }

    @Override
    public void update(MicroProcessor m) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, m.getModel());
            ps.setString(2, m.getSocket());
            ps.setInt(3, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error updating Microprocessor", e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting Microprocessor", e);
        }
    }
}
