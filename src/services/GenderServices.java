package services;

import dataBase.DataBaseConnection;
import models.Gender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

   public class GenderServices {

        // =====================================================
        // SAVE GENDER
        // =====================================================

        public void saveGender(
                Gender gender) {

            String sql =
                    "INSERT INTO gender " +
                            "(description) " +
                            "VALUES (?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        gender.getDescription());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Gender saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving gender");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL GENDERS
        // =====================================================

        public List<Gender> getAllGenders() {

            List<Gender> genders =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM gender";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Gender gender =
                            new Gender();

                    gender.setGenderId(
                            resultSet.getInt(
                                    "gender_id"));

                    gender.setDescription(
                            resultSet.getString(
                                    "description"));

                    genders.add(gender);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving genders");

                e.printStackTrace();
            }

            return genders;
        }


        // =====================================================
        // FIND GENDER BY ID
        // =====================================================

        public Gender findGenderById(
                int genderId) {

            String sql =
                    "SELECT * FROM gender " +
                            "WHERE gender_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        genderId);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    Gender gender =
                            new Gender();

                    gender.setGenderId(
                            resultSet.getInt(
                                    "gender_id"));

                    gender.setDescription(
                            resultSet.getString(
                                    "description"));

                    return gender;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding gender");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE GENDER
        // =====================================================

        public void updateGender(
                Gender gender) {

            String sql =
                    "UPDATE gender " +
                            "SET description = ? " +
                            "WHERE gender_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        gender.getDescription());

                statement.setInt(
                        2,
                        gender.getGenderId());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Gender updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating gender");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE GENDER
        // =====================================================

        public void deleteGender(
                int genderId) {

            String sql =
                    "DELETE FROM gender " +
                            "WHERE gender_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        genderId);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Gender deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting gender");

                e.printStackTrace();
            }
        }
    }

