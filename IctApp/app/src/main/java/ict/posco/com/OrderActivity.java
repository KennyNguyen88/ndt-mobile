package ict.posco.com;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ict.posco.com.models.orderDTO;
import ict.posco.com.views.adapters.orderAdapter;
import ict.posco.com.models.orderJSONParser;
import ict.posco.com.managers.HttpManager;
import ict.posco.com.ictapp.R;
import ict.posco.com.network.RequestPackage;

public class OrderActivity extends AppCompatActivity {
    List<orderDTO> resultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
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
                String uri = "http://172.27.26.74:9090/IctVnRService/getOrderBySts";
                requestData(uri);
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

    private void requestData(String uri) {
        MyTask task = new MyTask();
        RequestPackage p = new RequestPackage();
//        p.setMethod("GET");
        p.setMethod("POST");
        p.setUri(uri);
        p.setParam("ordStsCd","A");
        task.execute(p);
    }

    private void updateDisplay() {
        orderAdapter adapter = new orderAdapter(this,R.layout.item_order,resultList);
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
            if (s.isEmpty())
            {
                Toast.makeText(OrderActivity.this,"Empty Results Response",Toast.LENGTH_LONG).show();
            }
            else{
                resultList = orderJSONParser.parseString(s);
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
