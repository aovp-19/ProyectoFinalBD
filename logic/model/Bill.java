package logic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Bill implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private ArrayList<Item> components;
	private Client cliente;
	private double total;
	private Date sold;
	
	public Bill(ArrayList<Item> itemsVenta, Client cliente, double total, Date sold, int id) {
		super();
		this.id = id;
		this.components = itemsVenta;
		this.cliente = cliente;
		this.total = total;
		this.sold = sold;
	}

	public ArrayList<Item> getComponents() {
		return components;
	}

	public void setComponents(ArrayList<Item> components) {
		this.components = components;
	}

	public Client getCliente() {
		return cliente;
	}

	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getSold() {
		return sold;
	}

	public void setSold(Date sold) {
		this.sold = sold;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		
	}
	
	
}
