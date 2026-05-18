package visual.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MessagePanel extends JDialog {

    private Dimension screenSize;
    private JButton ok;
    private JLabel text;
    private String message;

    public MessagePanel(Frame parent, boolean modal, String message) {
        super(parent, modal);
        this.message = message;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        initComponents();
    }

    public void initComponents() {
        ok = new JButton("Ok");
        text = new JLabel("<html><div style= text-align: center; '>" + message + "</div><html>");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(null);

        setBounds((int) (screenSize.width * 0.38), (int) (screenSize.height * 0.4), (int) (screenSize.width * 0.24), (int) (screenSize.height * 0.15));
        text.setBounds((int) (screenSize.width * 0.01), (int) (screenSize.height * 0.02), (int) (screenSize.width * 0.22), (int) (screenSize.height * 0.08));
        ok.setBounds((int) (screenSize.width * 0.095), (int) (screenSize.height* 0.1), (int) (screenSize.width * 0.05), (int) (screenSize.height * 0.05));

        setBackground(new Color(160, 160, 160,240));

        text.setForeground(Color.black);
        text.setFont(new Font("Segoe UI", 1, (int) (screenSize.width * 0.012)));
        text.setHorizontalAlignment(SwingConstants.CENTER);

        getContentPane().add(text);

        ok.setFont(new Font("Segoe UI", 1, (int) (screenSize.width * 0.012)));
        ok.setForeground(Color.BLACK);
        ok.setOpaque(false);
        ok.setBorderPainted(false);
        ok.setContentAreaFilled(false);
        ok.setFocusPainted(false);
        ok.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                yesMouseEntered(evt);
            }

            public void mouseExited(MouseEvent evt) {
                yesMouseExited(evt);
            }
        });
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                yesActionPerformed(evt);
            }
        });
        getContentPane().add(ok);


        InputMap mapaEntrada = ok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap mapaAction = ok.getActionMap();

        KeyStroke tecla = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        mapaEntrada.put(tecla, "ok");
        mapaAction.put("ok", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok.doClick();
            }
        });

    }
    private void yesActionPerformed(ActionEvent evt) {
        dispose();
    }

    private void yesMouseExited(MouseEvent evt) {
        ok.setForeground(new Color(255, 255, 255));
    }

    private void yesMouseEntered(MouseEvent evt) {
        ok.setForeground(Color.red);
    }
}

