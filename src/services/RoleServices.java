package services;

import dataBase.DataBaseConnection;
import models.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleServices {

    // =====================================================
    // SAVE ROLE
    // =====================================================

    public void saveRole(Role role) {

        String sql =
                "INSERT INTO role (name) VALUES (?)";

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, role.getName());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Role saved successfully");
            }

        } catch (SQLException e) {
            System.out.println("Error saving role");
            e.printStackTrace();
        }
    }

    // =====================================================
    // GET ALL ROLES
    // =====================================================

    public List<Role> getAllRoles() {

        List<Role> roles = new ArrayList<>();

        String sql = "SELECT * FROM role";

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Role role = new Role(
                        resultSet.getInt("role_id"),
                        resultSet.getString("name")
                );

                roles.add(role);
            }

        } catch (SQLException e) {
            System.out.println("Error getting roles");
            e.printStackTrace();
        }

        return roles;
    }
}