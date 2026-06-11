package logic.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Purchase  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idPurchase;
    private Date dateSold;
    // private float totalPrice; // se calcula con trigger en la base
    private int idAdmin;
    private int idPerson;
    private int idUser;
    private ArrayList<Item> misArticulos;

        public Purchase(int idPurchase, Date dateSold, int idAdmin, int idPerson, int idUser, ArrayList<Item> misArticulos) {
        this.idPurchase = idPurchase;
        this.dateSold = dateSold;
        this.idAdmin = idAdmin;
        this.idPerson = idPerson;
        this.idUser = idUser;
        this.misArticulos = new ArrayList<>(misArticulos);
    }


	// Getters y Setters
    public int getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
    }

    public Date getDateSold() {
        return dateSold;
    }

    public void setDateSold(Date dateSold) {
        this.dateSold = dateSold;
    }

    /*
    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
    */

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

	public ArrayList<Item> getMisArticulos() {
		return misArticulos;
	}

	public void setMisArticulos(ArrayList<Item> misArticulos) {
		this.misArticulos = misArticulos;
	}
}
