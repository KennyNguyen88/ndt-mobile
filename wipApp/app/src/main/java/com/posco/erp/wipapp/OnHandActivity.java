package com.posco.erp.wipapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.posco.erp.wipapp.managers.HttpManager;
import com.posco.erp.wipapp.models.itemDTO;
import com.posco.erp.wipapp.models.ItemJSONParser;
import com.posco.erp.wipapp.network.RequestPackage;
import com.posco.erp.wipapp.views.adapters.OnhandAdapter;

import java.util.ArrayList;
import java.util.List;

public class OnHandActivity extends AppCompatActivity {
    List<itemDTO> resultList;
//    String uri = "http://172.27.26.55:8080/screen1JSONServlet";
    String uri = "http://113.164.120.62:8070/CHD/screen1JSONServlet";
    ProgressBar pb;
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
            Toast.makeText(OnHandActivity.this,"Network isn't available",Toast.LENGTH_LONG).show();
        }
    }
    private void requestData(String uri,String query) {
        MyTask task = new MyTask();
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(uri);
        p.setParam("itemCd",query);
        task.execute(p);
    }
    private void updateDisplay() {
        OnhandAdapter adapter = new OnhandAdapter(this,R.layout.item_onhand,resultList);
        if (resultList != null)
        {
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
        else
        {
            Toast.makeText(OnHandActivity.this,"Error Response",Toast.LENGTH_LONG).show();
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
                Toast.makeText(OnHandActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
            }
            else{
                resultList = ItemJSONParser.parseString(s);
                updateDisplay();

            }
            pb.setVisibility(View.INVISIBLE);
        }
    }
}
