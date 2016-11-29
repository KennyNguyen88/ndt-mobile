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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.posco.erp.wipapp.managers.HttpManager;
import com.posco.erp.wipapp.models.Item_TransactionJSONParser;
import com.posco.erp.wipapp.models.itemDTO;
import com.posco.erp.wipapp.network.RequestPackage;
import com.posco.erp.wipapp.utils.UtilIf;
import com.posco.erp.wipapp.views.adapters.Transaction_History_Item_Adapter;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {
    List<itemDTO> resultList;
    ListView lv ;
    ProgressBar pb;
    TextView tvSearch;
    Transaction_History_Item_Adapter adapter;
    String uri = "http://113.164.120.62:8070/CHD/screen3popJSONServlet";
    static String INVENTORY_ITEM_ID = "inventoryItemId";
    static String ITEM_CD = "itemCd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvSearch = (TextView) findViewById(R.id.searchEditText2);
        ImageButton submit = (ImageButton) findViewById(R.id.btnSearch2);
        lv = (ListView) findViewById(R.id.listView2);
        resultList = new ArrayList<>();
        //Main process
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
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doSearch();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TransactionHistoryActivity.this, TransactionHistoryDetailActivity.class);
                itemDTO it = (itemDTO) lv.getItemAtPosition(position);
                intent.putExtra(INVENTORY_ITEM_ID,it.getINVENTORY_ITEM_ID());
                intent.putExtra(ITEM_CD,it.getITEM_CD());
                startActivityForResult(intent,0);
            }
        });
        pb = (ProgressBar) findViewById(R.id.progressBar2);
        pb.setVisibility(View.INVISIBLE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    private void doSearch() {
        if(isOnline())
        {
            String itemCd = tvSearch.getText().toString().trim().toUpperCase();
            if (!itemCd.equalsIgnoreCase("") && !itemCd.isEmpty())
            {
                requestData(itemCd);
            }

        }
        else{
            UtilIf.notify_message(TransactionHistoryActivity.this,getString(R.string.no_network));
        }
    }
    private void requestData(String query) {
        TransactionHistoryActivity.MyTask task = new TransactionHistoryActivity.MyTask();
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(uri);
        p.setParam("itemCd",query);
        task.execute(p);
    }
    private void updateDisplay() {
        adapter = new Transaction_History_Item_Adapter(this,R.layout.item_transaction_history_item,resultList);
        if (resultList != null)
        {
            if (resultList.size() > 0)
            {
                lv.setAdapter(adapter);
            }
            else
            {
                UtilIf.notify_message(TransactionHistoryActivity.this,getString(R.string.no_result));
            }
        }
        else
        {
            UtilIf.notify_message(TransactionHistoryActivity.this,getString(R.string.null_result));
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
                UtilIf.notify_message(TransactionHistoryActivity.this,getString(R.string.no_result));
            }
            else{
                resultList = Item_TransactionJSONParser.parseString(s);
                updateDisplay();
            }
            pb.setVisibility(View.INVISIBLE);
        }
    }
}
