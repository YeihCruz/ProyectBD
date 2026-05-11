package models;


public class Coverage {

    private int coverageId;
    private int policyNumber;
    private String description;
    private double coverageAmount;


    // Full constructor
    public Coverage(int coverageId,
                    int policyNumber,
                    String description,
                    double coverageAmount) {

        this.coverageId = coverageId;
        this.policyNumber = policyNumber;
        this.description = description;
        this.coverageAmount = coverageAmount;
    }

    public int getCoverageId() {
        return coverageId;
    }

    public void setCoverageId(int coverageId) {
        this.coverageId = coverageId;
    }

    public int getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(int policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    @Override
    public String toString() {
        return description;
    }
}
