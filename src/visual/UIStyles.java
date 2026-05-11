package visual;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIStyles {

    public static final Color PRIMARY = new Color(30, 100, 200);
    public static final Color PRIMARY_DARK = new Color(20, 80, 170);
    public static final Color PRIMARY_LIGHT = new Color(220, 235, 255);
    public static final Color BG_LIGHT = new Color(242, 244, 248);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color TEXT_PRIMARY = new Color(30, 30, 35);
    public static final Color TEXT_SECONDARY = new Color(120, 125, 135);
    public static final Color TEXT_MUTED = new Color(170, 175, 185);
    public static final Color BORDER = new Color(225, 228, 235);
    public static final Color BORDER_LIGHT = new Color(238, 240, 245);

    public static final Color SIDEBAR_BG = new Color(22, 30, 45);
    public static final Color SIDEBAR_HOVER = new Color(32, 42, 60);
    public static final Color SIDEBAR_SELECTED = new Color(30, 100, 200);
    public static final Color SIDEBAR_BRAND_BG = new Color(16, 22, 35);
    public static final Color SIDEBAR_TEXT = new Color(195, 200, 210);
    public static final Color SIDEBAR_DIVIDER = new Color(40, 50, 68);

    public static final Color CARD_BLUE = new Color(30, 100, 200);
    public static final Color CARD_GREEN = new Color(40, 160, 80);
    public static final Color CARD_ORANGE = new Color(220, 140, 30);
    public static final Color CARD_RED = new Color(200, 60, 60);
    public static final Color CARD_PURPLE = new Color(130, 70, 190);
    public static final Color CARD_TEAL = new Color(20, 150, 150);

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_SIDEBAR = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 16);

    public static JButton createPrimaryButton(String text) {
        return createButton(text, PRIMARY, PRIMARY_DARK, Color.WHITE, FONT_BUTTON);
    }

    public static JButton createSecondaryButton(String text) {
        JButton btn = createButton(text, CARD_BG, new Color(245, 245, 247), TEXT_SECONDARY,
                new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)));
        return btn;
    }

    private static JButton createButton(String text, Color bg, Color hoverBg, Color fg, Font font) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hoverBg); }
            public void mouseExited(MouseEvent e) { btn.setBackground(bg); }
        });
        return btn;
    }

    public static void styleField(JTextField field) {
        field.setFont(FONT_BODY);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(CARD_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(9, 12, 9, 12)));
        field.setCaretColor(PRIMARY);
    }

    public static JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    public static JPanel createCard() {
        return createCard(null);
    }

    public static JPanel createCard(Color topAccent) {
        JPanel card = new JPanel();
        card.setBackground(CARD_BG);
        if (topAccent != null) {
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BORDER, 1),
                            BorderFactory.createMatteBorder(3, 0, 0, 0, topAccent)),
                    BorderFactory.createEmptyBorder(22, 24, 22, 24)));
        } else {
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER, 1),
                    BorderFactory.createEmptyBorder(22, 24, 22, 24)));
        }
        return card;
    }

    public static JPanel createDivider() {
        JPanel line = new JPanel();
        line.setBackground(SIDEBAR_DIVIDER);
        line.setPreferredSize(new Dimension(1, 1));
        line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return line;
    }

    public static void styleHover(JComponent comp, Color normal, Color hover) {
        comp.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { comp.setBackground(hover); }
            public void mouseExited(MouseEvent e) { comp.setBackground(normal); }
        });
    }
}
