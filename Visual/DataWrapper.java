package Visual;

import javax.swing.ImageIcon;

import logic.model.Administration;
import logic.model.Component;

/**
 * Wrapper para manejar datos del componente en la tabla.
 * Contiene icono, ID, nombre, cantidad y estado del checkbox de selección.
 */
public class DataWrapper {
    // Icono que se muestra en la tabla
    private ImageIcon icon;

    // ID del componente (se muestra como texto)
    private int txtID;

    // Nombre o modelo del componente
    private String txtName;

    // Valor del spinner (cantidad/unidades)
    private int spinnerValue;

    // Indica si el checkbox está seleccionado (true = seleccionado)
    private boolean checkboxSelected;
    
    /**
     * Constructor de DataWrapper
     * @param icon Icono del componente
     * @param textField ID o texto identificador
     * @param name Nombre o modelo del componente
     * @param spinnerValue Cantidad o unidades
     * @param checkboxSelected Estado inicial del checkbox (seleccionado o no)
     */
    public DataWrapper(ImageIcon icon, int  textField, String name, int spinnerValue, boolean checkboxSelected) {
        this.icon = icon;
        this.txtID = textField;
        this.txtName = name;
        this.spinnerValue = spinnerValue;
        this.checkboxSelected = checkboxSelected;
    }

    /**
     * Actualiza en la administración la cantidad actual de unidades del componente
     */
    public void updateData() {
        Component comp = Administration.getInstance().searchComponentById(this.getTextField());
        comp.setUnits(this.spinnerValue);
        Administration.getInstance().updateComponent(comp);
    }
    
    // Getters y setters

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public int getTextField() {
        return txtID;
    }

    public void setTextField(int textField) {
        this.txtID = textField;
    }

    public int getSpinnerValue() {
        return spinnerValue;
    }

    public void setSpinnerValue(int spinnerValue) {
        this.spinnerValue = spinnerValue;
    }

    /**
     * Obtiene el estado del checkbox (true si está seleccionado)
     * @return boolean estado checkbox
     */
    public boolean isCheckboxSelected() {
        return checkboxSelected;
    }

    /**
     * Cambia el estado del checkbox
     * @param checkboxSelected nuevo estado (true = seleccionado)
     */
    public void setCheckboxSelected(boolean checkboxSelected) {
        this.checkboxSelected = checkboxSelected;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }
}
