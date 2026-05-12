package services;



import dataBase.DataBaseConnection;
import models.Policy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

  public class PolicyServices {

        // =====================================================
        // SAVE POLICY
        // =====================================================

        public void savePolicy(
                Policy policy) {

            String sql =
                    "INSERT INTO policy (" +
                            "client_id, " +
                            "insurance_type_id, " +
                            "policy_status_id, " +
                            "start_date, " +
                            "end_date, " +
                            "monthly_premium, " +
                            "insured_amount, " +
                            "cancellation_reason" +
                            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        policy.getClientId());

                statement.setInt(
                        2,
                        policy.getInsuranceTypeId());

                statement.setInt(
                        3,
                        policy.getPolicyStatusId());

                statement.setDate(
                        4,
                        Date.valueOf(
                                policy.getStartDate()));

                statement.setDate(
                        5,
                        Date.valueOf(
                                policy.getEndDate()));

                statement.setDouble(
                        6,
                        policy.getMonthlyPremium());

                statement.setDouble(
                        7,
                        policy.getInsuredAmount());

                statement.setString(
                        8,
                        policy.getCancellationReason());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Policy saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving policy");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL POLICIES
        // =====================================================

        public List<Policy> getAllPolicies() {

            List<Policy> policies =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM policy";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Policy policy =
                            new Policy();

                    policy.setPolicyNumber(
                            resultSet.getInt(
                                    "policy_number"));

                    policy.setClientId(
                            resultSet.getInt(
                                    "client_id"));

                    policy.setInsuranceTypeId(
                            resultSet.getInt(
                                    "insurance_type_id"));

                    policy.setPolicyStatusId(
                            resultSet.getInt(
                                    "policy_status_id"));

                    policy.setStartDate(
                            resultSet.getDate(
                                            "start_date")
                                    .toLocalDate());

                    policy.setEndDate(
                            resultSet.getDate(
                                            "end_date")
                                    .toLocalDate());

                    policy.setMonthlyPremium(
                            resultSet.getDouble(
                                    "monthly_premium"));

                    policy.setInsuredAmount(
                            resultSet.getDouble(
                                    "insured_amount"));

                    policy.setCancellationReason(
                            resultSet.getString(
                                    "cancellation_reason"));

                    policies.add(policy);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving policies");

                e.printStackTrace();
            }

            return policies;
        }


        // =====================================================
        // FIND POLICY BY ID
        // =====================================================

        public Policy findPolicyById(
                int policyNumber) {

            String sql =
                    "SELECT * FROM policy " +
                            "WHERE policy_number = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        policyNumber);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    Policy policy =
                            new Policy();

                    policy.setPolicyNumber(
                            resultSet.getInt(
                                    "policy_number"));

                    policy.setClientId(
                            resultSet.getInt(
                                    "client_id"));

                    policy.setInsuranceTypeId(
                            resultSet.getInt(
                                    "insurance_type_id"));

                    policy.setPolicyStatusId(
                            resultSet.getInt(
                                    "policy_status_id"));

                    policy.setStartDate(
                            resultSet.getDate(
                                            "start_date")
                                    .toLocalDate());

                    policy.setEndDate(
                            resultSet.getDate(
                                            "end_date")
                                    .toLocalDate());

                    policy.setMonthlyPremium(
                            resultSet.getDouble(
                                    "monthly_premium"));

                    policy.setInsuredAmount(
                            resultSet.getDouble(
                                    "insured_amount"));

                    policy.setCancellationReason(
                            resultSet.getString(
                                    "cancellation_reason"));

                    return policy;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding policy");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE POLICY
        // =====================================================

        public void updatePolicy(
                Policy policy) {

            String sql =
                    "UPDATE policy " +
                            "SET " +
                            "client_id = ?, " +
                            "insurance_type_id = ?, " +
                            "policy_status_id = ?, " +
                            "start_date = ?, " +
                            "end_date = ?, " +
                            "monthly_premium = ?, " +
                            "insured_amount = ?, " +
                            "cancellation_reason = ? " +
                            "WHERE policy_number = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1,
                        policy.getClientId());

                statement.setInt(2,
                        policy.getInsuranceTypeId());

                statement.setInt(3,
                        policy.getPolicyStatusId());

                statement.setDate(4,
                        Date.valueOf(
                                policy.getStartDate()));

                statement.setDate(5,
                        Date.valueOf(
                                policy.getEndDate()));

                statement.setDouble(6,
                        policy.getMonthlyPremium());

                statement.setDouble(7,
                        policy.getInsuredAmount());

                statement.setString(8,
                        policy.getCancellationReason());

                statement.setInt(9,
                        policy.getPolicyNumber());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Policy updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating policy");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE POLICY
        // =====================================================

        public void deletePolicy(
                int policyNumber) {

            String sql =
                    "DELETE FROM policy " +
                            "WHERE policy_number = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        policyNumber);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Policy deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting policy");

                e.printStackTrace();
            }
        }
    }
