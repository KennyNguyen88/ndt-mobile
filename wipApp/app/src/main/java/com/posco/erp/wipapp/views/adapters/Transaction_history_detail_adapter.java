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
import com.posco.erp.wipapp.models.transactionDTO;

import java.util.List;

/**
 * Created by WIn on 11/26/2016.
 */

public class Transaction_History_Detail_Adapter extends ArrayAdapter<transactionDTO> {
    private Context context;
    private List<transactionDTO> resultList;
    public Transaction_History_Detail_Adapter(Context context, int resource, List<transactionDTO> objects) {
        super(context, resource, objects);
        this.context = context;
        resultList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_transaction_history_detail,parent,false);
        transactionDTO dto = resultList.get(position);

        String trxDate = dto.getsDate();
        TextView tv_date_detail = (TextView) view.findViewById(R.id.date_detail);
        tv_date_detail.setText(trxDate);
        tv_date_detail.setEllipsize(TextUtils.TruncateAt.END);
        tv_date_detail.setSingleLine();

        String trxStock = dto.getsStock();
        TextView tv_stock_detail = (TextView) view.findViewById(R.id.stock_detail);
        tv_stock_detail.setText(trxStock);
        tv_stock_detail.setSingleLine();

        String trxTp = String.valueOf(dto.getsTrx());
        TextView tv_trxTp_detail = (TextView) view.findViewById(R.id.trxTp_detail);
        tv_trxTp_detail.setText(trxTp);
        tv_trxTp_detail.setSingleLine();

        Double trxQty = dto.getdTrx();
        TextView tv_trxQty_detail = (TextView) view.findViewById(R.id.trxQty_detail);
        tv_trxQty_detail.setText(trxQty.toString());
        tv_trxQty_detail.setEllipsize(TextUtils.TruncateAt.END);
        tv_trxQty_detail.setSingleLine();

        Double trxDue = dto.getdDue();
        TextView tv_dueQty_detail = (TextView) view.findViewById(R.id.dueQty_detail);
        tv_dueQty_detail.setText(trxDue.toString());
        tv_dueQty_detail.setEllipsize(TextUtils.TruncateAt.END);
        tv_dueQty_detail.setSingleLine();

        return view;
    }
}
