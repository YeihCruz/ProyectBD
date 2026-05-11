package models;

import java.time.LocalDate;

public class Policy {

    private int policyNumber;
    private int clientId;
    private int insuranceTypeId;
    private int policyStatusId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double monthlyPremium;
    private double insuredAmount;
    private String cancellationReason;


    // Full constructor
    public Policy(int policyNumber,
                  int clientId,
                  int insuranceTypeId,
                  int policyStatusId,
                  LocalDate startDate,
                  LocalDate endDate,
                  double monthlyPremium,
                  double insuredAmount,
                  String cancellationReason) {

        this.policyNumber = policyNumber;
        this.clientId = clientId;
        this.insuranceTypeId = insuranceTypeId;
        this.policyStatusId = policyStatusId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyPremium = monthlyPremium;
        this.insuredAmount = insuredAmount;
        this.cancellationReason = cancellationReason;
    }

    public int getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(int policyNumber) {
        this.policyNumber = policyNumber;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getInsuranceTypeId() {
        return insuranceTypeId;
    }

    public void setInsuranceTypeId(int insuranceTypeId) {
        this.insuranceTypeId = insuranceTypeId;
    }

    public int getPolicyStatusId() {
        return policyStatusId;
    }

    public void setPolicyStatusId(int policyStatusId) {
        this.policyStatusId = policyStatusId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getMonthlyPremium() {
        return monthlyPremium;
    }

    public void setMonthlyPremium(double monthlyPremium) {
        this.monthlyPremium = monthlyPremium;
    }

    public double getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(double insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    @Override
    public String toString() {
        return "Policy #" + policyNumber;
    }
}