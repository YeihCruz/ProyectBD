package visual.panels;

import models.Role;
import models.User;
import services.RoleServices;
import services.UserServices;
import visual.UIStyles;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
import java.util.List;

public class UsersPanel extends JPanel {

    private final UserServices userServices = new UserServices();
    private final RoleServices roleServices = new RoleServices();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public UsersPanel() {
        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Gesti\u00F3n de Usuarios");
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
            new String[]{"ID", "Usuario", "Nombre Completo", "Rol", "Activo"}, 0
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
                roleName, u.isActive() ? "\u2705" : "\u274C"
            });
        }
    }

    private void showForm(User existing) {
        boolean isEdit = existing != null;

        JDialog dialog = new JDialog((JFrame) null, isEdit ? "Editar Usuario" : "Nuevo Usuario", true);
        dialog.setSize(420, 420);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UIStyles.CARD_BG);
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JTextField txtUsername = new JTextField(15);
        JPasswordField txtPassword = new JPasswordField(15);
        JTextField txtFullName = new JTextField(15);
        JComboBox<String> cmbRole = new JComboBox<>();
        JCheckBox chkActive = new JCheckBox("Usuario activo");

        UIStyles.styleField(txtUsername);
        UIStyles.styleField(txtPassword);
        UIStyles.styleField(txtFullName);

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
            txtPassword.setText(existing.getPassword());
        } else {
            chkActive.setSelected(true);
        }

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 4, 0);

        form.add(UIStyles.createFieldLabel("Usuario"), c);
        c.gridy = 1; c.insets = new Insets(0, 0, 10, 0);
        form.add(txtUsername, c);

        c.gridy = 2; c.insets = new Insets(0, 0, 4, 0);
        form.add(UIStyles.createFieldLabel("Contrase\u00F1a"), c);
        c.gridy = 3; c.insets = new Insets(0, 0, 10, 0);
        form.add(txtPassword, c);

        c.gridy = 4; c.insets = new Insets(0, 0, 4, 0);
        form.add(UIStyles.createFieldLabel("Nombre Completo"), c);
        c.gridy = 5; c.insets = new Insets(0, 0, 10, 0);
        form.add(txtFullName, c);

        c.gridy = 6; c.insets = new Insets(0, 0, 4, 0);
        form.add(UIStyles.createFieldLabel("Rol"), c);
        c.gridy = 7; c.insets = new Insets(0, 0, 10, 0);
        form.add(cmbRole, c);

        c.gridy = 8; c.insets = new Insets(0, 0, 0, 0);
        chkActive.setFont(UIStyles.FONT_BODY);
        chkActive.setBackground(UIStyles.CARD_BG);
        form.add(chkActive, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);

        JButton btnSave = UIStyles.createPrimaryButton("Guardar");
        JButton btnCancel = UIStyles.createSecondaryButton("Cancelar");

        btnSave.addActionListener(e -> {
            String user = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword());
            String full = txtFullName.getText().trim();

            if (user.isEmpty() || pass.isEmpty() || full.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Complete todos los campos.", "Validaci\u00F3n", JOptionPane.WARNING_MESSAGE);
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

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla.", "Editar", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla.", "Eliminar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String username = (String) tableModel.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "\u00BFDesactivar al usuario \"" + username + "\"?", "Confirmar",
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
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return btn;
    }

    private void styleTable(JTable t) {
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
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
        t.setIntercellSpacing(new Dimension(10, 0));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        t.getColumnModel().getColumn(0).setMaxWidth(60);
        t.getColumnModel().getColumn(1).setMinWidth(100);
        t.getColumnModel().getColumn(4).setMaxWidth(70);
    }
}
