package com.example.firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebase.adapter.VideoAdapter;
import com.example.firebase.model.Video;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView emailTextView, usernameTextView;
    ImageView avatarImageView, backButton;
    RecyclerView videosRecyclerView;
    VideoAdapter videoAdapter;
    ArrayList<Video> videos = new ArrayList<>();

    DatabaseReference userRef;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        emailTextView = findViewById(R.id.emailTextView);
        usernameTextView = findViewById(R.id.usernameTextView);
        avatarImageView = findViewById(R.id.avatarImageView);
        videosRecyclerView = findViewById(R.id.recyclerView);
        backButton = findViewById(R.id.backButton); // Lấy nút quay lại

        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance("https://myvideoapp-f365e-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");

        videosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(this, videos);
        videosRecyclerView.setAdapter(videoAdapter);

        // Thêm sự kiện cho nút quay lại
        backButton.setOnClickListener(v -> finish()); // Khi nhấn vào nút, Activity sẽ đóng và quay lại HomeActivity

        loadUserProfile();
    }

    private void loadUserProfile() {
        String uid = auth.getCurrentUser().getUid();

        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String email = snapshot.child("email").getValue(String.class);
                String avatar = snapshot.child("avatar").getValue(String.class);
                String username = snapshot.child("username").getValue(String.class);

                emailTextView.setText(email);
                usernameTextView.setText(username);
                Glide.with(ProfileActivity.this).load(avatar).into(avatarImageView);

                // Load the user's videos
                for (DataSnapshot videoSnap : snapshot.child("videos").getChildren()) {
                    Video video = videoSnap.getValue(Video.class);
                    videos.add(video);
                }

                videoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Failed to load profile.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
