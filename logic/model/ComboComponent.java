package logic.model;

public class ComboComponent {
    private int idCombo;
    private int idComponent;
    private int quantity;

    public ComboComponent() {}

    public ComboComponent(int idCombo, int idComponent, int quantity) {
        this.idCombo = idCombo;
        this.idComponent = idComponent;
        this.quantity = quantity;
    }

    public int getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(int idCombo) {
        this.idCombo = idCombo;
    }

    public int getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(int idComponent) {
        this.idComponent = idComponent;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
