package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

import logic.model.Administration;
import logic.model.Client;

public class ClientList extends JDialog implements MouseListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane = new JPanel();
    private static JScrollPane scrollPaneTable;
    private static JTable ClientTable;
    static ArrayList<Client> clients;
    static TableModel model;
    private static int rows;
    private static int columns;
    private JPanel bottonPanel;
    private JButton btn_cancel;
    private static JButton btn_addNew;
    private static JButton btn_delete;
    private static JButton btn_update;
    private static JButton btn_select;
    private int idClient = 0;
    public onSelectedClient clientInterface;
    private Origin origin;

    public enum Origin {
        MAIN_MENU,
        OTRO
    }

    public interface onSelectedClient {
        void getSelectedClient(int idClient);
    }

    public static void main(String[] args) {
        try {
            ClientList dialog = new ClientList(null, Origin.MAIN_MENU);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientList(onSelectedClient clientInterface, Origin origin) {
        this.clientInterface = clientInterface;
        this.origin = origin;
        setIconImage(Toolkit.getDefaultToolkit().getImage(ClientList.class.getResource("/Images/nueva-cuenta (2).png")));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(800, 551);

        startComponents();
        setLocationRelativeTo(null);
        buildTable();
    }

    public static int getRows() {
        return rows;
    }

    public static void setRows(int rows) {
        ClientList.rows = rows;
    }

    public static int getColumns() {
        return columns;
    }

    public static void setColumns(int columns) {
        ClientList.columns = columns;
    }

    private static void buildTable() {
        
        model = new TableModel(new String[]{"Cargando..."}, new Object[][]{{"Por favor espere"}});
        ClientTable.setModel(model);

        new javax.swing.SwingWorker<ArrayList<Client>, Void>() {
            @Override
            protected ArrayList<Client> doInBackground() {
                return new ArrayList<>(Administration.getInstance().getAllClients());
            }
            @Override
            protected void done() {
                try {
                    clients = get();
                    ArrayList<String> headers = new ArrayList<>();
                    headers.add("Código");
                    headers.add("Nombre");
                    headers.add("Cédula");
                    headers.add("E-mail");
                    headers.add("Dirección");
                    headers.add("Teléfono");

                    String[] Titles = headers.toArray(new String[0]);
                    Object[][] data = getData(headers);
                    putTogetherEverything(Titles, data);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ClientTable, "Error cargando clientes", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }


    private static void putTogetherEverything(String[] Titles, Object[][] data) {
        model = new TableModel(Titles, data);

        ClientTable.setModel(model);

        setRows(ClientTable.getRowCount());
        setColumns(ClientTable.getColumnCount());

        for (int i = 0; i < Titles.length; i++) {
            ClientTable.getColumnModel().getColumn(i).setCellRenderer(new CellsConfig("text"));
        }

        ClientTable.getTableHeader().setReorderingAllowed(false);
        ClientTable.setRowHeight(25);
        ClientTable.setGridColor(new Color(0, 0, 0));

        ClientTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        ClientTable.getColumnModel().getColumn(1).setPreferredWidth(380);
        ClientTable.getColumnModel().getColumn(2).setPreferredWidth(350);
        ClientTable.getColumnModel().getColumn(3).setPreferredWidth(400);
        ClientTable.getColumnModel().getColumn(4).setPreferredWidth(350);
        ClientTable.getColumnModel().getColumn(5).setPreferredWidth(200);

        JTableHeader TableHeader = ClientTable.getTableHeader();
        TableHeader.setDefaultRenderer(new TableTitles());
        ClientTable.setTableHeader(TableHeader);

        scrollPaneTable.setViewportView(ClientTable);

        btn_delete.setEnabled(false);
        btn_update.setEnabled(false);
        btn_select.setEnabled(false);
        btn_addNew.setEnabled(true);
    }

    private static Object[][] getData(ArrayList<String> titles) {
        String info[][] = new String[clients.size()][titles.size()];

        for (int i = 0; i < info.length; i++) {
            info[i][0] = String.valueOf(clients.get(i).getId());
            info[i][1] = clients.get(i).getName();
            info[i][2] = clients.get(i).getNi();
            info[i][3] = clients.get(i).getEmail();
            info[i][4] = clients.get(i).getAddress();
            info[i][5] = clients.get(i).getPhone();
        }

        return info;
    }

    private void startComponents() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel lbl_clientTable = new JLabel("Listado Clientes");
        lbl_clientTable.setFont(new Font("Verdana", Font.BOLD, 30));
        contentPane.add(lbl_clientTable, BorderLayout.NORTH);

        scrollPaneTable = new JScrollPane();
        contentPane.add(scrollPaneTable);

        ClientTable = new JTable();
        ClientTable.setFont(new Font("Verdana", Font.PLAIN, 12));
        ClientTable.setBackground(Color.white);
        ClientTable.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        ClientTable.addMouseListener(this);
        ClientTable.setOpaque(false);
        scrollPaneTable.setViewportView(ClientTable);

        bottonPanel = new JPanel();
        contentPane.add(bottonPanel, BorderLayout.SOUTH);

        btn_select = new JButton("Seleccionar");
        btn_select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idClient >= 1) {
                    if (origin == Origin.MAIN_MENU) {
                        // Aquí abres el historial de compras del cliente seleccionado
                        PurchaseHistory historial = new PurchaseHistory(null, Source.MAIN_MENU, idClient);
                        historial.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        historial.setVisible(true);
                    } else if (clientInterface != null) {
                        // Si viene desde otra ventana, retorna el cliente seleccionado
                        clientInterface.getSelectedClient(idClient);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(ClientList.this,
                                "No autorizado para abrir historial desde aquí",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btn_select.setPreferredSize(new Dimension(85, 30));
        btn_select.setFont(new Font("Verdana", Font.BOLD, 12));
        btn_select.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        bottonPanel.add(btn_select);

        btn_update = new JButton("Actualizar");
        btn_update.setPreferredSize(new Dimension(85, 30));
        btn_update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idClient >= 1) {
                    Client aux = Administration.getInstance().searchClientById(idClient);
                    if (aux != null) {
                        ClientRegistry updateClient = new ClientRegistry(aux);
                        updateClient.setModal(true);
                        updateClient.setVisible(true);
                    }
                }
            }
        });
        btn_update.setFont(new Font("Verdana", Font.BOLD, 12));
        btn_update.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        bottonPanel.add(btn_update);

        btn_delete = new JButton("Eliminar");
        btn_delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idClient >= 1) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "Seguro desea eliminar el cliente con código: " + idClient, "Confirmación",
                            JOptionPane.WARNING_MESSAGE);
                    if (option == JOptionPane.YES_OPTION) {
                        Administration.getInstance().deleteClientDB(idClient);
                        btn_delete.setEnabled(false);
                        updateTable();
                    }
                }
            }
        });
        btn_delete.setPreferredSize(new Dimension(85, 30));
        btn_delete.setFont(new Font("Verdana", Font.BOLD, 12));
        btn_delete.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        bottonPanel.add(btn_delete);

        btn_addNew = new JButton("Agregar");
        btn_addNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientRegistry newClient = new ClientRegistry(null);
                newClient.setModal(true);
                newClient.setVisible(true);
                updateTable();
            }
        });
        btn_addNew.setPreferredSize(new Dimension(85, 30));
        btn_addNew.setFont(new Font("Verdana", Font.BOLD, 12));
        btn_addNew.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        bottonPanel.add(btn_addNew);

        btn_cancel = new JButton("Cancelar");
        btn_cancel.setPreferredSize(new Dimension(85, 30));
        btn_cancel.setFont(new Font("Verdana", Font.BOLD, 12));
        btn_cancel.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        btn_cancel.setSize(50, 50);
        btn_cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        bottonPanel.add(btn_cancel);

        updateTable();
    }

    public static void updateTable() {
        buildTable();
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        int i = ClientTable.getSelectedRow();
        if (i >= 0) {
            Object raw = ClientTable.getValueAt(i, 0);
            int parsedId = -1;
            if (raw instanceof Integer) {
                parsedId = (Integer) raw;
            } else {
                try {
                    parsedId = Integer.parseInt(raw.toString());
                } catch (NumberFormatException ex) {
                    return;
                }
            }
            idClient = parsedId;
            btn_addNew.setEnabled(false);

            if (clientInterface == null) {
                btn_delete.setEnabled(true);
                btn_update.setEnabled(true);
                btn_select.setEnabled(false);
            } else {
                btn_delete.setEnabled(true); // se puede eliminar
                btn_update.setEnabled(true); // se puede actualizar 
                btn_select.setEnabled(true);  // Ahora se activa siempre que hay clientInterface
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) { }
    @Override
    public void mouseExited(MouseEvent arg0) { }
    @Override
    public void mousePressed(MouseEvent arg0) { }
    @Override
    public void mouseReleased(MouseEvent arg0) { }
}
