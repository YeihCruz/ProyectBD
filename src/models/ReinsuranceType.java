package models;


public class ReinsuranceType {

    private int reinsuranceTypeId;
    private String description;

    public ReinsuranceType(){}

    public ReinsuranceType(int reinsuranceTypeId,
                           String description) {

        this.reinsuranceTypeId = reinsuranceTypeId;
        this.description = description;
    }

    public int getReinsuranceTypeId() {
        return reinsuranceTypeId;
    }

    public void setReinsuranceTypeId(int reinsuranceTypeId) {
        this.reinsuranceTypeId = reinsuranceTypeId;
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