package visual;

import javax.swing.SwingUtilities;

public class Visual {

    public static void showLogin() {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}
