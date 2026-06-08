package visual.panels;

import models.*;
import services.*;
import visual.UIStyles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ReinsurersPanel extends JPanel {

    private final ReinsuranceServices reinsuranceServices = new ReinsuranceServices();
    private final ReinsuranceTypeServices reinsuranceTypeServices = new ReinsuranceTypeServices();
    private final CountryServices countryServices = new CountryServices();
    private final AgencyServices agencyServices = new AgencyServices();
    private Dimension screenSize;

    private final JTable table;
    private final DefaultTableModel tableModel;

    public ReinsurersPanel() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBackground(UIStyles.BG_LIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Gesti\u00F3n de Reaseguradoras");
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
                new String[]{"Id", "Agencia ", "Tipo de Reaseguramiento", "Pais", "Nombre"}, 0
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
        List<Reinsurer> reinsurers = reinsuranceServices.getAllReinsurers();
        List<ReinsuranceType> types = reinsuranceTypeServices.getAllReinsuranceTypes();
        List<Country> countries = countryServices.getAllCountries();
        List<Agency> agencies =agencyServices.getAllAgencies();



        for (Reinsurer cl : reinsurers) {
            String typeName = "";
            String agency = "";
            String country = "";
            for (ReinsuranceType t : types) {
                if (t.getReinsuranceTypeId() == cl.getReinsuranceTypeId()) {
                    typeName = t.getDescription();
                    break;
                }
            }
            for (Country c : countries) {
                if (c.getCountryId() == cl.getCountryId()) {
                    country = c.getName();
                    break;
                }
            }
            for (Agency a: agencies){
                if(a.getAgencyId() == cl.getAgencyId()){
                    agency= a.getName();
                    break;
                }
            }

            tableModel.addRow(new Object[]{
                cl.getReinsurerId(),agency, typeName, country,
                cl.getName()
            });
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
        t.setRowHeight(32);
        t.setShowGrid(true);
        t.setGridColor(UIStyles.BORDER_LIGHT);
        t.setSelectionBackground(UIStyles.PRIMARY_LIGHT);
        t.setSelectionForeground(UIStyles.TEXT_PRIMARY);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    private void showForm(Reinsurer existing) {
        boolean isEdit = existing != null;

        JDialog dialog = new JDialog((JFrame) null, true);
        dialog.setBounds((int) (screenSize.width * 0.32), (int) (screenSize.height * 0.33), (int) (screenSize.width * 0.36), (int) (screenSize.height * 0.34));
        dialog.setUndecorated(true);
        dialog.setResizable(false);

        JLabel header = new JLabel(isEdit ? "Editar Reaseguradora " : "Nueva Reaseguradora");
        header.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width * 0.014)));
        header.setBounds((int) (screenSize.width * 0.1), (int) (screenSize.height * 0.001), (int) (screenSize.width * 0.16), (int) (screenSize.height * 0.05));
        header.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel form = new JPanel(null);
        form.setBounds(0, 0, (int) (screenSize.width * 0.4), (int) (screenSize.height * 0.6));
        form.setBackground(new Color(200, 200, 200));
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        form.add(header);

        JLabel lName = new JLabel("Nombre");
        JLabel lType = new JLabel("Tipo de Reaseguramiento");
        JLabel lCountry = new JLabel("Pais");
        JLabel lAgency = new JLabel("Agencia");


        JComboBox<String> cmbType = new JComboBox<>();
        JComboBox<String> cmbCountry = new JComboBox<>();
        JComboBox<String> cmbAgency = new JComboBox<>();
        JTextField txtName = new JTextField(12);

        txtName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int large = txtName.getText().length();
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && (c != '.')) {
                    e.consume();
                } else if (large >= 11) {
                    e.consume();
                }
            }
        });

        List<ReinsuranceType> reinsuranceTypes = reinsuranceTypeServices.getAllReinsuranceTypes();
        List<Country> countrs = countryServices.getAllCountries();
        List<Agency> agencs = agencyServices.getAllAgencies();

        for (ReinsuranceType rt : reinsuranceTypes) cmbType.addItem(rt.toString());
        for (Country c : countrs) cmbCountry.addItem(c.getName());
        for (Agency a : agencs) cmbAgency.addItem(a.getName());

        lName.setBounds((int) (screenSize.width * 0.025), (int) (screenSize.height * 0.055), (int) (screenSize.width * 0.14), (int) (screenSize.height * 0.04));
        lName.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width * 0.0115)));
        lName.setHorizontalAlignment(SwingConstants.LEFT);
        txtName.setBounds((int) (screenSize.width * 0.025), (int) (screenSize.height * 0.09), (int) (screenSize.width * 0.14), (int) (screenSize.height * 0.04));
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, (int) (screenSize.width * 0.01)));

        lType.setBounds((int) (screenSize.width * 0.025), (int) (screenSize.height * 0.135), (int) (screenSize.width * 0.14), (int) (screenSize.height * 0.04));
        lType.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width * 0.0115)));
        lType.setHorizontalAlignment(SwingConstants.LEFT);
        cmbType.setBounds((int) (screenSize.width * 0.025), (int) (screenSize.height * 0.17), (int) (screenSize.width * 0.14), (int) (screenSize.height * 0.04));
        cmbType.setFont(new Font("Segoe UI", Font.PLAIN, (int) (screenSize.width * 0.01)));

        lCountry.setBounds((int) (screenSize.width * 0.195), (int) (screenSize.height * 0.055), (int) (screenSize.width * 0.14), (int) (screenSize.height * 0.04));
        lCountry.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width * 0.0115)));
        lCountry.setHorizontalAlignment(SwingConstants.LEFT);
        cmbCountry.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.09), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbCountry.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        lAgency.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.135), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        lAgency.setFont(new Font("Segoe UI", Font.BOLD, (int) (screenSize.width*0.0115)));
        lAgency.setHorizontalAlignment(SwingConstants.LEFT);
        cmbAgency.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.17), (int) (screenSize.width*0.14), (int) (screenSize.height*0.04));
        cmbAgency.setFont(new Font( "Segoe UI", Font.PLAIN, (int) (screenSize.width*0.01)));

        if (isEdit) {
            for (int i = 0; i < reinsuranceTypes.size(); i++)
                if (reinsuranceTypes.get(i).getReinsuranceTypeId() == existing.getReinsuranceTypeId()) cmbCountry.setSelectedIndex(i);
            for (int i = 0; i < countrs.size(); i++)
                if (countrs.get(i).getCountryId() == existing.getCountryId()) cmbType.setSelectedIndex(i);
            for (int i = 0; i < agencs.size(); i++)
                if (agencs.get(i).getAgencyId() == existing.getAgencyId()) cmbAgency.setSelectedIndex(i);
            txtName.setText(String.valueOf(existing.getName()));
             }

        form.add(txtName);
        form.add(cmbType);
        form.add(cmbCountry);
        form.add(cmbAgency);

        form.add(lName);
        form.add(lType);
        form.add(lCountry);
        form.add(lAgency);


        JButton btnSave = UIStyles.createPrimaryButton("Guardar");
        btnSave.setBounds((int) (screenSize.width*0.195), (int) (screenSize.height*0.24), (int) (screenSize.width*0.07), (int) (screenSize.height*0.05));
        JButton btnCancel = UIStyles.createSecondaryButton("Cancelar");
        btnCancel.setBounds((int) (screenSize.width*0.095), (int) (screenSize.height*0.24), (int) (screenSize.width*0.07), (int) (screenSize.height*0.05));

        btnSave.addActionListener(e -> {
            try {
               String name = txtName.getText().trim();
               int typeId = reinsuranceTypes.get(cmbCountry.getSelectedIndex()).getReinsuranceTypeId();
               int countryId = countrs.get(cmbType.getSelectedIndex()).getCountryId();
               int agencyIdId = agencs.get(cmbAgency.getSelectedIndex()).getAgencyId();

                Reinsurer r = new Reinsurer(
                        isEdit ? existing.getReinsurerId() : 0,
                        agencyIdId, typeId, countryId, name
                );

                if (isEdit) reinsuranceServices.updateReinsurer(r);
                else reinsuranceServices.saveReinsurer(r);

                dialog.dispose();
                loadData();

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


    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessagePanel mp = new MessagePanel(null, true ,"Seleccione una reaseguradora de la tabla.");
            mp.setVisible(true);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Reinsurer r = reinsuranceServices.findReinsurerById(id);
        if (r != null) showForm(r);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessagePanel mp = new MessagePanel(null, true ,"Seleccione una reaseguradora de la tabla.");
            mp.setVisible(true);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "\u00BFEliminar la reaseguradora #" + id + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            reinsuranceServices.deleteReinsurer(id);
            loadData();
        }
    }


}
