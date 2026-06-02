import dataBase.DataBaseConnection;
import models.User;
import services.UserServices;
import visual.Visual;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

import reports.ClientReport;
import services.ReportsServices;

import java.util.List;

public class Main {

    public static void main(String[] args) {

// pruebas de reportes

        ReportsServices reportsServicesClient = new ReportsServices();

        reportsServicesClient.printClientsReport();

        ReportsServices reportsServicesPolicy = new ReportsServices();

        reportsServicesPolicy.printPoliciesReport();

        ReportsServices reportsServicesClaim = new ReportsServices();

        reportsServicesClaim.printClaimsReport();



        //prueba conexiom BD
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
        JOptionPane.showMessageDialog(null, userServices.existsAdmin());
        System.out.println("No admin found. Creating default admin...");

        User admin = new User(
                0,
                1,
                "admin",
                "admin",
                "System Administrator",
                true
        );

        JOptionPane.showMessageDialog(null, admin.getUsername() + admin.getFullName() + admin.getPassword() + admin.getUserId());
        userServices.saveUser(admin);

        System.out.println("Default admin created:");
        System.out.println("username: admin");
        System.out.println("password: admin");
    }
}