package services;

import dataBase.DataBaseConnection;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServices {

    // =====================================================
    // SAVE USER (CON HASH)
    // =====================================================

    public void saveUser(User user) {

        String sql =
                "INSERT INTO \"user\" (" +
                        "role_id, username, password, full_name, active" +
                        ") VALUES (?, ?, ?, ?, ?)";

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, user.getRoleId());
            statement.setString(2, user.getUsername());

            // 🔐 HASH PASSWORD
            String hashedPassword =
                    BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            statement.setString(3, hashedPassword);

            statement.setString(4, user.getFullName());
            statement.setBoolean(5, user.isActive());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("User saved successfully");
            }

        } catch (SQLException e) {
            System.out.println("Error saving user");
            e.printStackTrace();
        }
    }

    // =====================================================
    // LOGIN USER (CON CHECK HASH)
    // =====================================================

    public User login(String username, String password) {

        String sql =
                "SELECT * FROM \"user\" WHERE username = ? AND active = true";

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                String hashedPassword =
                        rs.getString("password");

                // 🔐 VALIDAR PASSWORD
                if (BCrypt.checkpw(password, hashedPassword)) {

                    return new User(
                            rs.getInt("user_id"),
                            rs.getInt("role_id"),
                            rs.getString("username"),
                            hashedPassword,
                            rs.getString("full_name"),
                            rs.getBoolean("active")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error during login");
            e.printStackTrace();
        }

        return null;
    }

    // =====================================================
    // GET ALL USERS
    // =====================================================

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM \"user\"";

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet rs =
                     statement.executeQuery()) {

            while (rs.next()) {

                User user = new User(
                        rs.getInt("user_id"),
                        rs.getInt("role_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getBoolean("active")
                );

                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error getting users");
            e.printStackTrace();
        }

        return users;
    }

    // =====================================================
    // UPDATE USER (CON HASH)
    // =====================================================

    public void updateUser(User user) {

        String sql =
                "UPDATE \"user\" SET " +
                        "role_id = ?, " +
                        "username = ?, " +
                        "password = ?, " +
                        "full_name = ?, " +
                        "active = ? " +
                        "WHERE user_id = ?";

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, user.getRoleId());
            statement.setString(2, user.getUsername());

            // 🔐 HASH PASSWORD
            String hashedPassword =
                    BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            statement.setString(3, hashedPassword);

            statement.setString(4, user.getFullName());
            statement.setBoolean(5, user.isActive());
            statement.setInt(6, user.getUserId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating user");
            e.printStackTrace();
        }
    }

    // =====================================================
    // DEACTIVATE USER (SOFT DELETE)
    // =====================================================

    public void deactivateUser(int userId) {

        String sql =
                "UPDATE \"user\" SET active = false WHERE user_id = ?";

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deactivating user");
            e.printStackTrace();
        }
    }

    public boolean existsAdmin() {

        String sql = "SELECT COUNT(*) FROM \"user\" WHERE role_id = 1";

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet rs =
                     statement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}