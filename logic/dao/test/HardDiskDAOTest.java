package logic.dao.test;

import java.util.ArrayList;
import java.util.List;

import logic.dao.HardDiskDAO;
import logic.dao.impl.HardDiskDAOImpl;
import logic.dao.impl.PersonDaoImpl;
import logic.dao.impl.SupplierDAOImpl;
import logic.model.Administration;
import logic.model.HardDisk;
import logic.model.Person;
import logic.model.Supplier;
import logic.util.DAOException;

public class HardDiskDAOTest {
    public static void main(String[] args) {
        HardDiskDAO dao = new HardDiskDAOImpl();
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
    		
            List<HardDisk> antes = dao.findAll();
            System.out.println("Antes de insertar HardDisks: " + antes.size());

            HardDisk hd = new HardDisk(0, "Seagate", 59.99, 5, 1, "ST500LM", 500, "SATA", suplidor.getId());
            dao.insert(hd);
            System.out.println("Insertado HardDisk con id=" + hd.getId());

            HardDisk traido = dao.findById(hd.getId());
            System.out.println("Traído: model=" + traido.getModel() + ", capacity=" + traido.getCapacity() + "GB");

            traido.setCapacity(1000);
            dao.update(traido);
            System.out.println("Actualizada capacidad a " + dao.findById(hd.getId()).getCapacity() + "GB");

           // dao.delete(hd.getId());
            //System.out.println("Borrado. findById devuelve " + dao.findById(hd.getId()));

            System.out.println("Después de borrar HardDisks: " + dao.findAll().size());

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
