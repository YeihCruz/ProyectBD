package services;

import dataBase.DataBaseConnection;
import models.Agency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgencyServices {

    // =====================================================
    // SAVE AGENCY
    // =====================================================

    public void saveAgency(Agency agency) {

        String sql = """
                INSERT INTO agency
                (
                    name,
                    address,
                    phone,
                    email,
                    general_director,
                    insurance_manager,
                    claims_manager
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, agency.getName());
            statement.setString(2, agency.getAddress());
            statement.setString(3, agency.getPhone());
            statement.setString(4, agency.getEmail());
            statement.setString(5, agency.getGeneralDirector());
            statement.setString(6, agency.getInsuranceManager());
            statement.setString(7, agency.getClaimsManager());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Agency saved successfully");
            }

        } catch (SQLException e) {

            System.out.println("Error saving agency");
            e.printStackTrace();
        }
    }


    // =====================================================
    // GET ALL AGENCIES
    // =====================================================

    public List<Agency> getAllAgencies() {

        List<Agency> agencies = new ArrayList<>();

        String sql = "SELECT * FROM agency";

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Agency agency = new Agency();

                agency.setAgencyId(
                        resultSet.getInt("agency_id"));

                agency.setName(
                        resultSet.getString("name"));

                agency.setAddress(
                        resultSet.getString("address"));

                agency.setPhone(
                        resultSet.getString("phone"));

                agency.setEmail(
                        resultSet.getString("email"));

                agency.setGeneralDirector(
                        resultSet.getString("general_director"));

                agency.setInsuranceManager(
                        resultSet.getString("insurance_manager"));

                agency.setClaimsManager(
                        resultSet.getString("claims_manager"));

                agencies.add(agency);
            }

        } catch (SQLException e) {

            System.out.println("Error retrieving agencies");
            e.printStackTrace();
        }

        return agencies;
    }


    // =====================================================
    // FIND AGENCY BY ID
    // =====================================================

    public Agency findAgencyById(int agencyId) {

        String sql =
                "SELECT * FROM agency WHERE agency_id = ?";

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, agencyId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                Agency agency = new Agency();

                agency.setAgencyId(
                        resultSet.getInt("agency_id"));

                agency.setName(
                        resultSet.getString("name"));

                agency.setAddress(
                        resultSet.getString("address"));

                agency.setPhone(
                        resultSet.getString("phone"));

                agency.setEmail(
                        resultSet.getString("email"));

                agency.setGeneralDirector(
                        resultSet.getString("general_director"));

                agency.setInsuranceManager(
                        resultSet.getString("insurance_manager"));

                agency.setClaimsManager(
                        resultSet.getString("claims_manager"));

                return agency;
            }

        } catch (SQLException e) {

            System.out.println("Error finding agency");
            e.printStackTrace();
        }

        return null;
    }


    // =====================================================
    // UPDATE AGENCY
    // =====================================================

    public void updateAgency(Agency agency) {

        String sql = """
                UPDATE agency
                SET
                    name = ?,
                    address = ?,
                    phone = ?,
                    email = ?,
                    general_director = ?,
                    insurance_manager = ?,
                    claims_manager = ?
                WHERE agency_id = ?
                """;

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, agency.getName());
            statement.setString(2, agency.getAddress());
            statement.setString(3, agency.getPhone());
            statement.setString(4, agency.getEmail());
            statement.setString(5, agency.getGeneralDirector());
            statement.setString(6, agency.getInsuranceManager());
            statement.setString(7, agency.getClaimsManager());

            statement.setInt(8,
                    agency.getAgencyId());

            int rowsAffected =
                    statement.executeUpdate();

            if (rowsAffected > 0) {

                System.out.println(
                        "Agency updated successfully");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error updating agency");

            e.printStackTrace();
        }
    }


    // =====================================================
    // DELETE AGENCY
    // =====================================================

    public void deleteAgency(int agencyId) {

        String sql =
                "DELETE FROM agency WHERE agency_id = ?";

        try (Connection connection =
                     DataBaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, agencyId);

            int rowsAffected =
                    statement.executeUpdate();

            if (rowsAffected > 0) {

                System.out.println(
                        "Agency deleted successfully");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting agency");

            e.printStackTrace();
        }
    }
}