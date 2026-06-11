package logic.dao.test;

import java.util.ArrayList;
import java.util.List;

import logic.dao.MicroprocessorDAO;
import logic.dao.impl.MicroProcessorDAOImpl;
import logic.dao.impl.PersonDaoImpl;
import logic.dao.impl.SupplierDAOImpl;
import logic.model.Administration;
import logic.model.MicroProcessor;
import logic.model.Person;
import logic.model.Supplier;
import logic.util.DAOException;

public class MicroProcessorDAOTest {
    public static void main(String[] args) {
        MicroprocessorDAO dao = new MicroProcessorDAOImpl();
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
    		
            // 1) ¿Cuántos hay antes?
            List<MicroProcessor> antes = dao.findAll();
            System.out.println("Antes de insertar: " + antes.size());

            // 2) Inserta uno nuevo
            MicroProcessor cpu = new MicroProcessor(
                0,         
                "Intel",
                220.0,
                8,
                54321,
                "i9-9900K",
                "LGA1151",
                5,             // GHz
                suplidor.getId()
            );
            dao.insert(cpu);
            System.out.println("Insertado con id=" + cpu.getId());

            // 3) Verifícalo con findById
            MicroProcessor traido = dao.findById(cpu.getId());
            System.out.println("Traído: " + traido.getModel() + " a " + traido.getProcessingSpeed() + "GHz");

            // 4) Actualiza
            traido.setProcessingSpeed(6);
            dao.update(traido);
            System.out.println("Actualizado speed a " +
                dao.findById(cpu.getId()).getProcessingSpeed() + "GHz");

            //5) Borra
            dao.delete(cpu.getId());
            System.out.println("Borrado. findById devuelve " + dao.findById(cpu.getId()));

            // 6) ¿Cuántos quedan?
            List<MicroProcessor> despues = dao.findAll();
            System.out.println("Después de todo: " + despues.size());

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
