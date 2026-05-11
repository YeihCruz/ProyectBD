import dataBase.DataBaseConnection;
import models.User;
import services.UserServices;

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

                // =====================================================
                // CHECK IF ADMIN EXISTS
                // =====================================================

                if (!userServices.existsAdmin()) {

                    System.out.println("No admin found. Creating default admin...");

                    User admin = new User(
                            0,
                            1, // role_id = ADMIN
                            "admin",
                            "admin", // plain password (service will hash it)
                            "System Administrator",
                            true
                    );

                    userServices.saveUser(admin);

                    System.out.println("Default admin created:");
                    System.out.println("username: admin");
                    System.out.println("password: admin");



                } else {
                    System.out.println("Admin already exists.");
                    System.out.println(userServices.getAllUsers().get(0).getUsername() + "    " + userServices.getAllUsers().get(0).getPassword() );
                }

                System.out.println("Connection closed successfully");
            }

        } catch (SQLException e) {

            System.out.println("DATABASE CONNECTION FAILED");
            e.printStackTrace();
        }
    }
}