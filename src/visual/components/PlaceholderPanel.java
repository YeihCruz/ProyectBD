package visual.components;

import visual.UIStyles;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PlaceholderPanel extends JPanel {

    public PlaceholderPanel(String icon, String title, String message) {
        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel card = UIStyles.createCard();
        card.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        card.add(iconLabel, c);

        c.gridy = 1;
        c.insets = new java.awt.Insets(12, 0, 6, 0);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIStyles.FONT_TITLE);
        titleLabel.setForeground(UIStyles.TEXT_PRIMARY);
        card.add(titleLabel, c);

        c.gridy = 2;
        c.insets = new java.awt.Insets(0, 0, 0, 0);
        JLabel msgLabel = new JLabel(message);
        msgLabel.setFont(UIStyles.FONT_SUBTITLE);
        msgLabel.setForeground(UIStyles.TEXT_SECONDARY);
        card.add(msgLabel, c);

        add(card, BorderLayout.CENTER);
    }
}
