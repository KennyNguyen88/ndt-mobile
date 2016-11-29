package com.posco.erp.wipapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIn on 11/26/2016.
 */

public class Stock_CycleJSONParser {
    public static List<subInventoryDTO> parseString(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<subInventoryDTO> resultList = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                subInventoryDTO stock = new subInventoryDTO();
                stock.setSecondary_inventory_name(obj.getString("secondary_inventory_name"));
                stock.setDescription(obj.getString("description"));
                resultList.add(stock);
            }
            return resultList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
