package models;

public class InsuranceType {

    private int insuranceTypeId;
    private String description;


    public InsuranceType(int insuranceTypeId,
                         String description) {

        this.insuranceTypeId = insuranceTypeId;
        this.description = description;
    }

    public int getInsuranceTypeId() {
        return insuranceTypeId;
    }

    public void setInsuranceTypeId(int insuranceTypeId) {
        this.insuranceTypeId = insuranceTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}