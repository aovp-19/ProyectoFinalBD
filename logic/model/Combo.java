package logic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Combo implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;

	private String name;
    private ImageIcon icon;
    private int discount;
    private ArrayList<Component> comboComp;

    public Combo(int id, String name, int discount, List<Component> listaComps) {
        super();
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.comboComp = (ArrayList<Component>) listaComps;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
		this.id = id;
	}

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public ArrayList<Component> getComboComp() {
        return comboComp;
    }

    public void setComboComp(ArrayList<Component> comboComp) {
        this.comboComp = comboComp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    // Método corregido para devolver los componentes, si prefieres usarlo:
    public ArrayList<Component> getComponents() {
        return comboComp;
    }
}
