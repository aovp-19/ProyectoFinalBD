package logic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import logic.dao.BillDAO;
import logic.dao.ClientDAO;
import logic.dao.ItemDAO;
import logic.dao.PurchaseItemDAO;
import logic.model.Bill;
import logic.model.Client;
import logic.model.Item;
import logic.model.PurchaseItem;
import logic.util.Conexion;
import logic.util.DAOException;

public class BillDAOImpl implements BillDAO {

    private static final String SQL_FIND_ID  =
        "SELECT id_bill, date_sold, total_price, id_person, id_user "
      + "FROM BILL WHERE id_bill = ?";
    private static final String SQL_FIND_ALL =
        "SELECT id_bill, date_sold, total_price, id_person, id_user FROM BILL";
    private static final String SQL_INSERT   =
        "INSERT INTO BILL(date_sold, total_price, id_person, id_user) VALUES(?,?,?,?)";
    private static final String SQL_UPDATE   =
        "UPDATE BILL SET date_sold = ?, total_price = ?, id_person = ?, id_user = ? "
      + "WHERE id_bill = ?";
    private static final String SQL_DELETE   =
        "DELETE FROM BILL WHERE id_bill = ?";

    // DAOs auxiliares
    private final ItemDAO          itemDAO          = new ItemDAOImpl();
    private final PurchaseItemDAO  purchaseItemDAO  = new PurchaseItemDAOImpl();
    private final ClientDAO        clientDAO        = new ClientDAOImpl();

    @Override
    public Bill findById(int id) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                // 1) Recuperar y convertir las líneas de compra
                List<PurchaseItem> lineas = purchaseItemDAO.findByPurchaseId(id);
                List<Item> itemsVenta = new ArrayList<>();
                for (PurchaseItem pi : lineas) {
                    Item it = itemDAO.findById(pi.getItemId());
                    it.setQuantity(pi.getQuantity());
                    itemsVenta.add(it);
                }

                // 2) Cargar cliente y demás campos
                Client cliente = clientDAO.findById(rs.getInt("id_person"));
                double total   = rs.getDouble("total_price");
                Timestamp ts   = rs.getTimestamp("date_sold");
                java.util.Date sold = (ts != null) 
                    ? new java.util.Date(ts.getTime()) 
                    : null;

                // 3) Construir y devolver la Bill
                return new Bill(new ArrayList<>(itemsVenta),
                                cliente,
                                total,
                                sold,
                                id);
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding Bill", e);
        }
    }

    @Override
    public List<Bill> findAll() throws DAOException {
        List<Bill> lista = new ArrayList<>();
        try (Connection c = Conexion.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SQL_FIND_ALL)) {

            while (rs.next()) {
                int idBill = rs.getInt("id_bill");

                // Misma lógica que en findById para las líneas
                List<PurchaseItem> lineas = 
                    purchaseItemDAO.findByPurchaseId(idBill);
                List<Item> itemsVenta = new ArrayList<>();
                for (PurchaseItem pi : lineas) {
                    Item it = itemDAO.findById(pi.getItemId());
                    it.setQuantity(pi.getQuantity());
                    itemsVenta.add(it);
                }

                Client cliente = clientDAO.findById(rs.getInt("id_person"));
                double total   = rs.getDouble("total_price");
                Timestamp ts   = rs.getTimestamp("date_sold");
                java.util.Date sold = (ts != null) 
                    ? new java.util.Date(ts.getTime()) 
                    : null;

                lista.add(new Bill(new ArrayList<>(itemsVenta),
                                   cliente,
                                   total,
                                   sold,
                                   idBill));
            }
        } catch (SQLException e) {
            throw new DAOException("Error listing Bills", e);
        }
        return lista;
    }

    @Override
    public void insert(Bill bill) throws DAOException {
        String[] generatedCols = { "id_bill" };
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_INSERT, generatedCols)) {
            c.setAutoCommit(false);

            // 1) Insertar cabecera
            ps.setTimestamp(1, 
                new Timestamp(bill.getSold().getTime()));
            ps.setDouble(2, bill.getTotal());
            ps.setInt(3, bill.getCliente().getId());
            ps.setInt(4, /* bill.getIdUser() si lo tienes */ 0);
            ps.executeUpdate();

            // 2) Leer id generado
            int idBill;
            try (ResultSet gk = ps.getGeneratedKeys()) {
                if (!gk.next()) throw new DAOException("No se generó id_bill", null);
                idBill = gk.getInt(1);
                bill.setId(idBill);
            }

            // 3) Insertar cada línea en PURCHASE_ITEM
            for (Item it : bill.getComponents()) {
                PurchaseItem pi = new PurchaseItem(
                    idBill,
                    it.getIdItem(),
                    it.getQuantity()
                );
                purchaseItemDAO.insert(pi);
            }

            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Error inserting Bill", e);
        }
    }

    @Override
    public void update(Bill bill) throws DAOException {
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_UPDATE)) {
            c.setAutoCommit(false);

            // 1) Borrar líneas viejas
            purchaseItemDAO.deleteByPurchaseId(bill.getId());

            // 2) Actualizar cabecera
            ps.setTimestamp(1, new Timestamp(bill.getSold().getTime()));
            ps.setDouble(2, bill.getTotal());
            ps.setInt(3, bill.getCliente().getId());
            ps.setInt(4, /* bill.getIdUser() */ 0);
            ps.setInt(5, bill.getId());
            ps.executeUpdate();

            // 3) Reinsertar líneas nuevas
            for (Item it : bill.getComponents()) {
                purchaseItemDAO.insert(new PurchaseItem(
                    bill.getId(),
                    it.getIdItem(),
                    it.getQuantity()
                ));
            }

            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Error updating Bill", e);
        }
    }

    @Override
    public void delete(int idBill) throws DAOException {
        try (Connection c = Conexion.getConnection()) {
            c.setAutoCommit(false);

            // 1) Borrar líneas de detalle
            purchaseItemDAO.deleteByPurchaseId(idBill);

            // 2) Borrar la cabecera
            try (PreparedStatement ps = 
                     c.prepareStatement(SQL_DELETE)) {
                ps.setInt(1, idBill);
                ps.executeUpdate();
            }
            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Error deleting Bill", e);
        }
    }

    @Override
    public double[] getMonthlyProfits() throws DAOException {
        double[] monthlyProfits = new double[12]; // 0 = enero, 11 = diciembre
        String sql = "SELECT MONTH(sold_date) AS month, SUM(total) AS total_month "
                   + "FROM bills "
                   + "GROUP BY MONTH(sold_date)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int month = rs.getInt("month"); // 1 to 12
                double total = rs.getDouble("total_month");
                monthlyProfits[month - 1] = total;
            }

        } catch (SQLException e) {
            throw new DAOException("Error getting monthly profits", e);
        }

        return monthlyProfits;
    }

}
