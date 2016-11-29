package com.posco.erp.wipapp;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.posco.erp.wipapp.managers.onhandProvider;
import com.posco.erp.wipapp.utils.DBOpenHelper;
import com.posco.erp.wipapp.views.adapters.OnhandCursorAdapter;

public class Screen1Activity extends AppCompatActivity implements OnQueryTextListener, LoaderManager.LoaderCallbacks<Cursor> {
    private CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Screen1Activity","onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_screen1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cursorAdapter = new OnhandCursorAdapter(this, null, 0);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get all data from onhand table
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("Screen1Activity","onNewIntent");
        super.onNewIntent(intent);
        setIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this,"Searching item: " + query, Toast.LENGTH_SHORT).show();

        }
    }

    private void doSearch(String query) {
        Log.d("Screen1Activity","doSearch");
        Log.d("Screen1Activity",query);
        String selection = DBOpenHelper.ONHAND_ITEM_CD + " like '" + query + "%'";
        Cursor cursor = getContentResolver().query(onhandProvider.CONTENT_URI,DBOpenHelper.ALL_COLUMNS,selection,null,null);
        cursorAdapter.swapCursor(cursor);

        //getLoaderManager().restartLoader(0,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the options menu from XML
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);

        //Get the SearchView and set the searchable configuration
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Input Items...");
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this,Screen1Activity.class)));
        searchView.setIconifiedByDefault(false);
        MenuItemCompat.setOnActionExpandListener(searchItem,new MenuItemCompat.OnActionExpandListener(){

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.d("Screen1Activity","onMenuItemActionExpand");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Log.d("Screen1Activity","onMenuItemActionCollapse");
                doSearch("");
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Screen1Activity","onQueryTextSubmit");
        doSearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("Screen1Activity","onCreateLoader");
        return new CursorLoader(this,onhandProvider.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("Screen1Activity","onLoadFinished");
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d("Screen1Activity","onLoaderReset");
        cursorAdapter.swapCursor(null);
    }

}
