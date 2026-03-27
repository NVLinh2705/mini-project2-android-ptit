package com.btl_ptit.kiemtra2_android_ptit.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.btl_ptit.kiemtra2_android_ptit.R;
import com.btl_ptit.kiemtra2_android_ptit.database.AppDatabase;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Movie;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Showtime;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Theater;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.User;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class InvoiceActivity extends AppCompatActivity {

    private TextView tvInvoiceUserName, tvInvoiceMovieName, tvInvoiceTheater, tvShowtime, tvInvoiceSeats, tvInvoiceTotal, tvInvoiceCreatedAt;
    private AppCompatButton btnHome;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        tvInvoiceUserName = findViewById(R.id.tvInvoiceUserName);
        tvInvoiceMovieName = findViewById(R.id.tvInvoiceMovieName);
        tvInvoiceTheater = findViewById(R.id.tvInvoiceTheater);
        tvInvoiceSeats = findViewById(R.id.tvInvoiceSeats);
        tvInvoiceTotal = findViewById(R.id.tvInvoiceTotal);
        tvInvoiceCreatedAt = findViewById(R.id.tvInvoiceCreatedAt);
        tvShowtime = findViewById(R.id.tvShowtime);
        btnHome = findViewById(R.id.btnHome);

        db = AppDatabase.getInstance(this);

        int showtimeId = getIntent().getIntExtra("showtimeId", -1);
        int userId = getIntent().getIntExtra("userId", -1);
        ArrayList<Integer> seats = getIntent().getIntegerArrayListExtra("seats");
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        String createdAtStr = getIntent().getStringExtra("createdAt");

        if (createdAtStr != null) {
            tvInvoiceCreatedAt.setText("Thời gian đặt: " + createdAtStr);
        }

        if (seats != null && !seats.isEmpty()) {
            Collections.sort(seats);
            StringBuilder sb = new StringBuilder("Ghế: ");
            for (int i = 0; i < seats.size(); i++) {
                sb.append(seats.get(i));
                if (i < seats.size() - 1) sb.append(", ");
            }
            tvInvoiceSeats.setText(sb.toString());
        }

        tvInvoiceTotal.setText(String.format("Tổng tiền: %,.0fđ", totalPrice));

        if (userId != -1) {
            new Thread(() -> {
                User user = db.userDAO().getUserById(userId);
                if (user != null) {
                    runOnUiThread(() -> tvInvoiceUserName.setText("Khách hàng: " + user.getUsername()));
                }
            }).start();
        }

        if (showtimeId != -1) {
            loadShowtimeInfo(showtimeId);
        }

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(InvoiceActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadShowtimeInfo(int showtimeId) {
        new Thread(() -> {
            Showtime showtime = db.showtimeDAO().getShowtimeById(showtimeId);
            if (showtime != null) {
                Movie movie = db.movieDAO().getMovieById(showtime.movieId);
                Theater theater = db.theaterDAO().getTheaterById(showtime.theaterId);

                runOnUiThread(() -> {
                    if (movie != null) tvInvoiceMovieName.setText(movie.title);
                    if (theater != null) tvInvoiceTheater.setText("Rạp: " + theater.name);

                    if (showtime.startTime != null && showtime.endTime != null) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        tvShowtime.setText("Suất chiếu: " + showtime.startTime.format(formatter) + " - " + showtime.endTime.format(formatter) + " Ngày " + showtime.startTime.format(dateFormatter));
                    }
                });
            }
        }).start();
    }
}
