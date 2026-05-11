package models;

public class Agency {

    private int agencyId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String generalDirector;
    private String insuranceManager;
    private String claimsManager;








    // Full constructor
    public Agency(int agencyId,
                  String name,
                  String address,
                  String phone,
                  String email,
                  String generalDirector,
                  String insuranceManager,
                  String claimsManager) {

        this.agencyId = agencyId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.generalDirector = generalDirector;
        this.insuranceManager = insuranceManager;
        this.claimsManager = claimsManager;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
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

    @Override
    public String toString() {
        return name;
    }
}
