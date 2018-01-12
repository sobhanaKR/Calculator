package com.example.course.calculator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import javax.security.auth.callback.CallbackHandler;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by course on 24/12/17.
 */

public class EnterName extends DialogFragment {
    Activity activity;
    RelativeLayout mainLayout;
    LinearLayout cancelLyt;
    Button okBtn;
    EditText name;
    CallbackHandler callbackHandler;
    Database db;
    long id;

    @SuppressLint("ValidFragment")
    public EnterName(CallbackHandler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    public  EnterName(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_FRAME, theme = android.R.style.Theme_Holo_Light_Dialog_NoActionBar;
        activity = getActivity();
        setStyle(style, theme);
    }
    public interface CallbackHandler {
        void saveName(String name);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainLayout = (RelativeLayout) inflater.inflate(R.layout.enter_name_layout, container, false);
        intializeVariables();
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackHandler.saveName(name.getText().toString());
               Boolean checknameExists = db.nameExists(name.getText().toString());
               if(checknameExists){
                   Animation animShake = AnimationUtils.loadAnimation(activity.getWindow().getContext(), R.anim.rotate);
                   name.startAnimation(animShake);
                   name.setError("Name Already Exists");
                   name.setTextColor(Color.parseColor("#ff0000"));
               }else {
                   id = db.insertName(name.getText().toString());
                   SharedPreferences.Editor editor = activity.getSharedPreferences("calciplay", MODE_PRIVATE).edit();
                   editor.putLong("id", id);
                   editor.apply();
                   dismiss();
               }
            }
        });
        cancelLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return mainLayout;

    }
    private void intializeVariables(){
        cancelLyt = (LinearLayout) mainLayout.findViewById(R.id.cancel_layout);
        okBtn = (Button)mainLayout.findViewById(R.id.ok_btn);
        name = (EditText)mainLayout.findViewById(R.id.enter_name);
        db = new Database(activity);
    }
}
