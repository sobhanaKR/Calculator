package com.example.course.calculator;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by course on 24/12/17.
 */

public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ViewHolder>{

    public ScoreListAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list_recycler_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {




    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, score;
        public ViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name1);
            score = (TextView)view.findViewById(R.id.score_num1);
        }
    }
}
