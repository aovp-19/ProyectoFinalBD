package Visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logic.model.Administration;
import logic.model.Item;
import logic.model.Purchase;

enum Source {
    MAIN_MENU,
    OTRO
}

public class PurchaseHistory extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel(new BorderLayout(0, 0));
    private JTable purchaseTable;
    private JButton btnRepeat;
    private int idClient;
    @SuppressWarnings("unused")
    private Source source;

    private ArrayList<Purchase> purchases;

    public PurchaseHistory(Frame owner, Source source, int idClient) {
        super(owner, true);
        if (source != Source.MAIN_MENU) {
            throw new IllegalArgumentException("Solo se puede abrir desde el menú principal");
        }
        this.source = source;
        this.idClient = idClient;

        loadPurchases();
        initialize();
    }

    private void loadPurchases() {
        purchases = Administration.getInstance().getPurchasesForClient(idClient);
        if (purchases == null) {
            purchases = new ArrayList<>();
        }
    }

    private void initialize() {
        setTitle("Historial de Compras - Cliente ID: " + idClient);
        setBounds(100, 100, 920, 524);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        String[] cols = { "ID Compra", "Fecha", "Total" };
        DefaultTableModel tableModel = new DefaultTableModel(cols, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        purchaseTable = new JTable(tableModel);
        purchaseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

        for (Purchase p : purchases) {
            double total = computeTotalFromPurchase(p);
            Object[] row = {
                p.getIdPurchase(),
                fmt.format(p.getDateSold()),
                String.format("%.2f $RD", total)
            };
            tableModel.addRow(row);
        }

        JScrollPane scroll = new JScrollPane(purchaseTable);
        contentPanel.add(scroll, BorderLayout.CENTER);

        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        btnRepeat = new JButton("Repetir compra");
        btnRepeat.setEnabled(false);
        // Agregar borde redondeado
        btnRepeat.setPreferredSize(new Dimension(150, 30));
        btnRepeat.setBorder(new RoundedBorder(java.awt.Color.BLACK, 1, 25));
        btnRepeat.addActionListener(e -> {
            int sel = purchaseTable.getSelectedRow();
            if (sel >= 0) {
                Purchase selected = purchases.get(sel);
                int option = JOptionPane.showConfirmDialog(PurchaseHistory.this,
                        "¿Deseas repetir la compra #" + selected.getIdPurchase() + "?",
                        "Confirmar repetición", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    openSaleDialogWithPurchase(selected);
                }
            }
        });
        buttonPane.add(btnRepeat);

        JButton closeBtn = new JButton("Cerrar");
        closeBtn.setPreferredSize(new Dimension(85, 30));
        closeBtn.setBorder(new RoundedBorder(java.awt.Color.BLACK, 1, 25));
        closeBtn.addActionListener(e -> dispose());
        buttonPane.add(closeBtn);

        purchaseTable.getSelectionModel().addListSelectionListener(ev -> {
            btnRepeat.setEnabled(purchaseTable.getSelectedRow() >= 0);
        });
    }

    private double computeTotalFromPurchase(Purchase p) {
        double sum = 0;
        ArrayList<Item> items = p.getMisArticulos();
        if (items != null) {
            for (Item it : items) {
                sum += it.getPrice() * it.getQuantity();
            }
        }
        return sum;
    }

    private void openSaleDialogWithPurchase(Purchase purchase) {
        Venta ventaDialog = new Venta();
        ventaDialog.setModal(true);
        ventaDialog.loadFromPurchase(purchase);
        ventaDialog.setVisible(true);
    }

    /**
     * Método público para actualizar y refrescar la tabla con las compras más recientes.
     * Útil para llamar desde otras ventanas para que se actualice sin cerrar/abrir la ventana.
     */
    public void updateTable() {
        loadPurchases();

        DefaultTableModel model = (DefaultTableModel) purchaseTable.getModel();
        model.setRowCount(0); // limpiar filas

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

        for (Purchase p : purchases) {
            double total = computeTotalFromPurchase(p);
            Object[] row = {
                p.getIdPurchase(),
                fmt.format(p.getDateSold()),
                String.format("%.2f $RD", total)
            };
            model.addRow(row);
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Uso: PurchaseHistory <idCliente>");
            return;
        }
        try {
            int clientId = Integer.parseInt(args[0]);
            PurchaseHistory dialog = new PurchaseHistory(null, Source.MAIN_MENU, clientId);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (NumberFormatException e) {
            System.err.println("ID de cliente inválido: " + args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
