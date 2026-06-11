package Visual;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CustomTableCellRenderer extends JPanel implements TableCellRenderer {
    private static final long serialVersionUID = 1L;
    private final JLabel lblIcon;
    private final JLabel lblId;
    // Cambié a JCheckBox para selección múltiple correcta
    private final JCheckBox chkSelected;
    private final JSpinner spinner;
    private final JPanel innerPanel;
    private final JLabel lblName;

    public CustomTableCellRenderer() {
        setLayout(new BorderLayout());
        innerPanel = new JPanel();
        innerPanel.setLayout(null);
        innerPanel.setPreferredSize(new Dimension(227 / 3, 194));

        lblIcon = new JLabel();
        lblIcon.setBounds(70, 16, 79, 63);
        innerPanel.add(lblIcon);

        lblId = new JLabel();
        lblId.setFont(new Font("Verdana", Font.PLAIN, 15));
        lblId.setBounds(41, 128, 146, 26);
        innerPanel.add(lblId);

        chkSelected = new JCheckBox("");
        chkSelected.setBounds(11, 153, 34, 29);
        innerPanel.add(chkSelected);

        spinner = new JSpinner();
        spinner.setFont(new Font("Verdana", Font.PLAIN, 15));
        spinner.setBounds(41, 156, 146, 26);
        innerPanel.add(spinner);

        lblName = new JLabel();
        lblName.setFont(new Font("Verdana", Font.PLAIN, 15));
        lblName.setBounds(41, 92, 146, 26);
        innerPanel.add(lblName);

        add(innerPanel, BorderLayout.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (value instanceof DataWrapper) {
            DataWrapper data = (DataWrapper) value;

            if (data.getIcon() != null) {
                Image img = data.getIcon().getImage();
                Image scaledImg = img.getScaledInstance(lblIcon.getWidth(), lblIcon.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImg);
                lblIcon.setIcon(icon);
            } else {
                lblIcon.setIcon(null);
            }

            lblId.setVisible(true);
            lblName.setVisible(true);
            spinner.setVisible(true);
            chkSelected.setVisible(true);

            lblId.setText(String.valueOf(data.getTextField()));
            lblName.setText(data.getTxtName() != null ? data.getTxtName() : "");
            spinner.setValue(data.getSpinnerValue());
            // Aquí usamos el checkboxSelected del DataWrapper
            chkSelected.setSelected(data.isCheckboxSelected());

        } else {
            // No data: valores neutros pero componentes visibles
            lblIcon.setIcon(null);
            lblId.setVisible(true);
            lblName.setVisible(true);
            spinner.setVisible(true);
            chkSelected.setVisible(true);

            lblId.setText("");
            lblName.setText("");
            spinner.setValue(0);
            chkSelected.setSelected(false);
        }

        return this;
    }
}
