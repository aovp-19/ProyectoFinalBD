// src/logic/dao/impl/PersonDAOImpl.java
package logic.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import logic.dao.PersonDAO;
import logic.model.Person;
import logic.util.Conexion;
import logic.util.DAOException;

public class PersonDaoImpl implements PersonDAO {
    private static final String SQL_FIND_ID  =
        "SELECT id_person, ni, name, last_name, phone, address, email, id_admin " +
        "FROM PERSON WHERE id_person = ?";
    private static final String SQL_FIND_ALL =
        "SELECT id_person, ni, name, last_name, phone, address, email, id_admin FROM PERSON";
    private static final String SQL_INSERT   =
        "INSERT INTO PERSON(ni, name, last_name, phone, address, email, id_admin) VALUES(?,?,?,?,?,?, ?)";
    private static final String SQL_UPDATE   =
        "UPDATE PERSON SET ni = ?, name = ?, last_name = ?, phone = ?, address = ?, email = ?, id_admin = ? WHERE id_person = ?";
    private static final String SQL_DELETE   =
        "DELETE FROM PERSON WHERE id_person = ?";

    @Override
    public Person findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new Person(
                    rs.getString("ni"),
                    rs.getString("name"),
                    rs.getString("last_name"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getInt("id_person"),
                    1
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding Person", e);
        }
    }

    @Override
    public List<Person> findAll() throws DAOException {
        List<Person> list = new ArrayList<>();
        try (Connection c = Conexion.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SQL_FIND_ALL)) {

            while (rs.next()) {
                list.add(new Person(
                		rs.getString("ni"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getInt("id_person"),
                        1
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing Persons", e);
        }
        return list;
    }

    @Override
    public void insert(Person p) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            c.setAutoCommit(false);
            ps.setString(1, p.getNi());
            ps.setString(2, p.getName());
            ps.setString(3, p.getLast_name());
            ps.setString(4, p.getPhone());
            ps.setString(5, p.getAddress());
            ps.setString(6, p.getEmail());
            ps.setInt(7, 1);

            ps.executeUpdate();

            try (ResultSet gk = ps.getGeneratedKeys()) {
                if (!gk.next()) throw new DAOException("No se generó id_person", null);
                p.setId(gk.getInt(1));
            }
            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Error inserting Person", e);
        }
    }


    @Override
    public void update(Person p) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_UPDATE)) {

            c.setAutoCommit(false);
            ps.setString(1, p.getNi());
            ps.setString(2, p.getName());
            ps.setString(3, p.getPhone());
            ps.setString(4, p.getAddress());
            ps.setString(5, p.getEmail());
            ps.setInt(6, /* id_admin */ 1);
            ps.setInt(7, p.getId());
            ps.executeUpdate();
            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Error updating Person", e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting Person", e);
        }
    }
}
