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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.posco.erp.wipapp.managers.HttpManager;
import com.posco.erp.wipapp.models.Cycle_CycleJSONParser;
import com.posco.erp.wipapp.models.cycleDTO;
import com.posco.erp.wipapp.network.RequestPackage;
import com.posco.erp.wipapp.utils.UtilIf;
import com.posco.erp.wipapp.views.adapters.Cycle_Count_Detail_Adapter;

import java.util.List;

public class CycleCountDetailActivity extends AppCompatActivity {
    List resultList;
    ListView lv;
    Cycle_Count_Detail_Adapter adapter;
    CycleCountDetailActivity.MyTask task;
    String uri = "http://113.164.120.62:8070/CHD/screen2JSONServlet";
    String subInventory;
    static String SUB_INVENTORY = "subInventory";
    static String INVENTORY_ITEM_ID = "inventoryItemId";
    static String ITEM_CD = "itemCd";
    static String DESCRIPTION = "description";
    static String ON_HAND = "onhand";
    static String ACT_QTY = "actQty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_count_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.listView);

        //Get data from parent Activity
        Bundle b = getIntent().getExtras();
        subInventory = b.getString(CycleCountStockActivity.SUB_INVENTORY);

        //title
        setTitle(subInventory);

        //Main Action
        doSearch(subInventory);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CycleCountDetailActivity.this, CycleCountDetailEditorActivity.class);
                cycleDTO it = (cycleDTO) lv.getItemAtPosition(position);
                intent.putExtra(SUB_INVENTORY, subInventory);
                intent.putExtra(INVENTORY_ITEM_ID,it.getINVENTORY_ITEM_ID());
                intent.putExtra(ITEM_CD,it.getITEM_CD());
                intent.putExtra(DESCRIPTION,it.getDESCRIPTION());
                intent.putExtra(ON_HAND,String.valueOf(it.getQUANTITY()));
                intent.putExtra(ACT_QTY, String.valueOf(it.getActQty()));
                startActivityForResult(intent,0);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
            UtilIf.notify_message(CycleCountDetailActivity.this,getString(R.string.no_network));
        }
    }
    private void requestData(String uri,String subInventory) {
        task = new CycleCountDetailActivity.MyTask();
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(uri);
        p.setParam("subInventory",subInventory);
        task.execute(p);
    }
    private void updateDisplay() {
        adapter = new Cycle_Count_Detail_Adapter(this,R.layout.activity_cycle_count_detail,resultList);
        if (resultList != null)
        {
            if (resultList.size() > 0)
            {
                lv.setAdapter(adapter);
            }
            else
            {
                UtilIf.notify_message(CycleCountDetailActivity.this,getString(R.string.no_result));
            }
        }
        else
        {
            UtilIf.notify_message(CycleCountDetailActivity.this,getString(R.string.null_result));
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
                UtilIf.notify_message(CycleCountDetailActivity.this,getString(R.string.no_result));
            }
            else{
                resultList = Cycle_CycleJSONParser.parseString(s);
                updateDisplay();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 1)
        {
            String _subInventory = (String) data.getExtras().getString(CycleCountDetailEditorActivity.SUB_INVENTORY);
            doSearch(_subInventory);
//            updateDisplay();
        }
    }
}
