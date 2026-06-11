package Visual;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private final String[] columnNames = {"", "Lista de componentes", ""};
    private List<DataWrapper> data;

    public CustomTableModel() {
        data = new ArrayList<>();
    }

    public List<DataWrapper> getData() {
        return data;
    }

    public void setData(List<DataWrapper> data) {
        this.data = data;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        // Filas necesarias para 3 columnas por fila
        return (data.size() + 2) / 3;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        int dataIndex = rowIndex * 3 + columnIndex;
        if (dataIndex < data.size()) {
            return data.get(dataIndex);
        } else {
            return null; // Celda vacía si no hay datos suficientes
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Solo editable si hay DataWrapper en esa celda (no null)
        return getValueAt(rowIndex, columnIndex) != null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        int dataIndex = rowIndex * 3 + columnIndex;
        if (dataIndex < data.size() && value instanceof DataWrapper) {
            DataWrapper existing = data.get(dataIndex);
            DataWrapper incoming = (DataWrapper) value;
            // Actualizamos los campos para mantener la referencia y evitar perder datos
            existing.setSpinnerValue(incoming.getSpinnerValue());
            existing.setCheckboxSelected(incoming.isCheckboxSelected()); // Ajustado al boolean correcto
            existing.setTxtName(incoming.getTxtName());
            existing.setTextField(incoming.getTextField());
            existing.setIcon(incoming.getIcon());
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
}
