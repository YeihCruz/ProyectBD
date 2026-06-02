package reports;

import java.sql.Date;

public class ClaimStatusReport {

        private int claimNumber;
        private String clientName;
        private int policyNumber;
        private String insuranceType;
        private String claimType;
        private Date incidentDate;
        private String claimStatus;
        private double claimedAmount;
        private double compensatedAmount;

        public ClaimStatusReport() {
        }

        public int getClaimNumber() {
            return claimNumber;
        }

        public void setClaimNumber(int claimNumber) {
            this.claimNumber = claimNumber;
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

        public String getClaimType() {
            return claimType;
        }

        public void setClaimType(String claimType) {
            this.claimType = claimType;
        }

        public Date getIncidentDate() {
            return incidentDate;
        }

        public void setIncidentDate(Date incidentDate) {
            this.incidentDate = incidentDate;
        }

        public String getClaimStatus() {
            return claimStatus;
        }

        public void setClaimStatus(String claimStatus) {
            this.claimStatus = claimStatus;
        }

        public double getClaimedAmount() {
            return claimedAmount;
        }

        public void setClaimedAmount(double claimedAmount) {
            this.claimedAmount = claimedAmount;
        }

        public double getCompensatedAmount() {
            return compensatedAmount;
        }

        public void setCompensatedAmount(double compensatedAmount) {
            this.compensatedAmount = compensatedAmount;
        }
    }

