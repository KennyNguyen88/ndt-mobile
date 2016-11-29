package com.posco.erp.wipapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.posco.erp.wipapp.managers.HttpManager;
import com.posco.erp.wipapp.network.RequestPackage;
import com.posco.erp.wipapp.utils.UtilIf;

public class CycleCountDetailEditorActivity extends AppCompatActivity {
    TextView tv_item;
    TextView tv_desc;
    TextView tv_onhand;
    EditText ed_act;
    String inventoryItemId;
    String subInventory;
    String result = "";
    String uri = "http://113.164.120.62:8070/CHD/screen2JSONServlet";
    static String SUB_INVENTORY = "subInventory";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_count_detail_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button submit = (Button) findViewById(R.id.btnSubmit);
        tv_item = (TextView) findViewById(R.id.item_detail);
        tv_desc = (TextView) findViewById(R.id.item_desc_detail);
        tv_onhand = (TextView) findViewById(R.id.onhand_detail);
        ed_act = (EditText) findViewById(R.id.act_detail);

        //Get data from parent Activity
        Bundle b = getIntent().getExtras();
        subInventory = b.getString(CycleCountDetailActivity.SUB_INVENTORY);
        inventoryItemId = b.getString(CycleCountDetailActivity.INVENTORY_ITEM_ID);
        String itemCd = b.getString(CycleCountDetailActivity.ITEM_CD);
        String description = b.getString(CycleCountDetailActivity.DESCRIPTION);
        Double onhand = Double.parseDouble(b.getString(CycleCountDetailActivity.ON_HAND));
        Double actQty = Double.parseDouble(b.getString(CycleCountDetailActivity.ACT_QTY));

        //title
        setTitle(itemCd);
        //show value

        tv_item.setText(itemCd);
        tv_desc.setText(description);
        tv_onhand.setText(onhand.toString());
        ed_act.setText(actQty.toString());

        //Main action
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateData();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finishEditing();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        finishEditing();
    }
    private void finishEditing(){
        if (result.equalsIgnoreCase("success"))
        {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(SUB_INVENTORY, subInventory);
            setResult(1, resultIntent);
        }
        else{
            setResult(0);
        }
        finish();
    }
    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    private void updateData(){
        if(isOnline())
        {
            String actQty = ed_act.getText().toString();
            postData(uri,actQty, inventoryItemId);
        }
        else{
            UtilIf.notify_message(CycleCountDetailEditorActivity.this,getString(R.string.no_network));
        }
    }
    private void postData(String uri,String actQty, String itemId) {
        CycleCountDetailEditorActivity.MyPOSTTask task = new CycleCountDetailEditorActivity.MyPOSTTask();
        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri(uri);
        p.setParam("subInventory",subInventory);
        p.setParam("actQty",actQty);
        p.setParam("itemId",itemId);
        task.execute(p);
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
                UtilIf.notify_message(CycleCountDetailEditorActivity.this,getString(R.string.no_result));
            }
            else{
                UtilIf.notify_message(CycleCountDetailEditorActivity.this,s);
                result = s.trim();
            }
        }
    }
}
