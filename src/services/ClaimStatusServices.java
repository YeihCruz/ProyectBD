package services;

import dataBase.DataBaseConnection;
import models.ClaimStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

  public class ClaimStatusServices {

        // =====================================================
        // SAVE CLAIM STATUS
        // =====================================================

        public void saveClaimStatus(
                ClaimStatus claimStatus) {

            String sql =
                    "INSERT INTO claim_status " +
                            "(description) " +
                            "VALUES (?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        claimStatus.getDescription());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Claim status saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving claim status");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL CLAIM STATUS
        // =====================================================

        public List<ClaimStatus> getAllClaimStatus() {

            List<ClaimStatus> claimStatusList =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM claim_status";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    ClaimStatus claimStatus =
                            new ClaimStatus();

                    claimStatus.setClaimStatusId(
                            resultSet.getInt(
                                    "claim_status_id"));

                    claimStatus.setDescription(
                            resultSet.getString(
                                    "description"));

                    claimStatusList.add(
                            claimStatus);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving claim status");

                e.printStackTrace();
            }

            return claimStatusList;
        }


        // =====================================================
        // FIND CLAIM STATUS BY ID
        // =====================================================

        public ClaimStatus findClaimStatusById(
                int claimStatusId) {

            String sql =
                    "SELECT * FROM claim_status " +
                            "WHERE claim_status_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        claimStatusId);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    ClaimStatus claimStatus =
                            new ClaimStatus();

                    claimStatus.setClaimStatusId(
                            resultSet.getInt(
                                    "claim_status_id"));

                    claimStatus.setDescription(
                            resultSet.getString(
                                    "description"));

                    return claimStatus;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding claim status");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE CLAIM STATUS
        // =====================================================

        public void updateClaimStatus(
                ClaimStatus claimStatus) {

            String sql =
                    "UPDATE claim_status " +
                            "SET description = ? " +
                            "WHERE claim_status_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        claimStatus.getDescription());

                statement.setInt(
                        2,
                        claimStatus.getClaimStatusId());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Claim status updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating claim status");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE CLAIM STATUS
        // =====================================================

        public void deleteClaimStatus(
                int claimStatusId) {

            String sql =
                    "DELETE FROM claim_status " +
                            "WHERE claim_status_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        claimStatusId);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Claim status deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting claim status");

                e.printStackTrace();
            }
        }
    }
