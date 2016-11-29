package com.posco.erp.wipapp.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.posco.erp.wipapp.R;
import com.posco.erp.wipapp.models.cycleDTO;
import com.posco.erp.wipapp.models.subInventoryDTO;

import java.util.List;

public class Cycle_Count_Detail_Adapter extends ArrayAdapter<cycleDTO> {
    private Context context;
    private List<cycleDTO> resultList;

    public Cycle_Count_Detail_Adapter(Context context, int resource, List<cycleDTO> objects) {
        super(context, resource, objects);
        this.context = context;
        resultList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_cycle_count_detail,parent,false);

        final cycleDTO dto = resultList.get(position);

        String itemCd = dto.getITEM_CD();
        TextView tv_item_detail = (TextView) view.findViewById(R.id.item_detail);
        tv_item_detail.setText(itemCd);
        tv_item_detail.setEllipsize(TextUtils.TruncateAt.END);
        tv_item_detail.setSingleLine();

        String item_desc_detail = dto.getDESCRIPTION();
        TextView tv_item_desc_detail = (TextView) view.findViewById(R.id.item_desc_detail);
        tv_item_desc_detail.setText(item_desc_detail);
//        tv_item_desc_detail.setSingleLine();

        Double onhand_detail = dto.getQUANTITY();
        TextView tv_onhand_detail = (TextView) view.findViewById(R.id.onhand_detail);
        tv_onhand_detail.setText(onhand_detail.toString());
        tv_onhand_detail.setSingleLine();

        Double act_detail = dto.getActQty();
        TextView tv_act_detail = (TextView) view.findViewById(R.id.act_detail);
        tv_act_detail.setText(act_detail.toString());
        tv_act_detail.setSingleLine();

//        Switch btn_switch = (Switch) view.findViewById(R.id.btnswitch);
//        btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked)
//                    {
//                        Log.v("Switch checked=", ""+ dto.getINVENTORY_ITEM_ID());
//                    }
//                }
//        });

        return view;
    }
}
