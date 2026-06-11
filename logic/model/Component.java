package logic.model;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Component implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected String brand;
    protected ImageIcon icon;
    protected double unit_price;
    protected int units;
    protected int serie;
    protected int idSupplier;

    public Component(int id, String brand, double unit_price, int units, int serie, int idSupplier) {
        super();
        this.id = id;
        this.brand = brand;
        this.unit_price = unit_price;
        this.units = units;
        this.serie = serie;
        this.idSupplier = idSupplier;
    }


	public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return unit_price;
    }

    public void setPrice(double price) {
        this.unit_price = price;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }
}
