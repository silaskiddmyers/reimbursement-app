package com.p1.models;

public class Reimbursement {
    private int reimbId;
    private double amount;
    private String reimbSubmitted, reimbResolved;
    private String description;
    private boolean reimbReceipt;
    private String reimbAuthor, reimbResolver, reimbStatus, reimbType;

    public Reimbursement() {
    }

    public Reimbursement(int reimbId, double amount, String reimbSubmitted, String reimbResolved, String description,
            boolean reimbReceipt, String reimbAuthor, String reimbResolver, String reimbStatus, String reimbType) {
        this.reimbId = reimbId;
        this.amount = amount;
        this.reimbSubmitted = reimbSubmitted;
        this.reimbResolved = reimbResolved;
        this.description = description;
        this.reimbReceipt = reimbReceipt;
        this.reimbAuthor = reimbAuthor;
        this.reimbResolver = reimbResolver;
        this.reimbStatus = reimbStatus;
        this.reimbType = reimbType;
    }

    public int getReimbId() {
        return this.reimbId;
    }

    public void setReimbId(int reimbId) {
        this.reimbId = reimbId;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReimbSubmitted() {
        return this.reimbSubmitted;
    }

    public void setReimbSubmitted(String reimbSubmitted) {
        this.reimbSubmitted = reimbSubmitted;
    }

    public String getReimbResolved() {
        return this.reimbResolved;
    }

    public void setReimbResolved(String reimbSolved) {
        this.reimbResolved = reimbSolved;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getReimbReceipt() {
        return this.reimbReceipt;
    }

    public void setReimbReceipt(boolean reimbReceipt) {
        this.reimbReceipt = reimbReceipt;
    }

    public String getReimbAuthor() {
        return this.reimbAuthor;
    }

    public void setReimbAuthor(String reimbAuthor) {
        this.reimbAuthor = reimbAuthor;
    }

    public String getReimbResolver() {
        return this.reimbResolver;
    }

    public void setReimbResolver(String reimbResolver) {
        this.reimbResolver = reimbResolver;
    }

    public String getReimbStatus() {
        return this.reimbStatus;
    }

    public void setReimbStatus(String reimbStatus) {
        this.reimbStatus = reimbStatus;
    }

    public String getReimbType() {
        return this.reimbType;
    }

    public void setReimbType(String reimbType) {
        this.reimbType = reimbType;
    }

    @Override
    public String toString() {
        return "{" +
                " reimbId='" + getReimbId() + "'" +
                ", amount='" + getAmount() + "'" +
                ", reimbSubmitted='" + getReimbSubmitted() + "'" +
                ", reimbSolved='" + getReimbResolved() + "'" +
                ", description='" + getDescription() + "'" +
                ", reimbReceipt='" + getReimbReceipt() + "'" +
                ", reimbAuthor='" + getReimbAuthor() + "'" +
                ", reimbResolver='" + getReimbResolver() + "'" +
                ", reimbStatus='" + getReimbStatus() + "'" +
                ", reimbType='" + getReimbType() + "'" +
                "}";
    }

}
