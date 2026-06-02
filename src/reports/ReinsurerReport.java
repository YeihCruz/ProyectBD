package reports;

public class ReinsurerReport {

        private String reinsurerName;
        private String countryName;
        private String reinsuranceType;
        private String insuranceType;
        private double participationPercentage;

        public ReinsurerReport() {
        }

        public String getReinsurerName() {
            return reinsurerName;
        }

        public void setReinsurerName(String reinsurerName) {
            this.reinsurerName = reinsurerName;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getReinsuranceType() {
            return reinsuranceType;
        }

        public void setReinsuranceType(String reinsuranceType) {
            this.reinsuranceType = reinsuranceType;
        }

        public String getInsuranceType() {
            return insuranceType;
        }

        public void setInsuranceType(String insuranceType) {
            this.insuranceType = insuranceType;
        }

        public double getParticipationPercentage() {
            return participationPercentage;
        }

        public void setParticipationPercentage(double participationPercentage) {
            this.participationPercentage = participationPercentage;
        }
    }

