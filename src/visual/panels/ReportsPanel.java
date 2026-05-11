package visual.panels;

import models.Claim;
import models.Client;
import models.Policy;
import services.ClaimServices;
import services.ClientServices;
import services.PolicyServices;
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
import java.util.List;

public class ReportsPanel extends JPanel {

    public ReportsPanel() {
        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Reportes del Sistema");
        title.setFont(UIStyles.FONT_HEADER);
        title.setForeground(UIStyles.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel metricsGrid = new JPanel(new GridLayout(2, 3, 15, 15));
        metricsGrid.setOpaque(false);

        List<Client> clients = new ClientServices().getAllClients();
        List<Policy> policies = new PolicyServices().getAllPolicies();
        List<Claim> claims = new ClaimServices().getAllClaims();

        long activePolicies = policies.stream().filter(p -> {
            try { return p.getPolicyStatusId() == 1; } catch (Exception e) { return false; }
        }).count();

        long openClaims = claims.stream().filter(c -> {
            try { return c.getClaimStatusId() == 1; } catch (Exception e) { return false; }
        }).count();

        double totalClaimed = claims.stream().mapToDouble(Claim::getClaimedAmount).sum();
        double totalCompensated = claims.stream().mapToDouble(Claim::getCompensatedAmount).sum();

        metricsGrid.add(createMetricCard("Total Clientes", String.valueOf(clients.size()), UIStyles.CARD_BLUE));
        metricsGrid.add(createMetricCard("Total P\u00F3lizas", String.valueOf(policies.size()), UIStyles.CARD_GREEN));
        metricsGrid.add(createMetricCard("Total Reclamos", String.valueOf(claims.size()), UIStyles.CARD_ORANGE));
        metricsGrid.add(createMetricCard("P\u00F3lizas Vigentes", String.valueOf(activePolicies), UIStyles.CARD_TEAL));
        metricsGrid.add(createMetricCard("Reclamos Abiertos", String.valueOf(openClaims), UIStyles.CARD_RED));
        metricsGrid.add(createMetricCard("Monto Total Compensado", String.format("$%.2f", totalCompensated), UIStyles.CARD_PURPLE));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(metricsGrid, BorderLayout.NORTH);

        JPanel summaryCard = UIStyles.createCard();
        summaryCard.setLayout(new GridBagLayout());
        summaryCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyles.BORDER, 1),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 8, 0);

        JLabel summaryTitle = new JLabel("Resumen General");
        summaryTitle.setFont(UIStyles.FONT_SECTION);
        summaryTitle.setForeground(UIStyles.TEXT_PRIMARY);
        summaryCard.add(summaryTitle, c);

        c.gridy = 1; c.insets = new Insets(4, 0, 4, 0);
        summaryCard.add(createSummaryLine("Total reclamado:    ", String.format("$%.2f", totalClaimed)), c);
        c.gridy = 2;
        summaryCard.add(createSummaryLine("Total compensado:   ", String.format("$%.2f", totalCompensated)), c);
        c.gridy = 3;
        summaryCard.add(createSummaryLine("Diferencia:            ", String.format("$%.2f", totalClaimed - totalCompensated)), c);

        wrapper.add(summaryCard, BorderLayout.CENTER);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        add(new JScrollPane(wrapper, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
    }

    private JPanel createMetricCard(String label, String value, Color accent) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIStyles.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyles.BORDER, 1),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(3, 0, 0, 0, accent),
                        BorderFactory.createEmptyBorder(22, 22, 22, 22))));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        JLabel valLabel = new JLabel(value);
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
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

    private JLabel createSummaryLine(String label, String value) {
        JLabel lbl = new JLabel("<html><b>" + label + "</b>" + value + "</html>");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setForeground(UIStyles.TEXT_PRIMARY);
        return lbl;
    }
}
