package com.example.administrator.toolbarhide;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private RelativeLayout toolbarlayout;
    private int actionBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarHeight = toolbar.getLayoutParams().height;

        toolbarlayout =(RelativeLayout)findViewById(R.id.toolbarlayout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapter(this));
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            private boolean hideState = false;
            private int offset = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                Log.i("temp", "visi->" + firstVisibleItem);

                // 显示的时候 向上移动 大于高度后完全隐藏
                if (!hideState && dy > 0) {
                    offset += dy;
                    if(offset >= actionBarHeight){
                        offset = actionBarHeight;
                    }
                    toolbarlayout.setTranslationY(-offset);

                    if (offset >= actionBarHeight) {
                        hideState = true;
                        offset = 0;
                    }
                }

                // 隐藏的时候 向下移动 移动到全部以后停止移动
                if (hideState && dy < 0) {
                    offset += dy;
                    if(offset < -actionBarHeight){
                        offset = -actionBarHeight;
                    }
                    toolbarlayout.setTranslationY(-actionBarHeight - offset);
                    Log.i("temp", "offset->" + offset + ",toolbarH->" + toolbar.getLayoutParams().height);

                    if (Math.abs(offset) >= actionBarHeight) {
                        hideState = false;
                        offset = 0;
                    }
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
