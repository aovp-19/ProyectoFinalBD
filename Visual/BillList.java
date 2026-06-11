package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

import logic.model.Administration;
import logic.model.Bill;

public class BillList extends JFrame implements MouseListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPanel = new JPanel();
    private static JScrollPane scrollPaneTable;
    private static JTable BillsTable;
    static ArrayList<Bill> bills;
    static TableModel model;
    private static int rows;
    private static int columns;
    private JPanel panelButton;
    private JButton btn_delete;
    private JButton btn_cancel;
    private int idBill = 0;
    public onSelectedBill billInterface;

    public interface onSelectedBill {
        void getSelectedBill(String id);
    }

    public static void main(String[] args) {
        try {
            BillList dialog = new BillList(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BillList(onSelectedBill billInterface) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 551);

        startComponents();
        setLocationRelativeTo(null);
        buildTable();
    }

    private static void buildTable() {
        bills = Administration.getInstance().getTheBills();

        ArrayList<String> headers = new ArrayList<>();

        headers.add("Código");
        headers.add("Cliente");
        headers.add("Productos");
        headers.add("Total");
        headers.add("Fecha de compra");

        String[] titles = new String[headers.size()];
        for (int i = 0; i < titles.length; i++) {
            titles[i] = headers.get(i);
        }

        Object[][] data = getData(headers);
        putEverythingTogether(titles, data);
    }

    private static void putEverythingTogether(String[] titles, Object[][] data) {
        model = new TableModel(titles, data);

        BillsTable.setModel(model);

        setRows(BillsTable.getRowCount());
        setColumns(BillsTable.getColumnCount());

        for (int i = 0; i < titles.length; i++) {
            BillsTable.getColumnModel().getColumn(i).setCellRenderer(new CellsConfig("text"));
        }

        BillsTable.getTableHeader().setReorderingAllowed(false);
        BillsTable.setRowHeight(25);
        BillsTable.setGridColor(new Color(0, 0, 0));

        BillsTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        BillsTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        BillsTable.getColumnModel().getColumn(2).setPreferredWidth(500);
        BillsTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        BillsTable.getColumnModel().getColumn(4).setPreferredWidth(400);

        JTableHeader tableHeader = BillsTable.getTableHeader();
        tableHeader.setDefaultRenderer(new TableTitles());
        BillsTable.setTableHeader(tableHeader);

        scrollPaneTable.setViewportView(BillsTable);
    }

    private static Object[][] getData(ArrayList<String> headers) {
        String info[][] = new String[bills.size()][headers.size()];

        for (int i = 0; i < info.length; i++) {
            double total = bills.get(i).getTotal();
            String finalPrice = String.valueOf(total);

            info[i][0] = Integer.toString(bills.get(i).getId());
            info[i][1] = bills.get(i).getCliente().getName();
            info[i][2] = bills.get(i).getComponents().toString();
            info[i][3] = finalPrice;
            info[i][4] = bills.get(i).getSold().toString();
        }
        return info;
    }

    private void startComponents() {
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JLabel lbl_BillsTable = new JLabel("Facturas");
        lbl_BillsTable.setFont(new Font("Verdana", Font.BOLD, 30));
        contentPanel.add(lbl_BillsTable, BorderLayout.NORTH);

        scrollPaneTable = new JScrollPane();
        contentPanel.add(scrollPaneTable);

        BillsTable = new JTable();
        BillsTable.setBackground(Color.white);
        BillsTable.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        BillsTable.addMouseListener(this);
        BillsTable.setOpaque(false);
        scrollPaneTable.setViewportView(BillsTable);

        panelButton = new JPanel();
        contentPanel.add(panelButton, BorderLayout.SOUTH);

        btn_delete = new JButton("Eliminar");
        btn_delete.setFont(new Font("Verdana", Font.BOLD, 12));
        btn_delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idBill >= 1) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "Seguro desea eliminar la factura con código: " + idBill, "Confirmación",
                            JOptionPane.WARNING_MESSAGE);
                    if (option == JOptionPane.YES_OPTION) {
                        Administration.getInstance().deleteBill(idBill); // Cambio aquí
                        btn_delete.setEnabled(false);
                        idBill = 0; // limpiar id seleccionado
                        updateTable();
                    }
                }
            }
        });
        btn_delete.setPreferredSize(new Dimension(85, 30));
        btn_delete.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        btn_delete.setEnabled(false);
        panelButton.add(btn_delete);

        btn_cancel = new JButton("Cancelar");
        btn_cancel.setFont(new Font("Verdana", Font.BOLD, 12));
        btn_cancel.setPreferredSize(new Dimension(85, 30));
        btn_cancel.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        btn_cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelButton.add(btn_cancel);

        updateTable();
    }

    private static void updateTable() {
        buildTable();
    }

    // Aquí activamos el botón y asignamos el idBill al seleccionar fila
    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = BillsTable.getSelectedRow();
        if (selectedRow != -1) {
            String idStr = BillsTable.getValueAt(selectedRow, 0).toString();
            try {
                idBill = Integer.parseInt(idStr);
                btn_delete.setEnabled(true);
            } catch (NumberFormatException ex) {
                idBill = 0;
                btn_delete.setEnabled(false);
            }
        } else {
            idBill = 0;
            btn_delete.setEnabled(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // No usado
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // No usado
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // No usado
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // No usado
    }

    public static int getRows() {
        return rows;
    }

    public static void setRows(int rows) {
        BillList.rows = rows;
    }

    public static int getColumns() {
        return columns;
    }

    public static void setColumns(int columns) {
        BillList.columns = columns;
    }

}
