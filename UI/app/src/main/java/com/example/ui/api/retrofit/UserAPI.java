package com.example.ui.api.retrofit;

import com.example.ui.dto.TokenDTO;
import com.example.ui.dto.UserDTO;
import com.example.ui.dto.UserLoginDTO;
import com.example.ui.dto.UserUpdatePw;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserAPI {
    @GET("verify-token")  // Đảm bảo endpoint này khớp với API của bạn
    Call<UserLoginDTO> verifyToken(@Header("Authorization") String token);

    @GET("verify-email")
    Call<String> verifyEmail(@Query("email") String email);

    @GET("verify-phone")
    Call<String> verifyPhone(@Query("phone") String phone);

    @POST("login")
    Call<TokenDTO> login(@Body UserLoginDTO userLoginDTO);

    @POST("register")
    Call<UserDTO> register(@Body UserDTO userDTO);

    @POST("send-otp")
    Call<String> sendOtp(@Query("to") String to);

    @PUT("update-password")
    Call<String> updatePassword(@Body UserUpdatePw userUpdatePw);
}
