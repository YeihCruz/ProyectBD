import dataBase.DataBaseConnection;
import models.User;
import services.UserServices;
import visual.Visual;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try (Connection connection =
                     DataBaseConnection.getConnection()) {

            if (connection != null) {

                System.out.println("=================================");
                System.out.println("DATABASE CONNECTION SUCCESSFUL");
                System.out.println("=================================");

                UserServices userServices = new UserServices();

                ensureDefaultAdmin(userServices);

                System.out.println("Connection closed successfully");
                Visual.showLogin();
            }

        } catch (SQLException e) {

            System.out.println("DATABASE CONNECTION FAILED");
            e.printStackTrace();
        }
    }

    private static void ensureDefaultAdmin(UserServices userServices) {
        if (userServices.existsAdmin()) {
            System.out.println("Admin already exists.");
            return;
        }

        System.out.println("No admin found. Creating default admin...");

        User admin = new User(
                0,
                1,
                "admin",
                "admin",
                "System Administrator",
                true
        );

        userServices.saveUser(admin);

        System.out.println("Default admin created:");
        System.out.println("username: admin");
        System.out.println("password: admin");
    }
}