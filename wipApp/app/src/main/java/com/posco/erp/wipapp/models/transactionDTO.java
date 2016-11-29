package com.posco.erp.wipapp.models;
public class transactionDTO {
    private String sDate;
    private String sStock;
    private String sTrx;
    private double dTrx;
    private double dDue;

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsStock() {
        return sStock;
    }

    public void setsStock(String sStock) {
        this.sStock = sStock;
    }

    public String getsTrx() {
        return sTrx;
    }

    public void setsTrx(String sTrx) {
        this.sTrx = sTrx;
    }

    public double getdTrx() {
        return dTrx;
    }

    public void setdTrx(double dTrx) {
        this.dTrx = dTrx;
    }

    public double getdDue() {
        return dDue;
    }

    public void setdDue(double dDue) {
        this.dDue = dDue;
    }
}
