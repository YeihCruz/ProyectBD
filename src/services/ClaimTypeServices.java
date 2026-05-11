package services;

import dataBase.DataBaseConnection;
import models.ClaimType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class ClaimTypeServices {

        // =====================================================
        // SAVE CLAIM TYPE
        // =====================================================

        public void saveClaimType(
                ClaimType claimType) {

            String sql =
                    "INSERT INTO claim_type " +
                            "(description) " +
                            "VALUES (?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        claimType.getDescription());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Claim type saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving claim type");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL CLAIM TYPES
        // =====================================================

        public List<ClaimType> getAllClaimTypes() {

            List<ClaimType> claimTypes =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM claim_type";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    ClaimType claimType =
                            new ClaimType();

                    claimType.setClaimTypeId(
                            resultSet.getInt(
                                    "claim_type_id"));

                    claimType.setDescription(
                            resultSet.getString(
                                    "description"));

                    claimTypes.add(
                            claimType);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving claim types");

                e.printStackTrace();
            }

            return claimTypes;
        }


        // =====================================================
        // FIND CLAIM TYPE BY ID
        // =====================================================

        public ClaimType findClaimTypeById(
                int claimTypeId) {

            String sql =
                    "SELECT * FROM claim_type " +
                            "WHERE claim_type_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        claimTypeId);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    ClaimType claimType =
                            new ClaimType();

                    claimType.setClaimTypeId(
                            resultSet.getInt(
                                    "claim_type_id"));

                    claimType.setDescription(
                            resultSet.getString(
                                    "description"));

                    return claimType;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding claim type");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE CLAIM TYPE
        // =====================================================

        public void updateClaimType(
                ClaimType claimType) {

            String sql =
                    "UPDATE claim_type " +
                            "SET description = ? " +
                            "WHERE claim_type_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        claimType.getDescription());

                statement.setInt(
                        2,
                        claimType.getClaimTypeId());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Claim type updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating claim type");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE CLAIM TYPE
        // =====================================================

        public void deleteClaimType(
                int claimTypeId) {

            String sql =
                    "DELETE FROM claim_type " +
                            "WHERE claim_type_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        claimTypeId);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Claim type deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting claim type");

                e.printStackTrace();
            }
        }
    }

