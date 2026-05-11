package visual.panels;

import models.Claim;
import models.ClaimStatus;
import models.ClaimType;
import services.ClaimServices;
import services.ClaimStatusServices;
import services.ClaimTypeServices;
import visual.UIStyles;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

public class IncidentsPanel extends JPanel {

    private final ClaimServices claimServices = new ClaimServices();
    private final ClaimTypeServices claimTypeServices = new ClaimTypeServices();
    private final ClaimStatusServices claimStatusServices = new ClaimStatusServices();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public IncidentsPanel() {
        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Control de Siniestros");
        title.setFont(UIStyles.FONT_HEADER);
        title.setForeground(UIStyles.TEXT_PRIMARY);
        header.add(title, BorderLayout.WEST);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        toolbar.setOpaque(false);

        JButton btnView = createToolbarButton("\uD83D\uDD0D  Ver detalle", new Color(100, 110, 125), Color.WHITE);
        btnView.addActionListener(e -> viewDetail());
        toolbar.add(btnView);

        JButton btnRefresh = createToolbarButton("\uD83D\uDD04  Actualizar", UIStyles.PRIMARY, Color.WHITE);
        btnRefresh.addActionListener(e -> loadData());
        toolbar.add(btnRefresh);

        header.add(toolbar, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new String[]{"N\u00B0", "P\u00F3liza", "Tipo", "Estado", "Fecha Incidente", "Reclamado", "Compensado"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UIStyles.BORDER, 1));
        scroll.getViewport().setBackground(UIStyles.CARD_BG);
        add(scroll, BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Claim> claims = claimServices.getAllClaims();
        List<ClaimType> types = claimTypeServices.getAllClaimTypes();
        List<ClaimStatus> statuses = claimStatusServices.getAllClaimStatus();

        for (Claim cl : claims) {
            String typeName = "";
            for (ClaimType t : types) if (t.getClaimTypeId() == cl.getClaimTypeId()) { typeName = t.getDescription(); break; }
            String statusName = "";
            for (ClaimStatus s : statuses) if (s.getClaimStatusId() == cl.getClaimStatusId()) { statusName = s.getDescription(); break; }

            tableModel.addRow(new Object[]{
                cl.getClaimNumber(), cl.getPolicyNumber(), typeName, statusName,
                cl.getIncidentDate(),
                String.format("$%.2f", cl.getClaimedAmount()),
                String.format("$%.2f", cl.getCompensatedAmount())
            });
        }
    }

    private void viewDetail() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un siniestro para ver su detalle.", "Ver detalle", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        Claim claim = claimServices.findClaimById(id);
        if (claim == null) return;

        ClaimType type = claimTypeServices.findClaimTypeById(claim.getClaimTypeId());
        ClaimStatus status = claimStatusServices.findClaimStatusById(claim.getClaimStatusId());

        JDialog dialog = new JDialog((JFrame) null, "Detalle del Siniestro #" + id, true);
        dialog.setSize(420, 350);
        dialog.setLocationRelativeTo(null);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(UIStyles.CARD_BG);
        content.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 6, 0);

        int row2 = 0;
        addDetailField(content, gbc, row2++, "P\u00F3liza", String.valueOf(claim.getPolicyNumber()));
        addDetailField(content, gbc, row2++, "Tipo", type != null ? type.getDescription() : "-");
        addDetailField(content, gbc, row2++, "Estado", status != null ? status.getDescription() : "-");
        addDetailField(content, gbc, row2++, "Fecha del Incidente", claim.getIncidentDate().toString());
        addDetailField(content, gbc, row2++, "Monto Reclamado", String.format("$%.2f", claim.getClaimedAmount()));
        addDetailField(content, gbc, row2++, "Monto Compensado", String.format("$%.2f", claim.getCompensatedAmount()));

        if (claim.getRejectionReason() != null && !claim.getRejectionReason().isEmpty()) {
            addDetailField(content, gbc, row2, "Raz\u00F3n Rechazo", claim.getRejectionReason());
        }

        JButton btnClose = UIStyles.createSecondaryButton("Cerrar");
        btnClose.addActionListener(e -> dialog.dispose());

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIStyles.CARD_BG);
        root.add(content, BorderLayout.CENTER);
        root.add(btnClose, BorderLayout.SOUTH);
        root.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 15));

        dialog.add(root);
        dialog.setVisible(true);
    }

    private void addDetailField(JPanel panel, GridBagConstraints c, int row, String label, String value) {
        c.gridy = row;
        JLabel lbl = new JLabel("<html><b>" + label + ":</b> " + value + "</html>");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(UIStyles.TEXT_PRIMARY);
        panel.add(lbl, c);
    }

    private JButton createToolbarButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return btn;
    }

    private void styleTable(JTable t) {
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.getTableHeader().setBackground(new Color(240, 242, 245));
        t.getTableHeader().setForeground(UIStyles.TEXT_PRIMARY);
        t.setRowHeight(32);
        t.setShowGrid(true);
        t.setGridColor(UIStyles.BORDER_LIGHT);
        t.setSelectionBackground(UIStyles.PRIMARY_LIGHT);
        t.setSelectionForeground(UIStyles.TEXT_PRIMARY);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.getColumnModel().getColumn(0).setMaxWidth(50);
    }
}
