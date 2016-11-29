package com.posco.erp.wipapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.posco.erp.wipapp.managers.HttpManager;
import com.posco.erp.wipapp.models.ItemJSONParser;
import com.posco.erp.wipapp.models.itemDTO;
import com.posco.erp.wipapp.network.RequestPackage;
import com.posco.erp.wipapp.utils.UtilIf;
import com.posco.erp.wipapp.views.adapters.OnhandAdapter;

import java.util.ArrayList;
import java.util.List;

public class OnHandActivity extends AppCompatActivity {
    List<itemDTO> resultList;
    String uri = "http://113.164.120.62:8070/CHD/screen1JSONServlet";
    ProgressBar pb;
    TextView tvSearch;
    OnhandAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_hand);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvSearch = (TextView) findViewById(R.id.searchEditText);
        ImageButton submit = (ImageButton) findViewById(R.id.btnSearch);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        resultList = new ArrayList<>();

        //Main action
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            doSearch();
            }
        });
        tvSearch.setOnKeyListener(new View.OnKeyListener()
        {
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
            String itemCd = tvSearch.getText().toString().trim().toUpperCase();
            if (!itemCd.equalsIgnoreCase("") && !itemCd.isEmpty())
            {
                requestData(itemCd);
            }
        }
        else{
            UtilIf.notify_message(OnHandActivity.this,getString(R.string.no_network));
        }
    }
    private void requestData(String itemCd) {
        MyTask task = new MyTask();
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(uri);
        p.setParam("itemCd",itemCd);
        task.execute(p);
    }
    private void updateDisplay() {

        adapter = new OnhandAdapter(this,R.layout.item_onhand,resultList);
        if (resultList != null)
        {
            if (resultList.size() > 0)
            {
                ListView lv = (ListView) findViewById(R.id.listView);
                lv.setAdapter(adapter);
            }
            else
            {
                UtilIf.notify_message(OnHandActivity.this,getString(R.string.no_result));
            }
        }
        else
        {
            UtilIf.notify_message(OnHandActivity.this,getString(R.string.null_result));
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
                UtilIf.notify_message(OnHandActivity.this,getString(R.string.no_result));
            }
            else{
                resultList = ItemJSONParser.parseString(s);
                updateDisplay();
            }
            pb.setVisibility(View.INVISIBLE);
        }
    }

}
