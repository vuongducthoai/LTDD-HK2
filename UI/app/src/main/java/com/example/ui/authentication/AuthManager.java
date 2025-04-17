package com.example.ui.authentication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.ui.Activity.Login;
import com.example.ui.Activity.Product_Detail;
import com.example.ui.api.retrofit.APIRetrofit;
import com.example.ui.api.retrofit.UserAPI;
import com.example.ui.dto.UserLoginDTO;
import com.example.ui.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthManager {
    private SharedPrefManager sharedPrefManager;
    private Context context;

    public AuthManager( Context context) {
        this.context = context;
        this.sharedPrefManager = new SharedPrefManager(context);
    }

    public void checkLoginStatus(){
        String token = sharedPrefManager.getToken();
        if(token != null){
            //Send token for backend to check valid
//            promptUserForLogin();
            sendTokenBackendForVerification(token);
        } else {
            //If no token, request user login
            promptUserForLogin();
        }
    }

    private void promptUserForLogin() {
        // Avoid repeatedly starting LoginActivity
        if (!(context instanceof Login)) {
            Intent intent = new Intent(context, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }
    private void sendTokenBackendForVerification(String token) {
        UserAPI userAPI =  APIRetrofit.getApiService();

        //Send request to backend with token
        Call<UserLoginDTO> call = userAPI.verifyToken("Bearer " + token);
        call.enqueue(new Callback<UserLoginDTO>() {
            @Override
            public void onResponse(Call<UserLoginDTO> call, Response<UserLoginDTO> response) {
                if(response.isSuccessful()){
                    //Token valid, log in successfully
                    UserLoginDTO user = response.body();
                    onLoginSuccess(user);
                } else {
                    //Token invalid, request relogin
                    onLoginFailed();
                }
            }

            @Override
            public void onFailure(Call<UserLoginDTO> call, Throwable t) {
                // Log the failure
                Log.d("TokenVerification", "Token verification failed with error: " + t.getMessage());
                onLoginFailed();
            }
        });
    }

    private void onLoginSuccess(UserLoginDTO user){
        //Tiến hành lưu thông tin người dùng và chuyển đến màn hình chính của ứng dụng
        Intent intent = new Intent(context, Product_Detail.class);
        intent.putExtra("user_id", user.getPhone_number());
        context.startActivity(intent);
    }

    private void onLoginFailed() {
        // Nếu không thể xác thực token, yêu cầu người dùng đăng nhập lại
        if (!(context instanceof Login)) {
            Intent intent = new Intent(context, Login.class);
            // Clear task stack và khởi tạo Login Activity như một task mới
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }
}
