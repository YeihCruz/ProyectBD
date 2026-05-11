package visual.panels;

import models.User;
import visual.UIStyles;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

public class WelcomePanel extends JPanel {

    private static final Color[][] METRIC_COLORS = {
        {UIStyles.CARD_BLUE, new Color(235, 245, 255)},
        {UIStyles.CARD_GREEN, new Color(235, 250, 240)},
        {UIStyles.CARD_ORANGE, new Color(255, 248, 235)},
        {UIStyles.CARD_PURPLE, new Color(245, 235, 255)},
        {UIStyles.CARD_RED, new Color(255, 235, 235)},
        {UIStyles.CARD_TEAL, new Color(230, 248, 248)},
        {UIStyles.CARD_BLUE, new Color(235, 245, 255)},
        {UIStyles.CARD_GREEN, new Color(235, 250, 240)},
    };

    public WelcomePanel(User user) {
        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());

        JPanel wrapper = new JPanel(new BorderLayout(0, 25));
        wrapper.setBackground(UIStyles.BG_LIGHT);
        wrapper.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        wrapper.add(createHeader(user), BorderLayout.NORTH);
        wrapper.add(createMetricsSection(), BorderLayout.CENTER);
        wrapper.add(createModulesSection(), BorderLayout.SOUTH);

        add(new JScrollPane(wrapper, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
    }

    private JPanel createHeader(User user) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        JLabel icon = new JLabel("\uD83D\uDC4B");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        panel.add(icon, c);

        c.gridy = 1;
        c.insets = new Insets(4, 0, 2, 0);
        JLabel welcome = new JLabel("Bienvenido, " + user.getFullName());
        welcome.setFont(UIStyles.FONT_TITLE);
        welcome.setForeground(UIStyles.TEXT_PRIMARY);
        panel.add(welcome, c);

        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        JLabel subtitle = new JLabel("Panel de control del Sistema de Seguros");
        subtitle.setFont(UIStyles.FONT_SUBTITLE);
        subtitle.setForeground(UIStyles.TEXT_SECONDARY);
        panel.add(subtitle, c);

        return panel;
    }

    private JPanel createMetricsSection() {
        JPanel section = new JPanel(new BorderLayout(0, 14));
        section.setOpaque(false);

        JLabel sectionTitle = new JLabel("Resumen general");
        sectionTitle.setFont(UIStyles.FONT_SECTION);
        sectionTitle.setForeground(UIStyles.TEXT_PRIMARY);
        section.add(sectionTitle, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 4, 14, 14));
        grid.setOpaque(false);

        String[][] metrics = {
            {"Usuarios activos", "—"},
            {"P\u00F3lizas vigentes", "—"},
            {"Reclamos abiertos", "—"},
            {"Clientes registrados", "—"},
            {"Siniestros reportados", "—"},
            {"Agencias activas", "—"},
            {"Tasa de resoluci\u00F3n", "—"},
            {"Ingresos del mes", "—"},
        };

        for (int i = 0; i < metrics.length; i++) {
            Color accent = METRIC_COLORS[i][0];
            Color bg = METRIC_COLORS[i][1];
            grid.add(createMetricCard(metrics[i][0], metrics[i][1], accent, bg));
        }

        section.add(grid, BorderLayout.CENTER);
        return section;
    }

    private JPanel createMetricCard(String label, String value, Color accent, Color bgLight) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIStyles.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyles.BORDER, 1),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(3, 0, 0, 0, accent),
                        BorderFactory.createEmptyBorder(18, 20, 18, 20))));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        JLabel valLabel = new JLabel(value);
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valLabel.setForeground(accent);
        card.add(valLabel, c);

        c.gridy = 1;
        c.insets = new Insets(6, 0, 0, 0);
        JLabel descLabel = new JLabel(label);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(UIStyles.TEXT_SECONDARY);
        card.add(descLabel, c);

        return card;
    }

    private JPanel createModulesSection() {
        JPanel section = new JPanel(new BorderLayout(0, 14));
        section.setOpaque(false);

        JLabel sectionTitle = new JLabel("M\u00F3dulos del sistema");
        sectionTitle.setFont(UIStyles.FONT_SECTION);
        sectionTitle.setForeground(UIStyles.TEXT_PRIMARY);
        section.add(sectionTitle, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 4, 14, 14));
        grid.setOpaque(false);

        String[][] modules = {
            {"\uD83D\uDC65", "Usuarios", "Gesti\u00F3n de usuarios del sistema"},
            {"\uD83D\uDCCB", "Clientes", "Registro y gesti\u00F3n de clientes"},
            {"\uD83D\uDCC4", "P\u00F3lizas", "Administraci\u00F3n de p\u00F3lizas"},
            {"\uD83D\uDCE9", "Reclamos", "Gesti\u00F3n de reclamos"},
            {"\u26A0\uFE0F", "Siniestros", "Control de siniestros"},
            {"\uD83C\uDFE2", "Agencias", "Red de agencias"},
            {"\uD83D\uDCCA", "Reportes", "Informes del sistema"},
            {"\u2699\uFE0F", "Configuraci\u00F3n", "Ajustes del sistema"},
        };

        for (String[] m : modules) {
            grid.add(createModuleCard(m[0], m[1], m[2]));
        }

        section.add(grid, BorderLayout.CENTER);
        return section;
    }

    private JPanel createModuleCard(String icon, String title, String desc) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIStyles.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyles.BORDER, 1),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        card.add(iconLabel, c);

        c.gridy = 1;
        c.insets = new Insets(8, 0, 2, 0);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(UIStyles.TEXT_PRIMARY);
        card.add(titleLabel, c);

        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descLabel.setForeground(UIStyles.TEXT_SECONDARY);
        card.add(descLabel, c);

        return card;
    }
}
