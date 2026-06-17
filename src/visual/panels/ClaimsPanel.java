package visual.panels;

import com.toedter.calendar.JDateChooser;
import models.Claim;
import models.ClaimStatus;
import models.ClaimType;
import models.Policy;
import services.ClaimServices;
import services.ClaimStatusServices;
import services.ClaimTypeServices;
import services.PolicyServices;
import utils.Options;
import visual.UIStyles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ClaimsPanel extends JPanel {

    private final ClaimServices claimServices = new ClaimServices();
    private final PolicyServices policyServices = new PolicyServices();
    private final ClaimTypeServices claimTypeServices = new ClaimTypeServices();
    private final ClaimStatusServices jDate = new ClaimStatusServices();

    private final JTable table;
    private final DefaultTableModel tableModel;
    private Dimension screenSize;

    public ClaimsPanel() {
        screenSize = Options.getOptions().getScreenSize();

        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Gesti\u00F3n de Reclamos");
        title.setFont(UIStyles.getCurrentFont(UIStyles.FONT_HEADER));
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
        List<ClaimStatus> statuses = jDate.getAllClaimStatus();

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

        JDialog dialog = new JDialog((JFrame) null, true);
        dialog.setBounds((int) (screenSize.width*0.32), (int) (screenSize.height*0.25), (int) (screenSize.width*0.36), (int) (screenSize.height*0.5));
        dialog.setUndecorated(true);
        dialog.setResizable(false);

        JLabel header = new JLabel(isEdit ? "Editar Reclamo " : "Nuevo Reclamo");
        header.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.014)));
        header.setBounds((int) (screenSize.width*0.1), (int) (screenSize.height*0.001), (int) (screenSize.width*0.16), (int) (screenSize.height*0.05));
        header.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel form = new JPanel(null);
        form.setBounds(0, 0  ,(int) (screenSize.width*0.4), (int) (screenSize.height*0.6));
        form.setBackground(new Color(200, 200, 200 ));
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        form.add(header);

        JLabel lPolicy = new JLabel("Poliza");
        JLabel lType = new JLabel("Tipo de Siniestro");
        JLabel lStatus = new JLabel("Estado de la Reclamacion");
        JLabel lDate = new JLabel("Fecha");
        JLabel lClaimed = new JLabel("Reclamado");
        JLabel lCompensated = new JLabel("Compensado");
        JLabel lReason = new JLabel("Razon");

        JComboBox<String> cmbPolicy = new JComboBox<>();
        JComboBox<String> cmbType = new JComboBox<>();
        JComboBox<String> cmbStatus = new JComboBox<>();
        JDateChooser jDCDate = new JDateChooser();
        JTextField txtClaimed = new JTextField(12);
        JTextField txtCompensated = new JTextField(12);
        JTextField txtReason = new JTextField(12);

        txtClaimed.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtClaimed.getText().length();
                char c= e.getKeyChar();
                if(!Character.isDigit(c)  && c!='.') {
                    e.consume();
                }else if(large>=12) {
                    e.consume();
                }
            }
        });

        txtCompensated.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtCompensated.getText().length();
                char c= e.getKeyChar();
                if(!Character.isDigit(c)  && c!='.') {
                    e.consume();
                }else if(large>=12) {
                    e.consume();
                }
            }
        });

        txtReason.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtReason.getText().length();
               if(large>=300) {
                    e.consume();
                }
            }
        });

        List<Policy> policies = policyServices.getAllPolicies();
        List<ClaimType> types = claimTypeServices.getAllClaimTypes();
        List<ClaimStatus> statuses = this.jDate.getAllClaimStatus();

        for (Policy p : policies) cmbPolicy.addItem("P\u00F3liza #" + p.getPolicyNumber());
        for (ClaimType t : types) cmbType.addItem(t.getDescription());
        for (ClaimStatus s : statuses) cmbStatus.addItem(s.getDescription());

        if (isEdit) {
            for (int i = 0; i < policies.size(); i++)
                if (policies.get(i).getPolicyNumber() == existing.getPolicyNumber()) cmbPolicy.setSelectedIndex(i);
            for (int i = 0; i < types.size(); i++)
                if (types.get(i).getClaimTypeId() == existing.getClaimTypeId()) cmbType.setSelectedIndex(i);
            for (int i = 0; i < statuses.size(); i++)
                if (statuses.get(i).getClaimStatusId() == existing.getClaimStatusId()) cmbStatus.setSelectedIndex(i);
            jDCDate.setDate(Date.from(existing.getIncidentDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            txtClaimed.setText(String.valueOf(existing.getClaimedAmount()));
            txtCompensated.setText(String.valueOf(existing.getCompensatedAmount()));
            txtReason.setText(existing.getRejectionReason());
        } else {
            jDCDate.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }

        lPolicy.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.055), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lPolicy.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lPolicy.setHorizontalAlignment(SwingConstants.LEFT);
        cmbPolicy.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbPolicy.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lType.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.135), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lType.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lType.setHorizontalAlignment(SwingConstants.LEFT);
        cmbType.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.17), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbType.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lDate.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.215), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lDate.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lDate.setHorizontalAlignment(SwingConstants.LEFT);
        jDCDate.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.25), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        jDCDate.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lClaimed.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.295), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lClaimed.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lClaimed.setHorizontalAlignment(SwingConstants.LEFT);
        txtClaimed.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.33), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtClaimed.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));


        lStatus.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.055), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lStatus.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lStatus.setHorizontalAlignment(SwingConstants.LEFT);
        cmbStatus.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbStatus.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lCompensated.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.135), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lCompensated.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lCompensated.setHorizontalAlignment(SwingConstants.LEFT);
        txtCompensated.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.17), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtCompensated.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lReason.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.215), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lReason.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lReason.setHorizontalAlignment(SwingConstants.LEFT);
        txtReason.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.25), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtReason.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        form.add(lPolicy);
        form.add(lType);
        form.add(lStatus);
        form.add(lDate);
        form.add(lClaimed);
        form.add(lCompensated);
        form.add(lReason);

        form.add(cmbPolicy);
        form.add(cmbType);
        form.add(cmbStatus);
        form.add(jDCDate);
        form.add(txtClaimed);
        form.add(txtCompensated);
        form.add(txtReason);


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
                Date jdcdate = jDCDate.getDate();
                LocalDate date = jdcdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
                MessagePanel mp = new MessagePanel( null, true, "Error. Verifique nuevamente los campos antes de continuar");
                mp.setVisible(true);
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

        dialog.setLocationRelativeTo(null);

        dialog.setVisible(true);

    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessagePanel mp = new MessagePanel( null, true, "Seleccione un reclamo de la tabla");
            mp.setVisible(true);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Claim c = claimServices.findClaimById(id);
        if (c != null) showForm(c);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessagePanel mp = new MessagePanel( null, true, "Seleccione un reclamo de la tabla");
            mp.setVisible(true);
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

    }
}
