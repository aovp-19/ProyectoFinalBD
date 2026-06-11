package logic.dao.test;

import java.util.List;

import logic.dao.PersonDAO;
import logic.dao.impl.PersonDaoImpl;
import logic.model.Person;
import logic.util.DAOException;

public class PersonDAOTest {
    public static void main(String[] args) {
        PersonDAO dao = new PersonDaoImpl();
        try {
            List<Person> before = dao.findAll();
            System.out.println("Antes de insertar Persons: " + before.size());

 
            Person p = new Person(
            	    "NI-0002",
            	    "Ana María",
            	    "González",
            	    "809-555-5678",
            	    "Avenida Siempre Viva 456",
            	    "ana.gonzalez@mail.com",
            	    2,
            	    1
            	);

            dao.insert(p);
            System.out.println("Insertado Person. (releer findAll para ver aumento)");

     
            List<Person> afterInsert = dao.findAll();
            Person fetched = afterInsert.get(afterInsert.size()-1);
            System.out.println("Traído by Id: " + dao.findById(fetched.getId()).getName());

            fetched.setName("Ana María");
            dao.update(fetched);
            System.out.println(
              "Actualizado name: " + dao.findById(fetched.getId()).getName()
            );


            dao.delete(fetched.getId());
            System.out.println(
              "Borrado. findById devuelve " + dao.findById(fetched.getId())
            );

            System.out.println(
              "Después de todo Persons: " + dao.findAll().size()
            );

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
