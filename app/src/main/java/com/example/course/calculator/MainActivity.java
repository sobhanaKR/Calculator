package com.example.course.calculator;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements EnterName.CallbackHandler {

    Button goBtn;
    Button addBtn, subBtn, mulBtn, divBtn;
    Button firstNum, secondNum;
    int Globalans;
    Button resultBtn;
    AppCompatImageView image;
    int flag = 0;
    TextView score;
    int scorenum;
    TextView refresh;
    ImageView arrow;
    String name;
    SharedPreferences sharedPreferences;
    EnterName.CallbackHandler callbackHandler;
    Context context;
    int range3;
    Button operator;
    int a,b,c,d;
    int clickTimes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickTimes = 0;
        callbackHandler = this;
        context = getWindow().getContext();
        enterNameFragment();
        sharedPreferences = getWindow().getContext().getSharedPreferences("calci_app", Context.MODE_PRIVATE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("CalciPlay");
        initializeVariables();
        addBtn.setTextColor(Color.parseColor("#000000"));
        subBtn.setTextColor(Color.parseColor("#000000"));
        mulBtn.setTextColor(Color.parseColor("#000000"));
        divBtn.setTextColor(Color.parseColor("#000000"));
        goBtn.setEnabled(true);
        range3 = generationNumbers();
        choosingOperators();
         arrow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 refreshGame();
                 goBtn.setEnabled(true);
             }
         });
         refresh.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 refreshGame();
                 goBtn.setEnabled(true);
             }
         });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtn.setBackgroundResource(R.drawable.blue_border);
                addBtn.setTextColor(Color.parseColor("#ffffff"));
                subBtn.setTextColor(Color.parseColor("#000000"));
                mulBtn.setTextColor(Color.parseColor("#000000"));
                divBtn.setTextColor(Color.parseColor("#000000"));
                subBtn.setBackgroundResource(R.drawable.remove_btn_border);
                mulBtn.setBackgroundResource(R.drawable.remove_btn_border);
                divBtn.setBackgroundResource(R.drawable.remove_btn_border);
                String r = addBtn.getText().toString();
                String segments[] = r.split(" ");
                resultBtn.setText(segments[1]);
                resultBtn.setBackgroundResource(R.drawable.circle_shape);
                image.setVisibility(View.GONE);
                flag = 1;
                goBtn.setEnabled(true);
            }
        });
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subBtn.setBackgroundResource(R.drawable.blue_border);
                subBtn.setTextColor(Color.parseColor("#ffffff"));
                addBtn.setTextColor(Color.parseColor("#000000"));
                mulBtn.setTextColor(Color.parseColor("#000000"));
                divBtn.setTextColor(Color.parseColor("#000000"));
                addBtn.setBackgroundResource(R.drawable.remove_btn_border);
                mulBtn.setBackgroundResource(R.drawable.remove_btn_border);
                divBtn.setBackgroundResource(R.drawable.remove_btn_border);
                String r = subBtn.getText().toString();
                String segments[] = r.split(" ");
                resultBtn.setText(segments[1]);
                resultBtn.setBackgroundResource(R.drawable.circle_shape);
                image.setVisibility(View.GONE);
                flag =1;
                goBtn.setEnabled(true);
            }
        });
        mulBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mulBtn.setBackgroundResource(R.drawable.blue_border);
                mulBtn.setTextColor(Color.parseColor("#ffffff"));
                subBtn.setTextColor(Color.parseColor("#000000"));
                addBtn.setTextColor(Color.parseColor("#000000"));
                divBtn.setTextColor(Color.parseColor("#000000"));
                subBtn.setBackgroundResource(R.drawable.remove_btn_border);
                addBtn.setBackgroundResource(R.drawable.remove_btn_border);
                divBtn.setBackgroundResource(R.drawable.remove_btn_border);
                String r = mulBtn.getText().toString();
                String segments[] = r.split(" ");
                resultBtn.setText(segments[1]);
                resultBtn.setBackgroundResource(R.drawable.circle_shape);
                image.setVisibility(View.GONE);
                flag =1;
                goBtn.setEnabled(true);
            }
        });
        divBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                divBtn.setBackgroundResource(R.drawable.blue_border);
                divBtn.setTextColor(Color.parseColor("#ffffff"));
                subBtn.setTextColor(Color.parseColor("#000000"));
                mulBtn.setTextColor(Color.parseColor("#000000"));
                addBtn.setTextColor(Color.parseColor("#000000"));
                subBtn.setBackgroundResource(R.drawable.remove_btn_border);
                mulBtn.setBackgroundResource(R.drawable.remove_btn_border);
                addBtn.setBackgroundResource(R.drawable.remove_btn_border);
                String r = divBtn.getText().toString();
                String segments[] = r.split(" ");
                resultBtn.setText(segments[1]);
                resultBtn.setBackgroundResource(R.drawable.circle_shape);
                image.setVisibility(View.GONE);
                flag =1;
                goBtn.setEnabled(true);
            }
        });
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 clickTimes++;
                 view = getWindow().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(getWindow().getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if(flag>0) {
                    if (resultBtn.getText().toString().matches("")) {
                        Toast.makeText(getWindow().getContext(), "Enter the Answer!", Toast.LENGTH_SHORT).show();
                    }
                    if (!resultBtn.getText().toString().matches("")) {
                        int temp = Integer.parseInt(resultBtn.getText().toString());
                        if (temp == Globalans) {
                            resultBtn.setBackgroundResource(R.drawable.green_circle);
                            image.setVisibility(View.VISIBLE);
                            score.setVisibility(View.VISIBLE);
                            scorenum = sharedPreferences.getInt("score", 0);
                            if(clickTimes == 1) {
                                scorenum = scorenum + 4;
                            }
                            else if(clickTimes == 2){
                                scorenum = scorenum + 3;
                            }
                            else if(clickTimes == 3){
                                scorenum = scorenum + 2;
                            }
                            SharedPreferences.Editor editor = context.getSharedPreferences("calci_app",
                                    Context.MODE_PRIVATE).edit();
                            editor.putInt("score", scorenum);
                            editor.apply();
                            Animation animShake = AnimationUtils.loadAnimation(getWindow().getContext(), R.anim.rotate);
                            image.startAnimation(animShake);
                            image.setBackgroundResource(R.drawable.flower);
                            addBtn.setEnabled(false);
                            subBtn.setEnabled(false);
                            mulBtn.setEnabled(false);
                            divBtn.setEnabled(false);
                            refresh.setVisibility(View.VISIBLE);
                            arrow.setVisibility(View.VISIBLE);
                            if(scorenum >= 50) {
                                score.setText("SCORE = " + String.valueOf(scorenum)+"  Level = 2");
                            }
                            else if(scorenum >= 100){
                                score.setText("SCORE = " + String.valueOf(scorenum)+"  Level = 3");
                            }
                            else{
                                score.setText("SCORE = " + String.valueOf(scorenum)+"  Level = 1");
                            }
                            goBtn.setEnabled(false);

                        } else {
                            Animation animShake = AnimationUtils.loadAnimation(getWindow().getContext(), R.anim.rotate);
                            resultBtn.setBackgroundResource(R.drawable.red_circle);
                            resultBtn.startAnimation(animShake);
                            image.setVisibility(View.VISIBLE);
                            Animation animShake1 = AnimationUtils.loadAnimation(getWindow().getContext(), R.anim.rotate);
                            image.startAnimation(animShake1);
                            image.setBackgroundResource(R.drawable.tryagain);
                            arrow.setVisibility(View.VISIBLE);
                            refresh.setVisibility(View.VISIBLE);

                        }
                    }
                }
                else {
                    Toast.makeText(getWindow().getContext(),"Choose the Operators!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void choosingOperators() {
        addBtn.setEnabled(true);
        subBtn.setEnabled(true);
        mulBtn.setEnabled(true);
        divBtn.setEnabled(true);
        switch(range3) {
            case 0: {
                int addResult = Integer.parseInt(firstNum.getText().toString()) + Integer.parseInt(secondNum.getText().toString());
                operator.setText("+");
                newFun(addResult);
                break;
            }
            case 1: {
                int subResult = Integer.parseInt(firstNum.getText().toString()) - Integer.parseInt(secondNum.getText().toString());
                operator.setText("-");
                newFun(subResult);
                break;
            }
            case 2: {
                int mulResult = Integer.parseInt(firstNum.getText().toString()) * Integer.parseInt(secondNum.getText().toString());
                operator.setText("*");
                newFun(mulResult);
                break;
            }
            case 3: {
                int divResult = Integer.parseInt(firstNum.getText().toString()) / Integer.parseInt(secondNum.getText().toString());
                operator.setText("/");
                newFun(divResult);
                break;
            }
        }
    }

    private void newFun(int Result) {
            Globalans = Result;
            switch(range3) {
                case 0: {
                    addBtn.setText("a. "+Result);
                    break;
                }
                case 1: {
                    subBtn.setText("b. "+Result);
                    break;
                }
                case 2: {
                    mulBtn.setText("c. "+Result);
                    break;
                }
                case 3: {
                    divBtn.setText("d. "+Result);
                    break;
                }
            }

    }

    private int  generationNumbers() {
        Random r = new Random();
        int range1 = 0,range2 = 0,range3 = 0;
        if(scorenum<50) {
             range1 = r.nextInt(10 - 1) + 1;
             range2 = r.nextInt(10 - 1) + 1;
             range3 = r.nextInt(3 - 0) + 0;
            a = r.nextInt(10 - 1) + 1;
            b = r.nextInt(10 - 1) + 1;
            c = r.nextInt(10 - 1) + 1;
            d = r.nextInt(10 - 1) + 1;
        }
        else {
            range1 = r.nextInt(100 - 1) + 1;
            range2 = r.nextInt(100 - 1) + 1;
            range3 = r.nextInt(3 - 0) + 0;
            a = r.nextInt(100 - 1) + 1;
            b = r.nextInt(100 - 1) + 1;
            c = r.nextInt(100 - 1) + 1;
            d = r.nextInt(100 - 1) + 1;
        }
        addBtn.setText("a. " + a);
        subBtn.setText("b. " + b);
        mulBtn.setText("c. " + c);
        divBtn.setText("d. " + d);

        if (range1 > range2 || range1 == range2) {
            firstNum.setText(String.valueOf(range1));
            secondNum.setText(String.valueOf(range2));
        } else{
            firstNum.setText(String.valueOf(range2));
            secondNum.setText(String.valueOf(range1));
        }
        return  range3;
    }

    private void enterNameFragment() {
        SharedPreferences.Editor editor = context.getSharedPreferences("calci_app",
                Context.MODE_PRIVATE).edit();
        editor.putInt("score", 0);
        editor.apply();
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EnterName enterName = new EnterName(callbackHandler);
        enterName.show(fragmentTransaction, "dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, DisplayScoreCard.class);
        if(name==null){
            enterNameFragment();
            return super.onOptionsItemSelected(item);
        }
        else{
            intent.putExtra("name",name);
            intent.putExtra("score",scorenum);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }

    }

    private void refreshGame() {
        clickTimes = 0;
        range3 = generationNumbers();
        refreshOptions();
        choosingOperators();
        addBtn.setTextColor(Color.parseColor("#000000"));
        subBtn.setTextColor(Color.parseColor("#000000"));
        mulBtn.setTextColor(Color.parseColor("#000000"));
        divBtn.setTextColor(Color.parseColor("#000000"));
        image.setVisibility(View.GONE);
        score.setVisibility(View.GONE);
        arrow.setVisibility(View.GONE);
        refresh.setVisibility(View.GONE);
        resultBtn.setText("");
        resultBtn.setBackgroundResource(R.drawable.circle_shape);
    }

    private void refreshOptions() {
        addBtn.setBackgroundResource(R.drawable.remove_btn_border);
        subBtn.setBackgroundResource(R.drawable.remove_btn_border);
        mulBtn.setBackgroundResource(R.drawable.remove_btn_border);
        divBtn.setBackgroundResource(R.drawable.remove_btn_border);
    }

    private void initializeVariables() {
        goBtn = (Button)findViewById(R.id.go_button);
        addBtn = (Button)findViewById(R.id.add_btn);
        subBtn = (Button)findViewById(R.id.sub_btn);
        mulBtn = (Button)findViewById(R.id.multiply_btn);
        divBtn = (Button)findViewById(R.id.divide_btn);
        firstNum = (Button)findViewById(R.id.first_button);
        secondNum = (Button)findViewById(R.id.second_button);
        resultBtn = (Button) findViewById(R.id.result_button);
        image = (AppCompatImageView)findViewById(R.id.congrats);
        score = (TextView) findViewById(R.id.score);
        refresh = (TextView)findViewById(R.id.new_game);
        arrow = (ImageView)findViewById(R.id.arrow);
        operator = (Button)findViewById(R.id.operator_btn);
    }

    @Override
    public void saveName(String name) {
       this.name = name;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
