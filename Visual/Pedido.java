package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Visual.Catalogo.onSelectedComp;
import Visual.SupplierList.onSelectedSupplier;
import logic.model.Administration;
import logic.model.Component;

public class Pedido extends JDialog implements onSelectedComp, onSelectedSupplier {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private int idSup;  // todos los ids son int ahora
    private JTable tableComponents;
    private DefaultTableModel tableModel;
    private JTextField txtIdSup;
    private JTextField txtPedido;
    private JTextField textField;
    private JButton btnSearchComp;
    private boolean isSupplierSelected = false;

    public static void main(String[] args) {
        try {
            Pedido dialog = new Pedido();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pedido() {
        setTitle("Pedido");
        setBounds(100, 100, 701, 445);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        contentPanel.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("ID Suplidor");
        lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        lblNewLabel.setBounds(33, 51, 99, 20);
        panel.add(lblNewLabel);

        txtIdSup = new JTextField();
        txtIdSup.setFont(new Font("Verdana", Font.PLAIN, 14));
        txtIdSup.setEditable(false);
        txtIdSup.setBounds(33, 84, 164, 26);
        panel.add(txtIdSup);
        txtIdSup.setColumns(10);

        JButton btnSearchSup = new JButton("Buscar");
        btnSearchSup.setBackground(new Color(230, 230, 250));
        btnSearchSup.setFont(new Font("Verdana", Font.PLAIN, 14));
        btnSearchSup.setBorder(new RoundedBorder(Color.BLACK, 1, 20));
        btnSearchSup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SupplierList suplist = new SupplierList(Pedido.this);
                suplist.setModal(true);
                suplist.setVisible(true);
                suplist.setResizable(false);
            }
        });
        btnSearchSup.setBounds(264, 83, 99, 29);
        panel.add(btnSearchSup);

        JPanel pnlRefillInfo = new JPanel();
        pnlRefillInfo.setBackground(new Color(230, 230, 250));
        pnlRefillInfo.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnlRefillInfo.setBounds(388, 23, 264, 210);
        panel.add(pnlRefillInfo);
        pnlRefillInfo.setLayout(null);

        JLabel lblIdPedido = new JLabel("ID Pedido");
        lblIdPedido.setFont(new Font("Verdana", Font.BOLD, 14));
        lblIdPedido.setBounds(26, 25, 99, 20);
        pnlRefillInfo.add(lblIdPedido);

        txtPedido = new JTextField();
        txtPedido.setFont(new Font("Verdana", Font.PLAIN, 14));
        txtPedido.setEditable(false);
        txtPedido.setColumns(10);
        txtPedido.setBounds(26, 58, 164, 26);
        pnlRefillInfo.add(txtPedido);
        Administration.getInstance();
		txtPedido.setText(String.valueOf(Administration.PedidoId));

        JLabel lblFechaRealizado = new JLabel("Fecha Realizado");
        lblFechaRealizado.setFont(new Font("Verdana", Font.BOLD, 14));
        lblFechaRealizado.setBounds(26, 109, 131, 20);
        pnlRefillInfo.add(lblFechaRealizado);

        textField = new JTextField();
        textField.setFont(new Font("Verdana", Font.PLAIN, 14));
        textField.setEditable(false);
        textField.setColumns(10);
        textField.setBounds(26, 145, 164, 26);
        pnlRefillInfo.add(textField);
        textField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        JLabel lblComponentes = new JLabel("Componentes");
        lblComponentes.setFont(new Font("Verdana", Font.BOLD, 14));
        lblComponentes.setBounds(33, 170, 115, 20);
        panel.add(lblComponentes);

        btnSearchComp = new JButton("Buscar");
        btnSearchComp.setBackground(new Color(230, 230, 250));
        btnSearchComp.setFont(new Font("Verdana", Font.PLAIN, 14));
        btnSearchComp.setBorder(new RoundedBorder(Color.BLACK, 1, 20));
        btnSearchComp.setEnabled(false); // se activa al seleccionar proveedor
        btnSearchComp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (!isSupplierSelected) {
                    JOptionPane.showMessageDialog(Pedido.this, "Por favor, seleccione un proveedor primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ArrayList<Component> componentsForSupplier = Administration.getInstance().getComponentsBySupplier(idSup);

                if (componentsForSupplier.isEmpty()) {
                    JOptionPane.showMessageDialog(Pedido.this, "El proveedor seleccionado no tiene componentes disponibles.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Catalogo cat = new Catalogo(Pedido.this, componentsForSupplier);
                cat.setModal(true);
                cat.setVisible(true);
                cat.setResizable(false);
            }
        });
        btnSearchComp.setBounds(264, 166, 99, 29);
        panel.add(btnSearchComp);

        JPanel pnlComponents = new JPanel();
        pnlComponents.setBorder(new RoundedBorder(Color.BLACK, 1, 15));
        pnlComponents.setBounds(33, 203, 330, 165);
        panel.add(pnlComponents);
        pnlComponents.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        pnlComponents.add(scrollPane, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Tipo", "Cantidad"}, 0) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // cantidad editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Integer.class;
                if (columnIndex == 2) return Integer.class;
                return String.class;
            }
        };

