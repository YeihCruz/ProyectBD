package reports;

public class ClientReport {

    private String country;
    private String clientName;
    private String identificationNumber;
    private int activePolicies;
    private double totalPremiums;

    public ClientReport() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public int getActivePolicies() {
        return activePolicies;
    }

    public void setActivePolicies(int activePolicies) {
        this.activePolicies = activePolicies;
    }

    public double getTotalPremiums() {
        return totalPremiums;
    }

    public void setTotalPremiums(double totalPremiums) {
        this.totalPremiums = totalPremiums;
    }
}