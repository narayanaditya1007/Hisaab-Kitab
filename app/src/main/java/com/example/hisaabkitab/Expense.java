package com.example.hisaabkitab;

public class Expense {
    private int transactionID;
    private String remarks;
    private int amount;
    private String date;

    public Expense(int transactionID, String remarks, int amount, String date) {
        this.transactionID=transactionID;
        this.remarks=remarks;
        this.amount=amount;
        this.date=date;
    }

    public Expense(){

    }

    @Override
    public String toString() {
        return "Remarks=" + remarks +
                "\nAmount=" + amount +
                "\nDate=" + date  ;

    }

    public int getTransactionID() {
        return transactionID;
    }

    public String getRemarks() {
        return remarks;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
