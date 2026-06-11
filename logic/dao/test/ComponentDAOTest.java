package logic.dao.test;

import java.util.ArrayList;
import java.util.List;

import logic.dao.ComponentDAO;
import logic.dao.HardDiskDAO;
import logic.dao.MicroprocessorDAO;
import logic.dao.MotherboardDAO;
import logic.dao.RamDAO;
import logic.dao.impl.ComponentDAOImpl;
import logic.dao.impl.HardDiskDAOImpl;
import logic.dao.impl.MicroProcessorDAOImpl;
import logic.dao.impl.MotherBoardDAOImpl;
import logic.dao.impl.PersonDaoImpl;
import logic.dao.impl.RamDAOImpl;
import logic.dao.impl.SupplierDAOImpl;
import logic.model.Administration;
import logic.model.Component;
import logic.model.HardDisk;
import logic.model.MicroProcessor;
import logic.model.MotherBoard;
import logic.model.Person;
import logic.model.RAM;
import logic.model.Supplier;

public class ComponentDAOTest {
	  public static void main(String[] args) {
		  
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
		
		ArrayList<String> conexiones = new ArrayList<>();
        conexiones.add("IDE");
        conexiones.add("SATA-2");
		  
	    ComponentDAO dao = new ComponentDAOImpl();

	    MicroprocessorDAO daoCPU = new MicroProcessorDAOImpl();
	    List<Component> todos = dao.findAll();
	    System.out.println("Component Micro count: " + todos.size());
	    
	    MicroprocessorDAO cpuDao = new MicroProcessorDAOImpl();
	    MicroProcessor cpu = new MicroProcessor(Administration.ComponentId, "Intel", 220.0, 8, 54321, "i9-9900K", "LGA1151", 5, suplidor.getId());
	    cpuDao.insert(cpu);
	    
	    MotherboardDAO daoMother = new MotherBoardDAOImpl();
	    List<Component> todosM = dao.findAll();
	    System.out.println("Component Mother count: " + todosM.size());
	    
	    MotherboardDAO motherDao = new MotherBoardDAOImpl();
	    MotherBoard mother = new MotherBoard(0, "Intel", 220.0, 8, 54321, "i9-9900K", "LGA1151", "tipo 2", conexiones, suplidor.getId());
	    motherDao.insert(mother);
	    
	    RamDAO daoRam = new RamDAOImpl();
	    List<Component> todosR = dao.findAll();
	    System.out.println("Component Ram count: " + todosR.size());
	    
	    RamDAO ramDao = new RamDAOImpl();
	    RAM ram = new RAM(0, "Intel", 220.0, 8, 54321, 16, "Model 1", suplidor.getId());
	    ramDao.insert(ram);
	    
	    HardDiskDAO daoDisk = new HardDiskDAOImpl();
	    List<Component> todosH = dao.findAll();
	    System.out.println("Component Disco count: " + todosH.size());
	    
	    HardDiskDAO diskDao = new HardDiskDAOImpl();
	    HardDisk hard = new HardDisk(0, "Intel", 220.0, 8, 54321, "Model 1", 10, "DAT-3", suplidor.getId());
	    diskDao.insert(hard);
	    
	     
	    
	    
	    
	    
	  }
}

