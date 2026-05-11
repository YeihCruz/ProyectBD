package services;

import dataBase.DataBaseConnection;
import models.PolicyStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class PolicyStatusServices {

        // =====================================================
        // SAVE POLICY STATUS
        // =====================================================

        public void savePolicyStatus(
                PolicyStatus policyStatus) {

            String sql =
                    "INSERT INTO policy_status " +
                            "(description) " +
                            "VALUES (?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        policyStatus.getDescription());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Policy status saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving policy status");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL POLICY STATUS
        // =====================================================

        public List<PolicyStatus> getAllPolicyStatuses() {

            List<PolicyStatus> policyStatuses =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM policy_status";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    PolicyStatus policyStatus =
                            new PolicyStatus();

                    policyStatus.setPolicyStatusId(
                            resultSet.getInt(
                                    "policy_status_id"));

                    policyStatus.setDescription(
                            resultSet.getString(
                                    "description"));

                    policyStatuses.add(
                            policyStatus);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving policy status");

                e.printStackTrace();
            }

            return policyStatuses;
        }


        // =====================================================
        // FIND POLICY STATUS BY ID
        // =====================================================

        public PolicyStatus findPolicyStatusById(
                int policyStatusId) {

            String sql =
                    "SELECT * FROM policy_status " +
                            "WHERE policy_status_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        policyStatusId);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    PolicyStatus policyStatus =
                            new PolicyStatus();

                    policyStatus.setPolicyStatusId(
                            resultSet.getInt(
                                    "policy_status_id"));

                    policyStatus.setDescription(
                            resultSet.getString(
                                    "description"));

                    return policyStatus;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding policy status");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE POLICY STATUS
        // =====================================================

        public void updatePolicyStatus(
                PolicyStatus policyStatus) {

            String sql =
                    "UPDATE policy_status " +
                            "SET description = ? " +
                            "WHERE policy_status_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        policyStatus.getDescription());

                statement.setInt(
                        2,
                        policyStatus.getPolicyStatusId());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Policy status updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating policy status");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE POLICY STATUS
        // =====================================================

        public void deletePolicyStatus(
                int policyStatusId) {

            String sql =
                    "DELETE FROM policy_status " +
                            "WHERE policy_status_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        policyStatusId);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Policy status deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting policy status");

                e.printStackTrace();
            }
        }
    }
