package visual;

import models.User;
import visual.panels.ClientsPanel;
import visual.panels.ClaimsPanel;
import visual.panels.IncidentsPanel;
import visual.panels.PoliciesPanel;
import visual.panels.ReportsPanel;
import visual.panels.UsersPanel;
import visual.panels.WelcomePanel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeView extends JFrame {

    private final User user;
    private JLabel headerTitle;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton btnLogout;
    private JButton selectedButton;
    private Dimension screenSize;
    public HomeView(User user) {

        this.user = user;
        SessionManager.login(user);

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setBounds(0, 0, screenSize.width, screenSize.height);
        setLayout(null);

        add(createMainPanel());

        /*
        JLabel brandIcon = new JLabel("\uD83D\uDEE1\uFE0F", SwingConstants.CENTER);
        brandIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        bc.insets = new Insets(22, 0, 2, 0);
        brandPanel.add(brandIcon, bc);



        btn.addActionListener(e -> showPanel(panelId, label, btn));

        */

    }

    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(UIStyles.FONT_SIDEBAR);
        btn.setForeground(UIStyles.SIDEBAR_TEXT);
        btn.setBackground(UIStyles.SIDEBAR_BG);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, UIStyles.SIDEBAR_BG),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (btn != selectedButton) btn.setBackground(UIStyles.SIDEBAR_HOVER);
            }
            public void mouseExited(MouseEvent e) {
                if (btn != selectedButton) btn.setBackground(UIStyles.SIDEBAR_BG);
            }
        });
        return btn;
    }

    private void crearLogOut(){

        btnLogout = createSidebarButton("\uD83D\uDEAA  Cerrar Sesi\u00F3n");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnLogout.setForeground(new Color(200, 120, 120));
        btnLogout.addActionListener(e -> logout());
        btnLogout.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnLogout.setBackground(UIStyles.SIDEBAR_HOVER);
            }
            public void mouseExited(MouseEvent e) {
                btnLogout.setBackground(UIStyles.SIDEBAR_BG);
            }
        });

    }
    private JPanel createMainPanel() {

        JPanel main = new JPanel(null);
        main.setBounds(0, 0, screenSize.width, screenSize.height);
        main.setBackground(Color.pink);

        JPanel header = createHeader();
        main.add(header);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UIStyles.BG_LIGHT);
        contentPanel.setBounds(0, (int) (screenSize.height*0.061), screenSize.width, (int) (screenSize.height*0.94));


        contentPanel.add(new WelcomePanel(user), "welcome");
        contentPanel.add(new UsersPanel(), "users");
        contentPanel.add(new ClientsPanel(), "clients");
        contentPanel.add(new PoliciesPanel(), "policies");
        contentPanel.add(new ClaimsPanel(), "claims");
        contentPanel.add(new IncidentsPanel(), "incidents");
        contentPanel.add(new ReportsPanel(), "reports");


        main.add(contentPanel);

        return main;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(null);

        header.setBackground(UIStyles.CARD_BG);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIStyles.BORDER),
                BorderFactory.createEmptyBorder(12, 28, 12, 28)));

        crearLogOut();
        btnLogout.setBounds((int) (screenSize.width*0.86), (int) (screenSize.height*0.01), (int) (screenSize.width*0.12), (int) (screenSize.height*0.04));
        header.add(btnLogout);

        header.setBounds(0, 0, screenSize.width, (int) (screenSize.height*0.06));

        headerTitle = new JLabel("Inicio");
        headerTitle.setFont(UIStyles.FONT_HEADER);
        headerTitle.setForeground(UIStyles.TEXT_PRIMARY);
        headerTitle.setBounds((int) (screenSize.width*0.017), (int) (screenSize.height*0.01), (int) (screenSize.width*0.05), (int) (screenSize.height*0.04));
        header.add(headerTitle);

        JPanel userInfo = new JPanel(null);
        userInfo.setBounds((int) (screenSize.width*0.7), (int) (screenSize.height*0.008), (int) (screenSize.width*0.15), (int) (screenSize.height*0.04));
        userInfo.setOpaque(false);

        JLabel avatar = new JLabel("\uD83D\uDC64");
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        avatar.setBounds((int) (screenSize.width*0.001), (int) (screenSize.height*0.004), (int) (screenSize.width*0.05), (int) (screenSize.height*0.05));
        userInfo.add(avatar);

        JLabel userName = new JLabel(user.getFullName());
        userName.setFont(UIStyles.FONT_BODY);
        userName.setForeground(UIStyles.TEXT_PRIMARY);
        userName.setHorizontalAlignment(SwingConstants.LEFT);
        userName.setBounds((int) (screenSize.width*0.06), (int) (screenSize.height*0.001), (int) (screenSize.width*0.07), (int) (screenSize.height*0.05));
        userInfo.add(userName);

        JLabel roleLabel = new JLabel(user.getUsername());
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        roleLabel.setForeground(UIStyles.TEXT_SECONDARY);
        roleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
        roleLabel.setBounds((int) (screenSize.width*0.023), (int) (screenSize.height*0.00001), (int) (screenSize.width*0.05), (int) (screenSize.height*0.05));
        userInfo.add(roleLabel);

        header.add(userInfo);

        return header;
    }

    private void showPanel(String panelId, String panelLabel, JButton btn) {
        if (selectedButton != null) {
            selectedButton.setBackground(UIStyles.SIDEBAR_BG);
            selectedButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 3, 0, 0, UIStyles.SIDEBAR_BG),
                    BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        }

        btn.setBackground(UIStyles.SIDEBAR_SELECTED);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, UIStyles.PRIMARY),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        selectedButton = btn;

        String id = "welcome".equals(panelId) ? "welcome" : panelId;
        cardLayout.show(contentPanel, id);

        headerTitle.setText(panelLabel);
        setTitle("Sistema de Seguros - " + panelLabel);
    }

    private void logout() {
        SessionManager.logout();
        LoginView login = new LoginView();
        login.setVisible(true);
        dispose();
    }
}
