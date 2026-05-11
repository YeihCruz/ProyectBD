package services;


import dataBase.DataBaseConnection;
import models.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

   public class ClientServices {

        // =====================================================
        // SAVE CLIENT
        // =====================================================

        public void saveClient(Client client) {

            String sql =
                    "INSERT INTO client (" +
                            "agency_id, " +
                            "gender_id, " +
                            "country_id, " +
                            "first_name, " +
                            "last_name, " +
                            "identification_number, " +
                            "age, " +
                            "address, " +
                            "phone, " +
                            "email" +
                            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        client.getAgencyId());

                statement.setInt(
                        2,
                        client.getGenderId());

                statement.setInt(
                        3,
                        client.getCountryId());

                statement.setString(
                        4,
                        client.getFirstName());

                statement.setString(
                        5,
                        client.getLastName());

                statement.setString(
                        6,
                        client.getIdentificationNumber());

                statement.setInt(
                        7,
                        client.getAge());

                statement.setString(
                        8,
                        client.getAddress());

                statement.setString(
                        9,
                        client.getPhone());

                statement.setString(
                        10,
                        client.getEmail());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Client saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving client");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL CLIENTS
        // =====================================================

        public List<Client> getAllClients() {

            List<Client> clients =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM client";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Client client =
                            new Client();

                    client.setClientId(
                            resultSet.getInt(
                                    "client_id"));

                    client.setAgencyId(
                            resultSet.getInt(
                                    "agency_id"));

                    client.setGenderId(
                            resultSet.getInt(
                                    "gender_id"));

                    client.setCountryId(
                            resultSet.getInt(
                                    "country_id"));

                    client.setFirstName(
                            resultSet.getString(
                                    "first_name"));

                    client.setLastName(
                            resultSet.getString(
                                    "last_name"));

                    client.setIdentificationNumber(
                            resultSet.getString(
                                    "identification_number"));

                    client.setAge(
                            resultSet.getInt(
                                    "age"));

                    client.setAddress(
                            resultSet.getString(
                                    "address"));

                    client.setPhone(
                            resultSet.getString(
                                    "phone"));

                    client.setEmail(
                            resultSet.getString(
                                    "email"));

                    clients.add(client);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving clients");

                e.printStackTrace();
            }

            return clients;
        }


        // =====================================================
        // FIND CLIENT BY ID
        // =====================================================

        public Client findClientById(
                int clientId) {

            String sql =
                    "SELECT * FROM client " +
                            "WHERE client_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        clientId);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    Client client =
                            new Client();

                    client.setClientId(
                            resultSet.getInt(
                                    "client_id"));

                    client.setAgencyId(
                            resultSet.getInt(
                                    "agency_id"));

                    client.setGenderId(
                            resultSet.getInt(
                                    "gender_id"));

                    client.setCountryId(
                            resultSet.getInt(
                                    "country_id"));

                    client.setFirstName(
                            resultSet.getString(
                                    "first_name"));

                    client.setLastName(
                            resultSet.getString(
                                    "last_name"));

                    client.setIdentificationNumber(
                            resultSet.getString(
                                    "identification_number"));

                    client.setAge(
                            resultSet.getInt(
                                    "age"));

                    client.setAddress(
                            resultSet.getString(
                                    "address"));

                    client.setPhone(
                            resultSet.getString(
                                    "phone"));

                    client.setEmail(
                            resultSet.getString(
                                    "email"));

                    return client;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding client");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE CLIENT
        // =====================================================

        public void updateClient(
                Client client) {

            String sql =
                    "UPDATE client " +
                            "SET " +
                            "agency_id = ?, " +
                            "gender_id = ?, " +
                            "country_id = ?, " +
                            "first_name = ?, " +
                            "last_name = ?, " +
                            "identification_number = ?, " +
                            "age = ?, " +
                            "address = ?, " +
                            "phone = ?, " +
                            "email = ? " +
                            "WHERE client_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, client.getAgencyId());
                statement.setInt(2, client.getGenderId());
                statement.setInt(3, client.getCountryId());
                statement.setString(4, client.getFirstName());
                statement.setString(5, client.getLastName());
                statement.setString(6, client.getIdentificationNumber());
                statement.setInt(7, client.getAge());
                statement.setString(8, client.getAddress());
                statement.setString(9, client.getPhone());
                statement.setString(10, client.getEmail());

                statement.setInt(
                        11,
                        client.getClientId());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Client updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating client");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE CLIENT
        // =====================================================

        public void deleteClient(
                int clientId) {

            String sql =
                    "DELETE FROM client " +
                            "WHERE client_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        clientId);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Client deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting client");

                e.printStackTrace();
            }
        }
    }