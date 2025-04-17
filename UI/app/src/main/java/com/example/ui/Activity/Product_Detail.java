package com.example.ui.Activity;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ui.Adapter.ImageAdapter;
import com.example.ui.Adapter.ImageViewHolder2Adapter;
import com.example.ui.R;

public class Product_Detail extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewPager2 mainImageView; // ViewPager2 để lướt qua hình ảnh chính
    private ImageButton prevButton, nextButton;

    private Integer[] imageResources = {
            R.drawable.iphone1, R.drawable.iphone2, R.drawable.iphone3, R.drawable.iphone4, R.drawable.iphone1
    };

    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        recyclerView = findViewById(R.id.itemRecyclerView);
        mainImageView = findViewById(R.id.mainImageView); // ViewPager2 thay cho ImageView
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);

        // Cập nhật ViewPager2 để hiển thị hình ảnh chính
        ImageViewHolder2Adapter viewPagerAdapter = new ImageViewHolder2Adapter(this, imageResources);
        mainImageView.setAdapter(viewPagerAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //Thiet lap adapter cho RecycleView
        ImageAdapter adapter = new ImageAdapter(this, imageResources, position -> {
            currentPosition = position;
            mainImageView.setCurrentItem(currentPosition, true);
        });
        recyclerView.setAdapter(adapter);

        // Xử lý nút "Previous"
        prevButton.setOnClickListener(v -> {
            if (currentPosition > 0) {
                currentPosition--;
                mainImageView.setCurrentItem(currentPosition);
                adapter.setSelectedPosition(currentPosition);
            } else if(currentPosition == 0) {
                currentPosition = imageResources.length - 1;
                mainImageView.setCurrentItem(currentPosition);
                adapter.setSelectedPosition(currentPosition);
            }
        });

        // Xử lý nút "Next"
        nextButton.setOnClickListener(v -> {
            if (currentPosition < imageResources.length - 1) {
                currentPosition++;
                mainImageView.setCurrentItem(currentPosition);
                adapter.setSelectedPosition(currentPosition);
            } else if(currentPosition == imageResources.length - 1){
                currentPosition = 0;
                mainImageView.setCurrentItem(currentPosition);
                adapter.setSelectedPosition(currentPosition);
            }
        });
    }
}
