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


        add(container);

    }
}
