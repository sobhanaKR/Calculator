package com.example.course.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by course on 22/12/17.
 */

public class DisplayScoreCard extends AppCompatActivity {
    int scoreList;
    String nameStr;
    RecyclerView recyclerView;
    ScoreListAdapter scoreListAdapter;
    ArrayList<Integer> scoreArray = new ArrayList<Integer>();
    ArrayList<String> nameArray = new ArrayList<String>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.display_score_card);
        initializeVariables();
        Intent intent = getIntent();
        Context context = getWindow().getContext();
        sharedPreferences = context.getSharedPreferences("calciprefs",
                Context.MODE_PRIVATE);
        scoreList = intent.getIntExtra("score",0);
        nameStr = intent.getStringExtra("name");
        loadSharedPrefs();
        if (context != null) {
            editor = context.getSharedPreferences("calciprefs",
                    Context.MODE_PRIVATE).edit();
            if(nameArray.size() > 0) {
                if (nameArray.get(nameArray.size() - 1) == nameStr && nameArray.size() == 1) {
                    editor.remove("score" + (nameArray.size() - 1));
                    nameArray.remove(nameArray.size() - 1);
                    scoreArray.remove(scoreArray.size() - 1);
                    editor.putInt("score"+(nameArray.size()-1),scoreList);
                    editor.putString("name"+(scoreArray.size()-1),nameStr);
                    nameArray.add(nameStr);
                    scoreArray.add(scoreList);
                }
                else{
                    editor.putInt("name" + i, scoreList);
                    editor.putString("score" + i, nameStr);
                    nameArray.add(nameStr);
                    scoreArray.add(scoreList);
                }
            }
            else {

                editor.putInt("name" + i, scoreList);
                editor.putString("score" + i, nameStr);
                nameArray.add(nameStr);
                scoreArray.add(scoreList);
            }
            editor.putInt("arraysize", nameArray.size());
            for (i = 0; i < nameArray.size(); i++) {
                editor.putString("name" + i, nameArray.get(i));
                editor.putInt("score" + i, scoreArray.get(i));
            }
            editor.commit();
            editor.apply();
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Score Card");
        scoreListAdapter = new ScoreListAdapter(nameArray,scoreArray);
        recyclerView.setAdapter(scoreListAdapter);
        super.onCreate(savedInstanceState);
    }

    private void loadSharedPrefs() {
        int size = sharedPreferences.getInt("arraysize", 0);
        for(int i=0;i<size;i++)
        {
            String tempstr = sharedPreferences.getString("name"+i, null);
            int tempint = sharedPreferences.getInt("score"+i, 0);
            if(nameStr!=null) {
                if (tempstr != null && !tempstr.contains(nameStr)) {
                    nameArray.add(tempstr);
                    scoreArray.add(tempint);
                }
            }
            else{
                scoreArray.add(tempint);
            }
        }
    }

    private void initializeVariables() {
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayout);
    }

}
