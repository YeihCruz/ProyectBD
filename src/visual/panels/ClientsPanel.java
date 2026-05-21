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
import java.awt.*;
import java.util.List;

public class ClientsPanel extends JPanel {

    private final ClientServices clientServices = new ClientServices();
    private final AgencyServices agencyServices = new AgencyServices();
    private final GenderServices genderServices = new GenderServices();
    private final CountryServices countryServices = new CountryServices();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private Dimension screenSize;

    public ClientsPanel() {
        setBackground(UIStyles.BG_LIGHT);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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

        JDialog dialog = new JDialog((JFrame) null, true);
        dialog.setBounds((int) (screenSize.width*0.32), (int) (screenSize.height*0.21), (int) (screenSize.width*0.36), (int) (screenSize.height*0.58));
        dialog.setUndecorated(true);
        dialog.setResizable(false);

        JLabel header = new JLabel(isEdit ? "Editar Cliente " : "Nuevo Cliente");
        header.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.014)));
        header.setBounds((int) (screenSize.width*0.1), (int) (screenSize.height*0.001), (int) (screenSize.width*0.16), (int) (screenSize.height*0.05));
        header.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel form = new JPanel(null);
        form.setBounds(0, 0  ,(int) (screenSize.width*0.4), (int) (screenSize.height*0.6));
        form.setBackground(new Color(200, 200, 200 ));
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        form.add(header);

        JLabel lFirstNAme = new JLabel("Primer Nombre");
        JLabel lLastName = new JLabel("Apellido");
        JLabel lIdNumb = new JLabel("Numero de Id");
        JLabel lAge = new JLabel("Edad");
        JLabel lAddress = new JLabel("Direcci\u00F3n");
        JLabel lPhone = new JLabel("Telefono");
        JLabel lEmail = new JLabel("Email");
        JLabel lAgency = new JLabel("Agencia");
        JLabel lGender = new JLabel("Genero");
        JLabel lCountry = new JLabel("Pais");

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

        lFirstNAme.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.055), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lFirstNAme.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lFirstNAme.setHorizontalAlignment(SwingConstants.LEFT);
        txtFirstName.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtFirstName.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lLastName.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.135), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lLastName.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lLastName.setHorizontalAlignment(SwingConstants.LEFT);
        txtLastName.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.17), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtLastName.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lIdNumb.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.215), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lIdNumb.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lIdNumb.setHorizontalAlignment(SwingConstants.LEFT);
        txtIdNumber.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.25), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtIdNumber.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lAge.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.295), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lAge.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lAge.setHorizontalAlignment(SwingConstants.LEFT);
        txtAge.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.33), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtAge.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lAddress.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.375), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lAddress.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lAddress.setHorizontalAlignment(SwingConstants.LEFT);
        txtAddress.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.41), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtAddress.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        //segunda fila
        lPhone.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.055), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lPhone.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lPhone.setHorizontalAlignment(SwingConstants.LEFT);
        txtPhone.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtPhone.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lEmail.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.135), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lEmail.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lEmail.setHorizontalAlignment(SwingConstants.LEFT);
        txtEmail.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.17), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        txtEmail.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lAgency.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.215), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lAgency.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lAgency.setHorizontalAlignment(SwingConstants.LEFT);
        cmbAgency.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.25), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbAgency.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lGender.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.295), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lGender.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lGender.setHorizontalAlignment(SwingConstants.LEFT);
        cmbGender.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.33), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbGender.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lCountry.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.375), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lCountry.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lCountry.setHorizontalAlignment(SwingConstants.LEFT);
        cmbCountry.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.41), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbCountry.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));


        for (Agency a : agencies) cmbAgency.addItem(a.getName());
        for (Gender g : genders) cmbGender.addItem(g.getDescription());
        for (Country c : countries) cmbCountry.addItem(c.getName());

        JOptionPane.showMessageDialog(null, agencies);
        JOptionPane.showMessageDialog(null, genders);
        JOptionPane.showMessageDialog(null, countries);
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
        form.add(txtFirstName);
        form.add(txtLastName);
        form.add(txtIdNumber);
        form.add(txtAge);
        form.add(txtAddress);
        form.add(txtPhone);
        form.add(txtEmail);
        form.add(cmbAgency);
        form.add(cmbGender);
        form.add(cmbCountry);


        form.add(lFirstNAme);
        form.add(lLastName);
        form.add(lIdNumb);
        form.add(lAge);
        form.add(lAddress);
        form.add(lPhone);
        form.add(lEmail);
        form.add(lAgency);
        form.add(lGender);
        form.add(lCountry);


        JButton btnSave = UIStyles.createPrimaryButton("Guardar");
        btnSave.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.48), (int) (screenSize.width*0.07), (int) (screenSize.height*0.05));

        JButton btnCancel = UIStyles.createSecondaryButton("Cancelar");
        btnCancel.setBounds((int) (screenSize.width*0.095), (int) (screenSize.height*0.48), (int) (screenSize.width*0.07), (int) (screenSize.height*0.05));

        btnSave.addActionListener(e -> {
            if (txtFirstName.getText().trim().isEmpty() || txtLastName.getText().trim().isEmpty()) {
                MessagePanel mp = new MessagePanel( null, true, "Nombre y apellido son obligatorios");
                mp.setVisible(true);
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

        form.add(btnCancel);
        form.add(btnSave);

        dialog.add(form);
        dialog.setVisible(true);
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessagePanel mp = new MessagePanel( null, true, "Seleccione un cliente de la tabla");
            mp.setVisible(true);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Client c = clientServices.findClientById(id);
        if (c != null) showForm(c);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessagePanel mp = new MessagePanel( null, true, "Seleccione un cliente de la tabla");
            mp.setVisible(true);
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
