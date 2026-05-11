package services;

import dataBase.DataBaseConnection;
import models.Claim;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class ClaimServices {

        // =====================================================
        // SAVE CLAIM
        // =====================================================

        public void saveClaim(Claim claim) {

            String sql =
                    "INSERT INTO claim (" +
                            "policy_number, " +
                            "claim_type_id, " +
                            "claim_status_id, " +
                            "incident_date, " +
                            "claimed_amount, " +
                            "compensated_amount, " +
                            "rejection_reason" +
                            ") VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1,
                        claim.getPolicyNumber());

                statement.setInt(2,
                        claim.getClaimTypeId());

                statement.setInt(3,
                        claim.getClaimStatusId());

                statement.setDate(4,
                        Date.valueOf(
                                claim.getIncidentDate()));

                statement.setDouble(5,
                        claim.getClaimedAmount());

                statement.setDouble(6,
                        claim.getCompensatedAmount());

                statement.setString(7,
                        claim.getRejectionReason());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Claim saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving claim");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL CLAIMS
        // =====================================================

        public List<Claim> getAllClaims() {

            List<Claim> claims =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM claim";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Claim claim =
                            new Claim();

                    claim.setClaimNumber(
                            resultSet.getInt(
                                    "claim_number"));

                    claim.setPolicyNumber(
                            resultSet.getInt(
                                    "policy_number"));

                    claim.setClaimTypeId(
                            resultSet.getInt(
                                    "claim_type_id"));

                    claim.setClaimStatusId(
                            resultSet.getInt(
                                    "claim_status_id"));

                    claim.setIncidentDate(
                            resultSet.getDate(
                                            "incident_date")
                                    .toLocalDate());

                    claim.setClaimedAmount(
                            resultSet.getDouble(
                                    "claimed_amount"));

                    claim.setCompensatedAmount(
                            resultSet.getDouble(
                                    "compensated_amount"));

                    claim.setRejectionReason(
                            resultSet.getString(
                                    "rejection_reason"));

                    claims.add(claim);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving claims");

                e.printStackTrace();
            }

            return claims;
        }


        // =====================================================
        // FIND CLAIM BY ID
        // =====================================================

        public Claim findClaimById(
                int claimNumber) {

            String sql =
                    "SELECT * FROM claim " +
                            "WHERE claim_number = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        claimNumber);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    Claim claim =
                            new Claim();

                    claim.setClaimNumber(
                            resultSet.getInt(
                                    "claim_number"));

                    claim.setPolicyNumber(
                            resultSet.getInt(
                                    "policy_number"));

                    claim.setClaimTypeId(
                            resultSet.getInt(
                                    "claim_type_id"));

                    claim.setClaimStatusId(
                            resultSet.getInt(
                                    "claim_status_id"));

                    claim.setIncidentDate(
                            resultSet.getDate(
                                            "incident_date")
                                    .toLocalDate());

                    claim.setClaimedAmount(
                            resultSet.getDouble(
                                    "claimed_amount"));

                    claim.setCompensatedAmount(
                            resultSet.getDouble(
                                    "compensated_amount"));

                    claim.setRejectionReason(
                            resultSet.getString(
                                    "rejection_reason"));

                    return claim;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding claim");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE CLAIM
        // =====================================================

        public void updateClaim(
                Claim claim) {

            String sql =
                    "UPDATE claim " +
                            "SET " +
                            "policy_number = ?, " +
                            "claim_type_id = ?, " +
                            "claim_status_id = ?, " +
                            "incident_date = ?, " +
                            "claimed_amount = ?, " +
                            "compensated_amount = ?, " +
                            "rejection_reason = ? " +
                            "WHERE claim_number = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1,
                        claim.getPolicyNumber());

                statement.setInt(2,
                        claim.getClaimTypeId());

                statement.setInt(3,
                        claim.getClaimStatusId());

                statement.setDate(4,
                        Date.valueOf(
                                claim.getIncidentDate()));

                statement.setDouble(5,
                        claim.getClaimedAmount());

                statement.setDouble(6,
                        claim.getCompensatedAmount());

                statement.setString(7,
                        claim.getRejectionReason());

                statement.setInt(8,
                        claim.getClaimNumber());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Claim updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating claim");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE CLAIM
        // =====================================================

        public void deleteClaim(
                int claimNumber) {

            String sql =
                    "DELETE FROM claim " +
                            "WHERE claim_number = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        claimNumber);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Claim deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting claim");

                e.printStackTrace();
            }
        }
    }
