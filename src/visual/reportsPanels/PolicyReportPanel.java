package visual.reportsPanels;

import reports.ExpiredPolicyReport;
import reports.PolicyReport;
import services.ReportsServices;
import utils.Options;
import visual.UIStyles;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PolicyReportPanel extends ParentReportPanel {
    private Dimension screenSize;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private List<PolicyReport> policyReports;

    public PolicyReportPanel() {
       screenSize = Options.getOptions().getScreenSize();
        setVisible(false);
        setBounds(0, 0, (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.795));
        setLayout(null);
        setBackground(UIStyles.BG_LIGHT);


        tableModel = new DefaultTableModel(
                new String[]{"Insurance Type", "Policy Number", "Client Name", "Start Date", "End Date", "Monthly Premium",  "Policy Status", "Insured Amount"}, 0
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
        policyReports = new ReportsServices().getPoliciesReport();

        tableModel.setRowCount(0);
        for (PolicyReport c : policyReports) {
            tableModel.addRow(new Object[]{
                    c.getInsuranceType(), c.getPolicyNumber(), c.getClientName(),
                    c.getStartDate(), c.getEndDate(), c.getMonthlyPremium(),
                    c.getPolicyStatus(), c.getInsuredAmount()
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
