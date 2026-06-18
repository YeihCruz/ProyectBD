package services;

import dataBase.DataBaseConnection;
import reports.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportsServices {

    public List<ClientReport> getClientsReport() {

        List<ClientReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "co.name, " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "c.identification_number, " +
                        "COUNT(p.policy_number), " +
                        "COALESCE(SUM(p.monthly_premium),0) " +
                        "FROM client c " +
                        "INNER JOIN country co " +
                        "ON c.country_id = co.country_id " +
                        "LEFT JOIN policy p " +
                        "ON c.client_id = p.client_id " +
                        "LEFT JOIN policy_status ps " +
                        "ON p.policy_status_id = ps.policy_status_id " +
                        "AND ps.description = 'Active' " +
                        "GROUP BY " +
                        "co.name, " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "c.identification_number " +
                        "ORDER BY " +
                        "co.name, " +
                        "c.last_name, " +
                        "c.first_name";

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
                        resultSet.getString(1));

                report.setClientName(
                        resultSet.getString(2)
                                + " "
                                + resultSet.getString(3));

                report.setIdentificationNumber(
                        resultSet.getString(4));

                report.setActivePolicies(
                        resultSet.getInt(5));

                report.setTotalPremiums(
                        resultSet.getDouble(6));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating clients report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printClientsReport() {

        List<ClientReport> reports =
                getClientsReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "CLIENTS REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        String currentCountry = "";

        for (ClientReport report : reports) {

            if (!report.getCountry()
                    .equals(currentCountry)) {

                currentCountry =
                        report.getCountry();

                System.out.println();

                System.out.println(
                        "COUNTRY: "
                                + currentCountry);

                System.out.println(
                        "----------------------------------------");
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
                            + report.getTotalPremiums());

            System.out.println();
        }
    }

    public List<PolicyReport> getPoliciesReport() {

        List<PolicyReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "it.description, " +
                        "p.policy_number, " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "p.start_date, " +
                        "p.end_date, " +
                        "p.monthly_premium, " +
                        "p.insured_amount, " +
                        "ps.description " +
                        "FROM policy p " +
                        "INNER JOIN client c " +
                        "ON p.client_id = c.client_id " +
                        "INNER JOIN insurance_type it " +
                        "ON p.insurance_type_id = it.insurance_type_id " +
                        "INNER JOIN policy_status ps " +
                        "ON p.policy_status_id = ps.policy_status_id " +
                        "ORDER BY " +
                        "it.description, " +
                        "p.policy_number";

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

                report.setInsuranceType(
                        resultSet.getString(1));

                report.setPolicyNumber(
                        resultSet.getInt(2));

                report.setClientName(
                        resultSet.getString(3)
                                + " "
                                + resultSet.getString(4));

                report.setStartDate(
                        resultSet.getDate(5)
                                .toLocalDate());

                report.setEndDate(
                        resultSet.getDate(6)
                                .toLocalDate());

                report.setMonthlyPremium(
                        resultSet.getDouble(7));

                report.setInsuredAmount(
                        resultSet.getDouble(8));

                report.setPolicyStatus(
                        resultSet.getString(9));

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
                "\n========================================");

        System.out.println(
                "POLICIES REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        String currentInsuranceType = "";

        for (PolicyReport report : reports) {

            if (!report.getInsuranceType()
                    .equals(currentInsuranceType)) {

                currentInsuranceType =
                        report.getInsuranceType();

                System.out.println();

                System.out.println(
                        "INSURANCE TYPE: "
                                + currentInsuranceType);

                System.out.println(
                        "----------------------------------------");
            }

            System.out.println(
                    "Policy Number: "
                            + report.getPolicyNumber());

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

            System.out.println();
        }
    }

    public List<ClaimReport> getClaimsReport() {

        List<ClaimReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "p.policy_number, " +
                        "it.description, " +
                        "cl.claim_number, " +
                        "ct.description, " +
                        "cl.incident_date, " +
                        "cl.claimed_amount, " +
                        "cs.description, " +
                        "cl.compensated_amount " +
                        "FROM claim cl " +
                        "INNER JOIN policy p " +
                        "ON cl.policy_number = p.policy_number " +
                        "INNER JOIN client c " +
                        "ON p.client_id = c.client_id " +
                        "INNER JOIN insurance_type it " +
                        "ON p.insurance_type_id = it.insurance_type_id " +
                        "INNER JOIN claim_type ct " +
                        "ON cl.claim_type_id = ct.claim_type_id " +
                        "INNER JOIN claim_status cs " +
                        "ON cl.claim_status_id = cs.claim_status_id " +
                        "ORDER BY cl.claim_number";

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
                        resultSet.getString(1)
                                + " "
                                + resultSet.getString(2));

                report.setPolicyNumber(
                        resultSet.getInt(3));

                report.setInsuranceType(
                        resultSet.getString(4));

                report.setClaimNumber(
                        resultSet.getInt(5));

                report.setClaimType(
                        resultSet.getString(6));

                report.setIncidentDate(
                        resultSet.getDate(7)
                                .toLocalDate());

                report.setClaimedAmount(
                        resultSet.getDouble(8));

                report.setClaimStatus(
                        resultSet.getString(9));

                report.setCompensatedAmount(
                        resultSet.getDouble(10));

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
                "\n========================================");

        System.out.println(
                "CLAIMS REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        for (ClaimReport report : reports) {

            System.out.println();

            System.out.println(
                    "Client Name: "
                            + report.getClientName());

            System.out.println(
                    "Policy Number: "
                            + report.getPolicyNumber());

            System.out.println(
                    "Insurance Type: "
                            + report.getInsuranceType());

            System.out.println(
                    "Claim Number: "
                            + report.getClaimNumber());

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
                    "Compensated Amount: $"
                            + report.getCompensatedAmount());

            System.out.println(
                    "----------------------------------------");
        }
    }

    public List<ReinsurerReport> getReinsurersReport() {

        List<ReinsurerReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "r.name, " +
                        "c.name, " +
                        "rt.description, " +
                        "it.description, " +
                        "rp.participation_percentage " +
                        "FROM reinsurer r " +
                        "INNER JOIN country c " +
                        "ON r.country_id = c.country_id " +
                        "INNER JOIN reinsurance_type rt " +
                        "ON r.reinsurance_type_id = rt.reinsurance_type_id " +
                        "LEFT JOIN reinsurance_participation rp " +
                        "ON r.reinsurer_id = rp.reinsurer_id " +
                        "LEFT JOIN insurance_type it " +
                        "ON rp.insurance_type_id = it.insurance_type_id " +
                        "ORDER BY r.name, it.description";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                ReinsurerReport report =
                        new ReinsurerReport();

                report.setReinsurerName(
                        resultSet.getString(1));

                report.setCountryName(
                        resultSet.getString(2));

                report.setReinsuranceType(
                        resultSet.getString(3));

                report.setInsuranceType(
                        resultSet.getString(4));

                report.setParticipationPercentage(
                        resultSet.getDouble(5));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating reinsurers report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printReinsurersReport() {

        List<ReinsurerReport> reports =
                getReinsurersReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "REINSURERS REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        String currentReinsurer = "";

        for (ReinsurerReport report : reports) {

            if (!report.getReinsurerName()
                    .equals(currentReinsurer)) {

                currentReinsurer =
                        report.getReinsurerName();

                System.out.println();

                System.out.println(
                        "REINSURER: "
                                + report.getReinsurerName());

                System.out.println(
                        "Country: "
                                + report.getCountryName());

                System.out.println(
                        "Reinsurance Type: "
                                + report.getReinsuranceType());

                System.out.println(
                        "Participation Percentages:");

                System.out.println(
                        "----------------------------------------");
            }

            if (report.getInsuranceType() != null) {

                System.out.println(
                        report.getInsuranceType()
                                + " -> "
                                + report.getParticipationPercentage()
                                + "%");
            }
        }
    }

    public List<ExpiredPolicyReport> getExpiredPoliciesReport() {

        List<ExpiredPolicyReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "p.policy_number, " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "it.description, " +
                        "p.start_date, " +
                        "p.end_date, " +
                        "p.insured_amount " +
                        "FROM policy p " +
                        "INNER JOIN client c " +
                        "ON p.client_id = c.client_id " +
                        "INNER JOIN insurance_type it " +
                        "ON p.insurance_type_id = it.insurance_type_id " +
                        "WHERE p.end_date < CURRENT_DATE " +
                        "ORDER BY p.end_date";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                ExpiredPolicyReport report =
                        new ExpiredPolicyReport();

                report.setPolicyNumber(
                        resultSet.getInt(1));

                report.setClientName(
                        resultSet.getString(2)
                                + " "
                                + resultSet.getString(3));

                report.setInsuranceType(
                        resultSet.getString(4));

                report.setStartDate(
                        resultSet.getDate(5)
                                .toLocalDate());

                report.setEndDate(
                        resultSet.getDate(6)
                                .toLocalDate());

                report.setInsuredAmount(
                        resultSet.getDouble(7));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating expired policies report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printExpiredPoliciesReport() {

        List<ExpiredPolicyReport> reports =
                getExpiredPoliciesReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "EXPIRED POLICIES REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        for (ExpiredPolicyReport report : reports) {

            System.out.println();

            System.out.println(
                    "Policy Number: "
                            + report.getPolicyNumber());

            System.out.println(
                    "Client Name: "
                            + report.getClientName());

            System.out.println(
                    "Insurance Type: "
                            + report.getInsuranceType());

            System.out.println(
                    "Start Date: "
                            + report.getStartDate());

            System.out.println(
                    "End Date: "
                            + report.getEndDate());

            System.out.println(
                    "Insured Amount: $"
                            + report.getInsuredAmount());

            System.out.println(
                    "----------------------------------------");
        }
    }

    public List<CancelledPolicyReport> getCancelledPoliciesReport() {

        List<CancelledPolicyReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "c.identification_number, " +
                        "COUNT(p.policy_number), " +
                        "STRING_AGG(COALESCE(p.cancellation_reason,'Not specified'), ', ') " +
                        "FROM client c " +
                        "INNER JOIN policy p " +
                        "ON c.client_id = p.client_id " +
                        "INNER JOIN policy_status ps " +
                        "ON p.policy_status_id = ps.policy_status_id " +
                        "WHERE ps.description = 'Cancelled' " +
                        "GROUP BY " +
                        "c.client_id, " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "c.identification_number " +
                        "ORDER BY " +
                        "c.last_name, " +
                        "c.first_name";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                CancelledPolicyReport report =
                        new CancelledPolicyReport();

                report.setClientName(
                        resultSet.getString(1)
                                + " "
                                + resultSet.getString(2));

                report.setIdentificationNumber(
                        resultSet.getString(3));

                report.setCancelledPolicies(
                        resultSet.getInt(4));

                report.setCancellationReasons(
                        resultSet.getString(5));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating cancelled policies report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printCancelledPoliciesReport() {

        List<CancelledPolicyReport> reports =
                getCancelledPoliciesReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "CLIENTS WITH CANCELLED POLICIES REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        for (CancelledPolicyReport report : reports) {

            System.out.println();

            System.out.println(
                    "Client Name: "
                            + report.getClientName());

            System.out.println(
                    "Identification Number: "
                            + report.getIdentificationNumber());

            System.out.println(
                    "Cancelled Policies: "
                            + report.getCancelledPolicies());

            System.out.println(
                    "Cancellation Reasons: "
                            + report.getCancellationReasons());

            System.out.println(
                    "----------------------------------------");
        }
    }

    public List<PolicySummaryReport> getPolicySummaryReport() {

        List<PolicySummaryReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "it.description, " +
                        "COUNT(p.policy_number), " +
                        "SUM(p.monthly_premium), " +
                        "SUM(p.insured_amount) " +
                        "FROM policy p " +
                        "INNER JOIN insurance_type it " +
                        "ON p.insurance_type_id = it.insurance_type_id " +
                        "INNER JOIN policy_status ps " +
                        "ON p.policy_status_id = ps.policy_status_id " +
                        "WHERE ps.description = 'Active' " +
                        "GROUP BY it.description " +
                        "ORDER BY it.description";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                PolicySummaryReport report =
                        new PolicySummaryReport();

                report.setInsuranceType(
                        resultSet.getString(1));

                report.setActivePolicies(
                        resultSet.getInt(2));

                report.setTotalMonthlyPremium(
                        resultSet.getDouble(3));

                report.setTotalInsuredAmount(
                        resultSet.getDouble(4));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating policy summary report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printPolicySummaryReport() {

        List<PolicySummaryReport> reports =
                getPolicySummaryReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "POLICY SUMMARY REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        for (PolicySummaryReport report : reports) {

            System.out.println();

            System.out.println(
                    "Insurance Type: "
                            + report.getInsuranceType());

            System.out.println(
                    "Active Policies: "
                            + report.getActivePolicies());

            System.out.println(
                    "Total Monthly Premium: $"
                            + report.getTotalMonthlyPremium());

            System.out.println(
                    "Total Insured Amount: $"
                            + report.getTotalInsuredAmount());

            System.out.println(
                    "----------------------------------------");
        }
    }

    public List<ClaimStatusSummaryReport> getClaimStatusSummaryReport() {

        List<ClaimStatusSummaryReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "cs.description, " +
                        "COUNT(c.claim_number), " +
                        "SUM(c.claimed_amount), " +
                        "SUM(COALESCE(c.compensated_amount,0)) " +
                        "FROM claim c " +
                        "INNER JOIN claim_status cs " +
                        "ON c.claim_status_id = cs.claim_status_id " +
                        "GROUP BY cs.description " +
                        "ORDER BY cs.description";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                ClaimStatusSummaryReport report =
                        new ClaimStatusSummaryReport();

                report.setClaimStatus(
                        resultSet.getString(1));

                report.setTotalClaims(
                        resultSet.getInt(2));

                report.setTotalClaimedAmount(
                        resultSet.getDouble(3));

                report.setTotalCompensatedAmount(
                        resultSet.getDouble(4));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating claim status summary report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printClaimStatusSummaryReport() {

        List<ClaimStatusSummaryReport> reports =
                getClaimStatusSummaryReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "CLAIM STATUS SUMMARY REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        for (ClaimStatusSummaryReport report : reports) {

            System.out.println();

            System.out.println(
                    "Claim Status: "
                            + report.getClaimStatus());

            System.out.println(
                    "Total Claims: "
                            + report.getTotalClaims());

            System.out.println(
                    "Total Claimed Amount: $"
                            + report.getTotalClaimedAmount());

            System.out.println(
                    "Total Compensated Amount: $"
                            + report.getTotalCompensatedAmount());

            System.out.println(
                    "----------------------------------------");
        }
    }

    public List<MonthlyIncomeReport> getMonthlyIncomeReport() {

        List<MonthlyIncomeReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "EXTRACT(MONTH FROM p.start_date) AS month_number, " +
                        "TO_CHAR(p.start_date, 'Month'), " +
                        "SUM(p.monthly_premium) " +
                        "FROM policy p " +
                        "INNER JOIN policy_status ps " +
                        "ON p.policy_status_id = ps.policy_status_id " +
                        "WHERE ps.description = 'Active' " +
                        "GROUP BY " +
                        "EXTRACT(MONTH FROM p.start_date), " +
                        "TO_CHAR(p.start_date, 'Month') " +
                        "ORDER BY month_number";

        double totalAnnualIncome = 0;

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                MonthlyIncomeReport report =
                        new MonthlyIncomeReport();

                String monthName =
                        resultSet.getString(2).trim();

                double monthlyIncome =
                        resultSet.getDouble(3);

                totalAnnualIncome += monthlyIncome;

                report.setMonthName(monthName);
                report.setMonthlyIncome(monthlyIncome);

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating monthly income report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printMonthlyIncomeReport() {

        List<MonthlyIncomeReport> reports =
                getMonthlyIncomeReport();

        for (MonthlyIncomeReport report : reports) {

            System.out.println();

            System.out.println(
                    "Month: "
                            + report.getMonthName());

            System.out.println(
                    "Monthly Income: $"
                            + report.getMonthlyIncome());

            System.out.println(
                    "----------------------------------------");
        }
    }

 // PRUEBA

    public List<ApprovedClaimsReport> getApprovedClaimsReport() {

        List<ApprovedClaimsReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "c.identification_number, " +
                        "COUNT(cl.claim_number), " +
                        "SUM(cl.compensated_amount) " +
                        "FROM client c " +
                        "INNER JOIN policy p " +
                        "ON c.client_id = p.client_id " +
                        "INNER JOIN claim cl " +
                        "ON p.policy_number = cl.policy_number " +
                        "INNER JOIN claim_status cs " +
                        "ON cl.claim_status_id = cs.claim_status_id " +
                        "WHERE cs.description = 'Approved' " +
                        "GROUP BY " +
                        "c.client_id, " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "c.identification_number " +
                        "ORDER BY " +
                        "c.last_name, " +
                        "c.first_name";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                ApprovedClaimsReport report =
                        new ApprovedClaimsReport();

                report.setClientName(
                        resultSet.getString(1)
                                + " "
                                + resultSet.getString(2));

                report.setIdentificationNumber(
                        resultSet.getString(3));

                report.setApprovedClaims(
                        resultSet.getInt(4));

                report.setTotalCompensatedAmount(
                        resultSet.getDouble(5));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating approved claims report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printApprovedClaimsReport() {

        List<ApprovedClaimsReport> reports =
                getApprovedClaimsReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "CLIENTS WITH APPROVED CLAIMS REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        for (ApprovedClaimsReport report : reports) {

            System.out.println();

            System.out.println(
                    "Client Name: "
                            + report.getClientName());

            System.out.println(
                    "Identification Number: "
                            + report.getIdentificationNumber());

            System.out.println(
                    "Approved Claims: "
                            + report.getApprovedClaims());

            System.out.println(
                    "Total Compensated Amount: $"
                            + report.getTotalCompensatedAmount());

            System.out.println(
                    "----------------------------------------");
        }
    }

    public List<RejectedClaimsReport> getRejectedClaimsReport() {

        List<RejectedClaimsReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "c.identification_number, " +
                        "COUNT(cl.claim_number), " +
                        "cl.rejection_reason " +
                        "FROM client c " +
                        "INNER JOIN policy p " +
                        "ON c.client_id = p.client_id " +
                        "INNER JOIN claim cl " +
                        "ON p.policy_number = cl.policy_number " +
                        "INNER JOIN claim_status cs " +
                        "ON cl.claim_status_id = cs.claim_status_id " +
                        "WHERE cs.description = 'Rejected' " +
                        "GROUP BY " +
                        "c.client_id, " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "c.identification_number, " +
                        "cl.rejection_reason " +
                        "ORDER BY " +
                        "c.last_name, " +
                        "c.first_name";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                RejectedClaimsReport report =
                        new RejectedClaimsReport();

                report.setClientName(
                        resultSet.getString(1)
                                + " "
                                + resultSet.getString(2));

                report.setIdentificationNumber(
                        resultSet.getString(3));

                report.setRejectedClaims(
                        resultSet.getInt(4));

                report.setRejectionReason(
                        resultSet.getString(5));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating rejected claims report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printRejectedClaimsReport() {

        List<RejectedClaimsReport> reports =
                getRejectedClaimsReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "CLIENTS WITH REJECTED CLAIMS REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        for (RejectedClaimsReport report : reports) {

            System.out.println();

            System.out.println(
                    "Client Name: "
                            + report.getClientName());

            System.out.println(
                    "Identification Number: "
                            + report.getIdentificationNumber());

            System.out.println(
                    "Rejected Claims: "
                            + report.getRejectedClaims());

            System.out.println(
                    "Rejection Reason: "
                            + report.getRejectionReason());

            System.out.println(
                    "----------------------------------------");
        }
    }

    public List<AgencyReport> getAgencyReport() {

        List<AgencyReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "name, " +
                        "address, " +
                        "phone, " +
                        "email, " +
                        "general_director, " +
                        "insurance_manager, " +
                        "claims_manager " +
                        "FROM agency";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                AgencyReport report =
                        new AgencyReport();

                report.setName(
                        resultSet.getString(1));

                report.setAddress(
                        resultSet.getString(2));

                report.setPhone(
                        resultSet.getString(3));

                report.setEmail(
                        resultSet.getString(4));

                report.setGeneralDirector(
                        resultSet.getString(5));

                report.setInsuranceManager(
                        resultSet.getString(6));

                report.setClaimsManager(
                        resultSet.getString(7));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating agency report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printAgencyReport() {

        List<AgencyReport> reports =
                getAgencyReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "INSURANCE AGENCY REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        for (AgencyReport report : reports) {

            System.out.println();

            System.out.println(
                    "Agency Name: "
                            + report.getName());

            System.out.println(
                    "Address: "
                            + report.getAddress());

            System.out.println(
                    "Phone: "
                            + report.getPhone());

            System.out.println(
                    "Email: "
                            + report.getEmail());

            System.out.println(
                    "General Director: "
                            + report.getGeneralDirector());

            System.out.println(
                    "Insurance Manager: "
                            + report.getInsuranceManager());

            System.out.println(
                    "Claims Manager: "
                            + report.getClaimsManager());

            System.out.println(
                    "----------------------------------------");
        }
    }

    public List<ClientProfileReport> getClientProfileReport(int clientId) {

        List<ClientProfileReport> reports = new ArrayList<>();

        String sql =
                "SELECT " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "c.identification_number, " +
                        "c.phone, " +
                        "c.address, " +
                        "c.email, " +
                        "(SELECT COUNT(*) " +
                        "FROM policy p2 " +
                        "INNER JOIN policy_status ps " +
                        "ON p2.policy_status_id = ps.policy_status_id " +
                        "WHERE p2.client_id = c.client_id " +
                        "AND ps.description = 'Active'), " +
                        "(SELECT COALESCE(SUM(monthly_premium),0) " +
                        "FROM policy p3 " +
                        "WHERE p3.client_id = c.client_id), " +
                        "cl.claim_number, " +
                        "cl.incident_date, " +
                        "cl.claimed_amount, " +
                        "cl.compensated_amount " +
                        "FROM client c " +
                        "LEFT JOIN policy p " +
                        "ON c.client_id = p.client_id " +
                        "LEFT JOIN claim cl " +
                        "ON p.policy_number = cl.policy_number " +
                        "WHERE c.client_id = ?";


        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, clientId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                ClientProfileReport report =
                        new ClientProfileReport();

                report.setClientName(
                        resultSet.getString(1)
                                + " "
                                + resultSet.getString(2));

                report.setIdentificationNumber(
                        resultSet.getString(3));

                report.setPhone(
                        resultSet.getString(4));

                report.setAddress(
                        resultSet.getString(5));

                report.setEmail(
                        resultSet.getString(6));

                report.setActivePolicies(
                        resultSet.getInt(7));

                report.setTotalPremiumsPaid(
                        resultSet.getDouble(8));

                report.setClaimNumber(
                        resultSet.getInt(9));

                report.setIncidentDate(
                        String.valueOf(
                                resultSet.getDate(10)));

                report.setClaimedAmount(
                        resultSet.getDouble(11));

                report.setCompensatedAmount(
                        resultSet.getDouble(12));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating client profile report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printClientProfileReport(int clientId) {

        List<ClientProfileReport> reports =
                getClientProfileReport(clientId);

        if (reports.isEmpty()) {

            System.out.println(
                    "Client not found.");

            return;
        }

        ClientProfileReport first =
                reports.get(0);

        System.out.println(
                "\n========================================");

        System.out.println(
                "CLIENT PROFILE REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        System.out.println(
                "Client Name: "
                        + first.getClientName());

        System.out.println(
                "Identification Number: "
                        + first.getIdentificationNumber());

        System.out.println(
                "Phone: "
                        + first.getPhone());

        System.out.println(
                "Address: "
                        + first.getAddress());

        System.out.println(
                "Email: "
                        + first.getEmail());

        System.out.println(
                "Active Policies: "
                        + first.getActivePolicies());

        System.out.println(
                "Total Premiums Paid: $"
                        + first.getTotalPremiumsPaid());

        System.out.println(
                "\nCLAIMS");

        System.out.println(
                "----------------------------------------");

        for (ClientProfileReport report : reports) {

            if (report.getClaimNumber() == 0) {
                continue;
            }

            System.out.println(
                    "Claim Number: "
                            + report.getClaimNumber());

            System.out.println(
                    "Incident Date: "
                            + report.getIncidentDate());

            System.out.println(
                    "Claimed Amount: $"
                            + report.getClaimedAmount());

            System.out.println(
                    "Compensated Amount: $"
                            + report.getCompensatedAmount());

            System.out.println();
        }
    }

    public List<ReinsurerProfileReport> getReinsurerProfileReport(int reinsurerId) {

        List<ReinsurerProfileReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "r.name, " +
                        "c.name, " +
                        "rt.description, " +
                        "it.description, " +
                        "rp.participation_percentage " +
                        "FROM reinsurer r " +
                        "INNER JOIN country c " +
                        "ON r.country_id = c.country_id " +
                        "INNER JOIN reinsurance_type rt " +
                        "ON r.reinsurance_type_id = rt.reinsurance_type_id " +
                        "LEFT JOIN reinsurance_participation rp " +
                        "ON r.reinsurer_id = rp.reinsurer_id " +
                        "LEFT JOIN insurance_type it " +
                        "ON rp.insurance_type_id = it.insurance_type_id " +
                        "WHERE r.reinsurer_id = ? " +
                        "ORDER BY it.description";


        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, reinsurerId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                ReinsurerProfileReport report =
                        new ReinsurerProfileReport();

                report.setReinsurerName(
                        resultSet.getString(1));

                report.setCountryName(
                        resultSet.getString(2));

                report.setReinsuranceType(
                        resultSet.getString(3));

                report.setInsuranceType(
                        resultSet.getString(4));

                report.setParticipationPercentage(
                        resultSet.getDouble(5));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating reinsurer profile report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printReinsurerProfileReport(int reinsurerId) {

        List<ReinsurerProfileReport> reports = getReinsurerProfileReport(reinsurerId);

        if (reports.isEmpty()) {

            System.out.println(
                    "Reinsurer not found.");

            return;
        }

        ReinsurerProfileReport first =
                reports.get(0);

        System.out.println(
                "\n========================================");

        System.out.println(
                "REINSURER PROFILE REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        System.out.println(
                "Reinsurer Name: "
                        + first.getReinsurerName());

        System.out.println(
                "Country: "
                        + first.getCountryName());

        System.out.println(
                "Reinsurance Type: "
                        + first.getReinsuranceType());

        System.out.println(
                "\nPARTICIPATION BY INSURANCE TYPE");

        System.out.println(
                "----------------------------------------");

        for (ReinsurerProfileReport report : reports) {

            if (report.getInsuranceType() == null) {
                continue;
            }

            System.out.println(
                    "Insurance Type: "
                            + report.getInsuranceType());

            System.out.println(
                    "Participation Percentage: "
                            + report.getParticipationPercentage()
                            + "%");

            System.out.println();
        }
    }

    public List<IssuedPoliciesReport> getIssuedPoliciesReport(Date startDate, Date endDate) {

        List<IssuedPoliciesReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "p.policy_number, " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "it.description, " +
                        "p.start_date, " +
                        "p.end_date, " +
                        "p.monthly_premium, " +
                        "ps.description " +
                        "FROM policy p " +
                        "INNER JOIN client c " +
                        "ON p.client_id = c.client_id " +
                        "INNER JOIN insurance_type it " +
                        "ON p.insurance_type_id = it.insurance_type_id " +
                        "INNER JOIN policy_status ps " +
                        "ON p.policy_status_id = ps.policy_status_id " +
                        "WHERE p.start_date BETWEEN ? AND ? " +
                        "ORDER BY p.start_date";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                IssuedPoliciesReport report =
                        new IssuedPoliciesReport();

                report.setPolicyNumber(
                        resultSet.getInt(1));

                report.setClientName(
                        resultSet.getString(2)
                                + " "
                                + resultSet.getString(3));

                report.setInsuranceType(
                        resultSet.getString(4));

                report.setStartDate(
                        resultSet.getDate(5));

                report.setEndDate(
                        resultSet.getDate(6));

                report.setMonthlyPremium(
                        resultSet.getDouble(7));

                report.setPolicyStatus(
                        resultSet.getString(8));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating issued policies report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printIssuedPoliciesReport(Date startDate, Date endDate) {

        List<IssuedPoliciesReport> reports =
                getIssuedPoliciesReport(
                        startDate,
                        endDate);

        System.out.println(
                "\n========================================");

        System.out.println(
                "ISSUED POLICIES REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "\nPeriod: "
                        + startDate
                        + " to "
                        + endDate);

        System.out.println(
                "========================================");

        for (IssuedPoliciesReport report : reports) {

            System.out.println();

            System.out.println(
                    "Policy Number: "
                            + report.getPolicyNumber());

            System.out.println(
                    "Client Name: "
                            + report.getClientName());

            System.out.println(
                    "Insurance Type: "
                            + report.getInsuranceType());

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
                    "Status: "
                            + report.getPolicyStatus());

            System.out.println(
                    "----------------------------------------");
        }
    }

    public List<ClaimStatusReport> getClaimStatusReport() {

        List<ClaimStatusReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "cl.claim_number, " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "p.policy_number, " +
                        "it.description, " +
                        "ct.description, " +
                        "cl.incident_date, " +
                        "cs.description, " +
                        "cl.claimed_amount, " +
                        "cl.compensated_amount " +
                        "FROM claim cl " +
                        "INNER JOIN policy p " +
                        "ON cl.policy_number = p.policy_number " +
                        "INNER JOIN client c " +
                        "ON p.client_id = c.client_id " +
                        "INNER JOIN insurance_type it " +
                        "ON p.insurance_type_id = it.insurance_type_id " +
                        "INNER JOIN claim_type ct " +
                        "ON cl.claim_type_id = ct.claim_type_id " +
                        "INNER JOIN claim_status cs " +
                        "ON cl.claim_status_id = cs.claim_status_id " +
                        "ORDER BY cl.claim_number";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                ClaimStatusReport report =
                        new ClaimStatusReport();

                report.setClaimNumber(
                        resultSet.getInt(1));

                report.setClientName(
                        resultSet.getString(2)
                                + " "
                                + resultSet.getString(3));

                report.setPolicyNumber(
                        resultSet.getInt(4));

                report.setInsuranceType(
                        resultSet.getString(5));

                report.setClaimType(
                        resultSet.getString(6));

                report.setIncidentDate(
                        resultSet.getDate(7));

                report.setClaimStatus(
                        resultSet.getString(8));

                report.setClaimedAmount(
                        resultSet.getDouble(9));

                report.setCompensatedAmount(
                        resultSet.getDouble(10));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating claim status report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printClaimStatusReport() {

        List<ClaimStatusReport> reports =
                getClaimStatusReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "CLAIM STATUS REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        for (ClaimStatusReport report : reports) {

            System.out.println();

            System.out.println(
                    "Claim Number: "
                            + report.getClaimNumber());

            System.out.println(
                    "Client Name: "
                            + report.getClientName());

            System.out.println(
                    "Policy Number: "
                            + report.getPolicyNumber());

            System.out.println(
                    "Insurance Type: "
                            + report.getInsuranceType());

            System.out.println(
                    "Claim Type: "
                            + report.getClaimType());

            System.out.println(
                    "Incident Date: "
                            + report.getIncidentDate());

            System.out.println(
                    "Claim Status: "
                            + report.getClaimStatus());

            System.out.println(
                    "Claimed Amount: $"
                            + report.getClaimedAmount());

            System.out.println(
                    "Compensated Amount: $"
                            + report.getCompensatedAmount());

            System.out.println(
                    "----------------------------------------");
        }
    }

    public List<MonthlyPremiumIncomeReport> getMonthlyPremiumIncomeReport() {

        List<MonthlyPremiumIncomeReport> reports = new ArrayList<>();

        String sql =
                "SELECT " +
                        "EXTRACT(YEAR FROM start_date), " +
                        "EXTRACT(MONTH FROM start_date), " +
                        "SUM(monthly_premium) " +
                        "FROM policy " +
                        "GROUP BY " +
                        "EXTRACT(YEAR FROM start_date), " +
                        "EXTRACT(MONTH FROM start_date) " +
                        "ORDER BY " +
                        "EXTRACT(YEAR FROM start_date), " +
                        "EXTRACT(MONTH FROM start_date)";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                MonthlyPremiumIncomeReport report =
                        new MonthlyPremiumIncomeReport();

                report.setYear(
                        resultSet.getInt(1));

                report.setMonth(
                        resultSet.getInt(2));

                report.setMonthlyIncome(
                        resultSet.getDouble(3));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating monthly premium income report");

            e.printStackTrace();
        }

        return reports;
    }

    private String getMonthName(int month) {

        switch (month) {

            case 1:
                return "January";

            case 2:
                return "February";

            case 3:
                return "March";

            case 4:
                return "April";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "August";

            case 9:
                return "September";

            case 10:
                return "October";

            case 11:
                return "November";

            case 12:
                return "December";

            default:
                return "Unknown";
        }
    }

    public void printMonthlyPremiumIncomeReport() {

        List<MonthlyPremiumIncomeReport> reports =
                getMonthlyPremiumIncomeReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "MONTHLY PREMIUM INCOME REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        for (MonthlyPremiumIncomeReport report : reports) {

            System.out.println();

            System.out.println(
                    "Year: "
                            + report.getYear());

            System.out.println(
                    "Month: "
                            + getMonthName(
                            report.getMonth()));

            System.out.println(
                    "Monthly Income: $"
                            + report.getMonthlyIncome());

            System.out.println(
                    "----------------------------------------");
        }
    }


    public List<ExpiringPolicyReport>
    getExpiringPolicyReport() {

        List<ExpiringPolicyReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "p.policy_number, " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "it.description, " +
                        "p.start_date, " +
                        "p.end_date " +
                        "FROM policy p " +
                        "INNER JOIN client c " +
                        "ON p.client_id = c.client_id " +
                        "INNER JOIN insurance_type it " +
                        "ON p.insurance_type_id = it.insurance_type_id " +
                        "WHERE p.end_date BETWEEN CURRENT_DATE " +
                        "AND CURRENT_DATE + INTERVAL '30 DAY' " +
                        "ORDER BY p.end_date";


        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                ExpiringPolicyReport report =
                        new ExpiringPolicyReport();

                report.setPolicyNumber(
                        resultSet.getInt(1));

                report.setClientName(
                        resultSet.getString(2)
                                + " "
                                + resultSet.getString(3));

                report.setInsuranceType(
                        resultSet.getString(4));

                report.setStartDate(
                        resultSet.getDate(5));

                report.setEndDate(
                        resultSet.getDate(6));

                long remainingDays =
                        java.time.temporal.ChronoUnit.DAYS.between(
                                java.time.LocalDate.now(),
                                resultSet.getDate(6).toLocalDate());

                report.setRemainingDays(
                        remainingDays);

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating expiring policies report");

            e.printStackTrace();
        }

        return reports;
    }


    public void printExpiringPolicyReport() {

        List<ExpiringPolicyReport> reports =
                getExpiringPolicyReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "EXPIRING POLICIES REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        for (ExpiringPolicyReport report : reports) {

            System.out.println();

            System.out.println(
                    "Policy Number: "
                            + report.getPolicyNumber());

            System.out.println(
                    "Client Name: "
                            + report.getClientName());

            System.out.println(
                    "Insurance Type: "
                            + report.getInsuranceType());

            System.out.println(
                    "Start Date: "
                            + report.getStartDate());

            System.out.println(
                    "End Date: "
                            + report.getEndDate());

            System.out.println(
                    "Remaining Days: "
                            + report.getRemainingDays());

            System.out.println(
                    "----------------------------------------");
        }
    }


    public List<PendingClaimReport>
    getPendingClaimReport() {

        List<PendingClaimReport> reports =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "cl.claim_number, " +
                        "c.first_name, " +
                        "c.last_name, " +
                        "p.policy_number, " +
                        "ct.description, " +
                        "cl.incident_date, " +
                        "cl.claimed_amount, " +
                        "cs.description " +
                        "FROM claim cl " +
                        "INNER JOIN policy p " +
                        "ON cl.policy_number = p.policy_number " +
                        "INNER JOIN client c " +
                        "ON p.client_id = c.client_id " +
                        "INNER JOIN claim_type ct " +
                        "ON cl.claim_type_id = ct.claim_type_id " +
                        "INNER JOIN claim_status cs " +
                        "ON cl.claim_status_id = cs.claim_status_id " +
                        "WHERE cs.description = 'Pending' " +
                        "ORDER BY cl.incident_date";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                PendingClaimReport report =
                        new PendingClaimReport();

                report.setClaimNumber(
                        resultSet.getInt(1));

                report.setClientName(
                        resultSet.getString(2)
                                + " "
                                + resultSet.getString(3));

                report.setPolicyNumber(
                        resultSet.getInt(4));

                report.setClaimType(
                        resultSet.getString(5));

                report.setIncidentDate(
                        resultSet.getDate(6));

                report.setClaimedAmount(
                        resultSet.getDouble(7));

                report.setClaimStatus(
                        resultSet.getString(8));

                reports.add(report);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error generating pending claims report");

            e.printStackTrace();
        }

        return reports;
    }

    public void printPendingClaimReport() {

        List<PendingClaimReport> reports =
                getPendingClaimReport();

        System.out.println(
                "\n========================================");

        System.out.println(
                "PENDING CLAIMS REPORT");

        System.out.println(
                "Report Date: "
                        + java.time.LocalDate.now());

        System.out.println(
                "========================================");

        for (PendingClaimReport report : reports) {

            System.out.println();

            System.out.println(
                    "Claim Number: "
                            + report.getClaimNumber());

            System.out.println(
                    "Client Name: "
                            + report.getClientName());

            System.out.println(
                    "Policy Number: "
                            + report.getPolicyNumber());

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
                    "Status: "
                            + report.getClaimStatus());

            System.out.println(
                    "----------------------------------------");
        }
    }

    }



