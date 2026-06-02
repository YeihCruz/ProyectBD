package reports;

import java.time.LocalDate;

public class ClaimReport {

        private String clientName;
        private int policyNumber;
        private String insuranceType;
        private int claimNumber;
        private String claimType;
        private LocalDate incidentDate;
        private double claimedAmount;
        private String claimStatus;
        private double compensatedAmount;

        public ClaimReport() {
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public int getPolicyNumber() {
            return policyNumber;
        }

        public void setPolicyNumber(int policyNumber) {
            this.policyNumber = policyNumber;
        }

        public String getInsuranceType() {
            return insuranceType;
        }

        public void setInsuranceType(String insuranceType) {
            this.insuranceType = insuranceType;
        }

        public int getClaimNumber() {
            return claimNumber;
        }

        public void setClaimNumber(int claimNumber) {
            this.claimNumber = claimNumber;
        }

        public String getClaimType() {
            return claimType;
        }

        public void setClaimType(String claimType) {
            this.claimType = claimType;
        }

        public LocalDate getIncidentDate() {
            return incidentDate;
        }

        public void setIncidentDate(LocalDate incidentDate) {
            this.incidentDate = incidentDate;
        }

        public double getClaimedAmount() {
            return claimedAmount;
        }

        public void setClaimedAmount(double claimedAmount) {
            this.claimedAmount = claimedAmount;
        }

        public String getClaimStatus() {
            return claimStatus;
        }

        public void setClaimStatus(String claimStatus) {
            this.claimStatus = claimStatus;
        }

        public double getCompensatedAmount() {
            return compensatedAmount;
        }

        public void setCompensatedAmount(double compensatedAmount) {
            this.compensatedAmount = compensatedAmount;
        }
    }

