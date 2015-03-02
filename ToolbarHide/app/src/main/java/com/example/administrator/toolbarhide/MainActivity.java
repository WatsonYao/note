package com.example.administrator.toolbarhide;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;


public class MainActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapter(this));
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            private boolean hideState = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                Log.i("temp", "visi->" + firstVisibleItem);

                if ( !hideState && firstVisibleItem > 0 && dy>0) {
                    Log.i("temp", "toolbar---------> hide" );
                    toolbar.animate().translationY(-toolbar.getLayoutParams().height).setInterpolator(new AccelerateInterpolator(2)).start();
                    hideState = true;
                }

                Log.i("temp", "dy->" + dy);
                if (hideState && dy < -10) {
                    Log.i("temp", "toolbar---------> show" );
                    toolbar.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2)).start();
                    hideState = false;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
