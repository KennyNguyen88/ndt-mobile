package com.posco.erp.wipapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.posco.erp.wipapp.managers.HttpManager;
import com.posco.erp.wipapp.models.Cycle_CycleJSONParser;
import com.posco.erp.wipapp.models.Detail_TransactionJSONParser;
import com.posco.erp.wipapp.models.cycleDTO;
import com.posco.erp.wipapp.models.itemDTO;
import com.posco.erp.wipapp.models.transactionDTO;
import com.posco.erp.wipapp.network.RequestPackage;
import com.posco.erp.wipapp.views.adapters.Cycle_Count_Detail_Adapter;
import com.posco.erp.wipapp.views.adapters.OnhandAdapter;
import com.posco.erp.wipapp.views.adapters.Transaction_History_Detail_Adapter;

import java.util.ArrayList;
import java.util.List;

public class CycleCountDetailActivity extends AppCompatActivity {
    String uri = "http://113.164.120.62:8070/CHD/screen2JSONServlet";
//    String uri = "http://172.27.26.55:8080/screen2JSONServlet";
    List resultList;
    ListView lv;
    String subInventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_count_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.listView);
        //Get data from parent Activity
        Bundle b = getIntent().getExtras();
        subInventory = b.getString("subInventory");
        //header
        TextView tv_stock_detail = (TextView) findViewById(R.id.stock_detail);
        tv_stock_detail.setText("Stock: "+ subInventory);

        doSearch(subInventory);
        Button submit = (Button) findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateView();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void updateView(){
//        for (int i = 0 ; i < lv.getCount(); i++) {
//            View v = lv.getChildAt(i);
//            cycleDTO it = (cycleDTO) lv.getItemAtPosition(i);
//            String itemId = it.getINVENTORY_ITEM_ID();
//            EditText ed_actQty = (EditText) v.findViewById(R.id.act_detail);
//            Double actQty = Double.parseDouble(ed_actQty.getText().toString());
//            postData(uri,actQty, itemId);
//        }

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
            return true;
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
    private void doSearch(String subInventory) {
        if(isOnline())
        {
            requestData(uri,subInventory);
        }
        else{
            Toast.makeText(CycleCountDetailActivity.this,"Network isn't available",Toast.LENGTH_LONG).show();
        }
    }
    private void postData(String uri,Double actQty, String itemId) {
        Log.v("postData",itemId );
        CycleCountDetailActivity.MyPOSTTask task = new CycleCountDetailActivity.MyPOSTTask();
        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri(uri);
        p.setParam("subInventory",subInventory);
        p.setParam("actQty",actQty.toString());
        p.setParam("itemId",itemId);
        task.execute(p);
    }
    private void requestData(String uri,String subInventory) {
        CycleCountDetailActivity.MyTask task = new CycleCountDetailActivity.MyTask();
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(uri);
        p.setParam("subInventory",subInventory);
        task.execute(p);
    }
    private void updateDisplay() {
        Cycle_Count_Detail_Adapter adapter = new Cycle_Count_Detail_Adapter(this,R.layout.activity_cycle_count_detail,resultList);
        if (resultList != null)
        {
            if (resultList.size() > 0)
            {
                lv.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(CycleCountDetailActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(CycleCountDetailActivity.this,"Error Response",Toast.LENGTH_LONG).show();
        }
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
                Toast.makeText(CycleCountDetailActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
            }
            else{
                resultList = Cycle_CycleJSONParser.parseString(s);
                updateDisplay();
            }
        }
    }
    private class MyPOSTTask extends AsyncTask<RequestPackage, String, String > {
        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }
        @Override
        protected void onPostExecute(String s) {
            if (s.isEmpty() || s.equalsIgnoreCase(""))
            {
                Toast.makeText(CycleCountDetailActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
            }
            else{
//                resultList = Cycle_CycleJSONParser.parseString(s);
//                updateDisplay();
            }
        }
    }
}
