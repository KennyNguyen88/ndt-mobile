package ict.posco.com.ict.posco.com.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ict.posco.com.model.flower;

public class FlowerJSONParser {
    public static List<flower> parseString(String content){
        try {
            JSONArray ar = new JSONArray(content);
            List<flower> flowerList = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                flower fl = new flower();
                fl.setProductId(obj.getInt("productId"));
                fl.setPrice(obj.getDouble("price"));
                fl.setCategory(obj.getString("category"));
                fl.setInstructions(obj.getString("instructions"));
                fl.setName(obj.getString("name"));
                fl.setPhoto(obj.getString("photo"));
                flowerList.add(fl);
            }
            return flowerList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
