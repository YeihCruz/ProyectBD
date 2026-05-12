package services;



import dataBase.DataBaseConnection;
import models.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

 public class CountryServices {

        // =====================================================
        // SAVE COUNTRY
        // =====================================================

        public void saveCountry(
                Country country) {

            String sql =
                    "INSERT INTO country " +
                            "(name, iso_code) " +
                            "VALUES (?, ?)";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        country.getName());

                statement.setString(
                        2,
                        country.getIsoCode());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Country saved successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error saving country");

                e.printStackTrace();
            }
        }


        // =====================================================
        // GET ALL COUNTRIES
        // =====================================================

        public List<Country> getAllCountries() {

            List<Country> countries =
                    new ArrayList<>();

            String sql =
                    "SELECT * FROM country";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Country country =
                            new Country();

                    country.setCountryId(
                            resultSet.getInt(
                                    "country_id"));

                    country.setName(
                            resultSet.getString(
                                    "name"));

                    country.setIsoCode(
                            resultSet.getString(
                                    "iso_code"));

                    countries.add(country);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error retrieving countries");

                e.printStackTrace();
            }

            return countries;
        }


        // =====================================================
        // FIND COUNTRY BY ID
        // =====================================================

        public Country findCountryById(
                int countryId) {

            String sql =
                    "SELECT * FROM country " +
                            "WHERE country_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        countryId);

                ResultSet resultSet =
                        statement.executeQuery();

                if (resultSet.next()) {

                    Country country =
                            new Country();

                    country.setCountryId(
                            resultSet.getInt(
                                    "country_id"));

                    country.setName(
                            resultSet.getString(
                                    "name"));

                    country.setIsoCode(
                            resultSet.getString(
                                    "iso_code"));

                    return country;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error finding country");

                e.printStackTrace();
            }

            return null;
        }


        // =====================================================
        // UPDATE COUNTRY
        // =====================================================

        public void updateCountry(
                Country country) {

            String sql =
                    "UPDATE country " +
                            "SET " +
                            "name = ?, " +
                            "iso_code = ? " +
                            "WHERE country_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        country.getName());

                statement.setString(
                        2,
                        country.getIsoCode());

                statement.setInt(
                        3,
                        country.getCountryId());

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Country updated successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error updating country");

                e.printStackTrace();
            }
        }


        // =====================================================
        // DELETE COUNTRY
        // =====================================================

        public void deleteCountry(
                int countryId) {

            String sql =
                    "DELETE FROM country " +
                            "WHERE country_id = ?";

            try (Connection connection =
                         DataBaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        countryId);

                int rowsAffected =
                        statement.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println(
                            "Country deleted successfully");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error deleting country");

                e.printStackTrace();
            }
        }
    }
