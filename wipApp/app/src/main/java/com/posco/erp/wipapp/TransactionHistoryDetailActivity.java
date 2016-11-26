package com.posco.erp.wipapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.posco.erp.wipapp.managers.HttpManager;
import com.posco.erp.wipapp.models.Detail_TransactionJSONParser;
import com.posco.erp.wipapp.models.itemDTO;
import com.posco.erp.wipapp.models.transactionDTO;
import com.posco.erp.wipapp.network.RequestPackage;
import com.posco.erp.wipapp.views.adapters.Transaction_History_Detail_Adapter;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryDetailActivity extends AppCompatActivity {
    String uri = "http://172.27.26.55:8080/screen3JSONServlet";
    List resultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();
        String inventoryItemId = b.getString("inventoryItemId");
        String itemCd = b.getString("itemCd");
        doSearch(inventoryItemId,itemCd);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    private void doSearch(String inventoryItemId,String itemCd) {
        if(isOnline())
        {
            requestData(uri,inventoryItemId, itemCd);
        }
        else{
            Toast.makeText(TransactionHistoryDetailActivity.this,"Network isn't available",Toast.LENGTH_LONG).show();
        }
    }
    private void requestData(String uri,String inventoryItemId, String itemCd) {
        TransactionHistoryDetailActivity.MyTask task = new TransactionHistoryDetailActivity.MyTask();
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(uri);
        p.setParam("itemCd",itemCd);
        p.setParam("inventoryItemId",inventoryItemId);
        task.execute(p);
    }
    private void updateDisplay() {
//        Transaction_History_Item_Adapter adapter = new Transaction_History_Item_Adapter(this,R.layout.item_transaction_history_item,resultList);
        if (resultList != null)
        {
            if (resultList.size() > 0)
            {
                itemDTO item = (itemDTO) resultList.get(0);

                List<transactionDTO> results = new ArrayList<>();
                for(int i = 1 ; i< resultList.size(); i ++)
                {
                    transactionDTO trx = (transactionDTO) resultList.get(i);
                    results.add(trx);
                }
                updateDisplayItem(item);
                Transaction_History_Detail_Adapter adapter = new Transaction_History_Detail_Adapter(this,R.layout.item_transaction_history_detail,results);
                ListView lv = (ListView) findViewById(R.id.listView3);
                lv.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(TransactionHistoryDetailActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(TransactionHistoryDetailActivity.this,"Error Response",Toast.LENGTH_LONG).show();
        }
    }
    private void updateDisplayItem(itemDTO dto)
    {
        TextView tv_Item_Detail = (TextView) findViewById(R.id.item_detail);
        tv_Item_Detail.setText(dto.getITEM_CD());
        tv_Item_Detail.setEllipsize(TextUtils.TruncateAt.END);
        tv_Item_Detail.setSingleLine();

        TextView tv_Onhand_Detail = (TextView) findViewById(R.id.onhand_detail);
        tv_Onhand_Detail.setText(String.valueOf(dto.getQUANTITY()));
        tv_Onhand_Detail.setEllipsize(TextUtils.TruncateAt.END);
        tv_Onhand_Detail.setSingleLine();

        TextView tv_Item_Desc_Detail = (TextView) findViewById(R.id.itemdesc_detail);
        tv_Item_Desc_Detail.setText(dto.getDESCRIPTION());
        tv_Item_Desc_Detail.setEllipsize(TextUtils.TruncateAt.END);
        tv_Item_Desc_Detail.setSingleLine();
    }
    private class MyTask extends AsyncTask<RequestPackage, String, String > {
        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }
        @Override
        protected void onPostExecute(String s) {
            if (s.isEmpty() || s.equalsIgnoreCase(""))
            {
                Toast.makeText(TransactionHistoryDetailActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
            }
            else{
                resultList = Detail_TransactionJSONParser.parseString(s);
                updateDisplay();
            }
        }
    }
}
