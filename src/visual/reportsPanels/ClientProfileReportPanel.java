package visual.reportsPanels;

import models.Client;
import reports.ClientProfileReport;
import services.ClientServices;
import services.ReportsServices;
import utils.Options;
import visual.UIStyles;
import visual.components.CustomComboBox;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;

 public class ClientProfileReportPanel extends ParentReportPanel {


    private Dimension screenSize;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private List<ClientProfileReport> clientProfileReports;
    private List<Client> clients;
    private JComboBox comboBox;
    private int number;

    public ClientProfileReportPanel() {
        clients = new ClientServices().getAllClients();
        screenSize = Options.getOptions().getScreenSize();
        setVisible(false);
        setBounds(0, 0, (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.765));
        setLayout(null);
        setBackground(UIStyles.BORDER);

        tableModel = new DefaultTableModel(
                new String[]{"Client Name", "Identification", "Phone", "Address", "E-mail", "Active Policies","Total Premium Amount", "Incident date", "Claimed Amount", "Compensated Amount"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        JLabel txtSearch = new JLabel("Buscar Cliente: ");
        txtSearch.setBounds((int) (screenSize.width * 0.3175),(int) (screenSize.height * 0.03), (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.04));
        txtSearch.setFont(new Font("Segoe UI", PLAIN, (int) (screenSize.width * 0.011)));
        add(txtSearch);

        JLabel txtLupa = new JLabel("\uD83D\uDD0D ");
        txtLupa.setBounds((int) (screenSize.width * 0.5825),(int) (screenSize.height * 0.0305), (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.04));
        txtLupa.setFont(new Font("Segoe UI Emoji", PLAIN, (int) (screenSize.width * 0.0115)));
        add(txtLupa);

        comboBox = new CustomComboBox().customComboBox();
        for(Client c: clients) comboBox.addItem(c.toString());
        comboBox.setBounds((int) (screenSize.width * 0.4025),(int) (screenSize.height * 0.03), (int) (screenSize.width * 0.2), (int) (screenSize.height * 0.04));
        comboBox.setFont(new Font("Segoe UI", BOLD, (int) (screenSize.width * 0.011)));
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedClient = comboBox.getSelectedIndex();
                Client client = clients.get(selectedClient);
                number = client.getClientId();
                loadData();
            }
        });
        comboBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                txtLupa.setForeground(Color.red);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                txtLupa.setForeground(Color.BLACK);
            }
        });
        add(comboBox);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UIStyles.BORDER, 1));
        scroll.getViewport().setBackground(UIStyles.CARD_BG);
        scroll.setBounds(0, (int) (screenSize.height * 0.09), (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.675));
        add(scroll);
        number=1;
        loadData();

    }

    public void loadData() {
        clientProfileReports = new ReportsServices().getClientProfileReport(number);
        tableModel.setRowCount(0);
        for (ClientProfileReport c : clientProfileReports) {
            tableModel.addRow(new Object[]{
                    c.getClientName(), c.getIdentificationNumber(), c.getPhone(),
                    c.getAddress(), c.getEmail(), c.getActivePolicies(), c.getTotalPremiumsPaid(),
                    c.getIncidentDate(),c.getClaimedAmount(), c.getCompensatedAmount()
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
