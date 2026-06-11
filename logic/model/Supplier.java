package logic.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Supplier extends Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int deliveryTime;
	private ArrayList<Component> myComponents;
	
	public Supplier(String ni, String name, String last_name,String phone, String address, String email, int id, int deliveryTime,
			ArrayList<Component> myComponents, int idAdmin) {
		super(ni, name, last_name, phone, address, email, id, idAdmin);
		this.deliveryTime = deliveryTime;
		this.myComponents = myComponents;
	}

	public int getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public ArrayList<Component> getMyComponents() {
		return myComponents;
	}

	public void setMyComponents(ArrayList<Component> myComponents) {
		this.myComponents = myComponents;
	}
	
	
}
