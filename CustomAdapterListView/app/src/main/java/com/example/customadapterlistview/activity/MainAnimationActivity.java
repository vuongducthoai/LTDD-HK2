package com.example.customadapterlistview.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customadapterlistview.R;
import com.example.customadapterlistview.adapter.CustomAnimationAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainAnimationActivity extends AppCompatActivity {
    private Button btnAddItem;
    private RecyclerView rvItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_main);
        btnAddItem = (Button)findViewById(R.id.btn_add_item);
        rvItems = (RecyclerView)findViewById(R.id.rv_items);
        List<String> data = new ArrayList<>();
        for(int i = 0; i < 5; i ++){
            data.add("item " + i);
        }

        final CustomAnimationAdapter adapter = new CustomAnimationAdapter(data);
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        rvItems.setItemAnimator(new DefaultItemAnimator());
        btnAddItem.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                adapter.addItem("new item");
                rvItems.scrollToPosition(adapter.getItemCount() - 1);
            }
        });

    }
}
