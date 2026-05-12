package models;

public class ClaimType {

    private int claimTypeId;
    private String description;

    public ClaimType() {}

    public ClaimType(int claimTypeId,
                     String description) {

        this.claimTypeId = claimTypeId;
        this.description = description;
    }

    public int getClaimTypeId() {
        return claimTypeId;
    }

    public void setClaimTypeId(int claimTypeId) {
        this.claimTypeId = claimTypeId;
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
