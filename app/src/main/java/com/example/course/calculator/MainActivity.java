package com.example.course.calculator;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements EnterName.CallbackHandler {

    Button goBtn;
    Button optionA, optionB, optionC, optionD;
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
    int a = 0,b = 0,c = 0,d = 0;
    int clickTimes;
    Database db;
    int rangeOperators =0;
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
        optionA.setTextColor(Color.parseColor("#000000"));
        optionB.setTextColor(Color.parseColor("#000000"));
        optionC.setTextColor(Color.parseColor("#000000"));
        optionD.setTextColor(Color.parseColor("#000000"));
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
        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingBordersColors(optionA,optionB,optionC,optionD);
                image.setVisibility(View.GONE);
                flag = 1;
                goBtn.setEnabled(true);
            }
        });
        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingBordersColors(optionB,optionA,optionC,optionD);
                image.setVisibility(View.GONE);
                flag =1;
                goBtn.setEnabled(true);
            }
        });
        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingBordersColors(optionC,optionA,optionB,optionD);
                image.setVisibility(View.GONE);
                flag =1;
                goBtn.setEnabled(true);
            }
        });
        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingBordersColors(optionD,optionA,optionC,optionB);
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
                            correctAnswerOutcome();

                        } else {
                            wrongAnswerOutcome();
                        }
                    }
                }
                else {
                    Toast.makeText(getWindow().getContext(),"Choose the Operators!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void settingBordersColors(Button optionA, Button optionB, Button optionC, Button optionD) {
        optionA.setBackgroundResource(R.drawable.blue_border);
        optionA.setTextColor(Color.parseColor("#ffffff"));
        optionB.setTextColor(Color.parseColor("#000000"));
        optionC.setTextColor(Color.parseColor("#000000"));
        optionD.setTextColor(Color.parseColor("#000000"));
        optionB.setBackgroundResource(R.drawable.remove_btn_border);
        optionC.setBackgroundResource(R.drawable.remove_btn_border);
        optionD.setBackgroundResource(R.drawable.remove_btn_border);
        String r = optionA.getText().toString();
        String segments[] = r.split(" ");
        resultBtn.setText(segments[1]);
        resultBtn.setBackgroundResource(R.drawable.circle_shape);
    }

    private void wrongAnswerOutcome() {
        image.setVisibility(View.VISIBLE);
        arrow.setVisibility(View.VISIBLE);
        refresh.setVisibility(View.VISIBLE);
        wrongAnswerAnim();
    }

    private void wrongAnswerAnim() {
        Animation animShake = AnimationUtils.loadAnimation(getWindow().getContext(), R.anim.rotate);
        resultBtn.setBackgroundResource(R.drawable.red_circle);
        resultBtn.startAnimation(animShake);
        Animation animShake1 = AnimationUtils.loadAnimation(getWindow().getContext(), R.anim.rotate);
        image.startAnimation(animShake1);
        image.setBackgroundResource(R.drawable.tryagain);
    }

    private void correctAnswerOutcome() {
        resultBtn.setBackgroundResource(R.drawable.green_circle);
        image.setVisibility(View.VISIBLE);
        settingScores();
        long id = db.getId();
        db.insertScore(scorenum,id);
        correctAnswerAnim();
        optionA.setEnabled(false);
        optionB.setEnabled(false);
        optionC.setEnabled(false);
        optionD.setEnabled(false);
        goBtn.setEnabled(false);
    }

    private void correctAnswerAnim() {
        Animation animShake = AnimationUtils.loadAnimation(getWindow().getContext(), R.anim.rotate);
        image.startAnimation(animShake);
        image.setBackgroundResource(R.drawable.flower);
        refresh.setVisibility(View.VISIBLE);
        arrow.setVisibility(View.VISIBLE);
    }

    private void settingScores() {
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
        if(scorenum >= 50) {
            score.setText("SCORE = " + String.valueOf(scorenum)+"  Level = 2");
        }
        else if(scorenum >= 100){
            score.setText("SCORE = " + String.valueOf(scorenum)+"  Level = 3");
        }
        else{
            score.setText("SCORE = " + String.valueOf(scorenum)+"  Level = 1");
        }
    }

    private void choosingOperators() {
        optionA.setEnabled(true);
        optionB.setEnabled(true);
        optionC.setEnabled(true);
        optionD.setEnabled(true);
        switch(rangeOperators) {
            case 0: {
                int addResult = Integer.parseInt(firstNum.getText().toString()) + Integer.parseInt(secondNum.getText().toString());
                operator.setText("+");
                settingCorrectOption(addResult);
                break;
            }
            case 1: {
                int subResult = Integer.parseInt(firstNum.getText().toString()) - Integer.parseInt(secondNum.getText().toString());
                operator.setText("-");
                settingCorrectOption(subResult);
                break;
            }
            case 2: {
                int mulResult = Integer.parseInt(firstNum.getText().toString()) * Integer.parseInt(secondNum.getText().toString());
                operator.setText("*");
                settingCorrectOption(mulResult);
                break;
            }
            case 3: {
                int divResult = Integer.parseInt(firstNum.getText().toString()) / Integer.parseInt(secondNum.getText().toString());
                operator.setText("/");
                settingCorrectOption(divResult);
                break;
            }case 4: {
                int modResult = Integer.parseInt(firstNum.getText().toString()) % Integer.parseInt(secondNum.getText().toString());
                operator.setText("%");
                settingCorrectOption(modResult);
                break;
            }case 5: {
                int expoResult = Integer.parseInt(firstNum.getText().toString()) ^ Integer.parseInt(secondNum.getText().toString());
                operator.setText("^");
                settingCorrectOption(expoResult);
                break;
            }
        }
    }
    private void calculatingResult(){
        switch(range3) {
            case 0: {
                int addResult = Integer.parseInt(firstNum.getText().toString()) + Integer.parseInt(secondNum.getText().toString());
                Globalans = addResult;
                break;
            }
            case 1: {
                int subResult = Integer.parseInt(firstNum.getText().toString()) - Integer.parseInt(secondNum.getText().toString());
                Globalans = subResult;
                break;
            }
            case 2: {
                int mulResult = Integer.parseInt(firstNum.getText().toString()) * Integer.parseInt(secondNum.getText().toString());
                Globalans = mulResult;
                break;
            }
            case 3: {
                int divResult = Integer.parseInt(firstNum.getText().toString()) / Integer.parseInt(secondNum.getText().toString());
                Globalans = divResult;
                break;
            }case 4: {
                int modResult = Integer.parseInt(firstNum.getText().toString()) % Integer.parseInt(secondNum.getText().toString());
                Globalans = modResult;
                break;
            }case 5: {
                int expoResult = Integer.parseInt(firstNum.getText().toString()) % Integer.parseInt(secondNum.getText().toString());
                Globalans = expoResult;
                break;
            }
        }

    }

    private void settingCorrectOption(int Result) {
        Globalans = Result;
        switch(range3) {
            case 0: {
                optionA.setText("a. "+Result);
                break;
            }
            case 1: {
                optionB.setText("b. "+Result);
                break;
            }
            case 2: {
                optionC.setText("c. "+Result);
                break;
            }
            case 3: {
                optionD.setText("d. "+Result);
                break;
            }
        }

    }

    private int  generationNumbers() {
        if(scorenum>50) {
            int end = 100;
            int start = 10;
            range3 = settingRandomValues(end,start,2);
        }else {
            int end = 10;
            int  start = 1;
            range3 = settingRandomValues(end,start, 2);
        }
        optionA.setText("a. " + a);
        optionB.setText("b. " + b);
        optionC.setText("c. " + c);
        optionD.setText("d. " + d);
        return  range3;
    }

    private int settingRandomValues(int end, int start, int i) {
        Random r = new Random();
        int range1 = 0,range2 = 0,range3 = 0;
        range1 = r.nextInt(end - start) + start;
        range2 = r.nextInt(end - start) + start;
        range3 = r.nextInt(3 - 0) + 0;
        rangeOperators = r.nextInt(5-0)+0;
        if (range1 > range2 || range1 == range2) {
            firstNum.setText(String.valueOf(range1));
            secondNum.setText(String.valueOf(range2));
        } else {
            firstNum.setText(String.valueOf(range2));
            secondNum.setText(String.valueOf(range1));
        }
        calculatingResult();
        a = GetRandomNumbers(r,b,c,d,Globalans,i,start,end);
        b = GetRandomNumbers(r,a,c,d,Globalans, i,start,end);
        c = GetRandomNumbers(r,b,a,d,Globalans, i,start,end);
        d = GetRandomNumbers(r,b,c,a,Globalans, i,start,end);
        return  range3;
    }

    public  int GetRandomNumbers(Random random, int b, int c, int d, int globalans, int i, int start, int end)
    {
        int number;
        if(i==1) {
            do {
                number = random.nextInt(end - start) + start;
            } while (number == b || number == c || number == d || number == globalans);
        }else{
            do {
                number = random.nextInt(end - start) + start;
            } while (number == b || number == c || number == d || number == globalans);

        }
        return number;
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
        intent.putExtra("score",scorenum);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        return super.onOptionsItemSelected(item);

    }

    private void refreshGame() {
        clickTimes = 0;
        range3 = generationNumbers();
        refreshBorders();
        choosingOperators();
        setTextColor();
        hidingItems();
        resultBtn.setText("");
        resultBtn.setBackgroundResource(R.drawable.circle_shape);
    }

    private void hidingItems() {
        image.setVisibility(View.GONE);
        score.setVisibility(View.GONE);
        arrow.setVisibility(View.GONE);
        refresh.setVisibility(View.GONE);
    }

    private void setTextColor() {
        optionA.setTextColor(Color.parseColor("#000000"));
        optionB.setTextColor(Color.parseColor("#000000"));
        optionC.setTextColor(Color.parseColor("#000000"));
        optionD.setTextColor(Color.parseColor("#000000"));
    }

    private void refreshBorders() {
        optionA.setBackgroundResource(R.drawable.remove_btn_border);
        optionB.setBackgroundResource(R.drawable.remove_btn_border);
        optionC.setBackgroundResource(R.drawable.remove_btn_border);
        optionD.setBackgroundResource(R.drawable.remove_btn_border);
    }

    private void initializeVariables() {
        goBtn = (Button)findViewById(R.id.go_button);
        optionA = (Button)findViewById(R.id.add_btn);
        optionB = (Button)findViewById(R.id.sub_btn);
        optionC = (Button)findViewById(R.id.multiply_btn);
        optionD = (Button)findViewById(R.id.divide_btn);
        firstNum = (Button)findViewById(R.id.first_button);
        secondNum = (Button)findViewById(R.id.second_button);
        resultBtn = (Button) findViewById(R.id.result_button);
        image = (AppCompatImageView)findViewById(R.id.congrats);
        score = (TextView) findViewById(R.id.score);
        refresh = (TextView)findViewById(R.id.new_game);
        arrow = (ImageView)findViewById(R.id.arrow);
        operator = (Button)findViewById(R.id.operator_btn);
        db = new Database(this);
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
