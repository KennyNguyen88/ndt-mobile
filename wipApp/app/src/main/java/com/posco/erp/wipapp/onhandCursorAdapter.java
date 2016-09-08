package com.posco.erp.wipapp;

import android.content.Context;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class onhandCursorAdapter extends CursorAdapter {
    public onhandCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String itemCode = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.ONHAND_ITEM_CD)
        );
        TextView tv_itemCode = (TextView) view.findViewById(R.id.item_detail);
        tv_itemCode.setText(itemCode);

        String quantity = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ONHAND_QUANTITY));
        TextView tv_quantity = (TextView) view.findViewById(R.id.onhand_detail);
        tv_quantity.setText(quantity);

        String itemDesc = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.ONHAND_ITEM_DESC)
        );
        TextView tv_itemDesc = (TextView) view.findViewById(R.id.desc_detail);
        tv_itemDesc.setText(itemDesc);
    }
}
