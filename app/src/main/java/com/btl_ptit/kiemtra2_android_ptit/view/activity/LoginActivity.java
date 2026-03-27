package com.btl_ptit.kiemtra2_android_ptit.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.btl_ptit.kiemtra2_android_ptit.R;
import com.btl_ptit.kiemtra2_android_ptit.database.AppDatabase;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.User;
import com.btl_ptit.kiemtra2_android_ptit.utils.Constants;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnCancel;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);

        db = AppDatabase.getInstance(this);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra đăng nhập trong thread riêng
            new Thread(() -> {
                User user = db.userDAO().getUser(username, password);

                runOnUiThread(() -> {
                    if (user != null) {
                        // Lưu session
                        SharedPreferences sp = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(Constants.KEY_USERNAME, username);
                        editor.apply();

                        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                        // Kiểm tra xem có cần chuyển hướng đến màn hình đơn hàng không
//                        boolean shouldRedirect = getIntent().getBooleanExtra(Constants.KEY_SHOULD_REDIRECT_TO_ORDERS, false);
//                        if (shouldRedirect) {
//                            Intent intent = new Intent(this, ListOrderActivity.class);
//                            startActivity(intent);
//                        } else {
                            // Mặc định về Home nếu không có yêu cầu cụ thể
                            boolean isFromTicket = getIntent().getBooleanExtra("is_from_ticket", false);
                            if (isFromTicket) {
                                setResult(RESULT_OK);
                            } else {
                                Intent intent = new Intent(this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        finish();
                    } else {
                        Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        btnCancel.setOnClickListener(v -> finish());
    }
}