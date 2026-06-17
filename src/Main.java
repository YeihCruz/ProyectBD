import dataBase.DataBaseConnection;
import services.CreateDataServices;
import services.UserServices;
import visual.Visual;

import java.sql.Connection;
import java.sql.SQLException;

import services.ReportsServices;

public class Main {

    public static void main(String[] args) {

        try (Connection connection =
                     DataBaseConnection.getConnection()) {

            if (connection != null) {

                System.out.println("=================================");
                System.out.println("DATABASE CONNECTION SUCCESSFUL");
                System.out.println("=================================");

                createData();

                System.out.println("Connection closed successfully");

                Visual.showLogin();
            }

        } catch (SQLException e) {

            System.out.println("DATABASE CONNECTION FAILED");
            e.printStackTrace();
        }
    }

    private static void createData() {
        CreateDataServices createDataServices = new CreateDataServices();
        createDataServices.checkAll();

    }
}