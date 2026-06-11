package visual.panels;

import models.Agency;
import services.AgencyServices;
import utils.Options;
import visual.UIStyles;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AgencyPanel extends JPanel {

    private final AgencyServices agencyServices = new AgencyServices();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private Dimension screenSize;

    public AgencyPanel() {
        screenSize = Options.getOptions().getScreenSize();
        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Gesti\u00F3n de Agencias");
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
                new String[]{"ID", "Nombre", "Direccion", "Telefono", "Email", "Director general", "Manager de seguros", "Manager de reportes"}, 0
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
        for (Agency c : agencyServices.getAllAgencies()) {
            tableModel.addRow(new Object[]{
                    c.getAgencyId(), c.getName(), c.getAddress(),
                    c.getPhone(), c.getEmail(),
                    c.getGeneralDirector(), c.getClaimsManager(), c.getInsuranceManager()
            });
        }
    }

    private void showForm(Agency existing) {
        boolean isEdit = existing != null;

        JDialog dialog = new JDialog((JFrame) null, true);
        dialog.setBounds((int) (screenSize.width*0.32), (int) (screenSize.height*0.25), (int) (screenSize.width*0.36), (int) (screenSize.height*0.5));
        dialog.setUndecorated(true);
        dialog.setResizable(false);

        JLabel header = new JLabel(isEdit ? "Editar Agencia " : "Nueva Agencia");
        header.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.014)));
        header.setBounds((int) (screenSize.width*0.1), (int) (screenSize.height*0.001), (int) (screenSize.width*0.16), (int) (screenSize.height*0.05));
        header.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel form = new JPanel(null);
        form.setBounds(0, 0  ,(int) (screenSize.width*0.4), (int) (screenSize.height*0.6));
        form.setBackground(new Color(200, 200, 200 ));
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        form.add(header);

        JLabel lName = new JLabel("Nombre");
        JTextField txtName = new JTextField(15);
        JLabel lAddress = new JLabel("Direccion");
        JTextField txtAddress = new JTextField(15);
        JLabel lPhone = new JLabel("Telefono");
        JTextField txtPhone = new JTextField(15);
        JLabel lEmail = new JLabel("Email");
        JTextField txtEmail = new JTextField(15);
        JLabel lGenDi = new JLabel("Director General");
        JTextField txtGenDir = new JTextField(15);
        JLabel lInsMa = new JLabel("Manager de Seguros");
        JTextField txtInsuranceMan = new JTextField(15);
        JLabel lClaimMa = new JLabel("Manager de Reportes");
        JTextField txtClaimsMan = new JTextField(15);


        txtName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtName.getText().length();
                if(large>=100) {
                    e.consume();
                }
            }
        });
        txtAddress.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtAddress.getText().length();
                 if(large>=200) {
                    e.consume();
                }
            }
        });
        txtPhone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtPhone.getText().length();
                char c= e.getKeyChar();
                if(!Character.isDigit(c) || large>=20) {
                    e.consume();
                }
            }
        });
        txtEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtEmail.getText().length();
                if(large>=100) {
                    e.consume();
                }
            }
        });
        txtGenDir.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtGenDir.getText().length();
                char c= e.getKeyChar();
                if(!Character.isLetter(c)  && c!=' ') {
                    e.consume();
                }else if(large>=100) {
                    e.consume();
                }
            }
        });
        txtInsuranceMan.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtInsuranceMan.getText().length();
                char c= e.getKeyChar();
                if(!Character.isLetter(c)  && c!=' ') {
                    e.consume();
                }else if(large>=100) {
                    e.consume();
                }
            }
        });
        txtClaimsMan.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtClaimsMan.getText().length();
                char c= e.getKeyChar();
                if(!Character.isLetter(c)  && c!=' ') {
                    e.consume();
                }else if(large>=100) {
                    e.consume();
                }
            }
        });
        lName.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.055), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lName.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lName.setHorizontalAlignment(SwingConstants.LEFT);
        txtName.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtName.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lAddress.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.135), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lAddress.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lAddress.setHorizontalAlignment(SwingConstants.LEFT);
        txtAddress.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.17), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtAddress.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lPhone.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.215), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lPhone.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lPhone.setHorizontalAlignment(SwingConstants.LEFT);
        txtPhone.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.25), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtPhone.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lEmail.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.295), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lEmail.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lEmail.setHorizontalAlignment(SwingConstants.LEFT);
        txtEmail.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.33), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtEmail.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));


        //segunda fila
        lGenDi.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.055), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lGenDi.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lGenDi.setHorizontalAlignment(SwingConstants.LEFT);
        txtGenDir.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtGenDir.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lInsMa.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.135), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lInsMa.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lInsMa.setHorizontalAlignment(SwingConstants.LEFT);
        txtInsuranceMan.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.17), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtInsuranceMan.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lClaimMa.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.215), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lClaimMa.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lClaimMa.setHorizontalAlignment(SwingConstants.LEFT);
        txtClaimsMan.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.25), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtClaimsMan.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        if(isEdit) {
            txtName.setText(existing.getName());
            txtAddress.setText(existing.getAddress());
            txtPhone.setText(String.valueOf(existing.getPhone()));
            txtEmail.setText(existing.getEmail());
            txtGenDir.setText(existing.getGeneralDirector());
            txtInsuranceMan.setText(existing.getInsuranceManager());
            txtClaimsMan.setText(existing.getClaimsManager());

        }

        form.add(txtName);
        form.add(txtAddress);
        form.add(txtPhone);
        form.add(txtEmail);
        form.add(txtGenDir);
        form.add(txtInsuranceMan);
        form.add(txtClaimsMan);

        form.add(lName);
        form.add(lAddress);
        form.add(lPhone);
        form.add(lEmail);
        form.add(lGenDi);
        form.add(lInsMa);
        form.add(lClaimMa);

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
            if (txtName.getText().trim().isEmpty() || txtAddress.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty() || txtGenDir.getText().trim().isEmpty() || txtInsuranceMan.getText().trim().isEmpty() || txtClaimsMan.getText().trim().isEmpty()) {
                MessagePanel mp = new MessagePanel( null, true, "Por favor llenar todos los campos antes de continuar");
                mp.setVisible(true);
                return;
            }
            Agency newAgency = new Agency((
                   isEdit ? existing.getAgencyId() :  0),
                    txtName.getText(),
                    txtAddress.getText(), txtPhone.getText(),
                    txtEmail.getText(), txtGenDir.getText(), txtInsuranceMan.getText(),  txtClaimsMan.getText());
            if (isEdit) agencyServices.updateAgency(newAgency);
            else agencyServices.saveAgency(newAgency);
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
            MessagePanel mp = new MessagePanel(null
                    , true, "Seleccione un cliente de la tabla.");
            mp.setVisible(true);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Agency a = agencyServices.findAgencyById(id);
        if (a != null) showForm(a);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessagePanel mp = new MessagePanel(null, true, "Seleccione un cliente de la tabla.");
            mp.setVisible(true); return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String name = tableModel.getValueAt(row, 1) + " " + tableModel.getValueAt(row, 2);

        int confirm = JOptionPane.showConfirmDialog(this,
                "\u00BFEliminar al cliente \"" + name + "\"?", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            agencyServices.deleteAgency(id);
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
