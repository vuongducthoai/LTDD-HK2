package com.example.firebase;



import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebase.adapter.VideoAdapter;
import com.example.firebase.model.Video;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView currentAvatar;
    VideoAdapter adapter;
    ArrayList<Video> videos = new ArrayList<>();

    DatabaseReference userRef;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        currentAvatar = findViewById(R.id.avatarTopRight);

        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance("https://myvideoapp-f365e-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(this, videos);
        recyclerView.setAdapter(adapter);

        loadAllVideos();
        loadCurrentUserAvatar();
        currentAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

    }

    private void loadAllVideos() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                videos.clear();
                for (DataSnapshot userSnap : snapshot.getChildren()) {
                    String email = userSnap.child("email").getValue(String.class);
                    String avatar = userSnap.child("avatar").getValue(String.class);

                    for (DataSnapshot videoSnap : userSnap.child("videos").getChildren()) {
                        Video video = videoSnap.getValue(Video.class);
                        video.setEmail(email);
                        video.setAvatar(avatar);
                        videos.add(video);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    private void loadCurrentUserAvatar() {
        String uid = auth.getCurrentUser().getUid();
        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String avatar = snapshot.child("avatar").getValue(String.class);
                Glide.with(HomeActivity.this).load(avatar).into(currentAvatar);
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

}

