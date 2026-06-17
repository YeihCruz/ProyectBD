package visual.panels;

import models.*;
import services.*;
import utils.Options;
import visual.UIStyles;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class CoveragePanel extends JPanel{
    private final CoverageServices coverageServices = new CoverageServices();
    private final PolicyServices policyServices = new PolicyServices();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private Dimension screenSize;

    public CoveragePanel() {
        setBackground(UIStyles.BG_LIGHT);
        screenSize = Options.getOptions().getScreenSize();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Gesti\u00F3n de Coberturas");
        title.setFont(UIStyles.getCurrentFont(UIStyles.FONT_HEADER));
        title.setForeground(UIStyles.TEXT_PRIMARY);
        header.add(title, BorderLayout.NORTH);

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
                new String[]{"ID", "Numero de Poliza", "Descripcion", "Monto Cubierto"}, 0
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
        for (Coverage c : coverageServices.getAllCoverages()) {
            tableModel.addRow(new Object[]{
                   c.getCoverageId(), c.getPolicyNumber(),
                    c.getDescription(), c.getCoverageAmount()
            });
        }
    }

    private void showForm(Coverage existing) {
        boolean isEdit = existing != null;

        JDialog dialog = new JDialog((JFrame) null, true);
        dialog.setBounds((int) (screenSize.width*0.32), (int) (screenSize.height*0.21), (int) (screenSize.width*0.36), (int) (screenSize.height*0.34));
        dialog.setUndecorated(true);
        dialog.setResizable(false);

        JLabel header = new JLabel(isEdit ? "Editar Cobertura " : "Nueva Cobertura");
        header.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.014)));
        header.setBounds((int) (screenSize.width*0.1), (int) (screenSize.height*0.001), (int) (screenSize.width*0.16), (int) (screenSize.height*0.05));
        header.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel form = new JPanel(null);
        form.setBounds(0, 0  ,(int) (screenSize.width*0.36), (int) (screenSize.height*0.34));
        form.setBackground(new Color(200, 200, 200 ));
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        form.add(header);

        JLabel lpolicyNumber = new JLabel("Numero de Polzia");
        JLabel ldescription = new JLabel("Descripcion");
        JLabel lMount = new JLabel("Monto Cubierto");


        JTextField txtDescription = new JTextField(15);
        JTextField txtAmount = new JTextField(15);

        txtDescription.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtDescription.getText().length();
                char c= e.getKeyChar();
                if(!Character.isLetter(c)  && c!=' ') {
                    e.consume();
                }else if(large>=80) {
                    e.consume();
                }
            }
        });

        txtAmount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtAmount.getText().length();
                char c= e.getKeyChar();
                if(!Character.isDigit(c)  && c!='.') {
                    e.consume();
                }else if(large>=8) {
                    e.consume();
                }
            }
        });

        JComboBox<String> cmbNumberPolicy = new JComboBox<>();

        List<Policy> policies = policyServices.getAllPolicies();

        lpolicyNumber.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.055), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lpolicyNumber.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lpolicyNumber.setHorizontalAlignment(SwingConstants.LEFT);
        cmbNumberPolicy.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbNumberPolicy.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));


        ldescription.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.135), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        ldescription.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        ldescription.setHorizontalAlignment(SwingConstants.LEFT);
        txtDescription.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.17), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtDescription.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lMount.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.055), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lMount.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lMount.setHorizontalAlignment(SwingConstants.LEFT);
        txtAmount.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtAmount.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        for (Policy p : policies) cmbNumberPolicy.addItem(p.toString());

        if (isEdit) {
        txtDescription.setText(policyServices.findPolicyById(existing.getPolicyNumber()).toString());
        txtAmount.setText(Double.toString(existing.getCoverageAmount()));

        for (int i = 0; i < policies.size(); i++)
            if (policies.get(i).getPolicyNumber() == existing.getPolicyNumber()) cmbNumberPolicy.setSelectedIndex(i);

    }
        form.add(txtDescription);
        form.add(txtAmount);
        form.add(cmbNumberPolicy);

        form.add(lpolicyNumber);
        form.add(ldescription);
        form.add(lMount);


    JButton btnSave = UIStyles.createPrimaryButton("Guardar");
    JButton btnCancel = UIStyles.createSecondaryButton("Cancelar") ;

        if(screenSize.width== Toolkit.getDefaultToolkit().getScreenSize().width) {
        btnSave.setBounds((int) (screenSize.width * 0.195), (int) (screenSize.height * 0.24), (int) (screenSize.width * 0.07), (int) (screenSize.height * 0.05));
        btnCancel.setBounds((int) (screenSize.width * 0.095), (int) (screenSize.height * 0.24), (int) (screenSize.width * 0.07), (int) (screenSize.height * 0.05));
    }else {
        btnSave.setBounds((int) (screenSize.width * 0.2), (int) (screenSize.height * 0.48), (int) (screenSize.width * 0.08), (int) (screenSize.height * 0.05));
        btnCancel.setBounds((int) (screenSize.width * 0.08), (int) (screenSize.height * 0.48), (int) (screenSize.width * 0.08), (int) (screenSize.height * 0.05));
    }

        btnSave.addActionListener(e -> {

            if (txtDescription.getText().trim().isEmpty() || txtAmount.getText().trim().isEmpty()) {
            MessagePanel mp = new MessagePanel( null, true, "Rellene los campos antes de continuar");
            mp.setVisible(true);
            return;
            }
            int policyNumber = policies.get(cmbNumberPolicy.getSelectedIndex()).getPolicyNumber();
            double amount = Double.parseDouble(txtAmount.getText().trim());
            Coverage newCoverage = new Coverage(
                    isEdit ? existing.getCoverageId() : 0,
                    policyNumber,
                    txtDescription.getText().trim(), amount);

            if (isEdit) coverageServices.updateCoverage(newCoverage);
            else coverageServices.saveCoverage(newCoverage);

            dialog.dispose();
            loadData();

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

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
}

private void editSelected() {
    int row = table.getSelectedRow();
    if (row < 0) {
        MessagePanel mp = new MessagePanel( null, true, "Seleccione una cobertura de la tabla");
        mp.setVisible(true);
        return;
    }
    int id = (int) tableModel.getValueAt(row, 0);
    Coverage c = coverageServices.findCoverageById(id);
    if (c != null) showForm(c);
}

private void deleteSelected() {
    int row = table.getSelectedRow();
    if (row < 0) {
        MessagePanel mp = new MessagePanel( null, true, "Seleccione una cobertura de la tabla");
        mp.setVisible(true);
        return;
    }
    int id = (int) tableModel.getValueAt(row, 0);
    int confirm = JOptionPane.showConfirmDialog(this,
            "\u00BFEliminar a la cobertura numero \"" + id + "\"?", "Confirmar",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

    if (confirm == JOptionPane.YES_OPTION) {
        coverageServices.deleteCoverage(id);
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


    DefaultTableCellRenderer center = new DefaultTableCellRenderer();
    center.setHorizontalAlignment(SwingConstants.CENTER);
}
}
