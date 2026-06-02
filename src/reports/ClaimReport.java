package reports;

import java.time.LocalDate;

public class ClaimReport {

        private String clientName;
        private int policyId;
        private String insuranceType;
        private int claimId;
        private String claimType;
        private LocalDate incidentDate;
        private double claimedAmount;
        private String claimStatus;
        private double indemnifiedAmount;

        public ClaimReport() {
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public int getPolicyId() {
            return policyId;
        }

        public void setPolicyId(int policyId) {
            this.policyId = policyId;
        }

        public String getInsuranceType() {
            return insuranceType;
        }

        public void setInsuranceType(String insuranceType) {
            this.insuranceType = insuranceType;
        }

        public int getClaimId() {
            return claimId;
        }

        public void setClaimId(int claimId) {
            this.claimId = claimId;
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

        public double getIndemnifiedAmount() {
            return indemnifiedAmount;
        }

        public void setIndemnifiedAmount(double indemnifiedAmount) {
            this.indemnifiedAmount = indemnifiedAmount;
        }
    }

