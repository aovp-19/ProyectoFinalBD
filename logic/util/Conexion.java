package logic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	private static final String URL =
			  "jdbc:sqlserver://localhost:1433;"
			+ "databaseName=Tiendas_Componentes;"
			+ "encrypt=true;"
			+ "trustServerCertificate=true";

    private static final String USER = "rensoAdmin";
    private static final String PASS = "1234";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(
                "No se encontró el driver JDBC: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
