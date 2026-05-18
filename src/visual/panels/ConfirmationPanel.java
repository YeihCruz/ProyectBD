package visual.panels;

import visual.LoginView;
import visual.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConfirmationPanel extends JDialog {

    private Dimension screenSize;
    private JButton yes;
    private JButton no;
    private JLabel text;
    private Frame parent;
    private JLabel label;

    public ConfirmationPanel(Frame parent, boolean modal) {
        super(parent, modal);
        this.parent= parent;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        initComponents();
    }

    public void initComponents() {
        yes = new JButton("Si");
        no = new JButton("No");
        text = new JLabel("<html>" + "¿Esta seguro de querer salir?" + "</html>");
        label = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(null);

        setBounds((int) (screenSize.width * 0.3), (int) (screenSize.height * 0.3), (int) (screenSize.width * 0.4), (int) (screenSize.height * 0.33));
        text.setBounds((int) (screenSize.width * 0.03), (int) (screenSize.height * 0.04), (int) (screenSize.width * 0.34), (int) (screenSize.height * 0.18));
        yes.setBounds((int) (screenSize.width * 0.07), (int) (screenSize.getHeight() * 0.23), (int) (screenSize.width * 0.05), (int) (screenSize.height * 0.05));
        no.setBounds((int) (screenSize.width * 0.27), (int) (screenSize.getHeight() * 0.23), (int) (screenSize.width * 0.05), (int) (screenSize.height * 0.05));
        label.setBounds(0, 0, (int) (screenSize.width * 0.4), (int) (screenSize.height * 0.33));

        setBackground(new Color(45, 45, 45, 250));


        text.setForeground(Color.white);
        text.setFont(new Font("Segoe UI", 0, (int) (screenSize.width * 0.017)));
        text.setHorizontalAlignment(SwingConstants.CENTER);

        getContentPane().add(text);

        yes.setFont(new Font("Segoe UI", 0, (int) (screenSize.width * 0.017)));
        yes.setForeground(new Color(255, 255, 255));
        yes.setOpaque(false);
        yes.setBorderPainted(false);
        yes.setContentAreaFilled(false);
        yes.setFocusPainted(false);
        yes.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                yesMouseEntered(evt);
            }

            public void mouseExited(MouseEvent evt) {
                yesMouseExited(evt);
            }
        });
        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                yesActionPerformed(evt);
            }
        });
        getContentPane().add(yes);

        no.setFont(new Font("Segoe UI", 0, (int) (screenSize.width * 0.017)));
        no.setForeground(new Color(255, 255, 255));
        no.setOpaque(false);
        no.setBorderPainted(false);
        no.setContentAreaFilled(false);
        no.setFocusPainted(false);

        no.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                noMouseEntered(evt);
            }

            public void mouseExited(MouseEvent evt) {
                noMouseExited(evt);
            }
        });
        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                noActionPerformed(evt);
            }
        });
        getContentPane().add(no);

        InputMap inputMap = no.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = no.getActionMap();

        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        inputMap.put(keyStroke, "activateBut");
        actionMap.put("activateBut", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                no.doClick();
            }
        });

        InputMap inputMap1 = yes.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap1 = yes.getActionMap();

        KeyStroke keyStroke1 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        inputMap1.put(keyStroke1, "activateButYes");
        actionMap1.put("activateButYes", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yes.doClick();
            }
        });
    }
        private void noActionPerformed(ActionEvent evt) {
            dispose();
        }

        private void noMouseExited(MouseEvent evt) {
            no.setForeground(new Color(255, 255, 255));
        }

        private void noMouseEntered(MouseEvent evt) {
            no.setForeground(Color.red);
        }

        private void yesActionPerformed(ActionEvent evt) {
        SessionManager.logout();
        LoginView login = new LoginView();
        login.setVisible(true);
        parent.dispose();
        }

        private void yesMouseExited(MouseEvent evt) {
            yes.setForeground(new Color(255, 255, 255));
        }

        private void yesMouseEntered(MouseEvent evt) {
            yes.setForeground(Color.red);
        }
    }

