package com.example.course.calculator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import javax.security.auth.callback.CallbackHandler;

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
                dismiss();
            }
        });
        cancelLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return mainLayout;

    }
    private void intializeVariables(){
        cancelLyt = (LinearLayout) mainLayout.findViewById(R.id.cancel_layout);
        okBtn = (Button)mainLayout.findViewById(R.id.ok_btn);
        name = (EditText)mainLayout.findViewById(R.id.enter_name);
    }
}
