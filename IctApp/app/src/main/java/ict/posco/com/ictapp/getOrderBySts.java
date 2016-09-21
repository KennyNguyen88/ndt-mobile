package ict.posco.com.ictapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ict.posco.com.dto.onhandDTO;
import ict.posco.com.dto.orderDTO;
import ict.posco.com.ict.posco.com.adapter.onhandAdapter;
import ict.posco.com.ict.posco.com.adapter.orderAdapter;
import ict.posco.com.ict.posco.com.parser.onhandJSONParser;
import ict.posco.com.ict.posco.com.parser.orderJSONParser;

public class getOrderBySts extends AppCompatActivity {
    List<orderDTO> resultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_order_by_sts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        resultList = new ArrayList<>();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_do_task){
            if(isOnline())
            {
                requestData();
            }
            else{
                Toast.makeText(this,"Network isn't available",Toast.LENGTH_LONG).show();
            }

        }
        return false;
    }

    protected boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void requestData() {
        MyTask task = new MyTask();
        String uri = "http://172.27.26.74:9090/IctVnRService/getOrderBySts";
        RequestPackage p = new RequestPackage();
//        p.setMethod("GET");
        p.setMethod("POST");
        p.setUri(uri);
//        p.setParam("ITEM_CD","AAU10P02");
        task.execute(p);
    }

    private void updateDisplay() {
        orderAdapter adapter = new orderAdapter(this,R.layout.item_onhand,resultList);
        if (resultList.size() > 0)
        {
            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(adapter);
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

            resultList = orderJSONParser.parseString(s);

            updateDisplay();
//            pb.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {
//            updateDisplay(values[0]);
        }
    }

}
