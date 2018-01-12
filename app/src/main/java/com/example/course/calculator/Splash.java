package com.example.course.calculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by course on 12/1/18.
 */

public class Splash extends AppCompatActivity {
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        return super.onCreateView(name, context, attrs);
    }
}
