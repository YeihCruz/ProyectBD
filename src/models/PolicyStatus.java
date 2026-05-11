package models;


public class PolicyStatus {

    private int policyStatusId;
    private String description;


    public PolicyStatus(int policyStatusId,
                        String description) {

        this.policyStatusId = policyStatusId;
        this.description = description;
    }

    public int getPolicyStatusId() {
        return policyStatusId;
    }

    public void setPolicyStatusId(int policyStatusId) {
        this.policyStatusId = policyStatusId;
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