package com.example.petrofit2;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("allProduct")
    Call<List<Product>> getAllProducts();

    @GET("search")
    Call<List<Product>> searchProducts(@Query("keyword") String keyword);
}
