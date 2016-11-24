package com.posco.erp.wipapp;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.posco.erp.wipapp.managers.HttpManager;
import com.posco.erp.wipapp.models.itemDTO;
import com.posco.erp.wipapp.models.onhandJSONParser;
import com.posco.erp.wipapp.network.RequestPackage;
import com.posco.erp.wipapp.views.adapters.onhandAdapter;

import java.util.ArrayList;
import java.util.List;

public class OnHandActivity extends AppCompatActivity {
    List<itemDTO> resultList;
    String uri = "http://172.27.26.55:8080/screen1JSONServlet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_hand);
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        resultList = new ArrayList<>();
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
            Toast.makeText(OnHandActivity.this,"Network isn't available",Toast.LENGTH_LONG).show();
        }
    }
    private void requestData(String uri,String query) {
        MyTask task = new MyTask();
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
//        p.setMethod("POST");
        p.setUri(uri);
        p.setParam("itemCd",query);
        task.execute(p);
    }
    private void updateDisplay() {
        onhandAdapter adapter = new onhandAdapter(this,R.layout.item_onhand,resultList);
        if (resultList.size() > 0)
        {
            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(OnHandActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
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
                Toast.makeText(OnHandActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
            }
            else{
                resultList = onhandJSONParser.parseString(s);
                updateDisplay();
            }
//            pb.setVisibility(View.INVISIBLE);
        }
        @Override
        protected void onProgressUpdate(String... values) {
//            updateDisplay(values[0]);
        }
    }
}
