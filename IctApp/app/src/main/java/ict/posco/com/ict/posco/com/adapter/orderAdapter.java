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

import ict.posco.com.dto.orderDTO;
import ict.posco.com.ictapp.R;

public class orderAdapter extends ArrayAdapter<orderDTO> {
    private Context context;
    private List<orderDTO> resultList;

    public orderAdapter(Context context, int resource, List<orderDTO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resultList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_order,parent,false);

        orderDTO dto = resultList.get(position);

        TextView tv_ordNo = (TextView) view.findViewById(R.id.textView_ordNo);
        tv_ordNo.setText(dto.getOrdNo());
        tv_ordNo.setEllipsize(TextUtils.TruncateAt.END);
        tv_ordNo.setSingleLine();

        TextView tv_cntrNm = (TextView) view.findViewById(R.id.textView_cntrNm);
        tv_cntrNm.setText(dto.getCntrNm());
        tv_cntrNm.setEllipsize(TextUtils.TruncateAt.END);
        tv_cntrNm.setSingleLine();

        TextView tv_custNm = (TextView) view.findViewById(R.id.textView_custNm);
        tv_custNm.setText(dto.getCustNm());
        tv_custNm.setEllipsize(TextUtils.TruncateAt.END);
        tv_custNm.setSingleLine();
        return view;
    }
}
