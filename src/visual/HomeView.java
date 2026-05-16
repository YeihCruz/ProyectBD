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

    private static final String[][] NAV_ITEMS = {
        {"\uD83C\uDFE0", "Inicio", "welcome"},
        {"\uD83D\uDC65", "Usuarios", "users"},
        {"\uD83D\uDCCB", "Clientes", "clients"},
        {"\uD83D\uDCC4", "P\u00F3lizas", "policies"},
        {"\uD83D\uDCE9", "Reclamos", "claims"},
        {"\u26A0\uFE0F", "Siniestros", "incidents"},
        {"\uD83D\uDCCA", "Reportes", "reports"},
    };

    private final User user;
    private JLabel headerTitle;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton selectedButton;
    private Dimension screenSize;
    public HomeView(User user) {
        this.user = user;
        SessionManager.login(user);

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setBounds(0, 0, screenSize.width, screenSize.height);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIStyles.BG_LIGHT);

        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainPanel(), BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(UIStyles.SIDEBAR_BG);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;

        JPanel brandPanel = new JPanel(new GridBagLayout());
        brandPanel.setBackground(UIStyles.SIDEBAR_BRAND_BG);
        GridBagConstraints bc = new GridBagConstraints();
        bc.gridx = 0; bc.gridy = 0;

        JLabel brandIcon = new JLabel("\uD83D\uDEE1\uFE0F", SwingConstants.CENTER);
        brandIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        bc.insets = new Insets(22, 0, 2, 0);
        brandPanel.add(brandIcon, bc);

        bc.gridy = 1;
        bc.insets = new Insets(0, 0, 0, 0);
        JLabel brand = new JLabel("SEGUROS", SwingConstants.CENTER);
        brand.setFont(new Font("Segoe UI", Font.BOLD, 15));
        brand.setForeground(Color.WHITE);
        brandPanel.add(brand, bc);

        bc.gridy = 2;
        bc.insets = new Insets(0, 0, 22, 0);
        JLabel brandSub = new JLabel("Sistema de Gesti\u00F3n", SwingConstants.CENTER);
        brandSub.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        brandSub.setForeground(new Color(140, 155, 180));
        brandPanel.add(brandSub, bc);

        c.gridy = 0;
        sidebar.add(brandPanel, c);

        JPanel navPanel = new JPanel(new GridBagLayout());
        navPanel.setOpaque(false);
        GridBagConstraints nc = new GridBagConstraints();
        nc.gridx = 0;
        nc.fill = GridBagConstraints.HORIZONTAL;
        nc.insets = new Insets(2, 12, 2, 12);

        for (int i = 0; i < NAV_ITEMS.length; i++) {
            String icon = NAV_ITEMS[i][0];
            String label = NAV_ITEMS[i][1];
            String panelId = NAV_ITEMS[i][2];
            JButton btn = createSidebarButton(icon + "  " + label);
            btn.addActionListener(e -> showPanel(panelId, label, btn));
            nc.gridy = i;
            navPanel.add(btn, nc);
        }

        c.gridy = 1;
        c.weighty = 1;
        sidebar.add(navPanel, c);

        JPanel dividerPanel = new JPanel(new BorderLayout());
        dividerPanel.setOpaque(false);
        dividerPanel.setBorder(BorderFactory.createEmptyBorder(4, 16, 4, 16));
        JPanel divider = new JPanel();
        divider.setBackground(UIStyles.SIDEBAR_DIVIDER);
        divider.setPreferredSize(new Dimension(1, 1));
        dividerPanel.add(divider, BorderLayout.CENTER);

        c.gridy = 2;
        c.weighty = 0;
        sidebar.add(dividerPanel, c);

        JButton btnLogout = createSidebarButton("\uD83D\uDEAA  Cerrar Sesi\u00F3n");
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

        JPanel logoutPanel = new JPanel(new BorderLayout());
        logoutPanel.setOpaque(false);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(2, 12, 6, 12));
        logoutPanel.add(btnLogout, BorderLayout.CENTER);

        c.gridy = 3;
        sidebar.add(logoutPanel, c);

        JPanel userCard = createSidebarUserCard();
        c.gridy = 4;
        sidebar.add(userCard, c);

        return sidebar;
    }

    private JPanel createSidebarUserCard() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIStyles.SIDEBAR_BRAND_BG);
        card.setBorder(BorderFactory.createEmptyBorder(12, 16, 14, 16));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.gridheight = 2;
        c.insets = new Insets(0, 0, 0, 10);

        JLabel avatar = new JLabel("\uD83D\uDC64");
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        card.add(avatar, c);

        c.gridx = 1; c.gridy = 0;
        c.gridheight = 1;
        c.insets = new Insets(0, 0, 1, 0);
        JLabel name = new JLabel(user.getFullName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 12));
        name.setForeground(new Color(220, 225, 235));
        card.add(name, c);

        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        JLabel role = new JLabel(user.getUsername());
        role.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        role.setForeground(new Color(140, 155, 180));
        card.add(role, c);

        return card;
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

    private JPanel createMainPanel() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(UIStyles.BG_LIGHT);

        JPanel header = createHeader();
        main.add(header, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UIStyles.BG_LIGHT);

        contentPanel.add(new WelcomePanel(user), "welcome");
        contentPanel.add(new UsersPanel(), "users");
        contentPanel.add(new ClientsPanel(), "clients");
        contentPanel.add(new PoliciesPanel(), "policies");
        contentPanel.add(new ClaimsPanel(), "claims");
        contentPanel.add(new IncidentsPanel(), "incidents");
        contentPanel.add(new ReportsPanel(), "reports");

        main.add(contentPanel, BorderLayout.CENTER);

        return main;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIStyles.CARD_BG);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIStyles.BORDER),
                BorderFactory.createEmptyBorder(12, 28, 12, 28)));

        headerTitle = new JLabel("Inicio");
        headerTitle.setFont(UIStyles.FONT_HEADER);
        headerTitle.setForeground(UIStyles.TEXT_PRIMARY);
        header.add(headerTitle, BorderLayout.WEST);

        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        userInfo.setOpaque(false);

        JLabel avatar = new JLabel("\uD83D\uDC64");
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        userInfo.add(avatar);

        JLabel userName = new JLabel(user.getFullName());
        userName.setFont(UIStyles.FONT_BODY);
        userName.setForeground(UIStyles.TEXT_PRIMARY);
        userInfo.add(userName);

        JLabel roleLabel = new JLabel(user.getUsername());
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roleLabel.setForeground(UIStyles.TEXT_SECONDARY);
        roleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
        userInfo.add(roleLabel);

        JPanel statusDot = new JPanel();
        statusDot.setBackground(UIStyles.CARD_GREEN);
        statusDot.setPreferredSize(new Dimension(8, 8));
        statusDot.setMinimumSize(new Dimension(8, 8));
        statusDot.setMaximumSize(new Dimension(8, 8));
        userInfo.add(statusDot);

        header.add(userInfo, BorderLayout.EAST);

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
