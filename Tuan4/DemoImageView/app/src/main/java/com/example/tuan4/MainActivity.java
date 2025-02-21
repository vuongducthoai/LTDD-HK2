package com.example.tuan4;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mainLayout;
    private Switch switchChangeBackgroud;
    private int[] backgroudImages  = {
            R.drawable.picture1,
            R.drawable.picture2,
            R.drawable.picture3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);
        switchChangeBackgroud = findViewById(R.id.switchChangeBackground);

        int selectedBackground = getRandomBackground();
        mainLayout.setBackgroundResource(selectedBackground);

        switchChangeBackgroud.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                int selectedBackground2 = getRandomBackground();
                mainLayout.setBackgroundResource(selectedBackground2);
            }
        }));

    }

    private int getRandomBackground(){
        Random random = new Random();
        return backgroudImages[random.nextInt(backgroudImages.length)];
    }
}