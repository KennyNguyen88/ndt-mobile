package com.posco.erp.wipapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIn on 11/26/2016.
 */

public class Detail_TransactionJSONParser {
    public static List parseString(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List resultList = new ArrayList<>();

            JSONObject obj = ar.getJSONObject(0);
            itemDTO onhand = new itemDTO();
            onhand.setITEM_CD(obj.getString("itemCd"));
            onhand.setQUANTITY(obj.getDouble("onhand"));
            onhand.setDESCRIPTION(obj.getString("itemDesc"));
            resultList.add(onhand);

            for (int i = 1; i < ar.length(); i++) {
                JSONObject obj1 = ar.getJSONObject(i);
                transactionDTO trx = new transactionDTO();
                trx.setsDate(obj1.getString("trxDate"));
                trx.setsStock(obj1.getString("trxStock"));
                trx.setsTrx(obj1.getString("trxTp"));
                trx.setdTrx(obj1.getDouble("trxQty"));
                trx.setdDue(obj1.getDouble("trxDue"));
                resultList.add(trx);
            }
            return resultList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
