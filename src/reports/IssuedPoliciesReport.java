package reports;

import java.sql.Date;

public class IssuedPoliciesReport {


        private int policyNumber;
        private String clientName;
        private String insuranceType;
        private Date startDate;
        private Date endDate;
        private double monthlyPremium;
        private String policyStatus;

        public IssuedPoliciesReport() {}

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

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public double getMonthlyPremium() {
            return monthlyPremium;
        }

        public void setMonthlyPremium(double monthlyPremium) {
            this.monthlyPremium = monthlyPremium;
        }

        public String getPolicyStatus() {
            return policyStatus;
        }

        public void setPolicyStatus(String policyStatus) {
            this.policyStatus = policyStatus;
        }
    }

