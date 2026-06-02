package reports;

public class AgencyReport {

        private String name;
        private String address;
        private String phone;
        private String email;
        private String generalDirector;
        private String insuranceManager;
        private String claimsManager;

        public AgencyReport() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGeneralDirector() {
            return generalDirector;
        }

        public void setGeneralDirector(String generalDirector) {
            this.generalDirector = generalDirector;
        }

        public String getInsuranceManager() {
            return insuranceManager;
        }

        public void setInsuranceManager(String insuranceManager) {
            this.insuranceManager = insuranceManager;
        }

        public String getClaimsManager() {
            return claimsManager;
        }

        public void setClaimsManager(String claimsManager) {
            this.claimsManager = claimsManager;
        }
    }

