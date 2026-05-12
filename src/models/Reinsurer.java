package models;

public class Reinsurer {

    private int reinsurerId;
    private int agencyId;
    private int reinsuranceTypeId;
    private int countryId;
    private String name;

    public Reinsurer(){}

    // Full constructor
    public Reinsurer(int reinsurerId,
                     int agencyId,
                     int reinsuranceTypeId,
                     int countryId,
                     String name) {

        this.reinsurerId = reinsurerId;
        this.agencyId = agencyId;
        this.reinsuranceTypeId = reinsuranceTypeId;
        this.countryId = countryId;
        this.name = name;
    }

    public int getReinsurerId() {
        return reinsurerId;
    }

    public void setReinsurerId(int reinsurerId) {
        this.reinsurerId = reinsurerId;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public int getReinsuranceTypeId() {
        return reinsuranceTypeId;
    }

    public void setReinsuranceTypeId(int reinsuranceTypeId) {
        this.reinsuranceTypeId = reinsuranceTypeId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
