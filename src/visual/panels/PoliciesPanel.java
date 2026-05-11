package visual.panels;

import models.Client;
import models.InsuranceType;
import models.Policy;
import models.PolicyStatus;
import services.ClientServices;
import services.InsuranceTypeServices;
import services.PolicyServices;
import services.PolicyStatusServices;
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

public class PoliciesPanel extends JPanel {

    private final PolicyServices policyServices = new PolicyServices();
    private final ClientServices clientServices = new ClientServices();
    private final InsuranceTypeServices insuranceTypeServices = new InsuranceTypeServices();
    private final PolicyStatusServices policyStatusServices = new PolicyStatusServices();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public PoliciesPanel() {
        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Gesti\u00F3n de P\u00F3lizas");
        title.setFont(UIStyles.FONT_HEADER);
        title.setForeground(UIStyles.TEXT_PRIMARY);
        header.add(title, BorderLayout.WEST);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        toolbar.setOpaque(false);

        JButton btnNew = createToolbarButton("\u2795  Nueva", UIStyles.PRIMARY, Color.WHITE);
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
            new String[]{"N\u00B0 P\u00F3liza", "Cliente", "Tipo Seguro", "Estado", "Inicio", "Fin", "Prima", "Monto Asegurado"}, 0
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
        List<Policy> policies = policyServices.getAllPolicies();
        List<Client> clients = clientServices.getAllClients();
        List<InsuranceType> types = insuranceTypeServices.getAllInsuranceTypes();
        List<PolicyStatus> statuses = policyStatusServices.getAllPolicyStatuses();

        for (Policy p : policies) {
            String clientName = "";
            for (Client c : clients) if (c.getClientId() == p.getClientId()) { clientName = c.toString(); break; }
            String typeName = "";
            for (InsuranceType t : types) if (t.getInsuranceTypeId() == p.getInsuranceTypeId()) { typeName = t.getDescription(); break; }
            String statusName = "";
            for (PolicyStatus s : statuses) if (s.getPolicyStatusId() == p.getPolicyStatusId()) { statusName = s.getDescription(); break; }

            tableModel.addRow(new Object[]{
                p.getPolicyNumber(), clientName, typeName, statusName,
                p.getStartDate(), p.getEndDate(),
                String.format("$%.2f", p.getMonthlyPremium()),
                String.format("$%.2f", p.getInsuredAmount())
            });
        }
    }

    private void showForm(Policy existing) {
        boolean isEdit = existing != null;

        JDialog dialog = new JDialog((JFrame) null, isEdit ? "Editar P\u00F3liza" : "Nueva P\u00F3liza", true);
        dialog.setSize(480, 480);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UIStyles.CARD_BG);
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        JComboBox<String> cmbClient = new JComboBox<>();
        JComboBox<String> cmbType = new JComboBox<>();
        JComboBox<String> cmbStatus = new JComboBox<>();
        JTextField txtStart = new JTextField(12);
        JTextField txtEnd = new JTextField(12);
        JTextField txtPremium = new JTextField(12);
        JTextField txtInsured = new JTextField(12);
        JTextField txtCancelReason = new JTextField(12);

        List<Client> clients = clientServices.getAllClients();
        List<InsuranceType> types = insuranceTypeServices.getAllInsuranceTypes();
        List<PolicyStatus> statuses = policyStatusServices.getAllPolicyStatuses();

        for (Client c : clients) cmbClient.addItem(c.toString());
        for (InsuranceType t : types) cmbType.addItem(t.getDescription());
        for (PolicyStatus s : statuses) cmbStatus.addItem(s.getDescription());

        JTextField[] fields = {txtStart, txtEnd, txtPremium, txtInsured, txtCancelReason};
        for (JTextField f : fields) UIStyles.styleField(f);

        if (isEdit) {
            for (int i = 0; i < clients.size(); i++)
                if (clients.get(i).getClientId() == existing.getClientId()) cmbClient.setSelectedIndex(i);
            for (int i = 0; i < types.size(); i++)
                if (types.get(i).getInsuranceTypeId() == existing.getInsuranceTypeId()) cmbType.setSelectedIndex(i);
            for (int i = 0; i < statuses.size(); i++)
                if (statuses.get(i).getPolicyStatusId() == existing.getPolicyStatusId()) cmbStatus.setSelectedIndex(i);
            txtStart.setText(existing.getStartDate().toString());
            txtEnd.setText(existing.getEndDate().toString());
            txtPremium.setText(String.valueOf(existing.getMonthlyPremium()));
            txtInsured.setText(String.valueOf(existing.getInsuredAmount()));
            txtCancelReason.setText(existing.getCancellationReason());
        } else {
            txtStart.setText(LocalDate.now().toString());
            txtEnd.setText(LocalDate.now().plusYears(1).toString());
        }

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.WEST;

        int row = 0;
        addField(form, c, row++, "Cliente", cmbClient);
        addField(form, c, row++, "Tipo de Seguro", cmbType);
        addField(form, c, row++, "Estado", cmbStatus);
        addField(form, c, row++, "Fecha Inicio (YYYY-MM-DD)", txtStart);
        addField(form, c, row++, "Fecha Fin (YYYY-MM-DD)", txtEnd);
        addField(form, c, row++, "Prima Mensual", txtPremium);
        addField(form, c, row++, "Monto Asegurado", txtInsured);
        addField(form, c, row, "Raz\u00F3n Cancelaci\u00F3n", txtCancelReason);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);

        JButton btnSave = UIStyles.createPrimaryButton("Guardar");
        JButton btnCancel = UIStyles.createSecondaryButton("Cancelar");

        btnSave.addActionListener(e -> {
            try {
                LocalDate start = LocalDate.parse(txtStart.getText().trim());
                LocalDate end = LocalDate.parse(txtEnd.getText().trim());
                double premium = Double.parseDouble(txtPremium.getText().trim());
                double insured = Double.parseDouble(txtInsured.getText().trim());

                int clientId = clients.get(cmbClient.getSelectedIndex()).getClientId();
                int typeId = types.get(cmbType.getSelectedIndex()).getInsuranceTypeId();
                int statusId = statuses.get(cmbStatus.getSelectedIndex()).getPolicyStatusId();

                Policy p = new Policy(
                    isEdit ? existing.getPolicyNumber() : 0,
                    clientId, typeId, statusId, start, end,
                    premium, insured, txtCancelReason.getText().trim()
                );

                if (isEdit) policyServices.updatePolicy(p);
                else policyServices.savePolicy(p);

                dialog.dispose();
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Verifique los datos ingresados (fechas: YYYY-MM-DD, montos: num\u00E9ricos).", "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Seleccione una p\u00F3liza de la tabla.", "Editar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Policy p = policyServices.findPolicyById(id);
        if (p != null) showForm(p);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una p\u00F3liza de la tabla.", "Eliminar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "\u00BFEliminar la p\u00F3liza #" + id + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            policyServices.deletePolicy(id);
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

        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        t.getColumnModel().getColumn(0).setMaxWidth(80);
    }
}
