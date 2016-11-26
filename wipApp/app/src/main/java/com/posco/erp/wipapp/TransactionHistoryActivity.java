package com.posco.erp.wipapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.posco.erp.wipapp.managers.HttpManager;
import com.posco.erp.wipapp.models.itemDTO;
import com.posco.erp.wipapp.models.Item_transactionJSONParser;
import com.posco.erp.wipapp.network.RequestPackage;
import com.posco.erp.wipapp.views.adapters.Transaction_history_item_adapter;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {
    List<itemDTO> resultList;
    ListView lv ;
    String uri = "http://172.27.26.55:8080/screen3popJSONServlet";
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView tvSearch = (TextView) findViewById(R.id.searchEditText2);
        ImageButton submit = (ImageButton) findViewById(R.id.btnSearch2);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                tvSearch.clearFocus();
                String query = tvSearch.getText().toString().trim().toUpperCase();
                if (!query.equalsIgnoreCase("") && !query.isEmpty())
                {
                    doSearch(query);
                }
            }
        });
        lv = (ListView) findViewById(R.id.listView2);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TransactionHistoryActivity.this, TransactionHistoryDetailActivity.class);
//                Uri uri = Uri.parse(NotesProvider.CONTENT_URI + "/" + id);
//                intent.putExtra(NotesProvider.CONTENT_ITEM_TYPE, uri);
//                view.get
                startActivity(intent);
//                Object listItem = lv.getItemAtPosition(position);
//                Log.v("TRUNG", "onItemClick");
            }
        });
        pb = (ProgressBar) findViewById(R.id.progressBar2);
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
            Toast.makeText(TransactionHistoryActivity.this,"Network isn't available",Toast.LENGTH_LONG).show();
        }
    }
    private void requestData(String uri,String query) {
        TransactionHistoryActivity.MyTask task = new TransactionHistoryActivity.MyTask();
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(uri);
        p.setParam("itemCd",query);
        task.execute(p);
    }
    private void updateDisplay() {
        Transaction_history_item_adapter adapter = new Transaction_history_item_adapter(this,R.layout.item_transaction_history_item,resultList);
        if (resultList != null)
        {
            if (resultList.size() > 0)
            {
                ListView lv = (ListView) findViewById(R.id.listView2);
                lv.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(TransactionHistoryActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(TransactionHistoryActivity.this,"Error Response",Toast.LENGTH_LONG).show();
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
                Toast.makeText(TransactionHistoryActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
            }
            else{
                resultList = Item_transactionJSONParser.parseString(s);
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
