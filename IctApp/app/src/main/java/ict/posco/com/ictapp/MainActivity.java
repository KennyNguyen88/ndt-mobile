package ict.posco.com.ictapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ict.posco.com.dto.onhandDTO;
import ict.posco.com.ict.posco.com.adapter.onhandAdapter;
import ict.posco.com.ict.posco.com.parser.onhandJSONParser;

public class MainActivity extends AppCompatActivity {
    ProgressBar pb;
    List<onhandDTO> resultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultList = new ArrayList<>();

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
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

    private void requestData() {
        MyTask task = new MyTask();
//        String uri = "http://172.27.26.55:8080/services/HelloWorld?wsdl";
//        String uri = "http://services.hanselandpetal.com/feeds/flowers.xml";
//        String uri = "http://services.hanselandpetal.com/feeds/flowers.json";
//        String uri = "http://172.27.26.55:8080/onhand/get";
        String uri = "http://172.27.26.74:9090/SpringMVCRESTFulExample/getOrder?name=JavaHonks";
        RequestPackage p = new RequestPackage();
//        p.setMethod("GET");
        p.setMethod("POST");
        p.setUri(uri);
        p.setParam("ITEM_CD","AAU10P02");
        task.execute(p);
    }

    private void updateDisplay() {
        onhandAdapter adapter = new onhandAdapter(this,R.layout.item_onhand,resultList);
        if (resultList.size() > 0)
        {
            ListView lv = (ListView) findViewById(R.id.list);
            lv.setAdapter(adapter);
        }
    }

    protected boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private class MyTask extends AsyncTask<RequestPackage, String, String >{

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
            resultList = onhandJSONParser.parseString(s);

            updateDisplay();
            pb.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {
//            updateDisplay(values[0]);
        }
    }
}
