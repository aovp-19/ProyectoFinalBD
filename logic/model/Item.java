package logic.model;

import java.io.Serializable;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idItem;          // id_item (se puede asignar por BD o lógica)
    private int idComponent;  // id_component (puede ser null si es combo)
    private int idCombo;      // id_combo (puede ser null si es componente suelto)
    private double price;        // precio calculado en el momento de la venta
    private int idAdmin;         // id_admin (sucursal donde se hace la venta)
    private int quantity;        // cantidad de ese item en la venta

    // Constructor vacío
    public Item() {
    }

    // Constructor completo (sin idItem que puede asignar BD)
    public Item(int idComponent, int idCombo, double price, int idAdmin, int quantity) {
        this.idComponent = idComponent;
        this.idCombo = idCombo;
        this.price = price;
        this.idAdmin = idAdmin;
        this.quantity = quantity;
    }

    // Getters y Setters
    public int getIdItem() {
        return idItem;
    }

    public int getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(int idComponent) {
        this.idComponent = idComponent;
    }

    public int getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(int idCombo) {
        this.idCombo = idCombo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    // Método para obtener subtotal (precio * cantidad)
    public double getSubtotal() {
        return price * quantity;
    }

	public int getId() {
		// TODO Auto-generated method stub se hara con un contador
		return idItem;
	}

	public int getUnits() {
	    return quantity;
	}

}
