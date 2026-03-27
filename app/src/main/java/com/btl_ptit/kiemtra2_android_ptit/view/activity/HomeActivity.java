package com.btl_ptit.kiemtra2_android_ptit.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.btl_ptit.kiemtra2_android_ptit.R;

public class HomeActivity extends AppCompatActivity {

    private Button loginBtn, theaterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        setListeners();
    }

    private void initView() {
        loginBtn = findViewById(R.id.loginBtn);
        theaterBtn = findViewById(R.id.theaterBtn);
    }

    private void setListeners() {
        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        theaterBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TheaterActivity.class);
            startActivity(intent);
        });
    }
}