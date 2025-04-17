package com.example.firebase;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity1 extends AppCompatActivity {

    TextView userEmail;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = findViewById(R.id.userEmail); // bạn tạo 1 TextView trong XML

        if (user != null) {
            userEmail.setText("Chào: " + user.getEmail());
        }
    }
}

