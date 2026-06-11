package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import logic.dao.impl.ComponentDAOImpl;
import logic.model.Administration;
import logic.model.Component;
import logic.model.HardDisk;
import logic.model.MicroProcessor;
import logic.model.MotherBoard;
import logic.model.RAM;

public class Catalogo extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private CustomTableModel tableModel = new CustomTableModel();

    public interface onSelectedComp {
        void onComponentSelected(int id, int quantity);
    }

    private JTable table;
    private JButton cancelButton;
    private static JButton deleteBtn;
    private static JButton selectBtn;
    private static JButton addBtn;

    public Catalogo(onSelectedComp compInterface) {
        setTitle("Catálogo");
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        loadComponentsFromDatabase(tableModel, "Todos");
        initComponents(compInterface);
    }

    public Catalogo(onSelectedComp compInterface, ArrayList<Component> filteredComponents) {
        this(compInterface);
        loadComponentsFromList(filteredComponents);
    }

    private void initComponents(onSelectedComp compInterface) {
        table = new JTable(tableModel);
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        table.setRowHeight(200);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        initializeColumns();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 400));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPane = new JPanel(new BorderLayout());
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPane.add(buttonsPanel, BorderLayout.CENTER);

        selectBtn = new JButton("Seleccionar");
        selectBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	commitTableEdits();
                List<DataWrapper> selectedItems = getSelectedCheckboxes();

                if (!selectedItems.isEmpty()) {
                    for (DataWrapper dw : selectedItems) {
                        if (compInterface != null) {
                            compInterface.onComponentSelected(dw.getTextField(), dw.getSpinnerValue());
                        }
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay componentes seleccionados.");
                }
            }
        });
        selectBtn.setFont(new Font("Verdana", Font.PLAIN, 15));
        selectBtn.setPreferredSize(new Dimension(95, 30));
        selectBtn.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        buttonsPanel.add(selectBtn);

        deleteBtn = new JButton("Eliminar");
        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	commitTableEdits();
                List<DataWrapper> selectedItems = getSelectedCheckboxes();

                if (!selectedItems.isEmpty()) {
                    int option = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el/los componentes?", "Confirmación", JOptionPane.WARNING_MESSAGE);
                    if (option == JOptionPane.YES_OPTION) {
                        for (DataWrapper dw : selectedItems) {
                            Administration.getInstance().deleteComponentDB(dw.getTextField());
                        }
                        deleteBtn.setEnabled(false);
                        loadComponentsFromDatabase(tableModel, "Todos");
                    }
                }
            }
        });
        deleteBtn.setFont(new Font("Verdana", Font.PLAIN, 15));
        deleteBtn.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        deleteBtn.setPreferredSize(new Dimension(75, 30));
        buttonsPanel.add(deleteBtn);

        addBtn = new JButton("Agregar");
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegComps newComp = new RegComps();
                newComp.setModal(true);
                newComp.setVisible(true);
                loadComponentsFromDatabase(tableModel, "Todos");
            }
        });
        addBtn.setFont(new Font("Verdana", Font.PLAIN, 15));
        addBtn.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        addBtn.setPreferredSize(new Dimension(80, 30));
        buttonsPanel.add(addBtn);

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        cancelButton.setFont(new Font("Verdana", Font.PLAIN, 15));
        cancelButton.setBorder(new RoundedBorder(Color.BLACK, 1, 25));
        cancelButton.setPreferredSize(new Dimension(80, 30));
        buttonsPanel.add(cancelButton);
    }

    private void initializeColumns() {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setCellRenderer(new CustomTableCellRenderer());
            column.setCellEditor(new TableEditor());
        }
    }

    public static void loadComponentsFromDatabase(final CustomTableModel tableModel, final String componentType) {
        // Fila placeholder mientras carga
        List<DataWrapper> placeholder = new ArrayList<>();
        placeholder.add(new DataWrapper(new ImageIcon(), 0, "Cargando componentes...", 0, false));
        tableModel.setData(placeholder);

        new javax.swing.SwingWorker<ArrayList<Component>, Void>() {
            @Override
            protected ArrayList<Component> doInBackground() {
                return new ArrayList<>(Administration.getInstance().getAllComponents());
            }

            @Override
            protected void done() {
                try {
                    ArrayList<Component> aux = get();
                    List<DataWrapper> data = new ArrayList<>();

                    for (Component comp : aux) {
                        // Filtrado por tipo (si no es "Todos")
                        if (!"Todos".equals(componentType) && !getComponentType(comp).equals(componentType)) {
                            continue;
                        }

                        // Íconos por tipo
                        if (comp instanceof MotherBoard) {
                            ((MotherBoard) comp).setIcon(loadIcon("/Images/tarjeta-madre.png"));
                        } else if (comp instanceof HardDisk) {
                            ((HardDisk) comp).setIcon(loadIcon("/Images/disco-duro.png"));
                        } else if (comp instanceof MicroProcessor) {
                            ((MicroProcessor) comp).setIcon(loadIcon("/Images/microprocesador.png"));
                        } else if (comp instanceof RAM) {
                            ((RAM) comp).setIcon(loadIcon("/Images/memoria-ram.png"));
                        }

                        ImageIcon icon = comp.getIcon();
                        int id = comp.getId();
                        int units = comp.getUnits();

                        String name;
                        if (comp instanceof MotherBoard) {
                            name = ((MotherBoard) comp).getModel();
                        } else if (comp instanceof HardDisk) {
                            name = ((HardDisk) comp).getModel();
                        } else if (comp instanceof MicroProcessor) {
                            name = ((MicroProcessor) comp).getModel();
                        } else if (comp instanceof RAM) {
                            name = ((RAM) comp).getType();
                        } else {
                            name = comp.getClass().getSimpleName();
                        }

                        data.add(new DataWrapper(icon, id, name, units, false));
                    }

                    tableModel.setData(data);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error cargando componentes: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    tableModel.setData(new ArrayList<>()); // limpia si falla
                }
            }
        }.execute();
    }



    private void loadComponentsFromList(ArrayList<Component> components) {
        List<DataWrapper> data = new ArrayList<>();

        for (Component comp : components) {
            ImageIcon icon = comp.getIcon();
            int textField = comp.getId();
            int spinnerValue = comp.getUnits();
            String name = "";

            if (comp instanceof MotherBoard) {
                name = ((MotherBoard) comp).getModel();
            } else if (comp instanceof HardDisk) {
                name = ((HardDisk) comp).getModel();
            } else if (comp instanceof MicroProcessor) {
                name = ((MicroProcessor) comp).getModel();
            } else if (comp instanceof RAM) {
                name = ((RAM) comp).getType();
            }

            data.add(new DataWrapper(icon, textField, name, spinnerValue, false));
        }

        tableModel.setData(data);
    }

    public static String getComponentType(Component comp) {
        if (comp instanceof MotherBoard) {
            return "Tarjeta Madre";
        } else if (comp instanceof HardDisk) {
            return "Disco Duro";
        } else if (comp instanceof RAM) {
            return "Ram";
        } else if (comp instanceof MicroProcessor) {
            return "Microprocesador";
        } else {
            return "Otros";
        }
    }

    private List<DataWrapper> getSelectedCheckboxes() {
        List<DataWrapper> selectedItems = new ArrayList<>();

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            for (int column = 0; column < tableModel.getColumnCount(); column++) {
                Object value = tableModel.getValueAt(row, column);
                if (value instanceof DataWrapper) {
                    DataWrapper data = (DataWrapper) value;
                    if (data.isCheckboxSelected()) {
                        selectedItems.add(data);
                    }
                }
            }
        }

        return selectedItems;
    }
    
    private static ImageIcon loadIcon(String path) {
        java.net.URL url = Catalogo.class.getResource(path);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            System.err.println("⚠ Imagen no encontrada: " + path);
            return new ImageIcon(); // imagen vacía o reemplazo
        }
    }
    
    private void commitTableEdits() {
        if (table != null && table.isEditing()) {
            try { table.getCellEditor().stopCellEditing(); } catch (Exception ignore) {}
        }
    }



    
}