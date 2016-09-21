package ict.posco.com.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ict.posco.com.models.orderDTO;
public class orderJSONParser {
    public static List<orderDTO> parseString(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<orderDTO> resultList = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                orderDTO objOrder = new orderDTO();
                objOrder.setOrdNo(obj.getString("ordNo"));
                objOrder.setCntrNm(obj.getString("cntrNm"));
                objOrder.setCustNm(obj.getString("custNm"));
                objOrder.setOrdDwpDt(obj.getString("ordDwpDt"));
                objOrder.setWgtDcsTp(obj.getString("wgtDcsTp"));
                objOrder.setPaymentTermCd(obj.getString("paymentTermCd"));
                objOrder.setDlvrTermCd(obj.getString("dlvrTermCd"));
                objOrder.setExchangeTp(obj.getString("exchangeTp"));
                objOrder.setDestNm(obj.getString("destNm"));
                objOrder.setCustPoNo(obj.getString("custPoNo"));

                resultList.add(objOrder);
            }
            return resultList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
