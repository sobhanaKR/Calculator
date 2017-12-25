package com.example.course.calculator;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    EditText resultBtn;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callbackHandler = this;
        context = getWindow().getContext();
        enterNameFragment();
        sharedPreferences = getWindow().getContext().getSharedPreferences("calci_app", Context.MODE_PRIVATE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("CalciPlay");
        initializeVariables();
        range3 = generationNumbers();
        choosingOperators();
         arrow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 refreshGame();
             }
         });
         refresh.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 refreshGame();
             }
         });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtn.setBackgroundResource(R.drawable.blue_border);
                subBtn.setBackgroundResource(R.drawable.remove_btn_border);
                mulBtn.setBackgroundResource(R.drawable.remove_btn_border);
                divBtn.setBackgroundResource(R.drawable.remove_btn_border);
                int addResult = Integer.parseInt(firstNum.getText().toString())+Integer.parseInt(secondNum.getText().toString());
                displayingOptions(firstNum.getText().toString(),secondNum.getText().toString(),addResult);
                flag = 1;
            }
        });
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subBtn.setBackgroundResource(R.drawable.blue_border);
                addBtn.setBackgroundResource(R.drawable.remove_btn_border);
                mulBtn.setBackgroundResource(R.drawable.remove_btn_border);
                divBtn.setBackgroundResource(R.drawable.remove_btn_border);
                int subResult =  Integer.parseInt(firstNum.getText().toString())-Integer.parseInt(secondNum.getText().toString());
                displayingOptions(firstNum.getText().toString(), secondNum.getText().toString(), subResult);
                flag =1;
            }
        });
        mulBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mulBtn.setBackgroundResource(R.drawable.blue_border);
                subBtn.setBackgroundResource(R.drawable.remove_btn_border);
                addBtn.setBackgroundResource(R.drawable.remove_btn_border);
                divBtn.setBackgroundResource(R.drawable.remove_btn_border);
                int mulResult = Integer.parseInt(firstNum.getText().toString())*Integer.parseInt(secondNum.getText().toString());
                displayingOptions(firstNum.getText().toString(), secondNum.getText().toString(), mulResult);
                flag =1;
            }
        });
        divBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                divBtn.setBackgroundResource(R.drawable.blue_border);
                subBtn.setBackgroundResource(R.drawable.remove_btn_border);
                mulBtn.setBackgroundResource(R.drawable.remove_btn_border);
                addBtn.setBackgroundResource(R.drawable.remove_btn_border);
                int divResult =  Integer.parseInt(firstNum.getText().toString())/Integer.parseInt(secondNum.getText().toString());
                displayingOptions(firstNum.getText().toString(), secondNum.getText().toString(), divResult);
                flag =1;
            }
        });
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            scorenum = scorenum + 2;
                            SharedPreferences.Editor editor = context.getSharedPreferences("calci_app",
                                    Context.MODE_PRIVATE).edit();
                            editor.putInt("score", scorenum);
                            editor.apply();
                            Animation animShake = AnimationUtils.loadAnimation(getWindow().getContext(), R.anim.rotate);
                            image.startAnimation(animShake);
                            image.setBackgroundResource(R.drawable.flower);
                            refresh.setVisibility(View.VISIBLE);
                            arrow.setVisibility(View.VISIBLE);
                            score.setText("SCORE = "+String.valueOf(scorenum));

                        } else {
                            Animation animShake = AnimationUtils.loadAnimation(getWindow().getContext(), R.anim.rotate);
                            resultBtn.setBackgroundResource(R.drawable.red_circle);
                            resultBtn.startAnimation(animShake);
                            image.setVisibility(View.VISIBLE);
                            Animation animShake1 = AnimationUtils.loadAnimation(getWindow().getContext(), R.anim.rotate);
                            image.startAnimation(animShake1);
                            image.setBackgroundResource(R.drawable.tryagain);
                            arrow.setVisibility(View.VISIBLE);
                            score.setVisibility(View.GONE);

                        }
                    }
                }
                else {
                    Toast.makeText(getWindow().getContext(),"Choose the Operators!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        resultBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(resultBtn.getText().toString().matches("")){
                    image.setVisibility(View.GONE);
                    resultBtn.setBackgroundResource(R.drawable.circle_shape);
                }

            }
        });
    }

    private void choosingOperators() {
        switch(range3) {
            case 0: {
                int addResult = Integer.parseInt(firstNum.getText().toString()) + Integer.parseInt(secondNum.getText().toString());
                clickFunction(addBtn, subBtn, mulBtn, divBtn, addResult);
                break;
            }
            case 1: {
                int subResult = Integer.parseInt(firstNum.getText().toString()) - Integer.parseInt(secondNum.getText().toString());
                clickFunction(subBtn, addBtn, mulBtn, divBtn, subResult);
                break;
            }
            case 2: {
                int mulResult = Integer.parseInt(firstNum.getText().toString()) * Integer.parseInt(secondNum.getText().toString());
                clickFunction(mulBtn, addBtn, subBtn, divBtn, mulResult);
                break;
            }
            case 3: {
                int divResult = Integer.parseInt(firstNum.getText().toString()) / Integer.parseInt(secondNum.getText().toString());
                clickFunction(divBtn, addBtn, mulBtn, subBtn, divResult);
                break;
            }
        }
    }

    private int  generationNumbers() {
        Random r = new Random();
        int range1 = r.nextInt(10 - 1) + 1;
        int range2 = r.nextInt(10 - 1) + 1;
        int range3 = r.nextInt(3-0) + 0;
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

    private void clickFunction(Button addBtn, Button subBtn, Button mulBtn, Button divBtn, int addResult) {
        addBtn.setBackgroundResource(R.drawable.blue_border);
        subBtn.setBackgroundResource(R.drawable.remove_btn_border);
        mulBtn.setBackgroundResource(R.drawable.remove_btn_border);
        divBtn.setBackgroundResource(R.drawable.remove_btn_border);
        displayingOptions(firstNum.getText().toString(),secondNum.getText().toString(),addResult);
        flag = 1;

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
        intent.putExtra("name",name);
        intent.putExtra("score",scorenum);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void refreshGame() {
        range3 = generationNumbers();
        choosingOperators();
        image.setVisibility(View.GONE);
        score.setVisibility(View.GONE);
        arrow.setVisibility(View.GONE);
        refresh.setVisibility(View.GONE);
        resultBtn.setText("");
        resultBtn.setBackgroundResource(R.drawable.circle_shape);
    }

    private void displayingOptions(String firstNum, String secondNum, int Result) {
        Globalans = Result;

    }

    private void initializeVariables() {
        goBtn = (Button)findViewById(R.id.go_button);
        addBtn = (Button)findViewById(R.id.add_btn);
        subBtn = (Button)findViewById(R.id.sub_btn);
        mulBtn = (Button)findViewById(R.id.multiply_btn);
        divBtn = (Button)findViewById(R.id.divide_btn);
        firstNum = (Button)findViewById(R.id.first_button);
        secondNum = (Button)findViewById(R.id.second_button);
        resultBtn = (EditText) findViewById(R.id.result_button);
        image = (AppCompatImageView)findViewById(R.id.congrats);
        score = (TextView) findViewById(R.id.score);
        refresh = (TextView)findViewById(R.id.new_game);
        arrow = (ImageView)findViewById(R.id.arrow);
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
