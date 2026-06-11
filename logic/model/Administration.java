package logic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import logic.dao.BillDAO;
import logic.dao.ClientDAO;
import logic.dao.ComboComponentDAO;
import logic.dao.ComboDAO;
import logic.dao.ComponentDAO;
import logic.dao.HardDiskDAO;
import logic.dao.ItemDAO;
import logic.dao.MotherboardDAO;
import logic.dao.PersonDAO;
import logic.dao.PurchaseDAO;
import logic.dao.PurchaseItemDAO;
import logic.dao.RamDAO;
import logic.dao.SupplierDAO;
import logic.dao.UserDAO;
import logic.dao.impl.BillDAOImpl;
import logic.dao.impl.ClientDAOImpl;
import logic.dao.impl.ComboComponentDAOImpl;
import logic.dao.impl.ComboDAOImpl;
import logic.dao.impl.ComponentDAOImpl;
import logic.dao.impl.HardDiskDAOImpl;
import logic.dao.impl.ItemDAOImpl;
import logic.dao.impl.MicroProcessorDAOImpl;
import logic.dao.impl.MotherBoardDAOImpl;
import logic.dao.impl.PersonDaoImpl;
import logic.dao.impl.PurchaseDAOImpl;
import logic.dao.impl.PurchaseItemDAOImpl;
import logic.dao.impl.RamDAOImpl;
import logic.dao.impl.SupplierDAOImpl;
import logic.dao.impl.UserDAOImpl;
import logic.util.DAOException;

