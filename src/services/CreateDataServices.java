package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import services.*;
import models.*;

import dataBase.DataBaseConnection;

public class CreateDataServices {

     public void checkCountries() {
        Object[][] paises = {
                {"Cuba", "CU"},
                {"Alemania", "DE"},
                {"Rusia", "RU"},
                {"EEUU", "US"},
                {"Mexico", "MX"},
                {"Japon", "JP"},
                {"Corea del Sur", "KR"},
                {"China", "CN"},
                {"Canada", "CA"}
        };
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM country WHERE name = ? OR iso_code = ? LIMIT 1";
            for (Object[] pais : paises) {
                String name = (String) pais[0];
                String code = (String) pais[1];
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setString(1, name);
                pstmt.setString(2, code);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                    Country nuevoPais = new Country(0, name, code);
                    new CountryServices().saveCountry(nuevoPais);
                    System.out.printf("País '%s' (%s) creado.\n", name, code);
                } else {
                    System.out.printf("País '%s' (%s) ya existe.\n", name, code);
                }

                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear países: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }

        }
    }

     public void checkClaimsStatus() {
        String[] status = {"En Proceso", "Aprobada", "Rechazada"};
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM claim_status WHERE description = ?";
            for (String description : status) {
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setString(1, description);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                   int i = new ClaimStatusServices().getAllClaimStatus().size();
                    ClaimStatus newStatus = new ClaimStatus(i, description);
                    new ClaimStatusServices().saveClaimStatus(newStatus);
                    System.out.printf("Estado (%s) creado.\n", description);
                } else {
                    System.out.printf("Estado (%s) ya existe.\n", description);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Estados de Reclamacion: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }
        }
    }

    public void checkClaimsType() {
        String[] type = {"Accidente", "Enfermedad", "Desastre Natural"};
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM claim_type WHERE description = ?";
            for (String description : type) {
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setString(1, description);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                    List<ClaimType> existing = new ClaimTypeServices().getAllClaimTypes();
                    int i = 1;
                    for (ClaimType ct : existing) {
                        i++;
                    }
                    ClaimType newType = new ClaimType(i, description);
                    new ClaimTypeServices().saveClaimType(newType);
                    System.out.printf("Tipo de Reclamacion (%s) creado.\n", description);
                } else {
                    System.out.printf("Tipo de Reclamacion (%s) ya existe.\n", description);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Tipo de Reclamaciones: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }

        }
    }

    public void checkReinsurerType() {
        String[] type = {"Proporcional", "No Proporcional"};
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM reinsurance_type WHERE description = ?";
            for (String description : type) {
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setString(1, description);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                    ReinsuranceType newType = new ReinsuranceType(0, description);
                    new ReinsuranceTypeServices().saveReinsuranceType(newType);
                    System.out.printf("Tipo de Reaseguramiento: (%s) creado.\n", description);
                } else {
                    System.out.printf("Tipo de Reaseguramiento: (%s) ya existe.\n", description);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Tipo de Reaseguramiento: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }

        }
    }

    public void checkGender() {
        String[] type = {"Masculino", "Femenino", "No-Binario"};
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM gender WHERE description = ?";
            for (String description : type) {
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setString(1, description);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                    List<Gender> existing = new GenderServices().getAllGenders();
                    int i = 1;
                    for (Gender g : existing) {
                        i++;
                    }
                    Gender newType = new Gender(i, description);
                    new GenderServices().saveGender(newType);
                    System.out.printf("Sexo: (%s) creado.\n", description);
                } else {
                    System.out.printf("Sexo: (%s) ya existe.\n", description);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Tipos de Sexos: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }

        }
    }

   public void checkInsuranceType() {
        String[] type = {"Seguro de Vida", "Seguro de Hogar", "Seguro de Auto", "Seguro de Salud"};
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn =DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM insurance_type WHERE description = ?";
            for (String description : type) {
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setString(1, description);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                    int i = new InsuranceTypeServices().getAllInsuranceTypes().size();
                    InsuranceType newType = new InsuranceType(i, description);
                    new InsuranceTypeServices().saveInsuranceType(newType);
                    System.out.printf("Tipo de Seguro: (%s) creado.\n", description);
                } else {
                    System.out.printf("Tipo de Seguro: (%s) ya existe.\n", description);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Tipos de Seguros: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }

        }
    }

    public void checkPolicyStatus() {
        String[] type = {"Activa", "Vencida", "Cancelada"};
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM policy_status WHERE description = ?";
            for (String description : type) {
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setString(1, description);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                    int i =new PolicyStatusServices().getAllPolicyStatuses().size();
                    PolicyStatus newType = new PolicyStatus(i, description);
                    new PolicyStatusServices().savePolicyStatus(newType);
                    System.out.printf("Estado de Poliza: (%s) creado.\n", description);
                } else {
                    System.out.printf("Estado de Poliza: (%s) ya existe.\n", description);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Estados de Poliza: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }

        }
    }

    public void checkAgency() {
        String[] type = {"LoraTEC", "PBTR", "ERDOR", "Best Armor"};
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM agency WHERE name = ?";
            for (String description : type) {
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setString(1, description);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {

                    int i = new AgencyServices().getAllAgencies().size();

                    Agency agency = null;
                    if (description.equals("LoraTEC")) {
                        agency = new Agency(i, description, "Calle Cotaron", "53242241", "LoraTgmail",
                                "Jean Claude", "Opero Muji", "Iduro Cain");
                    } else if (description.equals("PBTR")) {
                        agency = new Agency(i, description, "Virgin State", "87242791", "pbtRolgmail",
                                "Robert de Nicho", "Akira Toriyama", "Calvin Jordan");
                    } else if (description.equals("ERDOR")) {
                        agency = new Agency(i, description, "Victory Road", "81249429", "anibodygmail",
                                "Carlos Amaro", "Rick Riordan", "Bruno Noche");
                    } else if (description.equals("Best Armor")) {
                        agency = new Agency(i, description, "Calle Primera", "02141249", "smallError",
                                "Ernesto Herrero", "Alfonso Martinez", "Jordan Freeman");
                    }
                    if (agency != null) {
                        new AgencyServices().saveAgency(agency);
                        System.out.printf("Agencia: (%s) creada.\n", description);
                    }
                } else {
                    System.out.printf("Agencia: (%s) ya existe.\n", description);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Agencias: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }

        }
    }

    public void checkRole() {
        String[] type = {"admin", "user"};
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM role WHERE name = ?";
            for (String description : type) {
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setString(1, description);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {

                    int i = new RoleServices().getAllRoles().size();
                    Role newType = new Role(i, description);
                    new RoleServices().saveRole(newType);
                    System.out.printf("Rol: (%s) creado.\n", description);
                } else {
                    System.out.printf("Rol: (%s) ya existe.\n", description);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Roles: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }

        }
    }

     public int checkAdmin() {
        int userCount = 0;
        Connection conn = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            userCount = new UserServices().getAllUsers().size();
            if (userCount == 0) {
                User newAdmin = new User(0, 1, "admin", "admin", "admin", true);
                new UserServices().saveUser(newAdmin);
                System.out.println("Admin usuario creado.");
            } else {
                System.out.println("Ya existe al menos un usuario.");
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Admin: " + e.getMessage());
        }
        return userCount;
    }

    public void checkClients() {
        Object[][] type = {
                {"Peter", "Homer"},
                {"Percy", "Jackson"},
                {"William", "Vielo"},
                {"Matt", "Mercer"}
        };
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn =DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM client WHERE first_name = ?";
            for (Object[] clientData : type) {
                String fName = (String) clientData[0];
                String lName = (String) clientData[1];
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setString(1, fName);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                    int i = new ClientServices().getAllClients().size();
                    Client client = null;
                    if (fName.equals("Peter")) {
                        client = new Client(i, 2, 2, 2, fName, lName, "86432211", 43,
                                "Turumpa Traquilo", "PiterGgmail", "76541241");
                    } else if (fName.equals("Percy")) {
                        client = new Client(i, 2, 2, 5, fName, lName, "5188623", 27,
                                "New York", "callofdutygmail", "7632167");
                    } else if (fName.equals("William")) {
                        client = new Client(i, 3, 2, 8, fName, lName, "8435322", 76,
                                "Mori Tomba", "billyBgmail", "7421274");
                    } else if (fName.equals("Matt")) {
                        client = new Client(i, 1, 2, 4, fName, lName, "4125221", 48,
                                "Calle Paula", "criticalthinking", "2156723");
                    }
                    if (client != null) {
                        new ClientServices().saveClient(client);
                        System.out.printf("Cliente: (%s) creado.\n", fName);
                    }
                } else {
                    System.out.printf("Cliente: (%s) ya existe.\n", fName);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Clientes: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }
        }
    }

     public void checkPolicy() {
        int[] numbers = {1, 2, 3, 4, 5};
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM policy WHERE policy_number = ?";
            for (int data : numbers) {
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setInt(1, data);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                     Policy policy = null;
                    switch (data) {
                        case 1:
                            policy = new Policy(data, 3, 3, 2,
                                     LocalDate.of(2015, 3, 30), LocalDate.of(2024, 8, 5),
                                    300, 150, "Cambio de agencia");
                            break;
                        case 2:
                            policy = new Policy(data, 2, 1, 1,
                                    LocalDate.of(2012, 1, 4), LocalDate.of(2021, 2, 24),
                                    566, 315, "Fallecimiento del cliente");
                            break;
                        case 3:
                            policy = new Policy(data, 1, 2, 3,
                                    LocalDate.of(2024, 6, 23), LocalDate.of(2024, 7, 2),
                                    400, 213, "Cambio de idea del cliente");
                            break;
                        case 4:
                            policy = new Policy(data, 4, 2, 2,
                                    LocalDate.of(2021, 7, 31), LocalDate.of(2026, 5, 7),
                                    1000, 750, "Bancarrota");
                            break;
                        case 5:
                            policy = new Policy(data, 3, 1, 3,
                                    LocalDate.of(2025, 3, 13),LocalDate.of(2026, 5, 18),
                                    120, 75, "Bancarrota");
                            break;
                    }
                    if (policy != null) {
                        new PolicyServices().savePolicy(policy);
                        System.out.printf("Poliza numero: (%d) creada.\n", data);
                    }
                } else {
                    System.out.printf("Poliza numero: (%d) ya existe.\n", data);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Polizas: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }
        }
    }

    public void checkClaim() {
        int[] numbers = {1, 2, 3};
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM claim WHERE claim_number = ?";
            for (int data : numbers) {
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setInt(1, data);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                    Claim claim = null;
                    switch (data) {
                        case 1:
                            claim = new Claim(data, 3, 3, 2,
                                    LocalDate.of(2012, 1, 4), 2300, 1400, "Una avalancha");
                            break;
                        case 2:
                            claim = new Claim(data, 1, 2, 1,
                                    LocalDate.of(2010, 6, 21), 30400, 10200, "Contusion en accidente");
                            break;
                        case 3:
                            claim = new Claim(data, 4, 3, 2,
                                    LocalDate.of(2024, 11, 30), 4300, 2130, "Choque de Autos");
                            break;
                    }
                    if (claim != null) {
                        new ClaimServices().saveClaim(claim);
                        System.out.printf("Reclamo numero: (%d) creado.\n", data);
                    }
                } else {
                    System.out.printf("Reclamo numero: (%d) ya existe.\n", data);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Reclamos: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }
        }
    }

     public void checkCoverage() {
        int[] ids = { 1, 2, 3}; // includes 0 which will be skipped because not in if
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn =DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM coverage WHERE coverage_id = ?";
            for (int data : ids) {
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setInt(1, data);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                    Coverage coverage = null;
                    switch (data) {
                        case 1:
                            coverage = new Coverage(data, 1, "Cobertura Especializada en casas", 3340);
                            break;
                        case 2:
                            coverage = new Coverage(data, 3, "Cobertura especilizada en Puertes", 1000);
                            break;
                        case 3:
                            coverage = new Coverage(data, 2, "Cobertura especializada en carros", 2050);
                            break;
                    }
                    if (coverage != null) {
                        new CoverageServices().saveCoverage(coverage);
                        System.out.printf("Cobertura numero: (%d) creada.\n", data);
                    }
                } else {
                    System.out.printf("Cobertura numero: (%d) ya existe.\n", data);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Coberturas: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }
        }
    }

     public void checkReinsurer() {
        String[] names = {"AntiMistake", "TuRAC", "Mighty People", "ERASER"};
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn =DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM reinsurer WHERE name = ?";
            for (String data : names) {
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setString(1, data);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                    Reinsurer reinsurer = null;
                    if (data.equals("AntiMistake")) {
                        reinsurer = new Reinsurer(0, 2, 1, 3, data);
                    } else if (data.equals("TuRAC")) {
                        reinsurer = new Reinsurer(0, 3, 2, 5, data);
                    } else if (data.equals("Mighty People")) {
                        reinsurer = new Reinsurer(0, 4, 1, 1, data);
                    } else if (data.equals("ERASER")) {
                        reinsurer = new Reinsurer(0, 2, 2, 2, data);
                    }
                    if (reinsurer != null) {
                        new ReinsuranceServices().saveReinsurer(reinsurer);
                        System.out.printf("Reaseguradora: (%s) creada.\n", data);
                    }
                } else {
                    System.out.printf("Reaseguradora: (%s) ya existe.\n", data);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Reaseguradoras: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }

        }
    }

    public void checkReinsuranceParticipation() {
        int[] ids = {0, 1, 2, 3}; // includes 0 which will be skipped
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
            String sqlCheck = "SELECT 1 FROM reinsurance_participation WHERE participation_id = ?";
            for (int data : ids) {
                if (data == 0) continue;
                pstmt = conn.prepareStatement(sqlCheck);
                pstmt.setInt(1, data);
                rs = pstmt.executeQuery();
                boolean existe = rs.next();
                if (!existe) {
                    ReinsuranceParticipation part = null;
                    switch (data) {
                        case 1:
                            part = new ReinsuranceParticipation(data, data + 1, 3, 40);
                            break;
                        case 2:
                            part = new ReinsuranceParticipation(data, data + 1, 2, 25);
                            break;
                        case 3:
                            part = new ReinsuranceParticipation(data, data + 1, 1, 79);
                            break;
                        }
                    if (part != null) {
                        new ReinsuranceParticipationServices().saveParticipation(part);
                        System.out.printf("Participación de Reaseguro (%d) creada.\n", data);
                    }
                } else {
                    System.out.printf("Participación de Reaseguro (%d) ya existe.\n", data);
                }
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error al verificar/crear Participaciones de Reaseguro: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignore */ }

        }
    }

    public void checkAll() {
        checkRole();
        if (checkAdmin() == 0) {
            checkCountries();
            checkClaimsStatus();
            checkClaimsType();
            checkReinsurerType();
            checkGender();
            checkInsuranceType();
            checkPolicyStatus();
            checkAgency();
            checkClients();
            checkPolicy();
            checkClaim();
            checkCoverage();
            checkReinsurer();
            checkReinsuranceParticipation();
        }
    }
}