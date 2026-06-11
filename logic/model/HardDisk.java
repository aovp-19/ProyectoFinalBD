package logic.model;

import java.io.Serializable;

public class HardDisk extends Component implements Serializable {

    private static final long serialVersionUID = 1L;
    private String model;
    private int capacity;
    private String connectionType;

    public HardDisk(int id, String brand, double price, int units, int series, String model, int capacity, String connectionType, int idSupplier) {
        super(id, brand, price, units, series, idSupplier);
        this.model = model;
        this.capacity = capacity;
        this.connectionType = connectionType;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public String getConnectionType() {
        return connectionType;
    }
    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }
}
