package visual.panels;

import utils.Options;
import visual.UIStyles;
import visual.components.CustomComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsPanel extends JPanel {
    private Dimension screenSize;

    public OptionsPanel() {
        screenSize = Options.getOptions().getScreenSize();
        setBounds(0, 0, screenSize.width, (int) (screenSize.height * 0.94));
        setLayout(null);

        setBackground(UIStyles.BG_LIGHT);
        setLayout(null);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Opciones");
        title.setFont(UIStyles.getCurrentFont(UIStyles.FONT_HEADER));
        title.setForeground(UIStyles.TEXT_PRIMARY);
        title.setBounds((int) (screenSize.width * 0.02), (int) (screenSize.height * 0.04), (int) (screenSize.width * 0.15), (int) (screenSize.height * 0.06));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(title);

        JPanel container = new JPanel(null);
        container.setBounds((int) (screenSize.width * 0.04), (int) (screenSize.height * 0.11), (int) (screenSize.width * 0.92), (int) (screenSize.height * 0.79));
        container.setBackground(UIStyles.BORDER);

        JLabel scale = new JLabel("Tamaño de la Pantalla");
        scale.setFont(new Font("Segoe UI", Font.PLAIN, ((int) (screenSize.width * 0.013))));
        scale.setForeground(UIStyles.TEXT_PRIMARY);
        scale.setHorizontalAlignment(SwingConstants.CENTER);
        scale.setVerticalAlignment(SwingConstants.CENTER);
        scale.setBounds((int) (screenSize.width * 0.01), (int) (screenSize.height * 0.053), (int) (screenSize.width * 0.18), (int) (screenSize.height * 0.05));
        scale.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        container.add(scale);

        JComboBox<String> comboScale = new CustomComboBox().customComboBox();
        comboScale.addItem("100 %");
        comboScale.addItem("90 %");
        comboScale.addItem("75 %");
        comboScale.addItem("50 %");
        comboScale.addItem("25 %");

        comboScale.setSelectedIndex(Options.getOptions().getCurrentSize());
        comboScale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboScale.getSelectedIndex()!= Options.getOptions().getCurrentSize()){
                    Options.getOptions().changeAplicationSize(comboScale.getSelectedIndex());
                  }
            }
        });
        comboScale.setFont(new Font("Segoe UI", Font.PLAIN, ((int) (screenSize.width * 0.013))));
        comboScale.setBounds((int) (screenSize.width * 0.21), (int) (screenSize.height * 0.045), (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.05));
        container.add(comboScale);

        JLabel changeTheme = new JLabel("Estilo Visual");
        changeTheme.setFont(new Font("Segoe UI", Font.PLAIN, ((int) (screenSize.width * 0.013))));
        changeTheme.setForeground(UIStyles.TEXT_PRIMARY);
        changeTheme.setHorizontalAlignment(SwingConstants.CENTER);
        changeTheme.setVerticalAlignment(SwingConstants.CENTER);
        changeTheme.setBounds((int) (screenSize.width * 0.01), (int) (screenSize.height * 0.15), (int) (screenSize.width * 0.18), (int) (screenSize.height * 0.05));
        changeTheme.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        container.add(changeTheme);

        JComboBox<String> comboTheme = new CustomComboBox().customComboBox();
        comboTheme.addItem("Normal");
        comboTheme.addItem("Nocturno");

        comboTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Falta por resolver");
            }
        });
        comboTheme.setFont(new Font("Segoe UI", Font.PLAIN, ((int) (screenSize.width * 0.013))));
        comboTheme.setBounds((int) (screenSize.width * 0.21), (int) (screenSize.height * 0.14), (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.05));
        container.add(comboTheme);

        JLabel changeSex = new JLabel("Modificar Tabla de Sexos");
        changeSex.setFont(new Font("Segoe UI", Font.PLAIN, ((int) (screenSize.width * 0.013))));
        changeSex.setForeground(UIStyles.TEXT_PRIMARY);
        changeSex.setHorizontalAlignment(SwingConstants.CENTER);
        changeSex.setBounds((int) (screenSize.width * 0.01), (int) (screenSize.height * 0.247), (int) (screenSize.width * 0.18), (int) (screenSize.height * 0.05));
        changeSex.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        changeSex.setVerticalAlignment(SwingConstants.CENTER);
        container.add(changeSex);

        JButton changeSexButton = UIStyles.createPrimaryButton("Modificar");
        changeSexButton.setBounds((int) (screenSize.width * 0.21), (int) (screenSize.height * 0.334), (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.05));
        changeSexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Cambiar Sexos");
            }
        });
        container.add(changeSexButton);

        JLabel changeCountry = new JLabel("Modificar Tabla de Paises");
        changeCountry.setFont(new Font("Segoe UI", Font.PLAIN, ((int) (screenSize.width * 0.013))));
        changeCountry.setForeground(UIStyles.TEXT_PRIMARY);
        changeCountry.setHorizontalAlignment(SwingConstants.CENTER);
        changeCountry.setBounds((int) (screenSize.width * 0.01), (int) (screenSize.height * 0.344), (int) (screenSize.width * 0.18), (int) (screenSize.height * 0.05));
        changeCountry.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        changeCountry.setVerticalAlignment(SwingConstants.CENTER);
        container.add(changeCountry);

        JButton changeCountryBut = UIStyles.createPrimaryButton("Modificar");
        changeCountryBut.setBounds((int) (screenSize.width * 0.21), (int) (screenSize.height * 0.237), (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.05));
        changeCountryBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Cambiar Paises");
            }
        });
        container.add(changeCountryBut);

        /*JLabel changeCoverage = new JLabel("Modificar Coberturas");
        changeCoverage.setFont(new Font("Segoe UI", Font.PLAIN, ((int) (screenSize.width * 0.014))));
        changeCoverage.setForeground(UIStyles.TEXT_PRIMARY);
        changeCoverage.setHorizontalAlignment(SwingConstants.CENTER);
        changeCoverage.setBounds((int) (screenSize.width * 0.01), (int) (screenSize.height * 0.247), (int) (screenSize.width * 0.18), (int) (screenSize.height * 0.05));
        changeCoverage.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        changeCoverage.setVerticalAlignment(SwingConstants.CENTER);
        container.add(changeCoverage);

        JButton changeSexButton = UIStyles.createPrimaryButton("Modificar");
        changeSexButton.setBounds((int) (screenSize.width * 0.21), (int) (screenSize.height * 0.237), (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.05));
        changeSexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Cambiar Sexos");
            }
        });
        container.add(changeSexButton);*/

        add(container);

    }
}
