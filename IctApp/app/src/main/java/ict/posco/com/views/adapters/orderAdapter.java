package ict.posco.com.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ict.posco.com.models.orderDTO;
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

        TextView tv_ordDwpDt = (TextView) view.findViewById(R.id.textView_ordDwpDt);
        tv_ordDwpDt.setText(dto.getOrdDwpDt());
        tv_ordDwpDt.setEllipsize(TextUtils.TruncateAt.END);
        tv_ordDwpDt.setSingleLine();

        TextView tv_wgtDcsTp = (TextView) view.findViewById(R.id.textView_wgtDcsTp);
        tv_wgtDcsTp.setText(dto.getWgtDcsTp());
        tv_wgtDcsTp.setEllipsize(TextUtils.TruncateAt.END);
        tv_wgtDcsTp.setSingleLine();

        TextView tv_paymentTermCd = (TextView) view.findViewById(R.id.textView_paymentTermCd);
        tv_paymentTermCd.setText(dto.getPaymentTermCd());
        tv_paymentTermCd.setEllipsize(TextUtils.TruncateAt.END);
        tv_paymentTermCd.setSingleLine();

        TextView tv_dlvrTermCd = (TextView) view.findViewById(R.id.textView_dlvrTermCd);
        tv_dlvrTermCd.setText(dto.getDlvrTermCd());
        tv_dlvrTermCd.setEllipsize(TextUtils.TruncateAt.END);
        tv_dlvrTermCd.setSingleLine();

        TextView tv_exchangeTp = (TextView) view.findViewById(R.id.textView_exchangeTp);
        tv_exchangeTp.setText(dto.getExchangeTp());
        tv_exchangeTp.setEllipsize(TextUtils.TruncateAt.END);
        tv_exchangeTp.setSingleLine();

        TextView tv_destNm = (TextView) view.findViewById(R.id.textView_destNm);
        tv_destNm.setText(dto.getDestNm());
        tv_destNm.setEllipsize(TextUtils.TruncateAt.END);
        tv_destNm.setSingleLine();

        TextView tv_custPoNo = (TextView) view.findViewById(R.id.textView_custPoNo);
        tv_custPoNo.setText(dto.getCustPoNo());
        tv_custPoNo.setEllipsize(TextUtils.TruncateAt.END);
        tv_custPoNo.setSingleLine();
        return view;
    }
}
