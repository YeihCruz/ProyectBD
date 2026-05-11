package services;


import dataBase.DataBaseConnection;
import models.Coverage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class CoverageServices {

        // =====================================================
        // SAVE COVERAGE
        // =====================================================

        public void saveCoverage(
                Coverage coverage) {

            String sql =
                    "INSERT INTO coverage (" +
                            "policy_number, " +
                            "description, " +
                            "coverage_amount" +
                            ") VALUES (?, ?, ?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        coverage.getPolicyNumber());

                statement.setString(
                        2,
                        coverage.getDescription());

                statement.setDouble(
                        3,
                        coverage.getCoverageAmount());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Coverage saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving coverage");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL COVERAGES
        // =====================================================

        public List<Coverage> getAllCoverages() {

            List<Coverage> coverages =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM coverage";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Coverage coverage =
                            new Coverage();

                    coverage.setCoverageId(
                            resultSet.getInt(
                                    "coverage_id"));

                    coverage.setPolicyNumber(
                            resultSet.getInt(
                                    "policy_number"));

                    coverage.setDescription(
                            resultSet.getString(
                                    "description"));

                    coverage.setCoverageAmount(
                            resultSet.getDouble(
                                    "coverage_amount"));

                    coverages.add(
                            coverage);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving coverages");

                e.printStackTrace();
            }

            return coverages;
        }


        // =====================================================
        // FIND COVERAGE BY ID
        // =====================================================

        public Coverage findCoverageById(
                int coverageId) {

            String sql =
                    "SELECT * FROM coverage " +
                            "WHERE coverage_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        coverageId);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    Coverage coverage =
                            new Coverage();

                    coverage.setCoverageId(
                            resultSet.getInt(
                                    "coverage_id"));

                    coverage.setPolicyNumber(
                            resultSet.getInt(
                                    "policy_number"));

                    coverage.setDescription(
                            resultSet.getString(
                                    "description"));

                    coverage.setCoverageAmount(
                            resultSet.getDouble(
                                    "coverage_amount"));

                    return coverage;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding coverage");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE COVERAGE
        // =====================================================

        public void updateCoverage(
                Coverage coverage) {

            String sql =
                    "UPDATE coverage " +
                            "SET " +
                            "policy_number = ?, " +
                            "description = ?, " +
                            "coverage_amount = ? " +
                            "WHERE coverage_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        coverage.getPolicyNumber());

                statement.setString(
                        2,
                        coverage.getDescription());

                statement.setDouble(
                        3,
                        coverage.getCoverageAmount());

                statement.setInt(
                        4,
                        coverage.getCoverageId());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Coverage updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating coverage");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE COVERAGE
        // =====================================================

        public void deleteCoverage(
                int coverageId) {

            String sql =
                    "DELETE FROM coverage " +
                            "WHERE coverage_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        coverageId);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Coverage deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting coverage");

                e.printStackTrace();
            }
        }
    }
