package com.example.task;

public class TransactionModel {

    private int srNo;
    private String date;
    private String timestamp;
    private double amount;
    private double creditDebit;
    private double finalAmount;

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCreditDebit() {
        return creditDebit;
    }

    public void setCreditDebit(double creditDebit) {
        this.creditDebit = creditDebit;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }
}
