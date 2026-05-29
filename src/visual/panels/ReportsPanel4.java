package visual.panels;


import javax.swing.*;
import java.awt.*;

public class ReportsPanel4 extends JPanel {
    private Dimension screenSize;

    public ReportsPanel4() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(0, 0,  (int) (screenSize.width*0.89), (int) (screenSize.height*0.425));
        setLayout(null);
        setBackground(Color.PINK);
    }
}