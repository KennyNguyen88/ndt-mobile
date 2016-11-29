package com.posco.erp.wipapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.posco.erp.wipapp.managers.HttpManager;
import com.posco.erp.wipapp.models.Stock_CycleJSONParser;
import com.posco.erp.wipapp.models.subInventoryDTO;
import com.posco.erp.wipapp.network.RequestPackage;
import com.posco.erp.wipapp.utils.UtilIf;
import com.posco.erp.wipapp.views.adapters.Cycle_Count_Stock_Adapter;

import java.util.ArrayList;
import java.util.List;

public class CycleCountStockActivity extends AppCompatActivity {
    List<subInventoryDTO> resultList;
    ListView lv ;
    ProgressBar pb;
    TextView tvSearch;
    Cycle_Count_Stock_Adapter adapter;
    String uri = "http://113.164.120.62:8070/CHD/screen2popJSONServlet";
    static String SUB_INVENTORY = "subInventory";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_count_stock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvSearch = (TextView) findViewById(R.id.searchEditText);
        ImageButton submit = (ImageButton) findViewById(R.id.btnSearch);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        resultList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listView);

        //Main Action
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doSearch();
            }
        });
        tvSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            doSearch();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CycleCountStockActivity.this, CycleCountDetailActivity.class);
                subInventoryDTO it = (subInventoryDTO) lv.getItemAtPosition(position);
                intent.putExtra(SUB_INVENTORY,it.getSecondary_inventory_name());
                startActivityForResult(intent,0);
            }
        });
        pb.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    protected void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
    private void doSearch() {
        if(isOnline())
        {
            hideKeyboard();
            String subInventory = tvSearch.getText().toString().trim().toUpperCase();
            if (!subInventory.equalsIgnoreCase("") && !subInventory.isEmpty())
            {
                requestData(subInventory);
            }
        }
        else{
            UtilIf.notify_message(CycleCountStockActivity.this,getString(R.string.no_network));
        }
    }
    private void requestData(String subInventory) {
        CycleCountStockActivity.MyTask task = new CycleCountStockActivity.MyTask();
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(uri);
        p.setParam("subInventory",subInventory);
        task.execute(p);
    }
    private void updateDisplay() {
        adapter = new Cycle_Count_Stock_Adapter(this,R.layout.item_cycle_count_stock,resultList);
        if (resultList != null)
        {
            if (resultList.size() > 0)
            {
                ListView lv = (ListView) findViewById(R.id.listView);
                lv.setAdapter(adapter);
            }
            else
            {
                UtilIf.notify_message(CycleCountStockActivity.this,getString(R.string.no_result));
            }
        }
        else
        {
            UtilIf.notify_message(CycleCountStockActivity.this,getString(R.string.null_result));
        }
    }
    private class MyTask extends AsyncTask<RequestPackage, String, String > {
        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }
        @Override
        protected void onPostExecute(String s) {
            if (s.isEmpty() || s.equalsIgnoreCase(""))
            {
                UtilIf.notify_message(CycleCountStockActivity.this,getString(R.string.no_result));
            }
            else{
                resultList = Stock_CycleJSONParser.parseString(s);
                updateDisplay();
            }
            pb.setVisibility(View.INVISIBLE);
        }
    }
}
