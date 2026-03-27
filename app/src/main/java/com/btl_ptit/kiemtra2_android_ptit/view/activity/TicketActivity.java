package com.btl_ptit.kiemtra2_android_ptit.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.btl_ptit.kiemtra2_android_ptit.R;
import com.btl_ptit.kiemtra2_android_ptit.database.AppDatabase;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Movie;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Showtime;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Theater;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Ticket;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.User;
import com.btl_ptit.kiemtra2_android_ptit.utils.Constants;

import java.time.format.DateTimeFormatter;

public class TicketActivity extends AppCompatActivity {

    private TextView tvUserName, tvMovieDetail, tvTheaterDetail, tvTimeDetail, tvUnitPrice, tvQuantity;
    private Button btnMinus, btnPlus;
    private AppCompatButton btnExit, btnSelectSeat;

    private int quantity = 1;
    private double unitPrice = 0.0;
    private AppDatabase db;
    private User currentUser;
    private Showtime currentShowtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        tvUserName = findViewById(R.id.tvUserName);
        tvMovieDetail = findViewById(R.id.tvMovieDetail);
        tvTheaterDetail = findViewById(R.id.tvTheaterDetail);
        tvTimeDetail = findViewById(R.id.tvTimeDetail);
        tvUnitPrice = findViewById(R.id.tvUnitPrice);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnExit = findViewById(R.id.btnExit);
        btnSelectSeat = findViewById(R.id.btnSelectSeat);

        db = AppDatabase.getInstance(this);

        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateQuantity();
            }
        });

        btnPlus.setOnClickListener(v -> {
            quantity++;
            updateQuantity();
        });

        btnExit.setOnClickListener(v -> finish());

        btnSelectSeat.setOnClickListener(v -> bookTickets());

        loadData();
    }

    private void updateQuantity() {
        tvQuantity.setText(String.valueOf(quantity));
    }

    private void loadData() {
        int showtimeId = getIntent().getIntExtra("showtimeId", -1);
        if (showtimeId == -1) return;

        SharedPreferences sp = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        String username = sp.getString(Constants.KEY_USERNAME, "");

        new Thread(() -> {
            if (!username.isEmpty()) {
                currentUser = db.userDAO().getUserByUsername(username);
            }

            currentShowtime = db.showtimeDAO().getShowtimeById(showtimeId);
            if (currentShowtime != null) {
                Movie movie = db.movieDAO().getMovieById(currentShowtime.movieId);
                Theater theater = db.theaterDAO().getTheaterById(currentShowtime.theaterId);

                runOnUiThread(() -> {
                    if (currentUser != null) {
                        tvUserName.setText(currentUser.getUsername());
                    } else {
                        tvUserName.setText("Khách (Chưa đăng nhập)");
                    }

                    if (movie != null) {
                        tvMovieDetail.setText(movie.title);
                    }
                    if (theater != null) {
                        tvTheaterDetail.setText("Rạp: " + theater.name + " - " + theater.address);
                    }

                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    if (currentShowtime.startTime != null && currentShowtime.endTime != null) {
                        String timeStr = "Thời gian: " + currentShowtime.startTime.format(timeFormatter) +
                                " - " + currentShowtime.endTime.format(timeFormatter) +
                                " Ngày " + currentShowtime.startTime.format(dateFormatter);
                        tvTimeDetail.setText(timeStr);
                    }

                    unitPrice = currentShowtime.price;
                    tvUnitPrice.setText(String.format("%,.0fđ", unitPrice));
                });
            }
        }).start();
    }

    private void bookTickets() {
        if (currentUser == null) {
            Intent intent = new Intent(TicketActivity.this, LoginActivity.class);
            intent.putExtra("is_from_ticket", true);
            startActivityForResult(intent, 100);
            Toast.makeText(this, "Vui lòng đăng nhập để đặt vé", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentShowtime == null) {
            Toast.makeText(this, "Lỗi thông tin suất chiếu", Toast.LENGTH_SHORT).show();
            return;
        }

        android.content.Intent intent = new android.content.Intent(this, SeatActivity.class);
        intent.putExtra("showtimeId", currentShowtime.id);
        intent.putExtra("userId", (int) currentUser.getId());
        intent.putExtra("quantity", quantity);
        intent.putExtra("unitPrice", unitPrice);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            loadData();
        }
    }
}