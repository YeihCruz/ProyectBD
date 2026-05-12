package services;



import dataBase.DataBaseConnection;
import models.InsuranceType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

   public class InsuranceTypeServices {

        // =====================================================
        // SAVE INSURANCE TYPE
        // =====================================================

        public void saveInsuranceType(
                InsuranceType insuranceType) {

            String sql =
                    "INSERT INTO insurance_type " +
                            "(description) " +
                            "VALUES (?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        insuranceType.getDescription());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Insurance type saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving insurance type");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL INSURANCE TYPES
        // =====================================================

        public List<InsuranceType> getAllInsuranceTypes() {

            List<InsuranceType> insuranceTypes =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM insurance_type";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    InsuranceType insuranceType =
                            new InsuranceType();

                    insuranceType.setInsuranceTypeId(
                            resultSet.getInt(
                                    "insurance_type_id"));

                    insuranceType.setDescription(
                            resultSet.getString(
                                    "description"));

                    insuranceTypes.add(
                            insuranceType);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving insurance types");

                e.printStackTrace();
            }

            return insuranceTypes;
        }


        // =====================================================
        // FIND INSURANCE TYPE BY ID
        // =====================================================

        public InsuranceType findInsuranceTypeById(
                int insuranceTypeId) {

            String sql =
                    "SELECT * FROM insurance_type " +
                            "WHERE insurance_type_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        insuranceTypeId);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    InsuranceType insuranceType =
                            new InsuranceType();

                    insuranceType.setInsuranceTypeId(
                            resultSet.getInt(
                                    "insurance_type_id"));

                    insuranceType.setDescription(
                            resultSet.getString(
                                    "description"));

                    return insuranceType;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding insurance type");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE INSURANCE TYPE
        // =====================================================

        public void updateInsuranceType(
                InsuranceType insuranceType) {

            String sql =
                    "UPDATE insurance_type " +
                            "SET description = ? " +
                            "WHERE insurance_type_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        insuranceType.getDescription());

                statement.setInt(
                        2,
                        insuranceType.getInsuranceTypeId());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Insurance type updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating insurance type");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE INSURANCE TYPE
        // =====================================================

        public void deleteInsuranceType(
                int insuranceTypeId) {

            String sql =
                    "DELETE FROM insurance_type " +
                            "WHERE insurance_type_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        insuranceTypeId);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Insurance type deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting insurance type");

                e.printStackTrace();
            }
        }
    }
