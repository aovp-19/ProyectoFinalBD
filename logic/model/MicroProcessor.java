package logic.model;

import java.io.Serializable;

public class MicroProcessor extends Component implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String model;
	private String socket;
	private int processingSpeed;
	
	
	
	public MicroProcessor(int id, String brand, double price, int units, int serie, String model, String socket, int processingSpeed, int idSupplier) {
		super(id, brand, price, units, serie, idSupplier);
		this.model = model;
		this.socket = socket;
		this.processingSpeed = processingSpeed;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSocket() {
		return socket;
	}

	public void setSocket(String socket) {
		this.socket = socket;
	}

	public float getProcessingSpeed() {
		return processingSpeed;
	}

	public void setProcessingSpeed(int processingSpeed) {
		this.processingSpeed = processingSpeed;
	}

	
}
