package com.posco.erp.wipapp.views.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.posco.erp.wipapp.R;
import com.posco.erp.wipapp.utils.DBOpenHelper;

public class OnhandCursorAdapter extends CursorAdapter {
    public OnhandCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String itemCode = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.ONHAND_ITEM_CD)
        );
        TextView tv_itemCode = (TextView) view.findViewById(R.id.item_detail);
        tv_itemCode.setText(itemCode);
        tv_itemCode.setEllipsize(TextUtils.TruncateAt.END);
        tv_itemCode.setSingleLine();

        String quantity = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ONHAND_QUANTITY));
        TextView tv_quantity = (TextView) view.findViewById(R.id.onhand_detail);
//        try{
//            DecimalFormat format = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                format = new DecimalFormat("#,###,###");
//            }
//            quantity = format.format(quantity);
//        }catch (Exception ex)
//        {
//
//        }
        tv_quantity.setText(quantity);


        String itemDesc = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.ONHAND_ITEM_DESC)
        );
        TextView tv_itemDesc = (TextView) view.findViewById(R.id.desc_detail);
        tv_itemDesc.setText(itemDesc);
        tv_itemDesc.setEllipsize(TextUtils.TruncateAt.END);
        tv_itemDesc.setSingleLine();
    }
}
