package com.example.administrator.toolbarhide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 3/2.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TextViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private String[] mTitles;


    public RecyclerViewAdapter(Context context) {
        mTitles = context.getResources().getStringArray(R.array.titles);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new TextViewHolder(mLayoutInflater.inflate(R.layout.item_text, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(TextViewHolder textViewHolder, int i) {
        textViewHolder.textView.setText(mTitles[i]);
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.length;
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;


        public TextViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("temp","onclick - ");
                }
            });
        }
    }
}
