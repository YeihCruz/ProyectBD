package services;

import dataBase.DataBaseConnection;
import models.ReinsuranceType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class ReinsuranceTypeServices {

        // =====================================================
        // SAVE REINSURANCE TYPE
        // =====================================================

        public void saveReinsuranceType(
                ReinsuranceType reinsuranceType) {

            String sql =
                    "INSERT INTO reinsurance_type " +
                            "(description) " +
                            "VALUES (?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        reinsuranceType.getDescription());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Reinsurance type saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving reinsurance type");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL REINSURANCE TYPES
        // =====================================================

        public List<ReinsuranceType>
        getAllReinsuranceTypes() {

            List<ReinsuranceType>
                    reinsuranceTypes =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM " +
                            "reinsurance_type";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    ReinsuranceType
                            reinsuranceType =
                            new ReinsuranceType();

                    reinsuranceType
                            .setReinsuranceTypeId(
                                    resultSet.getInt(
                                            "reinsurance_type_id"));

                    reinsuranceType
                            .setDescription(
                                    resultSet.getString(
                                            "description"));

                    reinsuranceTypes.add(
                            reinsuranceType);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving reinsurance types");

                e.printStackTrace();
            }

            return reinsuranceTypes;
        }


        // =====================================================
        // FIND REINSURANCE TYPE BY ID
        // =====================================================

        public ReinsuranceType
        findReinsuranceTypeById(
                int reinsuranceTypeId) {

            String sql =
                    "SELECT * FROM " +
                            "reinsurance_type " +
                            "WHERE reinsurance_type_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        reinsuranceTypeId);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    ReinsuranceType
                            reinsuranceType =
                            new ReinsuranceType();

                    reinsuranceType
                            .setReinsuranceTypeId(
                                    resultSet.getInt(
                                            "reinsurance_type_id"));

                    reinsuranceType
                            .setDescription(
                                    resultSet.getString(
                                            "description"));

                    return reinsuranceType;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding reinsurance type");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE REINSURANCE TYPE
        // =====================================================

        public void updateReinsuranceType(
                ReinsuranceType reinsuranceType) {

            String sql =
                    "UPDATE reinsurance_type " +
                            "SET description = ? " +
                            "WHERE reinsurance_type_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        reinsuranceType.getDescription());

                statement.setInt(
                        2,
                        reinsuranceType
                                .getReinsuranceTypeId());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Reinsurance type updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating reinsurance type");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE REINSURANCE TYPE
        // =====================================================

        public void deleteReinsuranceType(
                int reinsuranceTypeId) {

            String sql =
                    "DELETE FROM " +
                            "reinsurance_type " +
                            "WHERE reinsurance_type_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        reinsuranceTypeId);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Reinsurance type deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting reinsurance type");

                e.printStackTrace();
            }
        }
    }