package reports;

public class ApprovedClaimsReport {

        private String clientName;
        private String identificationNumber;
        private int approvedClaims;
        private double totalCompensatedAmount;

        public ApprovedClaimsReport() {
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getIdentificationNumber() {
            return identificationNumber;
        }

        public void setIdentificationNumber(String identificationNumber) {
            this.identificationNumber = identificationNumber;
        }

        public int getApprovedClaims() {
            return approvedClaims;
        }

        public void setApprovedClaims(int approvedClaims) {
            this.approvedClaims = approvedClaims;
        }

        public double getTotalCompensatedAmount() {
            return totalCompensatedAmount;
        }

        public void setTotalCompensatedAmount(double totalCompensatedAmount) {
            this.totalCompensatedAmount = totalCompensatedAmount;
        }
    }


