package visual.reportsPanels;
import models.Client;
import org.jfree.chart.JFreeChart;
import reports.ReinsurerReport;
import services.ClientServices;
import services.ReportsServices;
import utils.Options;
import visual.UIStyles;
import visual.components.BarGraphics;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReinsurerReportPanel extends ParentReportPanel {
    private Dimension screenSize;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private List<ReinsurerReport> reinsurerReports;

    public ReinsurerReportPanel() {

        screenSize = Options.getOptions().getScreenSize();
        setVisible(false);
        setBounds(0, 0, (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.765));
        setLayout(null);
        setBackground(  UIStyles.BG_LIGHT);

        tableModel = new DefaultTableModel(
                new String[]{"Nombre", "Pais", "Tipo de Reaseguradora", "Tipo de Seguro", "Porcentaje de Participacion"}, 0
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
        scroll.setBounds(0, 0, (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.425));
        add(scroll);

        List<String> names = new ArrayList<>();
        List<Double> numbers = new ArrayList<>();

        for(Client c: new ClientServices().getAllClients()){
            names.add(c.getFirstName());
            double doble = c.getAge();
            numbers.add(doble);

        }

        JPanel bar = new BarGraphics().creteGrapsFromList(names, numbers);
        bar.setLayout(null);
        bar.setBounds(0,  (int) (screenSize.height * 0.432), (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.334));
        add(bar);
        loadData();

    }

    public void loadData() {
        reinsurerReports = new ReportsServices().getReinsurersReport();
        tableModel.setRowCount(0);
        for (ReinsurerReport c : reinsurerReports) {
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

