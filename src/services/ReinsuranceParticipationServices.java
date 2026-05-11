package services;

import dataBase.DataBaseConnection;
import models.ReinsuranceParticipation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

   public class ReinsuranceParticipationServices {

        // =====================================================
        // SAVE PARTICIPATION
        // =====================================================

        public void saveParticipation(
                ReinsuranceParticipation participation) {

            String sql =
                    "INSERT INTO reinsurance_participation (" +
                            "reinsurer_id, " +
                            "insurance_type_id, " +
                            "participation_percentage" +
                            ") VALUES (?, ?, ?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        participation.getReinsurerId());

                statement.setInt(
                        2,
                        participation.getInsuranceTypeId());

                statement.setDouble(
                        3,
                        participation
                                .getParticipationPercentage());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Participation saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving participation");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL PARTICIPATIONS
        // =====================================================

        public List<ReinsuranceParticipation>
        getAllParticipations() {

            List<ReinsuranceParticipation>
                    participations =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM " +
                            "reinsurance_participation";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    ReinsuranceParticipation
                            participation =
                            new ReinsuranceParticipation();

                    participation.setParticipationId(
                            resultSet.getInt(
                                    "participation_id"));

                    participation.setReinsurerId(
                            resultSet.getInt(
                                    "reinsurer_id"));

                    participation.setInsuranceTypeId(
                            resultSet.getInt(
                                    "insurance_type_id"));

                    participation
                            .setParticipationPercentage(
                                    resultSet.getDouble(
                                            "participation_percentage"));

                    participations.add(
                            participation);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving participations");

                e.printStackTrace();
            }

            return participations;
        }


        // =====================================================
        // FIND PARTICIPATION BY ID
        // =====================================================

        public ReinsuranceParticipation
        findParticipationById(
                int participationId) {

            String sql =
                    "SELECT * FROM " +
                            "reinsurance_participation " +
                            "WHERE participation_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        participationId);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    ReinsuranceParticipation
                            participation =
                            new ReinsuranceParticipation();

                    participation.setParticipationId(
                            resultSet.getInt(
                                    "participation_id"));

                    participation.setReinsurerId(
                            resultSet.getInt(
                                    "reinsurer_id"));

                    participation.setInsuranceTypeId(
                            resultSet.getInt(
                                    "insurance_type_id"));

                    participation
                            .setParticipationPercentage(
                                    resultSet.getDouble(
                                            "participation_percentage"));

                    return participation;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding participation");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE PARTICIPATION
        // =====================================================

        public void updateParticipation(
                ReinsuranceParticipation participation) {

            String sql =
                    "UPDATE " +
                            "reinsurance_participation " +
                            "SET " +
                            "reinsurer_id = ?, " +
                            "insurance_type_id = ?, " +
                            "participation_percentage = ? " +
                            "WHERE participation_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        participation.getReinsurerId());

                statement.setInt(
                        2,
                        participation.getInsuranceTypeId());

                statement.setDouble(
                        3,
                        participation
                                .getParticipationPercentage());

                statement.setInt(
                        4,
                        participation.getParticipationId());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Participation updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating participation");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE PARTICIPATION
        // =====================================================

        public void deleteParticipation(
                int participationId) {

            String sql =
                    "DELETE FROM " +
                            "reinsurance_participation " +
                            "WHERE participation_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        participationId);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Participation deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting participation");

                e.printStackTrace();
            }
        }
    }

