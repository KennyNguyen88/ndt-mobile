package ict.posco.com.ict.posco.com.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ict.posco.com.dto.orderDTO;
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
                resultList.add(objOrder);
            }
            return resultList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
