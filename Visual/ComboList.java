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
import java.util.List;

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
import logic.model.Combo;
import logic.model.Component;
import logic.model.HardDisk;
import logic.model.MicroProcessor;
import logic.model.MotherBoard;
import logic.model.RAM;

public class ComboList extends JDialog implements MouseListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane = new JPanel();
    private static JScrollPane scrollPaneTable;
    private static JTable comboTable;
    static ArrayList<Combo> combos;
    static TableModel model;
    private static int rows;
    private static int columns;
    private JPanel panelBotton;
    private JButton btn_cancel;
    private static JButton btn_select;
    private static JButton btn_addNew;
    private static JButton btn_delete;
    private int idCombo = -1;  // Cambiado a int

    public onSelectedCombo comboInterface;

    public interface onSelectedCombo {
        void getSelectedCombo(int id);  // Cambiado a int
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            ComboList dialog = new ComboList(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public ComboList(onSelectedCombo comboInterface) {
        this.comboInterface = comboInterface;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(1000, 551);

        startComponents();
        setLocationRelativeTo(null);
        buildTable();
    }

    private static void buildTable() {
        combos = Administration.getInstance().getTheCombos();

        ArrayList<String> headers = new ArrayList<>();
        headers.add("Código");
        headers.add("Nombre");
        headers.add("Descuento");
        headers.add("Componentes");

        String titles[] = new String[headers.size()];

        for (int i = 0; i < titles.length; i++) {
            titles[i] = headers.get(i);
        }

        Object[][] data = getData(headers);
        putEverythingTogether(titles, data);
    }

    private static void putEverythingTogether(String[] titles, Object[][] data) {
        model = new TableModel(titles, data);

        comboTable.setModel(model);

        setRows(comboTable.getRowCount());
        setColumns(comboTable.getColumnCount());

        for (int i = 0; i < titles.length; i++) {
            comboTable.getColumnModel().getColumn(i).setCellRenderer(new CellsConfig("text"));
        }

        comboTable.getTableHeader().setReorderingAllowed(false);
        comboTable.setRowHeight(25);
        comboTable.setGridColor(new java.awt.Color(0, 0, 0));

        comboTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        comboTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        comboTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        comboTable.getColumnModel().getColumn(3).setPreferredWidth(450);

        JTableHeader tableHeader = comboTable.getTableHeader();
        tableHeader.setDefaultRenderer(new TableTitles());
        comboTable.setTableHeader(tableHeader);

        scrollPaneTable.setViewportView(comboTable);
        btn_delete.setEnabled(false);
        btn_select.setEnabled(false);
        btn_addNew.setEnabled(true);
    }

    private static Object[][] getData(ArrayList<String> headers) {
        Object[][] info = new Object[combos.size()][headers.size()];

        for (int i = 0; i < combos.size(); i++) {
            Combo combo = combos.get(i);
            info[i][0] = combo.getId(); // id es int
            info[i][1] = combo.getName();
            info[i][2] = String.valueOf(combo.getDiscount());

            List<Component> components = combo.getComboComp();
            StringBuilder componentsString = new StringBuilder();

            if (components != null && !components.isEmpty()) {
                for (Component component : components) {
                    String compDesc = null;
                    if (component instanceof MotherBoard) {
                        compDesc = ((MotherBoard) component).getModel();
                    } else if (component instanceof HardDisk) {
                        compDesc = ((HardDisk) component).getModel();
                    } else if (component instanceof MicroProcessor) {
                        compDesc = ((MicroProcessor) component).getModel();
                    } else if (component instanceof RAM) {
                        compDesc = ((RAM) component).getType();
                    } else {
                        compDesc = component.toString();
                    }
                    if (compDesc != null) {
                        componentsString.append(compDesc).append(", ");
                    }
                }
                // Eliminar la última coma y espacio
                if (componentsString.length() > 2) {
                    componentsString.setLength(componentsString.length() - 2);
                }
            } else {
                componentsString.append("No componentes");
            }

            info[i][3] = componentsString.toString();
        }

        return info;
    }

    private void startComponents() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel lbl_comboTable = new JLabel("Listado de Combos");
        lbl_comboTable.setFont(new Font("Verdana", Font.BOLD, 30));
        contentPane.add(lbl_comboTable, BorderLayout.NORTH);

        scrollPaneTable = new JScrollPane();
        contentPane.add(scrollPaneTable);

        comboTable = new JTable();
        comboTable.setBackground(Color.white);
        comboTable.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        comboTable.addMouseListener(this);
        comboTable.setOpaque(false);
        scrollPaneTable.setViewportView(comboTable);

        panelBotton = new JPanel();
        contentPane.add(panelBotton, BorderLayout.SOUTH);

        btn_select = new JButton("Seleccionar");
        btn_select.setFont(new Font("Verdana", Font.BOLD, 12));
        btn_select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idCombo != -1) {
                    comboInterface.getSelectedCombo(idCombo);
                    dispose();
                }
            }
        });
        btn_select.setPreferredSize(new Dimension(85, 30));
        btn_select.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        panelBotton.add(btn_select);

        btn_delete = new JButton("Eliminar");
        btn_delete.setFont(new Font("Verdana", Font.BOLD, 12));
        btn_delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idCombo != -1) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "¿Seguro desea eliminar el combo con código: " + idCombo, "Confirmación",
                            JOptionPane.WARNING_MESSAGE);
                    if (option == JOptionPane.YES_OPTION) {
                        Administration.getInstance().deleteCombo(idCombo);
                        btn_delete.setEnabled(false);
                        updateTable();
                    }
                }
            }
        });
        btn_delete.setPreferredSize(new Dimension(85, 30));
        btn_delete.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        panelBotton.add(btn_delete);

        btn_addNew = new JButton("Agregar");
        btn_addNew.setFont(new Font("Verdana", Font.BOLD, 12));
        btn_addNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegComps newCombo = new RegComps();
                newCombo.setModal(true);
                newCombo.setVisible(true);
                updateTable();
            }
        });
        btn_addNew.setPreferredSize(new Dimension(85, 30));
        btn_addNew.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        panelBotton.add(btn_addNew);

        btn_cancel = new JButton("Cancelar");
        btn_cancel.setFont(new Font("Verdana", Font.BOLD, 12));
        btn_cancel.setPreferredSize(new Dimension(85, 30));
        btn_cancel.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        btn_cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBotton.add(btn_cancel);

        updateTable();
    }

    public static int getRows() {
        return rows;
    }

    public static void setRows(int rows) {
        ComboList.rows = rows;
    }

    public static int getColumns() {
        return columns;
    }

    public static void setColumns(int columns) {
        ComboList.columns = columns;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int i = comboTable.getSelectedRow();

        if (i >= 0) {
            Object value = comboTable.getValueAt(i, 0);
            if (value instanceof Integer) {
                idCombo = (Integer) value;
            } else {
                try {
                    idCombo = Integer.parseInt(value.toString());
                } catch (NumberFormatException ex) {
                    idCombo = -1;
                }
            }
            btn_addNew.setEnabled(false);

            if (comboInterface == null) {
                btn_delete.setEnabled(true);
            } else {
                btn_select.setEnabled(true);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // no usado
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // no usado
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // no usado
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // no usado
    }

    public static void updateTable() {
        buildTable();
    }
}
