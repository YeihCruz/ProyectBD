package visual.reportsPanels;

import reports.ClaimStatusSummaryReport;
import services.ReportsServices;
import utils.Options;
import visual.UIStyles;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClaimStatusSummaryReportPanel extends ParentReportPanel {
    private Dimension screenSize;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private List<ClaimStatusSummaryReport> claimStatusSummaryReports;

    public ClaimStatusSummaryReportPanel() {

        screenSize = Options.getOptions().getScreenSize();
        setVisible(false);
        setBounds(0, 0, (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.795));
        setLayout(null);
        setBackground(UIStyles.BG_LIGHT);

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
        scroll.setBounds(0, 0, (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.795));
        add(scroll);
        loadData();

    }

    public void loadData() {
        claimStatusSummaryReports = new ReportsServices().getClaimStatusSummaryReport();
        tableModel.setRowCount(0);
        for (ClaimStatusSummaryReport c : claimStatusSummaryReports) {
            tableModel.addRow(new Object[]{
                    c.getClaimStatus(), c.getTotalClaims(), c.getTotalClaimedAmount(),
                    c.getTotalCompensatedAmount()
            });
        }
    }

    private void styleTable(JTable t) {
        t.setFont(new Font("Segoe UI", Font.PLAIN, ((int) (screenSize.width * 0.0096))));
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, ((int) (screenSize.width * 0.0088))));
        t.getTableHeader().setBackground(new Color(240, 242, 245));
        t.getTableHeader().setForeground(UIStyles.TEXT_PRIMARY);
        t.getTableHeader().setBorder(BorderFactory.createLineBorder(UIStyles.BORDER));
        t.setRowHeight(((int) (screenSize.width * 0.022)));
        t.setShowGrid(true);
        t.setGridColor(UIStyles.BORDER_LIGHT);
        t.setSelectionBackground(UIStyles.PRIMARY_LIGHT);
        t.setSelectionForeground(UIStyles.TEXT_PRIMARY);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setIntercellSpacing(new Dimension(((int) (screenSize.width * 0.0006)), 0));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
