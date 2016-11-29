package com.posco.erp.wipapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIn on 11/25/2016.
 */

public class Item_TransactionJSONParser {
    public static List<itemDTO> parseString(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<itemDTO> resultList = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                itemDTO onhand = new itemDTO();
                onhand.setITEM_CD(obj.getString("itemCd"));
                onhand.setQUANTITY(obj.getDouble("quantity"));
                onhand.setDESCRIPTION(obj.getString("description"));
                onhand.setINVENTORY_ITEM_ID(obj.getString("inventory_item_id"));
                onhand.setPRIMARY_UOM_CODE(obj.getString("primary_uom_code"));
                resultList.add(onhand);
            }
            return resultList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
