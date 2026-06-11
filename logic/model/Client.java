package logic.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Client extends Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Bill> myBills;

	

	public Client(String ni, String name, String last_name, String phone, String address, String email, int id, int idAdmin) {
		super(ni, name, last_name ,phone, address, email, id, idAdmin);
		
		this.myBills = new ArrayList<Bill>();
	}

	public ArrayList<Bill> getMyBills() {
		return myBills;
	}

	public void addMyBills(Bill theBill) {
		this.myBills.add(theBill);
	}
	
}
