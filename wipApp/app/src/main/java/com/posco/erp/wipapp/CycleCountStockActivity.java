package com.posco.erp.wipapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.posco.erp.wipapp.R;
import com.posco.erp.wipapp.managers.HttpManager;
import com.posco.erp.wipapp.models.ItemJSONParser;
import com.posco.erp.wipapp.models.Stock_CycleJSONParser;
import com.posco.erp.wipapp.models.cycleDTO;
import com.posco.erp.wipapp.models.itemDTO;
import com.posco.erp.wipapp.models.subInventoryDTO;
import com.posco.erp.wipapp.network.RequestPackage;
import com.posco.erp.wipapp.views.adapters.Cycle_Count_Stock_Adapter;
import com.posco.erp.wipapp.views.adapters.OnhandAdapter;

import java.util.ArrayList;
import java.util.List;

public class CycleCountStockActivity extends AppCompatActivity {
    List<subInventoryDTO> resultList;
    ListView lv ;
    String uri = "http://172.27.26.55:8080/screen2popJSONServlet";
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_count_stock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView tvSearch = (TextView) findViewById(R.id.searchEditText);
        ImageButton submit = (ImageButton) findViewById(R.id.btnSearch);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String query = tvSearch.getText().toString().trim().toUpperCase();
                if (!query.equalsIgnoreCase("") && !query.isEmpty())
                {
                    doSearch(query);
                }
            }
        });
        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CycleCountStockActivity.this, CycleCountDetailActivity.class);
                subInventoryDTO it = (subInventoryDTO) lv.getItemAtPosition(position);
                intent.putExtra("subInventory",it.getSecondary_inventory_name());
                startActivityForResult(intent,0);
            }
        });
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        resultList = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    private void doSearch(String query) {
        if(isOnline())
        {
            requestData(uri,query);
        }
        else{
            Toast.makeText(CycleCountStockActivity.this,"Network isn't available",Toast.LENGTH_LONG).show();
        }
    }
    private void requestData(String uri,String query) {
        CycleCountStockActivity.MyTask task = new CycleCountStockActivity.MyTask();
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(uri);
        p.setParam("subInventory",query);
        task.execute(p);
    }
    private void updateDisplay() {
        Cycle_Count_Stock_Adapter adapter = new Cycle_Count_Stock_Adapter(this,R.layout.item_cycle_count_stock,resultList);
        if (resultList != null)
        {
            if (resultList.size() > 0)
            {
                ListView lv = (ListView) findViewById(R.id.listView);
                lv.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(CycleCountStockActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(CycleCountStockActivity.this,"Error Response",Toast.LENGTH_LONG).show();
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
                Toast.makeText(CycleCountStockActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
            }
            else{
                resultList = Stock_CycleJSONParser.parseString(s);
                updateDisplay();

            }
            pb.setVisibility(View.INVISIBLE);
        }
        @Override
        protected void onProgressUpdate(String... values) {
//            updateDisplay(values[0]);
        }
    }
}
