package com.example.course.calculator;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by course on 24/12/17.
 */

public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ViewHolder>{

    ArrayList<String> nameArray;
    ArrayList<Integer> scoreArray;
    public ScoreListAdapter(ArrayList<String> nameArray, ArrayList<Integer> scoreArray) {
        this.nameArray = nameArray;
        this.scoreArray = scoreArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String namestr = nameArray.get(position);
        int scorestr = scoreArray.get(position);
        if(namestr!=null && scorestr!=0 ){
            holder.name.setText(namestr);
            holder.score.setText(String.valueOf(scorestr));
        }

            Log.i("check", "name == " + namestr);
            Log.i("check", "score ==" + scorestr);
    }

    @Override
    public int getItemCount() {
        return nameArray.size();
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
