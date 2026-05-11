package visual;

import models.User;
import services.UserServices;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class LoginView extends JFrame {

    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final UserServices userServices;

    public LoginView() {
        userServices = new UserServices();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        setTitle("Login - Sistema de Seguros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 300);
        setMinimumSize(new Dimension(420, 300));
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rootPanel.setBackground(new Color(245, 247, 250));

        JLabel titleLabel = new JLabel("Acceso al sistema");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitleLabel = new JLabel("Ingresa con tu usuario y contraseña");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setOpaque(false);
        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.gridx = 0;
        headerConstraints.gridy = 0;
        headerConstraints.anchor = GridBagConstraints.WEST;
        headerConstraints.insets = new Insets(0, 0, 4, 0);
        headerPanel.add(titleLabel, headerConstraints);

        headerConstraints.gridy = 1;
        headerConstraints.insets = new Insets(0, 0, 0, 0);
        headerPanel.add(subtitleLabel, headerConstraints);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 20, 0));

        JLabel lblUsername = new JLabel("Usuario");
        JLabel lblPassword = new JLabel("Contraseña");

        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 0, 6, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        formPanel.add(lblUsername, c);

        c.gridy = 1;
        formPanel.add(txtUsername, c);

        c.gridy = 2;
        formPanel.add(lblPassword, c);

        c.gridy = 3;
        formPanel.add(txtPassword, c);

        JButton btnLogin = new JButton("Ingresar");
        btnLogin.addActionListener(e -> performLogin());

        JButton btnClear = new JButton("Limpiar");
        btnClear.addActionListener(e -> clearFields());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnLogin);

        rootPanel.add(headerPanel, BorderLayout.NORTH);
        rootPanel.add(formPanel, BorderLayout.CENTER);
        rootPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(rootPanel);
        getRootPane().setDefaultButton(btnLogin);
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Completa usuario y contraseña.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        User user = userServices.login(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Credenciales inválidas o usuario inactivo.",
                    "Acceso denegado",
                    JOptionPane.ERROR_MESSAGE
            );
            txtPassword.setText("");
            txtPassword.requestFocusInWindow();
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Bienvenido, " + user.getFullName(),
                "Acceso correcto",
                JOptionPane.INFORMATION_MESSAGE
        );

        HomeView homeView = new HomeView(user);
        homeView.setVisible(true);
        dispose();
    }

    private void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtUsername.requestFocusInWindow();
    }
}