        tableComponents = new JTable(tableModel);
        tableComponents.setFont(new Font("Verdana", Font.PLAIN, 13));
        scrollPane.setViewportView(tableComponents);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(230, 230, 250));
        btnCancelar.setFont(new Font("Verdana", Font.PLAIN, 14));
        btnCancelar.setBorder(new RoundedBorder(Color.BLACK, 1, 20));
        btnCancelar.setBounds(515, 339, 99, 29);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(btnCancelar);

        JButton btnRealizarPedido = new JButton("Pedir");
        btnRealizarPedido.setBackground(new Color(230, 230, 250));
        btnRealizarPedido.setFont(new Font("Verdana", Font.PLAIN, 14));
        btnRealizarPedido.setBorder(new RoundedBorder(Color.BLACK, 1, 20));
        btnRealizarPedido.setBounds(388, 339, 115, 29);
        btnRealizarPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isSupplierSelected) {
                    JOptionPane.showMessageDialog(Pedido.this, "Seleccione un proveedor antes de pedir.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ArrayList<Component> componentsOrder = new ArrayList<>();

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Object idObj = tableModel.getValueAt(i, 0);
                    int idComp;
                    if (idObj instanceof Integer) {
                        idComp = (Integer) idObj;
                    } else {
                        try {
                            idComp = Integer.parseInt(idObj.toString());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(Pedido.this, "ID de componente inválido en la fila " + (i + 1), "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    Object qtyObj = tableModel.getValueAt(i, 2);
                    if (!(qtyObj instanceof Integer) || (int) qtyObj <= 0) {
                        JOptionPane.showMessageDialog(Pedido.this, "Cantidad inválida para el componente " + idComp, "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int quantity = (int) qtyObj;
                    Component comp = Administration.getInstance().searchComponentById(idComp);
                    if (comp != null) {
                        comp.setUnits(quantity); // cantidad a reabastecer
                        componentsOrder.add(comp);
                    }
                }

                boolean refillSuccessful = Administration.getInstance().inventoryRefill(componentsOrder);
                if (refillSuccessful) {
                    JOptionPane.showMessageDialog(Pedido.this, "Reabastecimiento de inventario realizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // resetear campos
                    txtIdSup.setText("");
                    Administration.getInstance();
					txtPedido.setText(String.valueOf(Administration.PedidoId));
                    textField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    tableModel.setRowCount(0);
                    isSupplierSelected = false;
                    btnSearchComp.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(Pedido.this, "Error al reabastecer el inventario. Verifique los datos e intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(btnRealizarPedido);
    }

    // Implementación de onSelectedComp (componente individual), agrega o suma cantidad


    @Override
    public void onComponentSelected(int ID, int cantidad) {
        addOrIncrementComponent(ID, cantidad);
    }

    private void addOrIncrementComponent(int ID, int cantidad) {
        String type = Catalogo.getComponentType(Administration.getInstance().searchComponentById(ID));
        if (type == null) return;

        boolean exists = false;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object idObj = tableModel.getValueAt(i, 0);
            int idRow = (idObj instanceof Integer) ? (Integer) idObj : Integer.parseInt(idObj.toString());
            if (idRow == ID) {
                int currentQty = (int) tableModel.getValueAt(i, 2);
                tableModel.setValueAt(currentQty + cantidad, i, 2);
                exists = true;
                break;
            }
        }
        if (!exists) {
            tableModel.addRow(new Object[]{ID, type, cantidad});
        }
    }

    // Implementación de onSelectedSupplier
    @Override
    public void getSelectedSupplier(int id) {
        this.idSup = id;
        txtIdSup.setText(String.valueOf(idSup));
        isSupplierSelected = true;
        btnSearchComp.setEnabled(true);
    }
}
