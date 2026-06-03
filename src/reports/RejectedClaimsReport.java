package reports;

public class RejectedClaimsReport {


        private String clientName;
        private String identificationNumber;
        private int rejectedClaims;
        private String rejectionReason;

        public RejectedClaimsReport() {
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

        public int getRejectedClaims() {
            return rejectedClaims;
        }

        public void setRejectedClaims(int rejectedClaims) {
            this.rejectedClaims = rejectedClaims;
        }

        public String getRejectionReason() {
            return rejectionReason;
        }

        public void setRejectionReason(String rejectionReason) {
            this.rejectionReason = rejectionReason;
        }
    }

