package com.example.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class OTPVerification extends AppCompatActivity {
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() > 0){
                if(selectecPosition == 0){
                    selectecPosition = 1;
                    showKeyboard(otpEt2);
                } else if(selectecPosition == 1){
                    selectecPosition = 2;
                    showKeyboard(otpEt3);
                } else if (selectecPosition == 2){
                    selectecPosition = 3;
                    showKeyboard(otpEt4);
                } else if(selectecPosition == 3){
                    selectecPosition = 4;
                    showKeyboard(otpEt5);
                }
            }
        }
    };

    private EditText otpEt1, otpEt2,otpEt3, otpEt4, otpEt5;
    private TextView resendBtn;

    //true after every 60 seconds
    private boolean resendEnaled = false;

    //resend time in seconds;
    private int resendTime = 60;
    private int selectecPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverification);

        otpEt1 = findViewById(R.id.otpET1);
        otpEt2 = findViewById(R.id.otpET2);
        otpEt3 = findViewById(R.id.otpET3);
        otpEt4 = findViewById(R.id.otpET4);
        otpEt5 = findViewById(R.id.otpET5);

        resendBtn = findViewById(R.id.resendBtn);
        final Button verifyBtn = findViewById(R.id.verifyBtn);

        final TextView otpEmail = findViewById(R.id.otpEmail);
        final TextView otpMobile = findViewById(R.id.otpMobile);

        //getting email and mobile Register activity through intent
        final String getEmail = getIntent().getStringExtra("email");
        final String getMobile = getIntent().getStringExtra("mobile");

        //setting email and mobile to TextView
        otpEmail.setText(getEmail);
        otpMobile.setText(getMobile);

        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);
        otpEt5.addTextChangedListener(textWatcher);

        // by default open key keyboard at otpEt1
        showKeyboard(otpEt1);

        //start resend count doww timer
        startCountDownTimer();

        resendBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(resendEnaled){
                    //handle your resend code here

                    //start new resend count down timer
                    startCountDownTimer();
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String generateOtp = otpEt1.getText().toString()
                            + otpEt2.getText().toString()
                            + otpEt3.getText().toString()
                            + otpEt4.getText().toString()
                            + otpEt5.getText().toString();

                if(generateOtp.length() == 5){
                    //handle your otp verification here

                }

            }
        });
    }

    private void showKeyboard(EditText otpET){
        otpET.requestFocus();
        InputMethodManager inputMathodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMathodManager.showSoftInput(otpET, InputMethodManager.SHOW_IMPLICIT);
    }


    private void startCountDownTimer(){
        resendEnaled = false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));
        new CountDownTimer(resendTime * 1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Resend Code (" + (millisUntilFinished / 1000) + ")");
            }

            @Override
            public void onFinish() {
                resendEnaled = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor((R.color.primary)));
            }
        }.start();

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_DEL){
            if(selectecPosition == 4){
                selectecPosition = 3;
                showKeyboard(otpEt4);
            } else if (selectecPosition == 3){
                selectecPosition = 2;
                showKeyboard(otpEt3);
            } else if(selectecPosition == 2){
                selectecPosition = 1;
                showKeyboard(otpEt2);
            } else if(selectecPosition == 1){
                selectecPosition = 0;
                showKeyboard(otpEt1);
            }
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }
}