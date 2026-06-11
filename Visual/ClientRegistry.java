package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import logic.model.Administration;
import logic.model.Client;
import logic.model.Person;
import logic.util.DAOException;


public class ClientRegistry extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txt_clientId;
	private JTextField txt_clientName;
	private JTextField txt_clientPersonalDoc;
	private JTextField txt_clientEmail;
	private JTextField txt_clientAddress;
	private JTextField txt_clientPhoneNum;
	private JLabel lbl_warningName;
	private JLabel lbl_warningPersonalDoc;
	private JLabel lbl_warningEmail;
	private JLabel lbl_warningAddress;
	private JLabel lbl_warningPhoneNum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ClientRegistry dialog = new ClientRegistry(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ClientRegistry(Client client) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientRegistry.class.getResource("/Images/nueva-cuenta (2).png")));
		
		try {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		if(client != null)
		{
			setTitle("Actualizar Cliente");
		}
		else
		{
			setTitle("Registro Cliente");
		}
		setResizable(false);
		setBounds(100, 100, 691, 566);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(0, 153, 255));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel_1.setBackground(new Color(102, 204, 255));
			panel_1.setBounds(336, 38, 299, 409);
			panel.add(panel_1);
			panel_1.setLayout(null);
			
			JLabel lbl_clientInfo = new JLabel("Informaci\u00F3n del Cliente:");
			lbl_clientInfo.setFont(new Font("Verdana", Font.BOLD, 14));
			lbl_clientInfo.setBounds(56, 13, 205, 16);
			panel_1.add(lbl_clientInfo);
			
			JLabel lbl_clientId = new JLabel("Id:");
			lbl_clientId.setFont(new Font("Verdana", Font.BOLD, 12));
			lbl_clientId.setBounds(12, 66, 56, 16);
			panel_1.add(lbl_clientId);
			
			JLabel lbl_clientName = new JLabel("Nombre:");
			lbl_clientName.setFont(new Font("Verdana", Font.BOLD, 12));
			lbl_clientName.setBounds(12, 111, 70, 16);
			panel_1.add(lbl_clientName);
			
			JLabel lbl_clientAddress = new JLabel("Direcci\u00F3n:");
			lbl_clientAddress.setFont(new Font("Verdana", Font.BOLD, 12));
			lbl_clientAddress.setBounds(12, 294, 70, 16);
			panel_1.add(lbl_clientAddress);
			
			JLabel lbl_clientPersonalDoc = new JLabel("C\u00E9dula:");
			lbl_clientPersonalDoc.setFont(new Font("Verdana", Font.BOLD, 12));
			lbl_clientPersonalDoc.setBounds(12, 166, 56, 16);
			panel_1.add(lbl_clientPersonalDoc);
			
			JLabel lbl_clientEmail = new JLabel("Correo electr\u00F3nico:");
			lbl_clientEmail.setFont(new Font("Verdana", Font.BOLD, 12));
			lbl_clientEmail.setBounds(12, 229, 137, 16);
			panel_1.add(lbl_clientEmail);
			
			JLabel lbl_clientPhoneNum = new JLabel("Tel\u00E9fono:");
			lbl_clientPhoneNum.setFont(new Font("Verdana", Font.BOLD, 12));
			lbl_clientPhoneNum.setBounds(12, 353, 81, 16);
			panel_1.add(lbl_clientPhoneNum);
			
			txt_clientId = new JTextField();
			txt_clientId.setFont(new Font("Verdana", Font.PLAIN, 12));
			txt_clientId.setEditable(false);
			txt_clientId.setBounds(139, 55, 148, 22);
			panel_1.add(txt_clientId);
			txt_clientId.setText(Integer.toString(Administration.getInstance().getPersonId()));
			txt_clientId.setColumns(10);    
			
			txt_clientName = new JTextField();
			txt_clientName.setFont(new Font("Verdana", Font.PLAIN, 12));
			txt_clientName.setBounds(139, 108, 148, 22);
			panel_1.add(txt_clientName);
			txt_clientName.setColumns(10);
			
			txt_clientPersonalDoc = new JTextField();
			txt_clientPersonalDoc.setFont(new Font("Verdana", Font.PLAIN, 12));
			txt_clientPersonalDoc.setBounds(139, 163, 148, 22);
			panel_1.add(txt_clientPersonalDoc);
			txt_clientPersonalDoc.setColumns(10);
			
			txt_clientEmail = new JTextField();
			txt_clientEmail.setFont(new Font("Verdana", Font.PLAIN, 12));
			txt_clientEmail.setBounds(139, 226, 148, 22);
			panel_1.add(txt_clientEmail);
			txt_clientEmail.setColumns(10);
			
			txt_clientAddress = new JTextField();
			txt_clientAddress.setFont(new Font("Verdana", Font.PLAIN, 12));
			txt_clientAddress.setBounds(139, 287, 148, 22);
			panel_1.add(txt_clientAddress);
			txt_clientAddress.setColumns(10);
			
			txt_clientPhoneNum = new JTextField();
			txt_clientPhoneNum.setFont(new Font("Verdana", Font.PLAIN, 12));
			txt_clientPhoneNum.setBounds(139, 347, 148, 22);
			panel_1.add(txt_clientPhoneNum);
			txt_clientPhoneNum.setColumns(10);
			
			lbl_warningName = new JLabel("ESTE CAMPO ES OBLIGATORIO");
			lbl_warningName.setFont(new Font("Verdana", Font.BOLD, 12));
			lbl_warningName.setForeground(Color.RED);
			lbl_warningName.setBounds(80, 139, 219, 16);
			panel_1.add(lbl_warningName);
			lbl_warningName.setVisible(false);
			
			lbl_warningPersonalDoc = new JLabel("ESTE CAMPO ES OBLIGATORIO");
			lbl_warningPersonalDoc.setForeground(Color.RED);
			lbl_warningPersonalDoc.setFont(new Font("Verdana", Font.BOLD, 12));
			lbl_warningPersonalDoc.setBounds(80, 197, 219, 16);
			panel_1.add(lbl_warningPersonalDoc);
			lbl_warningPersonalDoc.setVisible(false);
			
			lbl_warningEmail = new JLabel("ESTE CAMPO ES OBLIGATORIO");
			lbl_warningEmail.setForeground(Color.RED);
			lbl_warningEmail.setFont(new Font("Verdana", Font.BOLD, 12));
			lbl_warningEmail.setBounds(80, 258, 219, 16);
			panel_1.add(lbl_warningEmail);
			lbl_warningEmail.setVisible(false);
			
			lbl_warningAddress = new JLabel("ESTE CAMPO ES OBLIGATORIO");
			lbl_warningAddress.setForeground(Color.RED);
			lbl_warningAddress.setFont(new Font("Verdana", Font.BOLD, 12));
			lbl_warningAddress.setBounds(80, 319, 219, 16);
			panel_1.add(lbl_warningAddress);
			lbl_warningAddress.setVisible(false);
			
			lbl_warningPhoneNum = new JLabel("ESTE CAMPO ES OBLIGATORIO");
			lbl_warningPhoneNum.setForeground(Color.RED);
			lbl_warningPhoneNum.setFont(new Font("Verdana", Font.BOLD, 12));
			lbl_warningPhoneNum.setBounds(80, 380, 219, 16);
			panel_1.add(lbl_warningPhoneNum);
			lbl_warningPhoneNum.setVisible(false);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(24, 38, 274, 409);
			panel.add(panel_2);
			panel_2.setLayout(null);
			
			JLabel foto = new JLabel("");
			foto.setMaximumSize(new Dimension(48, 70));
			foto.setBounds(0, 0, 274, 409);
			panel_2.add(foto);
			
			ImageIcon icon = new ImageIcon(this.getClass().getResource("/Images/foto1.jpg"));
			Image pic = icon.getImage();
			Image sized = pic.getScaledInstance(panel_2.getWidth(), panel_2.getHeight(), Image.SCALE_SMOOTH);
			foto.setIcon(new ImageIcon(sized));
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(0, 153, 255));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton reg_btn = new JButton("Registrar");
				reg_btn.setFont(new Font("Verdana", Font.BOLD, 14));
				reg_btn.setPreferredSize(new Dimension(85, 30));
				reg_btn.setBorder(new RoundedBorder(Color.BLACK,1,25));
				if(client != null)
				{
					reg_btn.setText("Actualizar");
				}
				reg_btn.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent arg0) {
				        try {
				            int id  = Integer.parseInt(txt_clientId.getText());
				            String name = txt_clientName.getText();
				            String personalDoc = txt_clientPersonalDoc.getText();
				            String address = txt_clientAddress.getText();
				            String email = txt_clientEmail.getText();
				            String phoneNum = txt_clientPhoneNum.getText();
				            boolean ready = true;

				            if(name.isEmpty()){
				                lbl_warningName.setVisible(true);
				                ready = false;
				            } else {
				                lbl_warningName.setVisible(false);
				            }

				            if(personalDoc.isEmpty()){
				                lbl_warningPersonalDoc.setVisible(true);
				                ready = false;
				            } else {
				                lbl_warningPersonalDoc.setVisible(false);
				            }

				            if(address.isEmpty()){
				                lbl_warningAddress.setVisible(true);
				                ready = false;
				            } else {
				                lbl_warningAddress.setVisible(false);
				            }

				            if(email.isEmpty()) {
				                lbl_warningEmail.setVisible(true);
				                ready = false;
				            } else {
				                lbl_warningEmail.setVisible(false);
				            }

				            if(phoneNum.isEmpty()) {
				                lbl_warningPhoneNum.setVisible(true);
				                ready = false;
				            } else {
				                lbl_warningPhoneNum.setVisible(false);
				            }

				            if(ready) {
				                if(client == null) {
				           			Client newClient = new Client(personalDoc, Person.getFirstName(name), Person.getLastName(name), phoneNum, address, email, id, Administration.AdminId);
				           			try {
				           			    Administration.getInstance().regClient(newClient);
				           			    JOptionPane.showMessageDialog(null, "Cliente registrado satisfactoriamente.", "Registro Cliente", JOptionPane.INFORMATION_MESSAGE);
				           			    clean();
				           			} catch (DAOException e) {
				           			    e.printStackTrace();
				           			    JOptionPane.showMessageDialog(null, "Error al registrar cliente en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
				           			}

									JOptionPane.showMessageDialog(null, "Operación satisfactoriamente completada.", "Registro Cliente", JOptionPane.INFORMATION_MESSAGE);
				                    clean();
				                } else {
				                    client.setName(name);
				                    client.setNi(personalDoc);
				                    client.setEmail(email);
				                    client.setAddress(address);
				                    client.setPhone(phoneNum);
				                    Administration.getInstance().updatePerson(client);
				                    ClientList.updateTable();
				                    dispose();
				                }
				            }
				        } 
				        catch (Exception e) {
				            e.printStackTrace();
				        }
				    }
				});

				reg_btn.setActionCommand("OK");
				buttonPane.add(reg_btn);
				getRootPane().setDefaultButton(reg_btn);
			}
			{
				JButton cancel_btn = new JButton("Cancelar");
				cancel_btn.setFont(new Font("Verdana", Font.BOLD, 14));
				cancel_btn.setBorder(new RoundedBorder(Color.BLACK,1,25));
				cancel_btn.setPreferredSize(new Dimension(85, 30));
				cancel_btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancel_btn.setActionCommand("Cancel");
				buttonPane.add(cancel_btn);
			}
		}
		
		loadClient(client);
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void loadClient(Client client) {
	   
		try {
	        if(client != null) {
	        	txt_clientId.setText(String.valueOf(client.getId()));
	            txt_clientName.setText(client.getName());
	            txt_clientPersonalDoc.setText(client.getNi());
	            txt_clientEmail.setText(client.getEmail());
	            txt_clientAddress.setText(client.getAddress());
	            txt_clientPhoneNum.setText(client.getPhone());
	        }
	    } 
		catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	private void clean() {
	    try {
	    	Administration.getInstance();
			txt_clientId.setText(String.valueOf(Administration.PersonId));
	        txt_clientName.setText("");
	        txt_clientPersonalDoc.setText("");
	        txt_clientEmail.setText("");
	        txt_clientAddress.setText("");
	        txt_clientPhoneNum.setText("");
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
