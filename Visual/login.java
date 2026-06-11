package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logic.dao.UserDAO;
import logic.dao.impl.UserDAOImpl;
import logic.model.User;
import logic.util.DAOException;

public class login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtUsername;
    private JPasswordField password;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // Crear DAO para usuarios
                UserDAO userDAO = new UserDAOImpl();

                // Verificar si hay usuarios en la base de datos
                boolean usersExist = !userDAO.findAll().isEmpty();

                if (!usersExist) {
                    // Crear usuario admin por defecto y guardar en base
                    User adminUser = new User(1, "Administrador", "admin", "admin", 1);
                    userDAO.insert(adminUser);
                }

                login frame = new login();
                frame.setVisible(true);
            } catch (DAOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error en la base de datos: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 580, 553);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(51, 51, 51));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(51, 51, 51));
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Bienvenido!");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 27));
        lblNewLabel.setBounds(196, 222, 159, 29);
        panel.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(70, 90, 105));
        panel_1.setBorder(new RoundedBorder(Color.WHITE, 1, 15));
        panel_1.setBounds(71, 264, 409, 158);
        panel.add(panel_1);
        panel_1.setLayout(null);

        JLabel label = new JLabel("Usuario:");
        label.setForeground(new Color(248, 248, 255));
        label.setFont(new Font("Verdana", Font.PLAIN, 17));
        label.setBounds(45, 42, 72, 16);
        panel_1.add(label);

        JLabel label_1 = new JLabel("Contraseña:");
        label_1.setForeground(new Color(248, 248, 255));
        label_1.setFont(new Font("Verdana", Font.PLAIN, 17));
        label_1.setBounds(12, 100, 105, 16);
        panel_1.add(label_1);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Verdana", Font.PLAIN, 17));
        txtUsername.setBounds(129, 41, 167, 22);
        panel_1.add(txtUsername);
        txtUsername.setColumns(10);

        password = new JPasswordField();
        password.setFont(new Font("Verdana", Font.PLAIN, 17));
        password.setBounds(129, 99, 268, 22);
        panel_1.add(password);

        JButton btnLogin = new JButton("Login");
        btnLogin.setForeground(new Color(248, 248, 255));
        btnLogin.setBackground(new Color(70, 90, 105));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String username = txtUsername.getText();
                char[] passTemp = password.getPassword();
                String pass = new String(passTemp);

                try {
                    UserDAO userDAO = new UserDAOImpl();
                    User user = userDAO.findByUsername(username);

                    if (user != null && user.getPassword().equals(pass)) {
                        // Login exitoso
                        // Podrías guardar usuario en Administration si quieres
                        Principal frame = new Principal(user);
                        dispose();
                        frame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos",
                                "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (DAOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error en la base de datos: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnLogin.setBorder(new RoundedBorder(Color.WHITE, 1, 25));
        btnLogin.setFont(new Font("Verdana", Font.PLAIN, 16));
        btnLogin.setBounds(383, 447, 97, 25);
        getRootPane().setDefaultButton(btnLogin);
        panel.add(btnLogin);

        JLabel lblLogo = new JLabel("");
        lblLogo.setBounds(153, 25, 246, 194);

        Image img = new ImageIcon(this.getClass().getResource("/Images/logo.png")).getImage();
        Image scaledImg = img.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(scaledImg));

        panel.add(lblLogo);
    }
}
