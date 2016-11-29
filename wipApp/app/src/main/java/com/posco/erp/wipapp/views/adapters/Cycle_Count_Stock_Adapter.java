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
import com.posco.erp.wipapp.models.subInventoryDTO;

import java.util.List;

public class Cycle_Count_Stock_Adapter extends ArrayAdapter<subInventoryDTO> {
    private Context context;
    private List<subInventoryDTO> resultList;

    public Cycle_Count_Stock_Adapter(Context context, int resource, List<subInventoryDTO> objects) {
        super(context, resource, objects);
        this.context = context;
        resultList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_cycle_count_stock,parent,false);

        subInventoryDTO dto = resultList.get(position);

        String stock_cd = dto.getSecondary_inventory_name();
        TextView tv_stock_cd = (TextView) view.findViewById(R.id.stock_cd);
        tv_stock_cd.setText(stock_cd);
        tv_stock_cd.setEllipsize(TextUtils.TruncateAt.END);
        tv_stock_cd.setSingleLine();

        String stock_desc = dto.getDescription();
        TextView tv_stock_desc = (TextView) view.findViewById(R.id.stock_desc);
        tv_stock_desc.setText(stock_desc);
        tv_stock_desc.setSingleLine();

        return view;
    }
}
