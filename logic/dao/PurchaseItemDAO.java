package logic.dao;

import java.util.List;
import logic.model.PurchaseItem;
import logic.util.DAOException;

public interface PurchaseItemDAO {
    /** Devuelve todos los ítems (líneas) asociados a una compra */
    List<PurchaseItem> findByPurchaseId(int idPurchase) throws DAOException;

    /** Inserta una línea en PURCHASE_ITEM */
    void insert(PurchaseItem pi) throws DAOException;

    /** Elimina todas las líneas de una compra (útil en update/delete de factura) */
    void deleteByPurchaseId(int idPurchase) throws DAOException;
}