public class Administration implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Component> theComponents;
    private ArrayList<Person> thePeople;
    private ArrayList<Bill> theBills;
    private ArrayList<Combo> theCombos;
    private ArrayList<Purchase> misVentas;

    // contadores
    public static int AdminId = 1;
    public static int BillId = 1;
    public static int PersonId = 1;
    public static int ComboId = 1;
    public static int ComponentId = 1;
    public static int UserId = 1;
    public static int ItemId = 1; 
    public static int PedidoId = 1;
    public static int VentaId = 1;

    private static Administration myAdmin = null;
    private static User loginUser;

    // DAO para usuarios y otros
    private UserDAO userDAO;
    private ComponentDAO componentDAO;
    private BillDAO billDAO;
    private SupplierDAO supplierDAO;
    private RamDAO ramDAO;
    private PurchaseDAO purchaseDAO;
    private PurchaseItemDAO purchaseItemDAO;
    private PersonDAO personDAO;
    private MotherboardDAO motherboardDAO;
    private ItemDAO itemDAO;
    private HardDiskDAO hardDiskDAO;
    private ComboComponentDAO comboComponentDAO;
    private ComboDAO          comboDAO;
    private ClientDAO         clientDAO;

    public Administration() {
        super();
        theComponents = new ArrayList<>();
        thePeople = new ArrayList<>();
        theBills = new ArrayList<>();
        theCombos = new ArrayList<>();
        misVentas = new ArrayList<>();

        userDAO = new UserDAOImpl();
        componentDAO = new ComponentDAOImpl();
        billDAO = new BillDAOImpl();
        supplierDAO        = new SupplierDAOImpl();
        ramDAO             = new RamDAOImpl();
        purchaseDAO        = new PurchaseDAOImpl();
        purchaseItemDAO    = new PurchaseItemDAOImpl();
        
        personDAO        = new PersonDaoImpl();
        motherboardDAO   = new MotherBoardDAOImpl();
        itemDAO          = new ItemDAOImpl();
        hardDiskDAO      = new HardDiskDAOImpl();
        comboComponentDAO = new ComboComponentDAOImpl();
        comboDAO          = new ComboDAOImpl();
        clientDAO         = new ClientDAOImpl();
        
    }

    public static Administration getInstance() {
        if (myAdmin == null) {
            myAdmin = new Administration();
        }
        return myAdmin;
    }

    // --- Gestión Usuarios via DAO ---

    public static User getLoginUser() {
        return loginUser;
    }

    public static void setLoginUser(User loginUser) {
        Administration.loginUser = loginUser;
    }

    public boolean confirmLogin(String username, String pass) {
        try {
            User u = userDAO.findByUsername(username);
            if (u != null && u.getPassword().equals(pass)) {
                loginUser = u;
                return true;
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getConfirmedUser(String username, String pass) {
        if (confirmLogin(username, pass)) {
            return loginUser;
        }
        return null;
    }

    public void regUser(User user) throws DAOException {
        userDAO.insert(user);
    }

    public User searchUserById(int id) {
        try {
            return userDAO.findById(id);
        } catch (DAOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User searchUserByUsername(String username) {
        try {
            return userDAO.findByUsername(username);
        } catch (DAOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getAllUsers() {
        try {
            return userDAO.findAll();
        } catch (DAOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void updateUser(User user) {
        try {
            userDAO.update(user);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int idUser) {
        try {
            userDAO.delete(idUser);
            if (loginUser != null && loginUser.getId() == idUser) {
                loginUser = null;
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    
    // --- Gestión Componentes via DAO ---
    public void regComponentDB(Component comp) throws DAOException {
        componentDAO.insert(comp);
    }
    
    public Component searchComponentByIdDB(int id) {
        try {
            return componentDAO.findById(id);
        } catch (DAOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Component> getAllComponents() {
        try {
            return componentDAO.findAll();
        } catch (DAOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void updateComponentDB(Component comp) {
        try {
            componentDAO.update(comp);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void deleteComponentDB(int id) {
        try {
            componentDAO.delete(id);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    
    //Cliente
    
    public void regClient(Client client) throws DAOException {

        personDAO.insert(client);

 
        clientDAO.insert(client);


        thePeople.add(client);


        PersonId++;
    }
    
    public Client searchClientByIdDB(int id) {
        try {
            return clientDAO.findById(id);
        } catch (DAOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Listar todos los clientes desde BD
    public List<Client> getAllClients() {
        try {
            return clientDAO.findAll();
        } catch (DAOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Actualizar cliente en BD
    public void updateClientDB(Client client) {
        try {
            personDAO.update(client);   // actualiza datos personales
            clientDAO.update(client);   // actualiza CLIENT
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    // Eliminar cliente de BD y memoria
    public void deleteClientDB(int id) {
        try {
            clientDAO.delete(id);
            personDAO.delete(id);
            deleteClient(id); // elimina de la lista en memoria
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    //Suplidor
    
    public void regSupplier(Supplier supplier) throws DAOException {
  
        personDAO.insert(supplier);

   
        supplierDAO.insert(supplier);

 
        thePeople.add(supplier);

     
        PersonId++;
    }
    
    public List<Supplier> getAllSuppliers() {
        try {
            List<Supplier> list = supplierDAO.findAll();

            // Vincular componentes correspondientes a cada proveedor
            List<Component> allComponents = componentDAO.findAll();
            for (Supplier supplier : list) {
                ArrayList<Component> myComps = new ArrayList<>();
                for (Component c : allComponents) {
                    if (c.getIdSupplier() == supplier.getId()) {
                        myComps.add(c);
                    }
                }
                supplier.setMyComponents(myComps);
            }

            return list;
        } catch (DAOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public Supplier searchSupplierByIdDB(int id) {
    	try {
    		return supplierDAO.findById(id);
    	}catch (DAOException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public void deleteSupplierDB(int id) {
    	try {
    		supplierDAO.delete(id);
    		personDAO.delete(id);
    		deleteSupplier(id);
    	}catch(DAOException e) {
    		e.printStackTrace();
    	}
    }

    
    
    // --- El resto del código se mantiene igual ---

    public ArrayList<Component> getTheComponents() {
        ArrayList<Component> list = new ArrayList<>();
        try {
            list.addAll(new RamDAOImpl().findAll());
            list.addAll(new HardDiskDAOImpl().findAll());
            list.addAll(new MicroProcessorDAOImpl().findAll());
            list.addAll(new MotherBoardDAOImpl().findAll());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return list;
    }



    public ArrayList<Client> getTheClients() {
        ArrayList<Client> theClients = new ArrayList<>();
        for (Person p : thePeople) {
            if (p instanceof Client) {
                theClients.add((Client) p);
            }
        }
        return theClients;
    }

    public ArrayList<Supplier> getTheSuppliers() {
        ArrayList<Supplier> theSuppliers = new ArrayList<>();
        for (Person p : thePeople) {
            if (p instanceof Supplier) {
                theSuppliers.add((Supplier) p);
            }
        }
        return theSuppliers;
    }

    public ArrayList<Bill> getTheBills() {
        return theBills;
    }

    public ArrayList<Combo> getTheCombos() {
        return theCombos;
    }

    public void addComponent(Component comp) {
        comp.setId(ComponentId);
        ComponentId++;
        theComponents.add(comp);
    }

    public void addPerson(Person p) {
        p.setId(PersonId);
        PersonId++;
        thePeople.add(p);
    }

    public void addBill(Bill bill) {
        bill.setId(BillId);
        BillId++;
        theBills.add(bill);
    }

    public void addCombo(Combo comb) {
        ComboId++;
        theCombos.add(comb);
    }

    public Client searchClientById(int id) {
        for (Client aux : getTheClients()) {
            if (aux.id == id) {
                return aux;
            }
        }
        return null;
    }

    public Component searchComponentById(int id) {
        for (Component c : getTheComponents()) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }



    public int searchComponentGetIndex(int id) {
        int component = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < theComponents.size()) {
            if (theComponents.get(i).getId() == id) {
                component = i;
                found = true;
            }
            i++;
        }
        return component;
    }

    public Bill searchBillById(int id) {
        for (Bill aux : theBills) {
            if (aux.getId() == id) {
                return aux;
            }
        }
        return null;
    }

    public Supplier searchSupplierById(int id) {
        for (Supplier s : getTheSuppliers()) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public int searchPeopleGetIndex(int id) {
        int person = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < thePeople.size()) {
            if (thePeople.get(i).getId() == id) {
                person = i;
                found = true;
            }
            i++;
        }
        return person;
    }

    public Combo searchComboById(int codComb) {
        for (Combo c : theCombos) {
            if (c.getId() == codComb) {
                return c;
            }
        }
        return null;
    }

    public int searchComboGetIndex(int codComb) {
        int combo = -1;
        boolean found = false;
        int i = 0;
        while (!found && i < theCombos.size()) {
            if (theCombos.get(i).getId() == codComb) {
                combo = i;
                found = true;
            }
            i++;
        }
        return combo;
    }

    public void deleteClient(int codCli) {
        Client aux = searchClientById(codCli);
        if (aux != null)
            thePeople.remove(aux);
    }

    public void deleteSupplier(int codSup) {
        Supplier aux = searchSupplierById(codSup);
        if (aux != null)
            thePeople.remove(aux);
    }

    public void deleteComponent(int codComp) {
        Component aux = searchComponentById(codComp);
        if (aux != null)
            theComponents.remove(aux);
    }

    public void deleteBill(int codBill) {
        Bill aux = searchBillById(codBill);
        if (aux != null)
            theBills.remove(aux);
    }

    public void deleteCombo(int codComb) {
        Combo aux = searchComboById(codComb);
        if (aux != null)
            theCombos.remove(aux);
    }

    public void updateComponent(Component comp) {
        int index = searchComponentGetIndex(comp.getId());
        if (index != -1)
            theComponents.set(index, comp);
    }

    public void updatePerson(Person per) {
        int index = searchPeopleGetIndex(per.getId());
        if (index != -1)
            thePeople.set(index, per);
    }

    public void updateCombo(Combo com) {
        int index = searchComboGetIndex(com.getId());
        if (index != -1)
            theCombos.set(index, com);
    }

    // --- VENTAS CON ITEMS (pueden ser componentes o combos) ---

    public boolean makeSaleWithItems(int idClient, Date billDate, ArrayList<Item> itemsVenta) {
        Client client = searchClientById(idClient);
        int userId = getLoginUser().getId();
        if (client == null) {
            return false; // Cliente no existe
        }

        // Validar stock para cada item
        for (Item item : itemsVenta) {
            if (!validateStock(item)) {
                return false; // Stock insuficiente
            }
        }

        // Actualizar stock
        for (Item item : itemsVenta) {
            updateStock(item);
        }

        // Calcular total
        double totalSale = 0.0;
        for (Item item : itemsVenta) {
            totalSale += item.getPrice() * item.getQuantity();
        }
        Purchase newCompra = new Purchase(VentaId , billDate, AdminId, client.getId(), userId, itemsVenta);
        VentaId++;
        misVentas.add(newCompra);

        // Crear factura con items
        Bill newBill = new Bill(itemsVenta, client, totalSale, billDate, BillId);
        addBill(newBill);

        return true;
    }

    // Validar que hay stock suficiente para un Item
    private boolean validateStock(Item item) {
        if (item.getIdComponent() != 0) {
            Component comp = searchComponentById(item.getIdComponent());
            return comp != null && comp.getUnits() >= item.getQuantity();
        } else if (item.getIdCombo() != 0) {
            Combo combo = searchComboById(item.getIdCombo());
            if (combo == null) return false;

            for (Component comp : combo.getComponents()) {
                Component invComp = searchComponentById(comp.getId());
                if (invComp == null || invComp.getUnits() < comp.getUnits() * item.getQuantity()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // Actualizar stock para un Item (descontar unidades)
    private void updateStock(Item item) {
        if (item.getIdComponent() != 0) {
            Component comp = searchComponentById(item.getIdComponent());
            int newUnits = comp.getUnits() - item.getQuantity();
            comp.setUnits(newUnits);
            updateComponent(comp);
        } else if (item.getIdCombo() != 0) {
            Combo combo = searchComboById(item.getIdCombo());
            for (Component comp : combo.getComponents()) {
                Component invComp = searchComponentById(comp.getId());
                int newUnits = invComp.getUnits() - comp.getUnits() * item.getQuantity();
                invComp.setUnits(newUnits);
                updateComponent(invComp);
            }
        }
    }

    // --- INVENTARIO ---

    public boolean inventoryRefill(ArrayList<Component> componentsToOrder) {
        boolean allComponentsUpdated = true;

        for (Component orderComponent : componentsToOrder) {
            boolean componentFound = false;

            for (Component inventoryComponent : theComponents) {
                if (inventoryComponent.getId() == orderComponent.getId()) {
                    componentFound = true;
                    int newUnits = inventoryComponent.getUnits() + orderComponent.getUnits();
                    inventoryComponent.setUnits(newUnits);
                    break;
                }
            }

            if (!componentFound) {
                allComponentsUpdated = false;
                break;
            }
        }

        return allComponentsUpdated;
    }

    // --- Contadores por tipo de componente ---

    public int getHowManyHDs() {
        try {
            return componentDAO.countByType("HardDisk");
        } catch (DAOException e) {
            e.printStackTrace();
            // fallback a contar en memoria si DAOs falla
            int hds = 0;
            for (Component c : theComponents) {
                if (c instanceof HardDisk) {
                    hds++;
                }
            }
            return hds;
        }
    }

    public int getHowManyMPs() {
        try {
            return componentDAO.countByType("MicroProcessor");
        } catch (DAOException e) {
            e.printStackTrace();
            int mps = 0;
            for (Component c : theComponents) {
                if (c instanceof MicroProcessor) {
                    mps++;
                }
            }
            return mps;
        }
    }

    public int getHowManyMBs() {
        try {
            return componentDAO.countByType("MotherBoard");
        } catch (DAOException e) {
            e.printStackTrace();
            int mbs = 0;
            for (Component c : theComponents) {
                if (c instanceof MotherBoard) {
                    mbs++;
                }
            }
            return mbs;
        }
    }

    public int getHowManyRAMs() {
        try {
            return componentDAO.countByType("RAM");
        } catch (DAOException e) {
            e.printStackTrace();
            int rams = 0;
            for (Component c : theComponents) {
                if (c instanceof RAM) {
                    rams++;
                }
            }
            return rams;
        }
    }

    // --- Ganancias mensuales ---

    public double[] getMonthlyProfits() {
        try {
            return billDAO.getMonthlyProfits();
        } catch (DAOException e) {
            e.printStackTrace();

            // fallback: calcular con lista en memoria
            double[] monthlyProfits = new double[12];
            Calendar cal = Calendar.getInstance();

            for (Bill bill : theBills) {
                cal.setTime(bill.getSold());
                int month = cal.get(Calendar.MONTH);
                monthlyProfits[month] += bill.getTotal();
            }
            return monthlyProfits;
        }
    }

    // --- Obtener componentes por proveedor ---

    public ArrayList<Component> getComponentsBySupplier(int supplierId) {
        for (Supplier supplier : getTheSuppliers()) {
            if (supplier.getId() == supplierId) {
                return supplier.getMyComponents();
            }
        }
        return new ArrayList<>();
    }

    public int getBillId() {
        return BillId;
    }

    public int getPersonId() {
        return PersonId;
    }

    public ArrayList<Purchase> getMisVentas() {
        return misVentas;
    }

    public void setMisVentas(ArrayList<Purchase> misVentas) {
        this.misVentas = misVentas;
    }

    public ArrayList<Purchase> getPurchasesForClient(int idClient) {
        ArrayList<Purchase> result = new ArrayList<>();
        if (misVentas == null) {
            return result;
        }
        for (Purchase p : misVentas) {
            if (p.getIdPerson() == idClient) {
                result.add(p);
            }
        }
        // Ordenar de más reciente a más antigua
        result.sort((a, b) -> b.getDateSold().compareTo(a.getDateSold()));
        return result;
    }
}
