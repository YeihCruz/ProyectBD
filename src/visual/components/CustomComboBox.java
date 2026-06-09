package visual.components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class CustomComboBox extends JComboBox {
        public JComboBox<String> customComboBox() {
            JComboBox<String> comboBox = new JComboBox<String>() {
                @Override
                public void updateUI() {
                    setUI(new BasicComboBoxUI() {
                        @Override
                        protected JButton createArrowButton() {
                            JButton button = new JButton() {
                                @Override
                                public void paint(Graphics g) {
                                }
                            };
                            button.setPreferredSize(new Dimension(0, 0));
                            button.setVisible(false);
                            return button;
                        }
                    });
                }
            };
            comboBox.setFocusable(false);
            return comboBox;
        }
}
