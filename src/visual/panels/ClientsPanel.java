package visual.panels;

import models.Agency;
import models.Client;
import models.Country;
import models.Gender;
import services.AgencyServices;
import services.ClientServices;
import services.CountryServices;
import services.GenderServices;
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
import java.util.List;

public class ClientsPanel extends JPanel {

    private final ClientServices clientServices = new ClientServices();
    private final AgencyServices agencyServices = new AgencyServices();
    private final GenderServices genderServices = new GenderServices();
    private final CountryServices countryServices = new CountryServices();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ClientsPanel() {
        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Gesti\u00F3n de Clientes");
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
            new String[]{"ID", "Nombres", "Apellidos", "Identificaci\u00F3n", "Edad", "Tel\u00E9fono", "Email"}, 0
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
        for (Client c : clientServices.getAllClients()) {
            tableModel.addRow(new Object[]{
                c.getClientId(), c.getFirstName(), c.getLastName(),
                c.getIdentificationNumber(), c.getAge(),
                c.getPhone(), c.getEmail()
            });
        }
    }

    private void showForm(Client existing) {
        boolean isEdit = existing != null;

        JDialog dialog = new JDialog((JFrame) null, isEdit ? "Editar Cliente" : "Nuevo Cliente", true);
        dialog.setSize(480, 520);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UIStyles.CARD_BG);
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        JTextField txtFirstName = new JTextField(15);
        JTextField txtLastName = new JTextField(15);
        JTextField txtIdNumber = new JTextField(15);
        JTextField txtAge = new JTextField(15);
        JTextField txtAddress = new JTextField(15);
        JTextField txtPhone = new JTextField(15);
        JTextField txtEmail = new JTextField(15);

        JComboBox<String> cmbAgency = new JComboBox<>();
        JComboBox<String> cmbGender = new JComboBox<>();
        JComboBox<String> cmbCountry = new JComboBox<>();

        List<Agency> agencies = agencyServices.getAllAgencies();
        List<Gender> genders = genderServices.getAllGenders();
        List<Country> countries = countryServices.getAllCountries();

        for (Agency a : agencies) cmbAgency.addItem(a.getName());
        for (Gender g : genders) cmbGender.addItem(g.getDescription());
        for (Country c : countries) cmbCountry.addItem(c.getName());

        JTextField[] fields = {txtFirstName, txtLastName, txtIdNumber, txtAge, txtAddress, txtPhone, txtEmail};
        for (JTextField f : fields) UIStyles.styleField(f);

        if (isEdit) {
            txtFirstName.setText(existing.getFirstName());
            txtLastName.setText(existing.getLastName());
            txtIdNumber.setText(existing.getIdentificationNumber());
            txtAge.setText(String.valueOf(existing.getAge()));
            txtAddress.setText(existing.getAddress());
            txtPhone.setText(existing.getPhone());
            txtEmail.setText(existing.getEmail());

            for (int i = 0; i < agencies.size(); i++)
                if (agencies.get(i).getAgencyId() == existing.getAgencyId()) cmbAgency.setSelectedIndex(i);
            for (int i = 0; i < genders.size(); i++)
                if (genders.get(i).getGenderId() == existing.getGenderId()) cmbGender.setSelectedIndex(i);
            for (int i = 0; i < countries.size(); i++)
                if (countries.get(i).getCountryId() == existing.getCountryId()) cmbCountry.setSelectedIndex(i);
        }

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.WEST;

        int row = 0;
        addField(form, c, row++, "Nombres", txtFirstName);
        addField(form, c, row++, "Apellidos", txtLastName);
        addField(form, c, row++, "Identificaci\u00F3n", txtIdNumber);
        addField(form, c, row++, "Edad", txtAge);
        addField(form, c, row++, "Direcci\u00F3n", txtAddress);
        addField(form, c, row++, "Tel\u00E9fono", txtPhone);
        addField(form, c, row++, "Email", txtEmail);

        addField(form, c, row++, "Agencia", cmbAgency);
        addField(form, c, row++, "G\u00E9nero", cmbGender);
        addField(form, c, row, "Pa\u00EDs", cmbCountry);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);

        JButton btnSave = UIStyles.createPrimaryButton("Guardar");
        JButton btnCancel = UIStyles.createSecondaryButton("Cancelar");

        btnSave.addActionListener(e -> {
            if (txtFirstName.getText().trim().isEmpty() || txtLastName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Nombre y apellidos son obligatorios.", "Validaci\u00F3n", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int age;
            try { age = Integer.parseInt(txtAge.getText().trim().isEmpty() ? "0" : txtAge.getText().trim()); }
            catch (NumberFormatException ex) { age = 0; }

            int agencyId = agencies.get(cmbAgency.getSelectedIndex()).getAgencyId();
            int genderId = genders.get(cmbGender.getSelectedIndex()).getGenderId();
            int countryId = countries.get(cmbCountry.getSelectedIndex()).getCountryId();

            Client newClient = new Client(
                isEdit ? existing.getClientId() : 0,
                agencyId, genderId, countryId,
                txtFirstName.getText().trim(), txtLastName.getText().trim(),
                txtIdNumber.getText().trim(), age,
                txtAddress.getText().trim(), txtPhone.getText().trim(), txtEmail.getText().trim()
            );

            if (isEdit) clientServices.updateClient(newClient);
            else clientServices.saveClient(newClient);

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
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.", "Editar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Client c = clientServices.findClientById(id);
        if (c != null) showForm(c);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.", "Eliminar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String name = tableModel.getValueAt(row, 1) + " " + tableModel.getValueAt(row, 2);

        int confirm = JOptionPane.showConfirmDialog(this,
                "\u00BFEliminar al cliente \"" + name + "\"?", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            clientServices.deleteClient(id);
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
        t.getColumnModel().getColumn(0).setMaxWidth(50);
    }
}
