package visual.panels;

import models.User;
import visual.UIStyles;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.SubmissionPublisher;

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
    private Dimension screenSize;
    private JPanel metricas;

    public WelcomePanel(User user) {

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBackground(UIStyles.BG_LIGHT);
        setLayout(null);
        setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));
        setBounds(0, 0, screenSize.width, (int) (screenSize.height*0.94));

        add(createHeader(user));
        metricas = createMetricsSection();
        add(metricas);
        add(createModulesSection());

    }

    private JPanel createHeader(User user) {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);
        panel.setBounds((int) (screenSize.width*0.35), 0, (int) (screenSize.width*0.3), (int) (screenSize.height*0.17));

        JLabel icon = new JLabel("\uD83D\uDC4B");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, (int) (screenSize.width*0.04)));
        icon.setBounds((int) (screenSize.width*0.11), (int) (screenSize.height*0.02), (int) (screenSize.width*0.08), (int) (screenSize.height*0.09));
        panel.add(icon);

        JLabel welcome = new JLabel("Bienvenido, " + user.getFullName());
        welcome.setFont(UIStyles.FONT_TITLE);
        welcome.setForeground(UIStyles.TEXT_PRIMARY);
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBounds((int) (screenSize.width*0.01), (int) (screenSize.height*0.1), (int) (screenSize.width*0.28), (int) (screenSize.height*0.03));
        panel.add(welcome);

        JLabel subtitle = new JLabel("Panel de control del Sistema de Seguros");
        subtitle.setFont(UIStyles.FONT_SUBTITLE);
        subtitle.setForeground(UIStyles.TEXT_SECONDARY);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setBounds((int) (screenSize.width*0.01), (int) (screenSize.height*0.14), (int) (screenSize.width*0.28), (int) (screenSize.height*0.03));
        panel.add(subtitle);

        return panel;
    }

    private JPanel createMetricsSection() {
        JPanel section = new JPanel(null);
        section.setOpaque(false);
        section.setBounds((int) (screenSize.width*0.03), (int) (screenSize.height*0.16), (int) (screenSize.width*0.94), (int) (screenSize.height*0.4));

        JLabel sectionTitle = new JLabel("Resumen general");
        sectionTitle.setFont(UIStyles.FONT_SECTION);
        sectionTitle.setForeground(UIStyles.TEXT_PRIMARY);
        sectionTitle.setHorizontalAlignment(SwingConstants.CENTER);
        sectionTitle.setBounds((int) (screenSize.width*0.37), (int) (screenSize.height*0.0025), (int) (screenSize.width*0.2), (int) (screenSize.height*0.06));
        section.add(sectionTitle);

        JPanel grid = new JPanel(null);
        grid.setBounds(0, (int) (screenSize.height*0.05), (int) (screenSize.width*0.94), (int) (screenSize.height*0.33));
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

        int j=0;int k=0;
        for (int i = 0; i < metrics.length; i++) {
            Color accent = METRIC_COLORS[i][0];
            Color bg = METRIC_COLORS[i][1];
            grid.add(createMetricCard(metrics[i][0], metrics[i][1], accent, bg, k, j ));
            k++;
            if(k>3){
                j++; k=0;
            }
        }

        section.add(grid);
        return section;
    }

    private JPanel createMetricCard(String label, String value, Color accent, Color bgLight, int k, int j) {

        JPanel card = new JPanel(null);
        card.setBackground(UIStyles.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyles.BORDER, 1),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(3, 0, 0, 0, accent),
                        BorderFactory.createEmptyBorder(18, 20, 18, 20))));
        card.setBounds((int) (screenSize.width*0.2375)*k, (int) (screenSize.height*0.174)*j, (int) (screenSize.width*0.2275), (int) (screenSize.height*0.156));

        JLabel valLabel = new JLabel(value);
        valLabel.setBounds((int) (screenSize.width*0.085), (int) (screenSize.height*0.04), (int) (screenSize.width*0.0575), (int) (screenSize.height*0.06));
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.016)));
        valLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valLabel.setForeground(accent);
        card.add(valLabel);

        JLabel descLabel = new JLabel(label);
        descLabel.setBounds((int) (screenSize.width*0.065), (int) (screenSize.height*0.08), (int) (screenSize.width*0.0975), (int) (screenSize.height*0.06));
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));
        descLabel.setForeground(UIStyles.TEXT_SECONDARY);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(descLabel);

        return card;
    }

    private JPanel createModulesSection() {
        JPanel section = new JPanel(null);
        section.setBounds((int) (screenSize.width*0.03), (int) (screenSize.height*0.55), (int) (screenSize.width*0.94), (int) (screenSize.height*0.4));
        section.setOpaque(false);

        JLabel sectionTitle = new JLabel("M\u00F3dulos del sistema");
        sectionTitle.setFont(UIStyles.FONT_SECTION);
        sectionTitle.setForeground(UIStyles.TEXT_PRIMARY);
        sectionTitle.setHorizontalAlignment(SwingConstants.CENTER);
        sectionTitle.setBounds((int) (screenSize.width*0.37), (int) (screenSize.height*-0.0045), (int) (screenSize.width*0.2), (int) (screenSize.height*0.06));
        section.add(sectionTitle);

        JPanel grid = new JPanel(null);
        grid.setBounds(0, (int) (screenSize.height*0.05), (int) (screenSize.width*0.94), (int) (screenSize.height*0.335));
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

        int i =0, j=0;
        for (String[] m : modules) {
            JPanel carta = createModuleCard(m[0], m[1], m[2], i, j);
            i++;
            if(i>3){
                i=0; j++;}
            grid.add(carta);
        }

        section.add(grid);
        return section;
    }

    private JPanel createModuleCard(String icon, String title, String desc, int largo, int ancho) {
        JPanel card = new JPanel(null);
        card.setBackground(UIStyles.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyles.BORDER, 1),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)));

        card.setBounds((int) (screenSize.width*0.2375)*largo, (int) (screenSize.height*0.174)*ancho, (int) (screenSize.width*0.2275), (int) (screenSize.height*0.156));


        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, (int) (screenSize.width*0.0225)));
        iconLabel.setBounds((int) (screenSize.width*0.01), (int) (screenSize.height*0.04), (int) (screenSize.width*0.05), (int) (screenSize.height*0.09));
        card.add(iconLabel);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.011)));
        titleLabel.setForeground(UIStyles.TEXT_PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds((int) (screenSize.width*0.05), (int) (screenSize.height*0.04), (int) (screenSize.width*0.15), (int) (screenSize.height*0.04));
        card.add(titleLabel);

        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descLabel.setBounds((int) (screenSize.width*0.05), (int) (screenSize.height*0.08), (int) (screenSize.width*0.15), (int) (screenSize.height*0.04));
        descLabel.setForeground(UIStyles.TEXT_SECONDARY);
        card.add(descLabel);

        return card;
    }
}
