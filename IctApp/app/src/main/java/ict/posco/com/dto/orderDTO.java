package ict.posco.com.dto;

import java.util.Date;

public class orderDTO {
    private String ordNo;
    private String cntrNm;
    private String custNm;
    private Date ordDwpDt;
    private String wgtDcsTp;
    private String paymentTermCd;
    private String dlvrTermCd;
    private String exchangeTp;
    private String destNm;
    private String custPoNo;

    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
    }

    public String getCntrNm() {
        return cntrNm;
    }

    public void setCntrNm(String cntrNm) {
        this.cntrNm = cntrNm;
    }

    public String getCustNm() {
        return custNm;
    }

    public void setCustNm(String custNm) {
        this.custNm = custNm;
    }

    public Date getOrdDwpDt() {
        return ordDwpDt;
    }

    public void setOrdDwpDt(Date ordDwpDt) {
        this.ordDwpDt = ordDwpDt;
    }

    public String getWgtDcsTp() {
        return wgtDcsTp;
    }

    public void setWgtDcsTp(String wgtDcsTp) {
        this.wgtDcsTp = wgtDcsTp;
    }

    public String getPaymentTermCd() {
        return paymentTermCd;
    }

    public void setPaymentTermCd(String paymentTermCd) {
        this.paymentTermCd = paymentTermCd;
    }

    public String getDlvrTermCd() {
        return dlvrTermCd;
    }

    public void setDlvrTermCd(String dlvrTermCd) {
        this.dlvrTermCd = dlvrTermCd;
    }

    public String getExchangeTp() {
        return exchangeTp;
    }

    public void setExchangeTp(String exchangeTp) {
        this.exchangeTp = exchangeTp;
    }

    public String getDestNm() {
        return destNm;
    }

    public void setDestNm(String destNm) {
        this.destNm = destNm;
    }

    public String getCustPoNo() {
        return custPoNo;
    }

    public void setCustPoNo(String custPoNo) {
        this.custPoNo = custPoNo;
    }
}
