package reports;

public class PolicySummaryReport {

        private String insuranceType;
        private int activePolicies;
        private double totalMonthlyPremium;
        private double totalInsuredAmount;

        public PolicySummaryReport() {
        }

        public String getInsuranceType() {
            return insuranceType;
        }

        public void setInsuranceType(String insuranceType) {
            this.insuranceType = insuranceType;
        }

        public int getActivePolicies() {
            return activePolicies;
        }

        public void setActivePolicies(int activePolicies) {
            this.activePolicies = activePolicies;
        }

        public double getTotalMonthlyPremium() {
            return totalMonthlyPremium;
        }

        public void setTotalMonthlyPremium(double totalMonthlyPremium) {
            this.totalMonthlyPremium = totalMonthlyPremium;
        }

        public double getTotalInsuredAmount() {
            return totalInsuredAmount;
        }

        public void setTotalInsuredAmount(double totalInsuredAmount) {
            this.totalInsuredAmount = totalInsuredAmount;
        }
    }

