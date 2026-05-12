package models;

public class ClaimStatus {

    private int claimStatusId;
    private String description;


    public ClaimStatus(int claimStatusId,
                       String description) {

        this.claimStatusId = claimStatusId;
        this.description = description;
    }

    public int getClaimStatusId() {
        return claimStatusId;
    }

    public void setClaimStatusId(int claimStatusId) {
        this.claimStatusId = claimStatusId;
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
