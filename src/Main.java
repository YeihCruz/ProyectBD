import dataBase.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try {

            Connection connection = DataBaseConnection.getConnection();

            if (connection != null) {

                System.out.println("=================================");
                System.out.println("DATABASE CONNECTION SUCCESSFUL");
                System.out.println("Connected to PostgreSQL 15");
                System.out.println("=================================");

                connection.close();

                System.out.println("Connection closed successfully");
            }

        } catch (SQLException e) {

            System.out.println("DATABASE CONNECTION FAILED");
            e.printStackTrace();
        }
    }
}