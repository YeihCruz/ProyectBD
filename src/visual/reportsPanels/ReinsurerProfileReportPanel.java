package visual.reportsPanels;

import models.Client;
import models.Reinsurer;
import reports.AgencyReport;
import reports.ReinsurerProfileReport;
import services.ReinsuranceServices;
import services.ReportsServices;
import utils.Options;
import visual.UIStyles;
import visual.components.CustomComboBox;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;

public class ReinsurerProfileReportPanel extends JPanel {
    private Dimension screenSize;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private List<ReinsurerProfileReport> reinsurerProfileReports;
    private List<Reinsurer> reinsurers;
    private JComboBox comboBox;

    public ReinsurerProfileReportPanel() {
        screenSize = Options.getOptions().getScreenSize();
        reinsurers = new ReinsuranceServices().getAllReinsurers();

        setVisible(false);
        setBounds(0, 0, (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.765));
        setLayout(null);
        setBackground(UIStyles.BORDER);

        tableModel = new DefaultTableModel(
                new String[]{"Nombre de Reaseguradora", "Pais", "Tipo de Reaseguros", "Tipos de Seguro", "Porcentaje de Participacion"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        JLabel txtSearch = new JLabel("Buscar Reaseguradora: ");
        txtSearch.setBounds((int) (screenSize.width * 0.3),(int) (screenSize.height * 0.03), (int) (screenSize.width * 0.13), (int) (screenSize.height * 0.04));
        txtSearch.setFont(new Font("Segoe UI", PLAIN, (int) (screenSize.width * 0.011)));
        add(txtSearch);

        JLabel txtLupa = new JLabel("\uD83D\uDD0D ");
        txtLupa.setBounds((int) (screenSize.width * 0.6),(int) (screenSize.height * 0.0305), (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.04));
        txtLupa.setFont(new Font("Segoe UI Emoji", PLAIN, (int) (screenSize.width * 0.0115)));
        add(txtLupa);

        comboBox = new CustomComboBox().customComboBox();
        for(Reinsurer c: reinsurers) comboBox.addItem(c.toString());
        comboBox.setBounds((int) (screenSize.width * 0.42),(int) (screenSize.height * 0.03), (int) (screenSize.width * 0.2), (int) (screenSize.height * 0.04));
        comboBox.setFont(UIStyles.FONT_TITLE);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedReins = comboBox.getSelectedIndex();
                Reinsurer reinsurer = reinsurers.get(selectedReins);
                int reinsurerId = reinsurer.getReinsurerId();
                loadData(reinsurerId);
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
        loadData(1);

    }

    private void loadData(int i) {
        reinsurerProfileReports = new ReportsServices().getReinsurerProfileReport(i);
        tableModel.setRowCount(0);
        for (ReinsurerProfileReport c : reinsurerProfileReports) {
            tableModel.addRow(new Object[]{
                    c.getReinsurerName(), c.getCountryName(), c.getReinsuranceType(),
                    c.getInsuranceType(), c.getParticipationPercentage(),
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

