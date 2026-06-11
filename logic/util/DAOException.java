package logic.util;

/** RuntimeException para encapsular errores JDBC. */
public class DAOException extends RuntimeException {
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
    public DAOException(Throwable cause) {
        super(cause);
    }
}
