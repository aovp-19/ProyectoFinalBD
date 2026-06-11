package logic.dao.test;

import java.util.List;

import logic.dao.UserDAO;
import logic.dao.impl.UserDAOImpl;
import logic.model.User;
import logic.util.DAOException;

public class UserDAOTest {
    public static void main(String[] args) {
        UserDAO dao = new UserDAOImpl();
        try {
            // 1) Antes
            List<User> antes = dao.findAll();
            System.out.println("Antes de insertar Users: " + antes.size());

            // 2) Insertar
            User u = new User(0, "admin", "rensoAdmin", "1234", 1);
            dao.insert(u);
            System.out.println("Insertado User username=" + u.getUsername());

            // 3) findById (username)
            User traido = dao.findById(u.getId());
            System.out.println("Traído: type=" + traido.getType());

            // 4) Update
            traido.setPassword("nuevaPass!");
            dao.update(traido);
            System.out.println("Actualizada password a " + dao.findById(u.getId()).getPassword());

            // 5) Delete
            //dao.delete(u.getId());
            //System.out.println("Borrado. findById devuelve " + dao.findById(u.getId()));

            // 6) Después
            System.out.println("Después de borrar Users: " + dao.findAll().size());

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
