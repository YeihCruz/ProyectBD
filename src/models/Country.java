package models;

public class Country {

    private int countryId;
    private String name;
    private String isoCode;


    public Country(int countryId,
                   String name,
                   String isoCode) {

        this.countryId = countryId;
        this.name = name;
        this.isoCode = isoCode;
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

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    @Override
    public String toString() {
        return name;
    }
}