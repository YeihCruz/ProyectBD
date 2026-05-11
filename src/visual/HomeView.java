package visual;

import models.User;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class HomeView extends JFrame {

    public HomeView(User user) {
        setTitle("Inicio - Sistema de Seguros");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 420);
        setMinimumSize(new Dimension(700, 420));
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 250, 252));

        JLabel welcomeLabel = new JLabel("Bienvenido, " + user.getFullName(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        JLabel roleLabel = new JLabel("Sesión iniciada como: " + user.getUsername(), SwingConstants.CENTER);
        roleLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JButton closeButton = new JButton("Cerrar ventana");
        closeButton.addActionListener(e -> dispose());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(welcomeLabel, BorderLayout.CENTER);
        centerPanel.add(roleLabel, BorderLayout.SOUTH);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);

        setContentPane(panel);
    }
}