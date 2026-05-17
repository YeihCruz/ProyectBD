package visual;

import models.User;
import services.UserServices;
import visual.panels.MessagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {

    private static final Color ACCENT_BAR = new Color(30, 100, 200);
    private static final Color LOGIN_BG = new Color(235, 238, 245);
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final UserServices userServices;

    public LoginView() {
        userServices = new UserServices();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setBounds((int) (screenSize.width * 0.36), (int) (screenSize.height * 0.21), (int) (screenSize.width * 0.28), (int) (screenSize.height * 0.58));
        setResizable(false);
        setBackground(LOGIN_BG);
        setLayout(null);

        JPanel card = new JPanel(null);
        card.setBackground(UIStyles.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

        card.setBounds(0, 0, (int) (screenSize.width * 0.28), (int) (screenSize.height * 0.58));

        JPanel accentBar = new JPanel();
        accentBar.setBackground(ACCENT_BAR);
        accentBar.setBounds((int) (screenSize.width * 0.001), (int) (screenSize.height * 0.0015), (int) (screenSize.width * 0.278), (int) (screenSize.height * 0.006));

        JLabel brandIcon = new JLabel("\uD83D\uDEE1\uFE0F");
        brandIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, (int) (screenSize.width * 0.06)));
        brandIcon.setBounds((int) (screenSize.width * 0.1), (int) (screenSize.height * 0.04), (int) (screenSize.width * 0.08), (int) (screenSize.height * 0.15));
        card.add(brandIcon);

        JLabel title = new JLabel("Sistema de Seguros");
        title.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width * 0.016)));
        title.setForeground(UIStyles.TEXT_PRIMARY);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds((int) (screenSize.width * 0.07), (int) (screenSize.height * 0.145), (int) (screenSize.width * 0.14), (int) (screenSize.height * 0.06));
        card.add(title);

        JLabel subtitle = new JLabel("Ingrese sus credenciales para acceder");
        subtitle.setFont(UIStyles.FONT_SUBTITLE);
        subtitle.setForeground(UIStyles.TEXT_SECONDARY);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setBounds((int) (screenSize.width * 0.05), (int) (screenSize.height * 0.185), (int) (screenSize.width * 0.18), (int) (screenSize.height * 0.06));
        card.add(subtitle);

        txtUsername = new JTextField(18);
        txtPassword = new JPasswordField(18);
        UIStyles.styleField(txtUsername);
        UIStyles.styleField(txtPassword);

        txtUsername.setBounds((int) (screenSize.width * 0.025), (int) (screenSize.height * 0.295), (int) (screenSize.width * 0.23), (int) (screenSize.height * 0.05));
        card.add(txtUsername);

        txtPassword.setBounds((int) (screenSize.width * 0.025), (int) (screenSize.height * 0.39), (int) (screenSize.width * 0.23), (int) (screenSize.height * 0.05));
        card.add(txtPassword);

        JLabel lblUser = new JLabel("Usuario");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width * 0.012)));
        lblUser.setHorizontalAlignment(SwingConstants.CENTER);
        lblUser.setBounds((int) (screenSize.width * 0.025), (int) (screenSize.height * 0.255), (int) (screenSize.width * 0.23), (int) (screenSize.height * 0.04));
        card.add(lblUser);

        JLabel lblPass = UIStyles.createFieldLabel("Contraseña");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width * 0.012)));
        lblPass.setHorizontalAlignment(SwingConstants.CENTER);
        lblPass.setBounds((int) (screenSize.width * 0.025), (int) (screenSize.height * 0.35), (int) (screenSize.width * 0.23), (int) (screenSize.height * 0.04));
        card.add(lblPass);

        JButton btnLogin = UIStyles.createPrimaryButton("Ingresar");
        btnLogin.addActionListener(e -> performLogin());
        btnLogin.setBounds((int) (screenSize.width * 0.1), (int) (screenSize.height * 0.46), (int) (screenSize.width * 0.08), (int) (screenSize.height * 0.04));
        card.add(btnLogin);


        JButton btnClear = UIStyles.createSecondaryButton("Limpiar");
        btnClear.addActionListener(e -> clearFields());
        btnClear.setBounds((int) (screenSize.width * 0.1), (int) (screenSize.height * 0.51), (int) (screenSize.width * 0.08), (int) (screenSize.height * 0.04));
        card.add(btnClear);

        JButton close = new JButton("x");
        close.setBounds((int) (screenSize.width * 0.25), 0, (int) (screenSize.width * 0.036), (int) (screenSize.height * 0.032));
        close.setForeground(Color.black);
        close.setFocusPainted(false);
        close.setBorderPainted(false);
        close.setContentAreaFilled(false);
        close.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width * 0.017)));
        close.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                close.setForeground(Color.red);
            }

            public void mouseExited(MouseEvent evt) {
                close.setForeground(Color.black);
            }
        });
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });
        card.add(close);

        JButton reduce = new JButton("-");
        reduce.setBounds((int) (screenSize.width * 0.23), 0, (int) (screenSize.width * 0.036), (int) (screenSize.height * 0.032));
        reduce.setForeground(Color.black);
        reduce.setHorizontalAlignment(SwingConstants.CENTER);
        reduce.setFocusPainted(false);
        reduce.setBorderPainted(false);
        reduce.setContentAreaFilled(false);
        reduce.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width * 0.017)));
        reduce.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                reduce.setForeground(Color.red);
            }

            public void mouseExited(MouseEvent evt) {
                reduce.setForeground(Color.black);
            }
        });
        reduce.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setExtendedState(ICONIFIED);
            }
        });
        card.add(reduce);

        card.add(accentBar);
        add(card);

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
            MessagePanel mess = new MessagePanel(this, true, "Complete todos los campos necesarios para iniciar sesion");
            mess.setVisible(true);
            return;
        }

        User user = userServices.login(username, password);

        if (user == null) {
            MessagePanel mess = new MessagePanel(this, true, "Usuario o contraseña incorrectos");
            mess.setVisible(true);
            txtPassword.setText("");
            txtPassword.requestFocusInWindow();
            return;
        }

        MessagePanel complete = new MessagePanel(this, true, "Ha iniciado sesion con exito");
        HomeView home = new HomeView(user);
        complete.setVisible(true);
        home.setVisible(true);
        dispose();
    }

    private void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtUsername.requestFocusInWindow();
    }
}
