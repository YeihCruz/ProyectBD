package visual.panels;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import models.Client;
import models.InsuranceType;
import models.Policy;
import models.PolicyStatus;
import services.ClientServices;
import services.InsuranceTypeServices;
import services.PolicyServices;
import services.PolicyStatusServices;
import utils.Options;
import visual.UIStyles;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class PoliciesPanel extends JPanel {

    private final PolicyServices policyServices = new PolicyServices();
    private final ClientServices clientServices = new ClientServices();
    private final InsuranceTypeServices insuranceTypeServices = new InsuranceTypeServices();
    private final PolicyStatusServices policyStatusServices = new PolicyStatusServices();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private Dimension screenSize;


    public PoliciesPanel() {
        screenSize = Options.getOptions().getScreenSize();
        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Gesti\u00F3n de P\u00F3lizas");
        title.setFont(UIStyles.getCurrentFont(UIStyles.FONT_HEADER));
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

        JDialog dialog = new JDialog((JFrame) null, true);
        dialog.setBounds((int) (screenSize.width * 0.32), (int) (screenSize.height * 0.25), (int) (screenSize.width * 0.36), (int) (screenSize.height * 0.5));
        dialog.setUndecorated(true);
        dialog.setResizable(false);

        dialog.setLocationRelativeTo(null);

        JLabel header = new JLabel(isEdit ? "Editar Poliza " : "Nueva Poliza");
        header.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width * 0.014)));
        header.setBounds((int) (screenSize.width * 0.1), (int) (screenSize.height * 0.001), (int) (screenSize.width * 0.16), (int) (screenSize.height * 0.05));
        header.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel form = new JPanel(null);
        form.setBounds(0, 0, (int) (screenSize.width * 0.4), (int) (screenSize.height * 0.6));
        form.setBackground(new Color(200, 200, 200));
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        form.add(header);

        JLabel lClient = new JLabel("Cliente");
        JLabel lType = new JLabel("Tipo de Seguro");
        JLabel lStatus = new JLabel("Estado de la Poliza");
        JLabel lStart = new JLabel("Fecha de inicio");
        JLabel lEnd = new JLabel("Fecha de Terminacion");
        JLabel lPremium = new JLabel("Premium");
        JLabel lInsured = new JLabel("Asegurado");
        JLabel lCancelReason = new JLabel("Razon de la cancelacion");

        JComboBox<String> cmbClient = new JComboBox<>();
        JComboBox<String> cmbType = new JComboBox<>();
        JComboBox<String> cmbStatus = new JComboBox<>();
        JDateChooser calStart = new JDateChooser();
        JDateChooser calEnd = new JDateChooser();
        JTextField txtPremium = new JTextField(12);
        JTextField txtInsured = new JTextField(12);
        JTextField txtCancelReason = new JTextField(12);

        txtPremium.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtPremium.getText().length();
                char c= e.getKeyChar();
                if(!Character.isDigit(c) && (c!='.')) {
                    e.consume();
                }else if(large>=11) {
                    e.consume();
                }
            }
        });
        txtInsured.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtInsured.getText().length();
                char c= e.getKeyChar();
                if(!Character.isDigit(c) && (c!='.')) {
                    e.consume();
                }else if(large>=11) {
                    e.consume();
                }
            }
        });
        txtCancelReason.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtCancelReason.getText().length();
                if(large>=300) {
                    e.consume();
                }
            }
        });

        List<Client> clients = clientServices.getAllClients();
        List<InsuranceType> types = insuranceTypeServices.getAllInsuranceTypes();
        List<PolicyStatus> statuses = policyStatusServices.getAllPolicyStatuses();

        for (Client c : clients) cmbClient.addItem(c.toString());
        for (InsuranceType t : types) cmbType.addItem(t.getDescription());
        for (PolicyStatus s : statuses) cmbStatus.addItem(s.getDescription());

        lClient.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.055), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lClient.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lClient.setHorizontalAlignment(SwingConstants.LEFT);
        cmbClient.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbClient.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lType.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.135), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lType.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lType.setHorizontalAlignment(SwingConstants.LEFT);
        cmbType.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.17), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbType.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lStart.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.215), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lStart.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lStart.setHorizontalAlignment(SwingConstants.LEFT);
        calStart.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.25), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        calStart.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lEnd.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.295), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lEnd.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lEnd.setHorizontalAlignment(SwingConstants.LEFT);
        calEnd.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.33), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        calEnd.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));


        //segunda fila
        lStatus.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.055), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lStatus.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lStatus.setHorizontalAlignment(SwingConstants.LEFT);
        cmbStatus.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbStatus.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lPremium.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.135), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lPremium.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lPremium.setHorizontalAlignment(SwingConstants.LEFT);
        txtPremium.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.17), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtPremium.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lInsured.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.215), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lInsured.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lInsured.setHorizontalAlignment(SwingConstants.LEFT);
        txtInsured.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.25), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtInsured.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lCancelReason.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.295), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lCancelReason.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lCancelReason.setHorizontalAlignment(SwingConstants.LEFT);
        txtCancelReason.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.33), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtCancelReason.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        if (isEdit) {
            for (int i = 0; i < clients.size(); i++)
                if (clients.get(i).getClientId() == existing.getClientId()) cmbClient.setSelectedIndex(i);
            for (int i = 0; i < types.size(); i++)
                if (types.get(i).getInsuranceTypeId() == existing.getInsuranceTypeId()) cmbType.setSelectedIndex(i);
            for (int i = 0; i < statuses.size(); i++)
                if (statuses.get(i).getPolicyStatusId() == existing.getPolicyStatusId())
                    cmbStatus.setSelectedIndex(i);
            txtPremium.setText(String.valueOf(existing.getMonthlyPremium()));
            txtInsured.setText(String.valueOf(existing.getInsuredAmount()));
            txtCancelReason.setText(existing.getCancellationReason());
            calStart.setDate(Date.from(existing.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            calEnd.setDate(Date.from(existing.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } else {
            calStart.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            calEnd.setDate(Date.from(LocalDate.now().plusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        }

        form.add(cmbClient);
        form.add(cmbType);
        form.add(cmbStatus);
        form.add(calStart);
        form.add(calEnd);
        form.add(txtPremium);
        form.add(txtInsured);
        form.add(txtCancelReason);

        form.add(lClient);
        form.add(lType);
        form.add(lStatus);
        form.add(lStart);
        form.add(lEnd);
        form.add(lPremium);
        form.add(lInsured);
        form.add(lCancelReason);

        JButton btnSave = UIStyles.createPrimaryButton("Guardar");
        JButton btnCancel = UIStyles.createSecondaryButton("Cancelar") ;
        if(screenSize.width== Toolkit.getDefaultToolkit().getScreenSize().width) {
            btnSave.setBounds((int) (screenSize.width * 0.195), (int) (screenSize.height * 0.4), (int) (screenSize.width * 0.07), (int) (screenSize.height * 0.05));
            btnCancel.setBounds((int) (screenSize.width * 0.095), (int) (screenSize.height * 0.4), (int) (screenSize.width * 0.07), (int) (screenSize.height * 0.05));
        }else {
            btnSave.setBounds((int) (screenSize.width * 0.2), (int) (screenSize.height * 0.4), (int) (screenSize.width * 0.08), (int) (screenSize.height * 0.05));
            btnCancel.setBounds((int) (screenSize.width * 0.08), (int) (screenSize.height * 0.4), (int) (screenSize.width * 0.08), (int) (screenSize.height * 0.05));
        }

        btnSave.addActionListener(e -> {
            try {
                Date dateStart= calStart.getDate();
                LocalDate start = dateStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Date dateEnd = calEnd.getDate();
                LocalDate end = dateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if(end.isAfter(start)){
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
                }else {
                    MessagePanel messagePanel = new MessagePanel(null, true, "La fecha de culminacion de la poliza no puede ser menor que la fecha de inicio");
                    messagePanel.setVisible(true);
                }

            } catch (Exception ex) {
               MessagePanel messagePanel = new MessagePanel(null, true, "Verifique nuevamente los datos ingresados");
            messagePanel.setVisible(true);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        InputMap inputMap = btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = btnSave.getActionMap();

        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        inputMap.put(keyStroke, "activateBut");
        actionMap.put("activateBut", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSave.doClick();
            }
        });

        form.add(btnCancel);
        form.add(btnSave);

        dialog.add(form);
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


        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
    }
}
