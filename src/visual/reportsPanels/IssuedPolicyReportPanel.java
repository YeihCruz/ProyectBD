package visual.reportsPanels;

import com.toedter.calendar.JDateChooser;
import models.Client;
import reports.ClientReport;
import reports.IssuedPoliciesReport;
import services.ReportsServices;
import utils.Options;
import visual.UIStyles;
import visual.components.CustomComboBox;
import visual.panels.MessagePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;
import java.sql.Date;

public class IssuedPolicyReportPanel extends JPanel {
    private Dimension screenSize;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private List<IssuedPoliciesReport> issuedPolicyReportPanels;

    public IssuedPolicyReportPanel() {

        screenSize = Options.getOptions().getScreenSize();
        setVisible(false);
        setBounds(0, 0, (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.795));
        setLayout(null);
        setBackground(UIStyles.BORDER);

        JLabel txtFirst = new JLabel("Fecha de Inicio: ");
        txtFirst.setBounds((int) (screenSize.width * 0.055),(int) (screenSize.height * 0.03), (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.04));
        txtFirst.setFont(new Font("Segoe UI", PLAIN, (int) (screenSize.width * 0.011)));
        add(txtFirst);

        JDateChooser first = new JDateChooser(Date.valueOf(LocalDate.now()));
        first.setBounds((int) (screenSize.width * 0.145),(int) (screenSize.height * 0.03), (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.04));
        first.setFont(new Font("Segoe UI", PLAIN, (int) (screenSize.width * 0.011)));
        add(first);

        JLabel txtLast = new JLabel("Fecha de Culminacion:");
        txtLast.setBounds((int) (screenSize.width * 0.29),(int) (screenSize.height * 0.03), (int) (screenSize.width * 0.15), (int) (screenSize.height * 0.04));
        txtLast.setFont(new Font("Segoe UI", PLAIN, (int) (screenSize.width * 0.011)));
        add(txtLast);

        JDateChooser last = new JDateChooser(Date.valueOf(LocalDate.now().plusMonths(2)));
        last.setBounds((int) (screenSize.width * 0.42),(int) (screenSize.height * 0.03), (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.04));
        last.setFont(new Font("Segoe UI", PLAIN, (int) (screenSize.width * 0.011)));
        add(last);

        JButton search = UIStyles.createPrimaryButton(" Buscar       " +      "\uD83D\uDD0D");
       search.setVerticalAlignment(SwingConstants.CENTER);
       search.setHorizontalAlignment(SwingConstants.CENTER);
        search.setBounds((int) (screenSize.width * 0.59),(int) (screenSize.height * 0.03), (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.04));
        search.setFont(new Font("Segoe UI Emoji", PLAIN, (int) (screenSize.width * 0.011)));
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.sql.Date dateStart= new java.sql.Date(first.getDate().getTime());
                java.sql.Date dateEnd = new java.sql.Date(last.getDate().getTime());
                if(dateEnd.after(dateStart)){
                    loadData(dateStart, dateEnd);
                }else {
                    MessagePanel messagePanel = new MessagePanel(null, true, "La fecha de inicio no puede ser despues de la fecha de culminacion");
                    messagePanel.setVisible(true);
                }
            }
        });
        add(search);
        tableModel = new DefaultTableModel(
                new String[]{"Numero de Poliza", "Nombre del Cilente", "Tipo de Seguro", "Fecha de inicio"  ,"Fecha de Culminacion", "Premium Mensual", "Estado de Poliza"}, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UIStyles.BORDER, 1));
        scroll.getViewport().setBackground(UIStyles.CARD_BG);
        scroll.setBounds(0, (int) (screenSize.height * 0.09), (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.675));
        add(scroll);
        loadData(new java.sql.Date(Date.valueOf(LocalDate.now()).getTime()), new java.sql.Date(Date.valueOf(LocalDate.now().plusMonths(2)).getTime()));

    }

    private void loadData(Date begin, Date end) {
        issuedPolicyReportPanels = new ReportsServices().getIssuedPoliciesReport(begin, end);
        tableModel.setRowCount(0);
        for (IssuedPoliciesReport c : issuedPolicyReportPanels) {
            tableModel.addRow(new Object[]{
                    c.getPolicyNumber(), c.getClientName(), c.getInsuranceType(),
                    c.getStartDate(), c.getEndDate(),c.getMonthlyPremium(), c.getPolicyStatus()
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
