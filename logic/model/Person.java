package logic.model;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected int id;
    protected String ni;
    protected String name;
    protected String last_name;  // Agregado atributo last_name
    protected String phone;
    protected String address;
    protected String email;
    protected int idAdmin;

    public Person(String ni, String name, String last_name, String phone, String address, String email, int id, int idAdmin) {
        super();
        this.id = id;
        this.ni = ni;
        this.name = name;
        this.last_name = last_name;  // Asignación last_name
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.idAdmin = idAdmin;
    }

    public String getNi() {
        return ni;
    }

    public void setNi(String ni) {
        this.ni = ni;
    }
    
    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getLastName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }
        fullName = fullName.trim();
        int spaceIndex = fullName.indexOf(' ');
        if (spaceIndex == -1) {
            // No hay espacio, no hay apellido
            return "";
        }
        // Retorna lo que hay después del primer espacio
        return fullName.substring(spaceIndex + 1).trim();
    }
    
    public static String getFirstName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";  // Si es null o vacío, retorna cadena vacía
        }
        fullName = fullName.trim();  // Elimina espacios al inicio y al final
        int spaceIndex = fullName.indexOf(' ');  // Busca el primer espacio
        if (spaceIndex == -1) {
            // No hay espacio, el nombre completo es solo un nombre
            return fullName;
        }
        // Retorna la parte antes del primer espacio (primer nombre)
        return fullName.substring(0, spaceIndex).trim();
    }

}
