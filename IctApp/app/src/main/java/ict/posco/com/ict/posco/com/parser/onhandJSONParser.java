package ict.posco.com.ict.posco.com.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ict.posco.com.dto.onhandDTO;

public class onhandJSONParser {
    public static List<onhandDTO> parseString(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<onhandDTO> resultList = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                onhandDTO onhand = new onhandDTO();
                onhand.setITEM_CD(obj.getString("item_cd"));
                onhand.setQUANTITY(obj.getDouble("quantity"));
                onhand.setDESCRIPTION(obj.getString("description"));
                resultList.add(onhand);
            }
            return resultList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
