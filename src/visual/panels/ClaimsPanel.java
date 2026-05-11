package visual.panels;

import models.Claim;
import models.ClaimStatus;
import models.ClaimType;
import models.Policy;
import services.ClaimServices;
import services.ClaimStatusServices;
import services.ClaimTypeServices;
import services.PolicyServices;
import visual.UIStyles;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
import java.time.LocalDate;
import java.util.List;

public class ClaimsPanel extends JPanel {

    private final ClaimServices claimServices = new ClaimServices();
    private final PolicyServices policyServices = new PolicyServices();
    private final ClaimTypeServices claimTypeServices = new ClaimTypeServices();
    private final ClaimStatusServices claimStatusServices = new ClaimStatusServices();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ClaimsPanel() {
        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Gesti\u00F3n de Reclamos");
        title.setFont(UIStyles.FONT_HEADER);
        title.setForeground(UIStyles.TEXT_PRIMARY);
        header.add(title, BorderLayout.WEST);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        toolbar.setOpaque(false);

        JButton btnNew = createToolbarButton("\u2795  Nuevo", UIStyles.PRIMARY, Color.WHITE);
        btnNew.addActionListener(e -> showForm(null));
        toolbar.add(btnNew);

        JButton btnEdit = createToolbarButton("\u270F\uFE0F  Editar", new Color(100, 110, 125), Color.WHITE);
        btnEdit.addActionListener(e -> editSelected());
        toolbar.add(btnEdit);

        JButton btnDelete = createToolbarButton("\uD83D\uDDD1\uFE0F  Eliminar", new Color(200, 70, 70), Color.WHITE);
        btnDelete.addActionListener(e -> deleteSelected());
        toolbar.add(btnDelete);

        header.add(toolbar, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new String[]{"N\u00B0 Reclamo", "P\u00F3liza", "Tipo", "Estado", "Fecha", "Monto Reclamado", "Monto Compensado"}, 0
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

    private void showForm(Claim existing) {
        boolean isEdit = existing != null;

        JDialog dialog = new JDialog((JFrame) null, isEdit ? "Editar Reclamo" : "Nuevo Reclamo", true);
        dialog.setSize(480, 460);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UIStyles.CARD_BG);
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        JComboBox<String> cmbPolicy = new JComboBox<>();
        JComboBox<String> cmbType = new JComboBox<>();
        JComboBox<String> cmbStatus = new JComboBox<>();
        JTextField txtDate = new JTextField(12);
        JTextField txtClaimed = new JTextField(12);
        JTextField txtCompensated = new JTextField(12);
        JTextField txtReason = new JTextField(12);

        List<Policy> policies = policyServices.getAllPolicies();
        List<ClaimType> types = claimTypeServices.getAllClaimTypes();
        List<ClaimStatus> statuses = claimStatusServices.getAllClaimStatus();

        for (Policy p : policies) cmbPolicy.addItem("P\u00F3liza #" + p.getPolicyNumber());
        for (ClaimType t : types) cmbType.addItem(t.getDescription());
        for (ClaimStatus s : statuses) cmbStatus.addItem(s.getDescription());

        JTextField[] fields = {txtDate, txtClaimed, txtCompensated, txtReason};
        for (JTextField f : fields) UIStyles.styleField(f);

        if (isEdit) {
            for (int i = 0; i < policies.size(); i++)
                if (policies.get(i).getPolicyNumber() == existing.getPolicyNumber()) cmbPolicy.setSelectedIndex(i);
            for (int i = 0; i < types.size(); i++)
                if (types.get(i).getClaimTypeId() == existing.getClaimTypeId()) cmbType.setSelectedIndex(i);
            for (int i = 0; i < statuses.size(); i++)
                if (statuses.get(i).getClaimStatusId() == existing.getClaimStatusId()) cmbStatus.setSelectedIndex(i);
            txtDate.setText(existing.getIncidentDate().toString());
            txtClaimed.setText(String.valueOf(existing.getClaimedAmount()));
            txtCompensated.setText(String.valueOf(existing.getCompensatedAmount()));
            txtReason.setText(existing.getRejectionReason());
        } else {
            txtDate.setText(LocalDate.now().toString());
        }

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.WEST;

        int row = 0;
        addField(form, c, row++, "P\u00F3liza", cmbPolicy);
        addField(form, c, row++, "Tipo de Reclamo", cmbType);
        addField(form, c, row++, "Estado", cmbStatus);
        addField(form, c, row++, "Fecha Incidente (YYYY-MM-DD)", txtDate);
        addField(form, c, row++, "Monto Reclamado", txtClaimed);
        addField(form, c, row++, "Monto Compensado", txtCompensated);
        addField(form, c, row, "Raz\u00F3n Rechazo", txtReason);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);

        JButton btnSave = UIStyles.createPrimaryButton("Guardar");
        JButton btnCancel = UIStyles.createSecondaryButton("Cancelar");

        btnSave.addActionListener(e -> {
            try {
                LocalDate date = LocalDate.parse(txtDate.getText().trim());
                double claimed = Double.parseDouble(txtClaimed.getText().trim());
                double compensated = Double.parseDouble(txtCompensated.getText().trim().isEmpty() ? "0" : txtCompensated.getText().trim());

                int policyId = policies.get(cmbPolicy.getSelectedIndex()).getPolicyNumber();
                int typeId = types.get(cmbType.getSelectedIndex()).getClaimTypeId();
                int statusId = statuses.get(cmbStatus.getSelectedIndex()).getClaimStatusId();

                Claim cl = new Claim(
                    isEdit ? existing.getClaimNumber() : 0,
                    policyId, typeId, statusId, date,
                    claimed, compensated, txtReason.getText().trim()
                );

                if (isEdit) claimServices.updateClaim(cl);
                else claimServices.saveClaim(cl);

                dialog.dispose();
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Verifique los datos (fecha: YYYY-MM-DD, montos: num\u00E9ricos).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttons.add(btnCancel);
        buttons.add(btnSave);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIStyles.CARD_BG);
        root.add(form, BorderLayout.CENTER);
        root.add(buttons, BorderLayout.SOUTH);
        root.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyles.BORDER, 1),
                BorderFactory.createEmptyBorder(0, 0, 15, 15)));

        dialog.add(root);
        dialog.setVisible(true);
    }

    private void addField(JPanel panel, GridBagConstraints c, int row, String label, Object component) {
        c.gridy = row * 2;
        c.insets = new Insets(0, 0, 4, 0);
        panel.add(UIStyles.createFieldLabel(label), c);
        c.gridy = row * 2 + 1;
        c.insets = new Insets(0, 0, 8, 0);
        panel.add((java.awt.Component) component, c);
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un reclamo de la tabla.", "Editar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Claim c = claimServices.findClaimById(id);
        if (c != null) showForm(c);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un reclamo de la tabla.", "Eliminar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "\u00BFEliminar el reclamo #" + id + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            claimServices.deleteClaim(id);
            loadData();
        }
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
        t.getTableHeader().setBorder(BorderFactory.createLineBorder(UIStyles.BORDER));
        t.setRowHeight(32);
        t.setShowGrid(true);
        t.setGridColor(UIStyles.BORDER_LIGHT);
        t.setSelectionBackground(UIStyles.PRIMARY_LIGHT);
        t.setSelectionForeground(UIStyles.TEXT_PRIMARY);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setIntercellSpacing(new Dimension(8, 0));

        t.getColumnModel().getColumn(0).setMaxWidth(90);
    }
}
