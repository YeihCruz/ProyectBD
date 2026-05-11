package services;

import dataBase.DataBaseConnection;
import models.Reinsurer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

  public class ReinsuranceServices {

        // =====================================================
        // SAVE REINSURER
        // =====================================================

        public void saveReinsurer(
                Reinsurer reinsurer) {

            String sql =
                    "INSERT INTO reinsurer (" +
                            "agency_id, " +
                            "reinsurance_type_id, " +
                            "country_id, " +
                            "name" +
                            ") VALUES (?, ?, ?, ?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        reinsurer.getAgencyId());

                statement.setInt(
                        2,
                        reinsurer
                                .getReinsuranceTypeId());

                statement.setInt(
                        3,
                        reinsurer.getCountryId());

                statement.setString(
                        4,
                        reinsurer.getName());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Reinsurer saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving reinsurer");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL REINSURERS
        // =====================================================

        public List<Reinsurer>
        getAllReinsurers() {

            List<Reinsurer>
                    reinsurers =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM reinsurer";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Reinsurer reinsurer =
                            new Reinsurer();

                    reinsurer.setReinsurerId(
                            resultSet.getInt(
                                    "reinsurer_id"));

                    reinsurer.setAgencyId(
                            resultSet.getInt(
                                    "agency_id"));

                    reinsurer
                            .setReinsuranceTypeId(
                                    resultSet.getInt(
                                            "reinsurance_type_id"));

                    reinsurer.setCountryId(
                            resultSet.getInt(
                                    "country_id"));

                    reinsurer.setName(
                            resultSet.getString(
                                    "name"));

                    reinsurers.add(
                            reinsurer);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving reinsurers");

                e.printStackTrace();
            }

            return reinsurers;
        }


        // =====================================================
        // FIND REINSURER BY ID
        // =====================================================

        public Reinsurer findReinsurerById(
                int reinsurerId) {

            String sql =
                    "SELECT * FROM reinsurer " +
                            "WHERE reinsurer_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        reinsurerId);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    Reinsurer reinsurer =
                            new Reinsurer();

                    reinsurer.setReinsurerId(
                            resultSet.getInt(
                                    "reinsurer_id"));

                    reinsurer.setAgencyId(
                            resultSet.getInt(
                                    "agency_id"));

                    reinsurer
                            .setReinsuranceTypeId(
                                    resultSet.getInt(
                                            "reinsurance_type_id"));

                    reinsurer.setCountryId(
                            resultSet.getInt(
                                    "country_id"));

                    reinsurer.setName(
                            resultSet.getString(
                                    "name"));

                    return reinsurer;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding reinsurer");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE REINSURER
        // =====================================================

        public void updateReinsurer(
                Reinsurer reinsurer) {

            String sql =
                    "UPDATE reinsurer " +
                            "SET " +
                            "agency_id = ?, " +
                            "reinsurance_type_id = ?, " +
                            "country_id = ?, " +
                            "name = ? " +
                            "WHERE reinsurer_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        reinsurer.getAgencyId());

                statement.setInt(
                        2,
                        reinsurer
                                .getReinsuranceTypeId());

                statement.setInt(
                        3,
                        reinsurer.getCountryId());

                statement.setString(
                        4,
                        reinsurer.getName());

                statement.setInt(
                        5,
                        reinsurer.getReinsurerId());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Reinsurer updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating reinsurer");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE REINSURER
        // =====================================================

        public void deleteReinsurer(
                int reinsurerId) {

            String sql =
                    "DELETE FROM reinsurer " +
                            "WHERE reinsurer_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        reinsurerId);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Reinsurer deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting reinsurer");

                e.printStackTrace();
            }
        }
    }