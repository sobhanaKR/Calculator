package com.example.course.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by course on 22/12/17.
 */

public class DisplayScoreCard extends AppCompatActivity {
    int scoreList;
    String nameStr;
    RecyclerView recyclerView;
    ScoreListAdapter scoreListAdapter;
    Database db;
    ArrayList<Integer>scoreArray = new ArrayList<>();
    ArrayList<String>nameArray = new ArrayList<>();
    ArrayList union  = new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.display_score_card);
        initializeVariables();
        db = new Database(this);
        Intent intent = getIntent();
        scoreList = intent.getIntExtra("score",0);
        nameStr = intent.getStringExtra("name");
        db.insertScore(nameStr,scoreList);
        nameArray = db.getname();
        scoreArray = db.getscore();
        union.add(nameArray);
        union.add(scoreArray);
        Log.i("check","name"+nameArray);
        Log.i("check","score"+scoreArray);
        Log.i("check","union"+union);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Score Card");
        scoreListAdapter = new ScoreListAdapter();
        recyclerView.setAdapter(scoreListAdapter);
        super.onCreate(savedInstanceState);
    }

    private void initializeVariables() {
      recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

}
