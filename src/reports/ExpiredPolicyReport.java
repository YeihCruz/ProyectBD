package reports;

import java.time.LocalDate;

public class ExpiredPolicyReport {

        private int policyNumber;
        private String clientName;
        private String insuranceType;
        private LocalDate startDate;
        private LocalDate endDate;
        private double insuredAmount;

        public ExpiredPolicyReport() {
        }

        public int getPolicyNumber() {
            return policyNumber;
        }

        public void setPolicyNumber(int policyNumber) {
            this.policyNumber = policyNumber;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getInsuranceType() {
            return insuranceType;
        }

        public void setInsuranceType(String insuranceType) {
            this.insuranceType = insuranceType;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public double getInsuredAmount() {
            return insuredAmount;
        }

        public void setInsuredAmount(double insuredAmount) {
            this.insuredAmount = insuredAmount;
        }
    }
