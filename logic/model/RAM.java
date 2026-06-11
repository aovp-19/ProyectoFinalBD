package logic.model;

import java.io.Serializable;

public class RAM extends Component implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int capacity;
	private String type;
	
	public RAM(int id, String brand, double price, int units, int serie, int capacity, String type, int idSupplier) {
		super(id, brand, price, units, serie, idSupplier);
		this.capacity = capacity;
		this.type = type;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
