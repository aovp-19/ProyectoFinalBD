package logic.util;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        try (Connection conn = Conexion.getConnection()) {
            System.out.println("Conectado URL = " 
                + conn.getMetaData().getURL());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("No se pudo conectar: " 
                + e.getMessage());
        }
    }
}
