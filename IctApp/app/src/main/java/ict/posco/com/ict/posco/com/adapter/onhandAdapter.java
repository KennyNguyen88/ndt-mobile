package ict.posco.com.ict.posco.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ict.posco.com.dto.onhandDTO;
import ict.posco.com.ictapp.R;

public class onhandAdapter extends ArrayAdapter<onhandDTO> {
    private Context context;
    private List<onhandDTO> resultList;

    public onhandAdapter(Context context, int resource, List<onhandDTO> objects) {
        super(context, resource, objects);
        this.context = context;
        resultList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_onhand,parent,false);

        onhandDTO dto = resultList.get(position);

        TextView tv_itemCode = (TextView) view.findViewById(R.id.item_detail);
        tv_itemCode.setText(dto.getITEM_CD());
        tv_itemCode.setEllipsize(TextUtils.TruncateAt.END);
        tv_itemCode.setSingleLine();

        String quantity;
        TextView tv_quantity = (TextView) view.findViewById(R.id.onhand_detail);
        tv_quantity.setText(String.valueOf(dto.getQUANTITY()));
        tv_quantity.setSingleLine();

        TextView tv_itemDesc = (TextView) view.findViewById(R.id.desc_detail);
        tv_itemDesc.setText(dto.getDESCRIPTION());
        tv_itemDesc.setEllipsize(TextUtils.TruncateAt.END);
        tv_itemDesc.setSingleLine();

        return view;
    }
}
