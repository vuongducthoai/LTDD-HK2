package com.example.firebase;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.model.User;
import com.example.firebase.model.Video;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initUI();
        initFirebase();

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void initUI() {
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        registerButton = findViewById(R.id.btnRegister);
    }

    private void initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase
                .getInstance("https://myvideoapp-f365e-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users");
    }

    private void registerUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = firebaseAuth.getCurrentUser().getUid();
                        createUserInDatabase(uid, email, password);
                    } else {
                        Toast.makeText(this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createUserInDatabase(String uid, String email, String password) {
        String defaultAvatar = "https://storage.googleapis.com/bucket_mobileapp/images/avatar_trang_1_cd729c335b.jpg";

        // Tạo danh sách video mặc định
        ArrayList<Video> videos = new ArrayList<>();

        // Thêm 2 video cố định vào danh sách
        videos.add(new Video(
                "https://storage.googleapis.com/bucket_mobileapp/images/bandicam%202025-04-11%2009-33-27-622.mp4", // URL video 1
                123, // Lượt thích ban đầu
                5  // Lượt không thích ban đầu
        ));

        videos.add(new Video(
                "https://storage.googleapis.com/bucket_mobileapp/images/bandicam%202025-04-11%2021-44-39-339.mp4", // URL video 2
                642,
                16
        ));

        // 3️⃣ Tạo đối tượng User chứa email, pass, avatar và video
        User newUser = new User(email, password, defaultAvatar, videos);

        // 4️⃣ Lưu vào Realtime Database
        usersRef.child(uid).setValue(newUser)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Lỗi khi lưu dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lỗi khi lưu dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

}
