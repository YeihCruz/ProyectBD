package visual;

import models.User;
import visual.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

public class HomeView extends JFrame {

    private final User user;
    private JLabel headerTitle;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton btnLogout;
    private ArrayList<String> codes;
    private Dimension screenSize;
    private JButton retWelcom;
    private Timer timer;
    private TimerTask task;
    private boolean backIsVisible;
    private boolean changeVisible;
    public HomeView(User user) {

        codes = new ArrayList<>();
        backIsVisible = false;
        changeVisible=false;
        createCodes();
        this.user = user;
        SessionManager.login(user);

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setBounds(0, 0, screenSize.width, screenSize.height);
        setLayout(null);

        add(createMainPanel());


        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                revalidate();
                repaint();
                if(backIsVisible !=changeVisible){
                    activateRetButn();
                }

            }
        };

        timer.scheduleAtFixedRate(task, 0, 10);

        InputMap inputMap = retWelcom.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = retWelcom.getActionMap();

        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0);

        inputMap.put(keyStroke, "activateBtn");
        actionMap.put("activateBtn", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retWelcom.doClick();
            }
        });

        InputMap inputMap1 = btnLogout.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap1 = btnLogout.getActionMap();

        KeyStroke keyStroke1 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        inputMap1.put(keyStroke1, "activateBtnLog");
        actionMap1.put("activateBtnLog", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLogout.doClick();
            }
        });
    }

    private void activateRetButn() {
        if(changeVisible){
            retWelcom.setVisible(true);
            retWelcom.setEnabled(true);
            headerTitle.setBounds((int) (screenSize.width*0.08), (int) (screenSize.height*0.01), (int) (screenSize.width*0.15), (int) (screenSize.height*0.04));

        }else {
            retWelcom.setVisible(false);
            retWelcom.setEnabled(false);
            headerTitle.setBounds((int) (screenSize.width*0.017), (int) (screenSize.height*0.01), (int) (screenSize.width*0.15), (int) (screenSize.height*0.04));

        }
        backIsVisible=changeVisible;

    }

    private void createBtnRet() {
        retWelcom = new JButton();
        retWelcom.addActionListener(e -> showPanel(codes.get(0), "Inicio"));
        retWelcom.setBounds(0, 0, (int) (screenSize.width*0.05), (int) (screenSize.height*0.07));
        retWelcom.setText("\u2190");
        retWelcom.setFont(new Font("Segoe UI Emoji", Font.PLAIN, (int) (screenSize.width*0.024)));
        retWelcom.setContentAreaFilled(false);
        retWelcom.setFocusPainted(false);
        retWelcom.setBorderPainted(false);
        retWelcom.setOpaque(false);
        retWelcom.setEnabled(false);
        retWelcom.setVisible(false);
        retWelcom.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                retWelcom.setForeground(Color.red);
            }
            public void mouseExited(MouseEvent e) {
                retWelcom.setForeground(Color.black);
            }
        });

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
        return btn;
    }

    private void createLogOut(){

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

        JPanel header = createHeader();
        main.add(header);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UIStyles.BG_LIGHT);
        contentPanel.setBounds(0, (int) (screenSize.height*0.061), screenSize.width, (int) (screenSize.height*0.94));


        WelcomePanel welcomePanel = new WelcomePanel(user);
        contentPanel.add(welcomePanel, "welcome");
        contentPanel.add(new UsersPanel(), "users");
        contentPanel.add(new ClientsPanel(), "clients");
        contentPanel.add(new PoliciesPanel(), "policies");
        contentPanel.add(new ClaimsPanel(), "claims");
        contentPanel.add(new IncidentsPanel(), "incidents");
        contentPanel.add(new AgencyPanel(), "agencys");
        contentPanel.add(new ReportsPanel(), "reports");

        addMovement(welcomePanel.getControllers());
        main.add(contentPanel);

        return main;
    }

    private void addMovement(ArrayList<JButton> controllers) {
        for(int i=0; i< controllers.size(); i++ ){
            JButton button = controllers.get(i);
            int finalI = i+1;
            button.addActionListener(e -> showPanel(codes.get(finalI), button.getName()));
        }
    }

    private void createCodes() {
        codes.add("welcome");
        codes.add("users");
        codes.add("clients");
        codes.add("policies");
        codes.add("claims");
        codes.add("incidents");
        codes.add("agencys");
        codes.add("reports");
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(null);

        header.setBackground(UIStyles.CARD_BG);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIStyles.BORDER),
                BorderFactory.createEmptyBorder(12, 28, 12, 28)));

        createLogOut();
        btnLogout.setBounds((int) (screenSize.width*0.86), (int) (screenSize.height*0.01), (int) (screenSize.width*0.12), (int) (screenSize.height*0.04));
        header.add(btnLogout);

        header.setBounds(0, 0, screenSize.width, (int) (screenSize.height*0.06));

        headerTitle = new JLabel("Inicio");
        headerTitle.setFont(UIStyles.FONT_HEADER);
        headerTitle.setForeground(UIStyles.TEXT_PRIMARY);
        headerTitle.setHorizontalAlignment(SwingConstants.LEFT);
        headerTitle.setBounds((int) (screenSize.width*0.017), (int) (screenSize.height*0.01), (int) (screenSize.width*0.15), (int) (screenSize.height*0.04));
        header.add(headerTitle);

        JPanel userInfo = new JPanel(null);
        userInfo.setBounds((int) (screenSize.width*0.7), (int) (screenSize.height*0.008), (int) (screenSize.width*0.15), (int) (screenSize.height*0.04));
        userInfo.setOpaque(false);

        JLabel avatar = new JLabel("\uD83D\uDC64");
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        avatar.setBounds((int) (screenSize.width*0.001), (int) (screenSize.height*0.004), (int) (screenSize.width*0.05), (int) (screenSize.height*0.05));
        userInfo.add(avatar);

        JLabel userName = new JLabel(user.getUsername());
        userName.setFont(UIStyles.FONT_BODY);
        userName.setForeground(UIStyles.TEXT_PRIMARY);
        userName.setHorizontalAlignment(SwingConstants.LEFT);
        userName.setBounds((int) (screenSize.width*0.02), (int) (screenSize.height*0.001), (int) (screenSize.width*0.07), (int) (screenSize.height*0.05));
        userInfo.add(userName);

        createBtnRet();
        header.add(retWelcom);

        header.add(userInfo);

        return header;
    }

    private void showPanel(String panelId, String panelLabel) {
        String id = "welcome".equals(panelId) ? "welcome" : panelId;

        cardLayout.show(contentPanel, id);
        if(id.equals("welcome"))
            changeVisible = false;
        else changeVisible=true;
        headerTitle.setText(panelLabel);

    }

    private void logout() {
        ConfirmationPanel exit = new ConfirmationPanel(this, true);
        exit.setVisible(true);
    }
}
