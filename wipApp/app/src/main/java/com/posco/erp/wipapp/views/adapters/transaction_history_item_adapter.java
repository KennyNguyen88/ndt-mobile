package com.posco.erp.wipapp.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.posco.erp.wipapp.R;
import com.posco.erp.wipapp.models.itemDTO;

import java.util.List;

public class Transaction_History_Item_Adapter extends ArrayAdapter<itemDTO> {
    private Context context;
    private List<itemDTO> resultList;

    public Transaction_History_Item_Adapter(Context context, int resource, List<itemDTO> objects) {
        super(context, resource, objects);
        this.context = context;
        resultList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_transaction_history_item,parent,false);

        itemDTO dto = resultList.get(position);

        String itemCode = dto.getITEM_CD();
        TextView tv_itemCode = (TextView) view.findViewById(R.id.item_detail);
        tv_itemCode.setText(itemCode);
        tv_itemCode.setEllipsize(TextUtils.TruncateAt.END);
        tv_itemCode.setSingleLine();

        String uom = dto.getPRIMARY_UOM_CODE();
        TextView tv_uom = (TextView) view.findViewById(R.id.uom_detail);
        tv_uom.setText(uom);
        tv_uom.setSingleLine();

        String quantity = String.valueOf(dto.getQUANTITY());
        TextView tv_quantity = (TextView) view.findViewById(R.id.onhand_detail);
        tv_quantity.setText(quantity);
        tv_quantity.setSingleLine();

        String desc = dto.getDESCRIPTION();
        TextView tv_itemDesc = (TextView) view.findViewById(R.id.desc_detail);
        tv_itemDesc.setText(desc);
        tv_itemDesc.setEllipsize(TextUtils.TruncateAt.END);
        tv_itemDesc.setSingleLine();

        return view;
    }
}
