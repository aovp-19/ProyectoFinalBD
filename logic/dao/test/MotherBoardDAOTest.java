package logic.dao.test;

import java.util.ArrayList;
import java.util.List;

import logic.dao.MotherboardDAO;
import logic.dao.impl.MotherBoardDAOImpl;
import logic.dao.impl.PersonDaoImpl;
import logic.dao.impl.SupplierDAOImpl;
import logic.model.Administration;
import logic.model.HardDisk;
import logic.model.MotherBoard;
import logic.model.Person;
import logic.model.Supplier;
import logic.util.DAOException;

public class MotherBoardDAOTest {
	public static void main(String[] args) {
        MotherboardDAO dao = new MotherBoardDAOImpl();
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
           
            List<MotherBoard> antes = dao.findAll();
            
            System.out.println("Antes de insertar: " + antes.size());
            
            ArrayList<String> conexiones = new ArrayList<>();
            conexiones.add("IDE");
            conexiones.add("SATA-2");

          
            MotherBoard cpu = new MotherBoard(
                0,         
                "Intel",
                220.0,
                8,
                54321,
                "i9-9900K",
                "LGA1151",
                "tipo 2",
                conexiones,
                suplidor.getId()
            );
            dao.insert(cpu);
            System.out.println("Insertado con id=" + cpu.getId());

            
            MotherBoard traido = dao.findById(cpu.getId());
            System.out.println("Traído: " + traido.getModel() + " a " + traido.getTypeRAM());

            
            traido.setTypeRAM("tipo 4");
            dao.update(traido);
            System.out.println("Actualizado speed a " +
                dao.findById(cpu.getId()).getTypeRAM());

            
            dao.delete(cpu.getId());
            System.out.println("Borrado. findById devuelve " + dao.findById(cpu.getId()));


            List<MotherBoard> despues = dao.findAll();
            System.out.println("Después de todo: " + despues.size());

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
