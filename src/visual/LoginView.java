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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginView extends JFrame {

    private static final Color ACCENT_BAR = new Color(30, 100, 200);
    private static final Color LOGIN_BG = new Color(235, 238, 245);

    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final UserServices userServices;

    public LoginView() {
        userServices = new UserServices();

        setTitle("Inicio de Sesión - Sistema de Seguros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(440, 480);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(LOGIN_BG);

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UIStyles.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

        JPanel accentBar = new JPanel();
        accentBar.setBackground(ACCENT_BAR);
        accentBar.setPreferredSize(new java.awt.Dimension(0, 4));

        JPanel inner = new JPanel(new BorderLayout(0, 18));
        inner.setBackground(UIStyles.CARD_BG);
        inner.setBorder(BorderFactory.createEmptyBorder(35, 40, 30, 40));

        JPanel header = new JPanel(new GridBagLayout());
        header.setOpaque(false);

        JLabel brandIcon = new JLabel("\uD83D\uDEE1\uFE0F");
        brandIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));

        JLabel title = new JLabel("Sistema de Seguros");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(UIStyles.TEXT_PRIMARY);

        JLabel subtitle = new JLabel("Ingrese sus credenciales para acceder");
        subtitle.setFont(UIStyles.FONT_SUBTITLE);
        subtitle.setForeground(UIStyles.TEXT_SECONDARY);

        GridBagConstraints hc = new GridBagConstraints();
        hc.gridx = 0; hc.gridy = 0;
        header.add(brandIcon, hc);
        hc.gridy = 1;
        hc.insets = new Insets(6, 0, 3, 0);
        header.add(title, hc);
        hc.gridy = 2;
        hc.insets = new Insets(0, 0, 0, 0);
        header.add(subtitle, hc);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        txtUsername = new JTextField(18);
        txtPassword = new JPasswordField(18);
        UIStyles.styleField(txtUsername);
        UIStyles.styleField(txtPassword);

        JLabel lblUser = UIStyles.createFieldLabel("Usuario");
        JLabel lblPass = UIStyles.createFieldLabel("Contraseña");

        GridBagConstraints fc = new GridBagConstraints();
        fc.gridx = 0; fc.gridy = 0;
        fc.fill = GridBagConstraints.HORIZONTAL;
        fc.anchor = GridBagConstraints.WEST;
        fc.insets = new Insets(0, 0, 4, 0);
        form.add(lblUser, fc);

        fc.gridy = 1;
        fc.insets = new Insets(0, 0, 16, 0);
        form.add(txtUsername, fc);

        fc.gridy = 2;
        fc.insets = new Insets(0, 0, 4, 0);
        form.add(lblPass, fc);

        fc.gridy = 3;
        fc.insets = new Insets(0, 0, 0, 0);
        form.add(txtPassword, fc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        JButton btnLogin = UIStyles.createPrimaryButton("Ingresar");
        btnLogin.addActionListener(e -> performLogin());

        JButton btnClear = UIStyles.createSecondaryButton("Limpiar");
        btnClear.addActionListener(e -> clearFields());

        GridBagConstraints bc = new GridBagConstraints();
        bc.gridx = 0; bc.gridy = 0;
        bc.fill = GridBagConstraints.HORIZONTAL;
        bc.insets = new Insets(0, 0, 8, 0);
        buttonPanel.add(btnLogin, bc);

        bc.gridy = 1;
        bc.insets = new Insets(0, 0, 0, 0);
        buttonPanel.add(btnClear, bc);

        JPanel versionPanel = new JPanel(new BorderLayout());
        versionPanel.setOpaque(false);
        JLabel version = new JLabel("v1.0.0", JLabel.CENTER);
        version.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        version.setForeground(UIStyles.TEXT_MUTED);
        versionPanel.add(version, BorderLayout.CENTER);

        inner.add(header, BorderLayout.NORTH);
        inner.add(form, BorderLayout.CENTER);
        inner.add(buttonPanel, BorderLayout.SOUTH);

        card.add(accentBar, BorderLayout.NORTH);
        card.add(inner, BorderLayout.CENTER);

        root.add(card);
        setContentPane(root);

        getRootPane().setDefaultButton(btnLogin);
        txtPassword.addActionListener(e -> performLogin());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                txtUsername.requestFocusInWindow();
            }
        });
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Complete todos los campos para iniciar sesión.",
                    "Campos requeridos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = userServices.login(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos.",
                    "Error de acceso",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtPassword.requestFocusInWindow();
            return;
        }

        HomeView home = new HomeView(user);
        home.setVisible(true);
        dispose();
    }

    private void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtUsername.requestFocusInWindow();
    }
}
