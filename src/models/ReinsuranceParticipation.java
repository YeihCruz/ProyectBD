package models;

public class ReinsuranceParticipation {

    private int participationId;
    private int reinsurerId;
    private int insuranceTypeId;
    private double participationPercentage;

    // Full constructor
    public ReinsuranceParticipation(int participationId,
                                    int reinsurerId,
                                    int insuranceTypeId,
                                    double participationPercentage) {

        this.participationId = participationId;
        this.reinsurerId = reinsurerId;
        this.insuranceTypeId = insuranceTypeId;
        this.participationPercentage = participationPercentage;
    }

    public int getParticipationId() {
        return participationId;
    }

    public void setParticipationId(int participationId) {
        this.participationId = participationId;
    }

    public int getReinsurerId() {
        return reinsurerId;
    }

    public void setReinsurerId(int reinsurerId) {
        this.reinsurerId = reinsurerId;
    }

    public int getInsuranceTypeId() {
        return insuranceTypeId;
    }

    public void setInsuranceTypeId(int insuranceTypeId) {
        this.insuranceTypeId = insuranceTypeId;
    }

    public double getParticipationPercentage() {
        return participationPercentage;
    }

    public void setParticipationPercentage(double participationPercentage) {
        this.participationPercentage = participationPercentage;
    }

    @Override
    public String toString() {
        return participationPercentage + "%";
    }
}
