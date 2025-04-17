package com.example.ui.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.ui.R;
import com.example.ui.Toast.CustomToast;
import com.example.ui.api.retrofit.APIRetrofit;
import com.example.ui.api.retrofit.UserAPI;
import com.example.ui.authentication.AuthManager;
import com.example.ui.dto.TokenDTO;
import com.example.ui.dto.UserLoginDTO;
import com.example.ui.utils.SharedPrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private boolean passwordShowing = false;
    private EditText usernameET, passwordET;
    private AppCompatButton loginBtn;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final TextView signUpBtn = findViewById(R.id.signUpBtn);
        final TextView fgPassWordBtn = findViewById(R.id.forgotPassword);
        loginBtn = findViewById(R.id.signInBtn);
        LoadingDialog loadingDialog = new LoadingDialog(Login.this);

        // Check if AuthManager is working correctly
        AuthManager authManager = new AuthManager(this);
        authManager.checkLoginStatus();

        passwordIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Checking if password is showing or not
                if(passwordShowing){
                    passwordShowing = false;
                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_show);
                } else {
                    passwordShowing = true;
                    passwordET.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_hide);
                }
                passwordET.setSelection(passwordET.length());
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String phoneNumber = usernameET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                if(phoneNumber.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this, "Please enter both fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserLoginDTO userLoginDTO = new UserLoginDTO(phoneNumber, password);
                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                    }
                }, 2000);
                callLoginApi(userLoginDTO);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        fgPassWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    private void callLoginApi(UserLoginDTO userLoginDTO){
        UserAPI userAPI = APIRetrofit.getRetrofitInstance().create(UserAPI.class);
        Call<TokenDTO> call = userAPI.login(userLoginDTO);

        call.enqueue(new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {

                if(response.isSuccessful()){
                    TokenDTO token = response.body();

                    //Save the token in SharedPreferences
                    SharedPrefManager sharedPrefManager = new SharedPrefManager(Login.this);
                    sharedPrefManager.saveToken(token.getToken());

                    //Navigative to MainActivity
                    Intent intent = new Intent(Login.this, Product_Detail.class);
                    CustomToast.makeText(Login.this, "Login successfully!", CustomToast.LONG, CustomToast.SUCCESS, true, Gravity.TOP,350, 100, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Handle the click event here
                        }
                    }).show();
                    startActivity(intent);
                    finish(); // Finish Login to prevent going back
                } else {
                    Toast.makeText(Login.this, "Invalid login credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenDTO> call, Throwable t) {
                Toast.makeText(Login.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
