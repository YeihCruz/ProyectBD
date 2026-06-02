package reports;

public class CancelledPolicyReport {

        private String clientName;
        private String identificationNumber;
        private int cancelledPolicies;
        private String cancellationReasons;

        public CancelledPolicyReport() {
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

        public int getCancelledPolicies() {
            return cancelledPolicies;
        }

        public void setCancelledPolicies(int cancelledPolicies) {
            this.cancelledPolicies = cancelledPolicies;
        }

        public String getCancellationReasons() {
            return cancellationReasons;
        }

        public void setCancellationReasons(String cancellationReasons) {
            this.cancellationReasons = cancellationReasons;
        }
    }

