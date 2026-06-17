package services;

import dataBase.DataBaseConnection;

import java.sql.*;

public class DashboardServices {

    public int getActiveUsers() {
        String sql = "SELECT COUNT(*) FROM \"user\" WHERE active = true";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getActivePolicies() {
        String sql = "SELECT COUNT(*) FROM policy WHERE policy_status_id = (SELECT policy_status_id FROM policy_status WHERE description = 'Active')";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getOpenClaims() {
        String sql = "SELECT COUNT(*) FROM claim WHERE claim_status_id = (SELECT claim_status_id FROM claim_status WHERE description = 'Pending')";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getRegisteredClients() {
        String sql = "SELECT COUNT(*) FROM client";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getReportedClaims() {
        String sql = "SELECT COUNT(*) FROM claim";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getActiveAgencies() {
        String sql = "SELECT COUNT(*) FROM agency";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getResolutionRate() {
        String sql = "SELECT ROUND(COALESCE(SUM(CASE WHEN cs.description = 'Approved' THEN 1 END) * 100.0 / NULLIF(COUNT(*), 0), 0), 1) FROM claim c LEFT JOIN claim_status cs ON c.claim_status_id = cs.claim_status_id";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                double rate = rs.getDouble(1);
                return String.format("%.1f%%", rate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0.0%";
    }

    public String getMonthlyIncome() {
        String sql = "SELECT COALESCE(SUM(monthly_premium), 0) FROM policy WHERE policy_status_id = (SELECT policy_status_id FROM policy_status WHERE description = 'Active')";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                double income = rs.getDouble(1);
                return String.format("$%,.2f", income);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "$0.00";
    }
}
