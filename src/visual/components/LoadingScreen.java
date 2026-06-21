package visual.components;
import utils.Options;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingScreen extends JDialog {
    private JLabel loadingLabel;

    public LoadingScreen(JFrame parent) {
        super(parent, "Loading", true); // modal
        setUndecorated(true); // sin bordes
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setBackground(new Color(0, 0, 0, 0));


        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        mainPanel.setBackground(new Color(0, 0, 0, 0));

        // Panel para el gif de carga
        JPanel loadingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loadingPanel.setBackground(Color.WHITE);

        BufferedImage imagen = null;

        try {
            imagen = ImageIO.read(new File("src/visual/components/loader.gif"));
             } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageIcon icono = new ImageIcon(imagen.getScaledInstance((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.2), (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.2), Image.SCALE_SMOOTH));

        JLabel iconLabel = new JLabel(icono);
        iconLabel.setBackground(new Color( 0, 0, 0, 0));
        loadingPanel.add(iconLabel);

        mainPanel.add(loadingPanel, BorderLayout.CENTER);
        add(mainPanel);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                iconLabel.revalidate();
                iconLabel.repaint();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 100);

    }

    public void setLoadingText(String text) {
        loadingLabel.setText(text);
    }

    public void showLoading() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void hideLoading() {
        SwingUtilities.invokeLater(() -> dispose());
    }
}

