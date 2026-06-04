package visual.reportsPanels;

import reports.ClaimStatusSummaryReport;
import services.ReportsServices;
import visual.UIStyles;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClaimStatusSummaryReportPanel extends JPanel {
    private Dimension screenSize;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final List<ClaimStatusSummaryReport> claimStatusSummaryReports;

    public ClaimStatusSummaryReportPanel() {

        claimStatusSummaryReports = new ReportsServices().getClaimStatusSummaryReport();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setVisible(false);
        setBounds(0, 0, (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.795));
        setLayout(null);
        setBackground(UIStyles.BG_LIGHT);

        JLabel title = new JLabel("Resumen de Estados de Reclamos");
        title.setFont(UIStyles.FONT_HEADER);
        title.setForeground(UIStyles.TEXT_PRIMARY);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds((int) (screenSize.width * 0.31), 0, (int) (screenSize.width * 0.3), (int) (screenSize.height * 0.05));
        add(title);

        tableModel = new DefaultTableModel(
                new String[]{"Estado del Reclamo","Total de Reclamos", "Monto Total Reclamado", "Monto Total Compensado"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UIStyles.BORDER, 1));
        scroll.getViewport().setBackground(UIStyles.CARD_BG);
        scroll.setBounds(0, (int) (screenSize.height * 0.05), (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.74));
        add(scroll);
        loadData();

    }

    private void loadData() {
        tableModel.setRowCount(0);
        for (ClaimStatusSummaryReport c : claimStatusSummaryReports) {
            tableModel.addRow(new Object[]{
                    c.getClaimStatus(), c.getTotalClaims(), c.getTotalClaimedAmount(),
                    c.getTotalCompensatedAmount()
            });
        }
    }

    private void styleTable(JTable t) {
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.getTableHeader().setBackground(new Color(240, 242, 245));
        t.getTableHeader().setForeground(UIStyles.TEXT_PRIMARY);
        t.getTableHeader().setBorder(BorderFactory.createLineBorder(UIStyles.BORDER));
        t.setRowHeight(32);
        t.setShowGrid(true);
        t.setGridColor(UIStyles.BORDER_LIGHT);
        t.setSelectionBackground(UIStyles.PRIMARY_LIGHT);
        t.setSelectionForeground(UIStyles.TEXT_PRIMARY);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setIntercellSpacing(new Dimension(10, 0));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
