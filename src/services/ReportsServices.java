package services;

import dataBase.DataBaseConnection;
import reports.ClaimReport;
import reports.ClientReport;
import reports.PolicyReport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportsServices {

        public List<ClientReport> getClientsReport() {

            List<ClientReport> reports = new ArrayList<>();

            String sql =
                    "SELECT " +
                            "c.name AS country, " +
                            "CONCAT(cl.first_name,' ',cl.last_name) AS client_name, " +
                            "cl.identification_number, " +
                            "COUNT(CASE WHEN ps.description = 'Active' THEN p.policy_id END) AS active_policies, " +
                            "COALESCE(SUM(CASE WHEN ps.description = 'Active' THEN p.monthly_premium ELSE 0 END),0) AS total_premiums_paid " +
                            "FROM client cl " +
                            "INNER JOIN country c ON cl.country_id = c.country_id " +
                            "LEFT JOIN policy p ON cl.client_id = p.client_id " +
                            "LEFT JOIN policy_status ps ON p.policy_status_id = ps.policy_status_id " +
                            "GROUP BY c.name, cl.first_name, cl.last_name, cl.identification_number " +
                            "ORDER BY c.name, cl.last_name, cl.first_name";

            try (
                    Connection connection =
                            DataBaseConnection.getConnection();

                    PreparedStatement statement =
                            connection.prepareStatement(sql);

                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    ClientReport report =
                            new ClientReport();

                    report.setCountry(
                            resultSet.getString(
                                    "country"));

                    report.setClientName(
                            resultSet.getString(
                                    "client_name"));

                    report.setIdentificationNumber(
                            resultSet.getString(
                                    "identification_number"));

                    report.setActivePolicies(
                            resultSet.getInt(
                                    "active_policies"));

                    report.setTotalPremiumsPaid(
                            resultSet.getDouble(
                                    "total_premiums_paid"));

                    reports.add(report);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error generating client report");

                e.printStackTrace();
            }

            return reports;
        }

    public void printClientsReport() {

        List<ClientReport> reports =
                getClientsReport();

        System.out.println(
                "\n======================================");

        System.out.println(
                "CLIENTS REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "======================================\n");

        String currentCountry = "";

        for (ClientReport report : reports) {

            if (!report.getCountry()
                    .equals(currentCountry)) {

                currentCountry =
                        report.getCountry();

                System.out.println(
                        "\nCOUNTRY: "
                                + currentCountry);

                System.out.println(
                        "----------------------------------");
            }

            System.out.println(
                    "Client Name: "
                            + report.getClientName());

            System.out.println(
                    "Identification Number: "
                            + report.getIdentificationNumber());

            System.out.println(
                    "Active Policies: "
                            + report.getActivePolicies());

            System.out.println(
                    "Total Premiums Paid: $"
                            + report.getTotalPremiumsPaid());

            System.out.println();
        }
    }

    public List<PolicyReport> getPoliciesReport() {

        List<PolicyReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "p.policy_id, " +
                        "CONCAT(c.first_name,' ',c.last_name) AS client_name, " +
                        "it.description AS insurance_type, " +
                        "ps.description AS policy_status, " +
                        "p.start_date, " +
                        "p.end_date, " +
                        "p.monthly_premium, " +
                        "p.insured_amount " +
                        "FROM policy p " +
                        "INNER JOIN client c ON p.client_id = c.client_id " +
                        "INNER JOIN insurance_type it ON p.insurance_type_id = it.insurance_type_id " +
                        "INNER JOIN policy_status ps ON p.policy_status_id = ps.policy_status_id " +
                        "ORDER BY it.description, p.policy_id";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                PolicyReport report =
                        new PolicyReport();

                report.setPolicyId(
                        resultSet.getInt(
                                "policy_id"));

                report.setClientName(
                        resultSet.getString(
                                "client_name"));

                report.setInsuranceType(
                        resultSet.getString(
                                "insurance_type"));

                report.setPolicyStatus(
                        resultSet.getString(
                                "policy_status"));

                report.setStartDate(
                        resultSet.getDate(
                                "start_date").toLocalDate());

                report.setEndDate(
                        resultSet.getDate(
                                "end_date").toLocalDate());

                report.setMonthlyPremium(
                        resultSet.getDouble(
                                "monthly_premium"));

                report.setInsuredAmount(
                        resultSet.getDouble(
                                "insured_amount"));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating policies report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printPoliciesReport() {

        List<PolicyReport> reports =
                getPoliciesReport();

        System.out.println(
                "\n====================================================");

        System.out.println(
                "POLICIES REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "====================================================");

        String currentInsuranceType = "";

        for (PolicyReport report : reports) {

            if (!report.getInsuranceType()
                    .equals(currentInsuranceType)) {

                currentInsuranceType =
                        report.getInsuranceType();

                System.out.println(
                        "\n****************************************************");

                System.out.println(
                        "INSURANCE TYPE: "
                                + currentInsuranceType);

                System.out.println(
                        "****************************************************");
            }

            System.out.println(
                    "\nPolicy Number: "
                            + report.getPolicyId());

            System.out.println(
                    "Client Name: "
                            + report.getClientName());

            System.out.println(
                    "Start Date: "
                            + report.getStartDate());

            System.out.println(
                    "End Date: "
                            + report.getEndDate());

            System.out.println(
                    "Monthly Premium: $"
                            + report.getMonthlyPremium());

            System.out.println(
                    "Insured Amount: $"
                            + report.getInsuredAmount());

            System.out.println(
                    "Status: "
                            + report.getPolicyStatus());

            System.out.println(
                    "----------------------------------------------------");
        }
    }

    public List<ClaimReport> getClaimsReport() {

        List<ClaimReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "CONCAT(c.first_name,' ',c.last_name) AS client_name, " +
                        "p.policy_id, " +
                        "it.description AS insurance_type, " +
                        "cl.claim_id, " +
                        "ct.description AS claim_type, " +
                        "cl.incident_date, " +
                        "cl.claimed_amount, " +
                        "cs.description AS claim_status, " +
                        "cl.indemnified_amount " +
                        "FROM claim cl " +
                        "INNER JOIN policy p ON cl.policy_id = p.policy_id " +
                        "INNER JOIN client c ON p.client_id = c.client_id " +
                        "INNER JOIN insurance_type it ON p.insurance_type_id = it.insurance_type_id " +
                        "INNER JOIN claim_type ct ON cl.claim_type_id = ct.claim_type_id " +
                        "INNER JOIN claim_status cs ON cl.claim_status_id = cs.claim_status_id " +
                        "ORDER BY c.last_name, c.first_name, cl.claim_id";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                ClaimReport report =
                        new ClaimReport();

                report.setClientName(
                        resultSet.getString(
                                "client_name"));

                report.setPolicyId(
                        resultSet.getInt(
                                "policy_id"));

                report.setInsuranceType(
                        resultSet.getString(
                                "insurance_type"));

                report.setClaimId(
                        resultSet.getInt(
                                "claim_id"));

                report.setClaimType(
                        resultSet.getString(
                                "claim_type"));

                report.setIncidentDate(
                        resultSet.getDate(
                                "incident_date").toLocalDate());

                report.setClaimedAmount(
                        resultSet.getDouble(
                                "claimed_amount"));

                report.setClaimStatus(
                        resultSet.getString(
                                "claim_status"));

                report.setIndemnifiedAmount(
                        resultSet.getDouble(
                                "indemnified_amount"));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating claims report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printClaimsReport() {

        List<ClaimReport> reports =
                getClaimsReport();

        System.out.println(
                "\n====================================================");

        System.out.println(
                "CLAIMS REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "====================================================");

        for (ClaimReport report : reports) {

            System.out.println(
                    "\nClient Name: "
                            + report.getClientName());

            System.out.println(
                    "Policy Number: "
                            + report.getPolicyId());

            System.out.println(
                    "Insurance Type: "
                            + report.getInsuranceType());

            System.out.println(
                    "Claim Number: "
                            + report.getClaimId());

            System.out.println(
                    "Claim Type: "
                            + report.getClaimType());

            System.out.println(
                    "Incident Date: "
                            + report.getIncidentDate());

            System.out.println(
                    "Claimed Amount: $"
                            + report.getClaimedAmount());

            System.out.println(
                    "Claim Status: "
                            + report.getClaimStatus());

            System.out.println(
                    "Indemnified Amount: $"
                            + report.getIndemnifiedAmount());

            System.out.println(
                    "----------------------------------------------------");
        }
    }





    }



