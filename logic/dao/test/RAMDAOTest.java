package logic.dao.test;

import java.util.ArrayList;
import java.util.List;

import logic.dao.RamDAO;
import logic.dao.impl.PersonDaoImpl;
import logic.dao.impl.RamDAOImpl;
import logic.dao.impl.SupplierDAOImpl;
import logic.model.Administration;
import logic.model.Person;
import logic.model.RAM;
import logic.model.Supplier;
import logic.util.DAOException;

public class RAMDAOTest {
    public static void main(String[] args) {
        RamDAO dao = new RamDAOImpl();
        try {
            
        	//Creando un suplidor para el componente
    		PersonDaoImpl personDAO = new PersonDaoImpl();
    		Person p = new Person("1020", "Renso", "Peralta", "809-267-8894",
    	                        "Las antillas", "renso@gmail.com", 0, Administration.AdminId);
    	  	personDAO.insert(p); // te genera p.id

    	  // 2) Insertar SUPPLIER con ese id_person
    	  	SupplierDAOImpl supplierDAO = new SupplierDAOImpl();
    	  	Supplier suplidor = new Supplier(p.getNi(), p.getName(), p.getLast_name(),
    	          p.getPhone(), p.getAddress(), p.getEmail(),
    	          p.getId(),           
    	          7, new ArrayList<>(), Administration.AdminId);
    		supplierDAO.insert(suplidor);
        	
        	List<RAM> antes = dao.findAll();
            System.out.println("Antes de insertar RAMs: " + antes.size());

            RAM ram = new RAM(0, "Corsair", 69.99, 8, 1, 16, "DDR4", suplidor.getId());
            dao.insert(ram);
            System.out.println("Insertado RAM con id=" + ram.getId());

            RAM traida = dao.findById(ram.getId());
            System.out.println("Traída: capacity=" + traida.getCapacity() + "GB, type=" + traida.getType());

            traida.setCapacity(32);
            dao.update(traida);
            System.out.println("Actualizada capacidad a " + dao.findById(ram.getId()).getCapacity() + "GB");

            dao.delete(ram.getId());
            System.out.println("Borrada. findById devuelve " + dao.findById(ram.getId()));


            System.out.println("Después de borrar RAMs: " + dao.findAll().size());

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
