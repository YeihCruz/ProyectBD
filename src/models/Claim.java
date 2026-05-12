package models;

import java.time.LocalDate;

public class Claim {

    private int claimNumber;
    private int policyNumber;
    private int claimTypeId;
    private int claimStatusId;
    private LocalDate incidentDate;
    private double claimedAmount;
    private double compensatedAmount;
    private String rejectionReason;

    // Full constructor
    public Claim(int claimNumber,
                 int policyNumber,
                 int claimTypeId,
                 int claimStatusId,
                 LocalDate incidentDate,
                 double claimedAmount,
                 double compensatedAmount,
                 String rejectionReason) {

        this.claimNumber = claimNumber;
        this.policyNumber = policyNumber;
        this.claimTypeId = claimTypeId;
        this.claimStatusId = claimStatusId;
        this.incidentDate = incidentDate;
        this.claimedAmount = claimedAmount;
        this.compensatedAmount = compensatedAmount;
        this.rejectionReason = rejectionReason;
    }

    public int getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(int claimNumber) {
        this.claimNumber = claimNumber;
    }

    public int getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(int policyNumber) {
        this.policyNumber = policyNumber;
    }

    public int getClaimTypeId() {
        return claimTypeId;
    }

    public void setClaimTypeId(int claimTypeId) {
        this.claimTypeId = claimTypeId;
    }

    public int getClaimStatusId() {
        return claimStatusId;
    }

    public void setClaimStatusId(int claimStatusId) {
        this.claimStatusId = claimStatusId;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public double getClaimedAmount() {
        return claimedAmount;
    }

    public void setClaimedAmount(double claimedAmount) {
        this.claimedAmount = claimedAmount;
    }

    public double getCompensatedAmount() {
        return compensatedAmount;
    }

    public void setCompensatedAmount(double compensatedAmount) {
        this.compensatedAmount = compensatedAmount;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    @Override
    public String toString() {
        return "Claim #" + claimNumber;
    }
}