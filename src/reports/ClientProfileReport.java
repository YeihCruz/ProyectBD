package reports;

public class ClientProfileReport {

        private String clientName;
        private String identificationNumber;
        private String phone;
        private String address;
        private String email;
        private int activePolicies;
        private double totalPremiumsPaid;
        private int claimNumber;
        private String incidentDate;
        private double claimedAmount;
        private double compensatedAmount;

        public ClientProfileReport() {
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getActivePolicies() {
            return activePolicies;
        }

        public void setActivePolicies(int activePolicies) {
            this.activePolicies = activePolicies;
        }

        public double getTotalPremiumsPaid() {
            return totalPremiumsPaid;
        }

        public void setTotalPremiumsPaid(double totalPremiumsPaid) {
            this.totalPremiumsPaid = totalPremiumsPaid;
        }

        public int getClaimNumber() {
            return claimNumber;
        }

        public void setClaimNumber(int claimNumber) {
            this.claimNumber = claimNumber;
        }

        public String getIncidentDate() {
            return incidentDate;
        }

        public void setIncidentDate(String incidentDate) {
            this.incidentDate = incidentDate;
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


