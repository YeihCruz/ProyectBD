package visual.panels;

import models.Agency;
import models.Role;
import models.User;
import services.RoleServices;
import services.UserServices;
import utils.Options;
import visual.UIStyles;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class UsersPanel extends JPanel {

    private final UserServices userServices = new UserServices();
    private final RoleServices roleServices = new RoleServices();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private Dimension screenSize;

    public UsersPanel() {
        screenSize = Options.getOptions().getScreenSize();
        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Users Manager");
        title.setFont(UIStyles.getCurrentFont(UIStyles.FONT_HEADER));
        title.setForeground(UIStyles.TEXT_PRIMARY);
        header.add(title, BorderLayout.WEST);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        toolbar.setOpaque(false);

        JButton btnNew = createToolbarButton("\u2795  New", UIStyles.PRIMARY, Color.WHITE);
        btnNew.addActionListener(e -> showForm(null));
        toolbar.add(btnNew);

        JButton btnEdit = createToolbarButton("\u270F\uFE0F  Edit", new Color(100, 110, 125), Color.WHITE);
        btnEdit.addActionListener(e -> editSelected());
        toolbar.add(btnEdit);

        JButton btnDelete = createToolbarButton("\uD83D\uDDD1\uFE0F  Delete", new Color(200, 70, 70), Color.WHITE);
        btnDelete.addActionListener(e -> deleteSelected());
        toolbar.add(btnDelete);

        header.add(toolbar, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new String[]{"Id", "User", "Full name", "Role", "Active"}, 0
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
        List<User> users = userServices.getAllUsers();
        List<Role> roles = roleServices.getAllRoles();

        for (User u : users) {
            String roleName = "";
            for (Role r : roles) {
                if (r.getRoleId() == u.getRoleId()) {
                    roleName = r.getName();
                    break;
                }
            }
            tableModel.addRow(new Object[]{
                u.getUserId(), u.getUsername(), u.getFullName(),
                roleName, u.isActive() ? "Active" : "Inactive"
            });
        }
    }

    private void showForm(User existing) {
        boolean isEdit = existing != null;

        JDialog dialog = new JDialog((JFrame) null, true);
        dialog.setBounds((int) (screenSize.width*0.32), (int) (screenSize.height*0.29), (int) (screenSize.width*0.36), (int) (screenSize.height*0.42));
        dialog.setUndecorated(true);
        dialog.setResizable(false);

        JLabel header = new JLabel(isEdit ? " Edit User " : "New User");
        header.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.014)));
        header.setBounds((int) (screenSize.width*0.1), (int) (screenSize.height*0.001), (int) (screenSize.width*0.16), (int) (screenSize.height*0.05));
        header.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel form = new JPanel(null);
        form.setBounds(0, 0  ,(int) (screenSize.width*0.4), (int) (screenSize.height*0.6));
        form.setBackground(new Color(200, 200, 200 ));
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        form.add(header);

        JLabel lName = new JLabel("Name");
        JTextField txtUsername = new JTextField(15);
        JLabel lPass = new JLabel("Password");
        JPasswordField txtPassword = new JPasswordField(15);
        JLabel lFullN = new JLabel("Full Name");
        JTextField txtFullName = new JTextField(15);
        JLabel lRole = new JLabel("Role");
        JComboBox<String> cmbRole = new JComboBox<>();
        JLabel lActive = new JLabel("Active");
        JCheckBox chkActive = new JCheckBox("Active User");


        List<Role> roles = roleServices.getAllRoles();
        int selectedRoleIdx = 0;
        for (int i = 0; i < roles.size(); i++) {
            cmbRole.addItem(roles.get(i).getName());
            if (isEdit && roles.get(i).getRoleId() == existing.getRoleId()) {
                selectedRoleIdx = i;
            }
        }
        cmbRole.setSelectedIndex(selectedRoleIdx);

        if (isEdit) {
            txtUsername.setText(existing.getUsername());
            txtFullName.setText(existing.getFullName());
            chkActive.setSelected(existing.isActive());
            txtPassword.setText("");
        } else {
            chkActive.setSelected(true);
        }

        lName.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.055), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lName.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lName.setHorizontalAlignment(SwingConstants.LEFT);
        txtUsername.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtUsername.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lPass.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.135), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lPass.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lPass.setHorizontalAlignment(SwingConstants.LEFT);
        txtPassword.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.17), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtPassword.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lFullN.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.215), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lFullN.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lFullN.setHorizontalAlignment(SwingConstants.LEFT);
        txtFullName.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.25), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtFullName.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lRole.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.055), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lRole.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lRole.setHorizontalAlignment(SwingConstants.LEFT);
        cmbRole.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbRole.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lActive.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.135), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lActive.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lActive.setHorizontalAlignment(SwingConstants.LEFT);
        chkActive.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.17), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        chkActive.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        form.add(lName);
        form.add(lPass);
        form.add(lFullN);
        form.add(lRole);
        form.add(lActive);

        form.add(txtUsername);
        form.add(txtPassword);
        form.add(txtFullName);
        form.add(cmbRole);
        form.add(chkActive);

        dialog.setLocationRelativeTo(null);

        JButton btnSave = UIStyles.createPrimaryButton("Save");
        JButton btnCancel = UIStyles.createSecondaryButton("Cancel") ;
        if(screenSize.width== Toolkit.getDefaultToolkit().getScreenSize().width) {
            btnSave.setBounds((int) (screenSize.width * 0.195), (int) (screenSize.height * 0.32), (int) (screenSize.width * 0.07), (int) (screenSize.height * 0.05));
            btnCancel.setBounds((int) (screenSize.width * 0.095), (int) (screenSize.height * 0.32), (int) (screenSize.width * 0.07), (int) (screenSize.height * 0.05));
        }else {
            btnSave.setBounds((int) (screenSize.width * 0.2), (int) (screenSize.height * 0.32), (int) (screenSize.width * 0.08), (int) (screenSize.height * 0.05));
            btnCancel.setBounds((int) (screenSize.width * 0.08), (int) (screenSize.height * 0.32), (int) (screenSize.width * 0.08), (int) (screenSize.height * 0.05));
        }

        btnSave.addActionListener(e -> {
            String user = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword());
            String full = txtFullName.getText().trim();

            if (user.isEmpty() || pass.isEmpty() || full.isEmpty()) {
                MessagePanel mp = new MessagePanel( null, true, "Please fill all the camps");
                mp.setVisible(true);
                return;
            }

            Role selectedRole = roles.get(cmbRole.getSelectedIndex());

            if (isEdit) {

                User updated = new User(existing.getUserId(), selectedRole.getRoleId(),
                        user, pass, full, chkActive.isSelected());
                userServices.updateUser(updated);
            } else {
                User nuevo = new User(0, selectedRole.getRoleId(),
                        user, pass, full, chkActive.isSelected());
                userServices.saveUser(nuevo);
            }

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
        dialog.setVisible(true);
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessagePanel mp = new MessagePanel( null, true, "Select one user from the table");
            mp.setVisible(true);
           return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        List<User> users = userServices.getAllUsers();
        for (User u : users) {
            if (u.getUserId() == id) {
                showForm(u);
                return;
            }
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessagePanel mp = new MessagePanel( null, true, "Select one user from the table");
            mp.setVisible(true);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String username = (String) tableModel.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "\u00BFDeactivate the user \"" + username + "\"?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            userServices.deactivateUser(id);
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
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
