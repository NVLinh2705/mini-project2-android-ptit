package com.btl_ptit.kiemtra2_android_ptit.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.btl_ptit.kiemtra2_android_ptit.R;
import com.btl_ptit.kiemtra2_android_ptit.databinding.ActivityHomeBinding;
import com.btl_ptit.kiemtra2_android_ptit.utils.Constants;

public class HomeActivity extends AppCompatActivity {

    private Button loginBtn, theaterBtn;

    private ActivityHomeBinding binding;
    private String username;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sp= getSharedPreferences(Constants.PREF_NAME, this.MODE_PRIVATE);
        username= sp.getString(Constants.KEY_USERNAME, "");

        if(!username.isEmpty()){
            binding.helloUserTxt.setText("Xin chào "+ username);
            binding.loginBtn.setText("Đăng xuất");
            binding.loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sp.edit().clear().apply();
                    binding.helloUserTxt.setText("");
                    binding.loginBtn.setText("Đăng nhập");
                }
            });
        }else{
            binding.helloUserTxt.setText("");
            binding.loginBtn.setText("Đăng nhập");
            binding.loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(HomeActivity.this,"click vao login",Toast.LENGTH_SHORT).show();
                }
            });
        }
        binding.movieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MovieActivity.class);
                startActivity(intent);
                Toast.makeText(HomeActivity.this,"click vao movie",Toast.LENGTH_SHORT).show();
            }
        });

        binding.theaterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TheaterActivity.class);
                startActivity(intent);
                 Toast.makeText(HomeActivity.this,"click vao theater",Toast.LENGTH_SHORT).show();
            }
        });

        binding.showtimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ShowtimeActivity.class);
                startActivity(intent);
                Toast.makeText(HomeActivity.this,"click vao showtime",Toast.LENGTH_SHORT).show();
            }
        });


    }
}