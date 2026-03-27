package com.btl_ptit.kiemtra2_android_ptit.view.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btl_ptit.kiemtra2_android_ptit.R;
import com.btl_ptit.kiemtra2_android_ptit.database.AppDatabase;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Theater;
import com.btl_ptit.kiemtra2_android_ptit.view.adapter.TheaterAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class TheaterActivity extends AppCompatActivity {

    private RecyclerView rcvTheater;
    private TheaterAdapter theaterAdapter;
    private List<Theater> theaterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_theater);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        loadData();
    }

    private void initView() {
        rcvTheater = findViewById(R.id.rcvTheater);
        theaterList = new ArrayList<>();
        theaterAdapter = new TheaterAdapter(theaterList);
        rcvTheater.setLayoutManager(new LinearLayoutManager(this));
        rcvTheater.setAdapter(theaterAdapter);
    }

    private void loadData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Theater> list = AppDatabase.getInstance(this).theaterDAO().getAllTheaters();
            
            // Nếu database trống, thêm một số dữ liệu mẫu để kiểm tra
            if (list == null || list.isEmpty()) {
                AppDatabase.getInstance(this).theaterDAO().insertTheater(new Theater("CGV Vincom Center", "72 Lê Thánh Tôn, Quận 1"));
                AppDatabase.getInstance(this).theaterDAO().insertTheater(new Theater("Lotte Cinema Cantavil", "Tầng 7, Cantavil Premier, Quận 2"));
                AppDatabase.getInstance(this).theaterDAO().insertTheater(new Theater("BHD Star Cineplex", "3-3C Đường 3/2, Quận 10"));
                list = AppDatabase.getInstance(this).theaterDAO().getAllTheaters();
            }
            
            List<Theater> finalList = list;
            runOnUiThread(() -> {
                theaterAdapter.setTheaterList(finalList);
            });
        });
    }
}