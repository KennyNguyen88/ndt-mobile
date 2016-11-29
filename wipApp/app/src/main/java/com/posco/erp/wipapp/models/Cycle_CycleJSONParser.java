package com.posco.erp.wipapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cycle_CycleJSONParser {
    public static List<cycleDTO> parseString(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<cycleDTO> resultList = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                cycleDTO cycle = new cycleDTO();
                cycle.setITEM_CD(obj.getString("itemCd"));
                cycle.setDESCRIPTION(obj.getString("description"));
                cycle.setINVENTORY_ITEM_ID(obj.getString("itemId"));
                cycle.setQUANTITY(obj.getDouble("donhand"));
                cycle.setActQty(obj.getDouble("dAct"));
                resultList.add(cycle);
            }
            return resultList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
