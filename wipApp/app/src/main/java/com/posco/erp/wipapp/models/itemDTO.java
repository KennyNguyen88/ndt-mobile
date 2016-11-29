package com.posco.erp.wipapp.models;

public class itemDTO {
    private String ITEM_CD;
    private double QUANTITY;
    private String DESCRIPTION;
    private String SUBINVENTORY_CODE;
    private String INVENTORY_ITEM_ID;
    private String PRIMARY_UOM_CODE;

    public String getPRIMARY_UOM_CODE() {
        return PRIMARY_UOM_CODE;
    }

    public void setPRIMARY_UOM_CODE(String PRIMARY_UOM_CODE) {
        this.PRIMARY_UOM_CODE = PRIMARY_UOM_CODE;
    }

    public String getINVENTORY_ITEM_ID() {
        return INVENTORY_ITEM_ID;
    }

    public void setINVENTORY_ITEM_ID(String INVENTORY_ITEM_ID) {
        this.INVENTORY_ITEM_ID = INVENTORY_ITEM_ID;
    }

    public String getSUBINVENTORY_CODE() {
        return SUBINVENTORY_CODE;
    }

    public void setSUBINVENTORY_CODE(String SUBINVENTORY_CODE) {
        this.SUBINVENTORY_CODE = SUBINVENTORY_CODE;
    }

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
