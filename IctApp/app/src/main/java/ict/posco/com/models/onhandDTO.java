package ict.posco.com.models;

public class onhandDTO {
    private String ITEM_CD;
    private double QUANTITY;
    private String DESCRIPTION;

    public String getITEM_CD() {
        return ITEM_CD;
    }

    public void setITEM_CD(String ITEM_CD) {
        this.ITEM_CD = ITEM_CD;
    }

    public double getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(double QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }
}
