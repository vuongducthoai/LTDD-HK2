package com.example.petrofit2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {
    RecyclerView rcCate;
    EditText searchBar;
    ProductAdapter productAdapter;
    APIService apiService;
    List<Product> productList = new ArrayList<>(); // Khởi tạo danh sách trước

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        setupRecyclerView(); // Gán adapter trước khi gọi API
        GetProduct();
        setupSearch();
    }

    private void GetProduct() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList = response.body();
                    Log.d("SearchAPI", "Found " + productList.size() + " products.");
                    updateRecyclerView(productList);
                } else {
                    Log.e("SearchAPI", "Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Retrofit", "API call failed: " + t.getMessage());
            }
        });
    }

    private void AnhXa() {
        rcCate = findViewById(R.id.rc_product);
        searchBar = findViewById(R.id.search_bar);
    }

    private void setupRecyclerView() {
        rcCate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        productAdapter = new ProductAdapter(this, productList);
        rcCate.setAdapter(productAdapter);
    }

    private void setupSearch() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                if (!query.isEmpty()) {
                    searchProducts(query);
                } else {
                    updateRecyclerView(productList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void searchProducts(String keyword) {
        apiService.searchProducts(keyword).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> searchResults = response.body();
                    Log.d("SearchAPI", "Found " + searchResults.size() + " results for: " + keyword);
                    for (Product product : searchResults) {
                        Log.d("SearchAPI", "Product: " + product.getName());
                    }
                    updateRecyclerView(searchResults);
                } else {
                    Log.e("SearchAPI", "Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("SearchAPI", "API call failed: " + t.getMessage());
            }
        });
    }

    private void updateRecyclerView(List<Product> list) {
        if (list == null || list.isEmpty()) {
            Log.d("SearchAPI", "No products found.");
        } else {
            Log.d("SearchAPI", "Updating RecyclerView with " + list.size() + " products.");
            for (Product product : list) {
                Log.d("SearchAPI", "Product: " + product.getName());
            }
        }
        productAdapter.updateList(list);
    }
}
