package com.posco.erp.wipapp.models;

/**
 * Created by DucTrung on 9/28/2016.
 */
public class cycleDTO extends itemDTO {
    private Double ActQty;

    public cycleDTO() {
        super();
    }

    public Double getActQty() {
        return ActQty;
    }

    public void setActQty(Double actQty) {
        ActQty = actQty;
    }
}
