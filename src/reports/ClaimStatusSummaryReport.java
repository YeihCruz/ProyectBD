package reports;

public class ClaimStatusSummaryReport {

        private String claimStatus;
        private int totalClaims;
        private double totalClaimedAmount;
        private double totalCompensatedAmount;

        public ClaimStatusSummaryReport() {
        }

        public String getClaimStatus() {
            return claimStatus;
        }

        public void setClaimStatus(String claimStatus) {
            this.claimStatus = claimStatus;
        }

        public int getTotalClaims() {
            return totalClaims;
        }

        public void setTotalClaims(int totalClaims) {
            this.totalClaims = totalClaims;
        }

        public double getTotalClaimedAmount() {
            return totalClaimedAmount;
        }

        public void setTotalClaimedAmount(double totalClaimedAmount) {
            this.totalClaimedAmount = totalClaimedAmount;
        }

        public double getTotalCompensatedAmount() {
            return totalCompensatedAmount;
        }

        public void setTotalCompensatedAmount(double totalCompensatedAmount) {
            this.totalCompensatedAmount = totalCompensatedAmount;
        }
    }

