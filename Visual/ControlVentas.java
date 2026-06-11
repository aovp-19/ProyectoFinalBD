package Visual;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import java.awt.Font;

public class ControlVentas extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtListaDeFacturas;
	private JTextField txtGraficoDeVenta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ControlVentas dialog = new ControlVentas();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ControlVentas() {
		setBounds(100, 100, 822, 553);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBounds(394, 5, 1, 1);
				panel_1.setLayout(null);
				panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_1.setBackground(new Color(51, 51, 51));
				panel.add(panel_1);
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setLayout(null);
				panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_1.setBackground(new Color(51, 51, 51));
				panel_1.setBounds(0, -12, 322, 517);
				panel.add(panel_1);
				{
					JPanel panel_2 = new JPanel();
					panel_2.setBounds(0, 171, 322, 178);
					panel_1.add(panel_2);
					panel_2.setLayout(null);
					panel_2.setBackground(new Color(51, 51, 51));
					{
						txtListaDeFacturas = new JTextField();
						txtListaDeFacturas.setText("          Lista de facturas");
						txtListaDeFacturas.setForeground(Color.WHITE);
						txtListaDeFacturas.setFont(new Font("Verdana", Font.PLAIN, 20));
						txtListaDeFacturas.setEditable(false);
						txtListaDeFacturas.setColumns(10);
						txtListaDeFacturas.setBackground(new Color(51, 51, 51));
						txtListaDeFacturas.setBounds(0, 0, 322, 88);
						panel_2.add(txtListaDeFacturas);
					}
					{
						txtGraficoDeVenta = new JTextField();
						txtGraficoDeVenta.setBounds(0, 86, 322, 88);
						panel_2.add(txtGraficoDeVenta);
						txtGraficoDeVenta.setText("          Grafico de venta");
						txtGraficoDeVenta.setForeground(Color.WHITE);
						txtGraficoDeVenta.setFont(new Font("Verdana", Font.PLAIN, 20));
						txtGraficoDeVenta.setEditable(false);
						txtGraficoDeVenta.setColumns(10);
						txtGraficoDeVenta.setBackground(new Color(51, 51, 51));
					}
				}
			}
		}
	}

}
