package com.btl_ptit.kiemtra2_android_ptit.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btl_ptit.kiemtra2_android_ptit.R;
import com.btl_ptit.kiemtra2_android_ptit.database.AppDatabase;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Movie;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Showtime;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Theater;
import com.btl_ptit.kiemtra2_android_ptit.listener.ItemListener;
import com.btl_ptit.kiemtra2_android_ptit.view.adapter.ShowtimeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowtimeActivity extends AppCompatActivity implements ItemListener {

    private RecyclerView rcvShowtimes;
    private AppDatabase db;
    private ShowtimeAdapter showtimeAdapter;
    private ArrayList<Showtime> showtimeList = new ArrayList<>();
    private Map<Integer, Movie> movieMap = new HashMap<>();
    private Map<Integer, Theater> theaterMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_showtime);

        rcvShowtimes = findViewById(R.id.rcvShowtimes);
        rcvShowtimes.setLayoutManager(new LinearLayoutManager(this));

        showtimeAdapter = new ShowtimeAdapter(showtimeList, movieMap, theaterMap, this);
        rcvShowtimes.setAdapter(showtimeAdapter);

        db = AppDatabase.getInstance(this);

        int movieId = getIntent().getIntExtra("movieId", -1);
        if (movieId != -1) {
            loadShowtimesForMovie(movieId);
        } else {
            loadAllShowtimes();
        }
    }

    private void loadShowtimesForMovie(int movieId) {
        new Thread(() -> {
            loadMapData();
            List<Showtime> showtimes = db.showtimeDAO().getShowtimesByMovie(movieId);
            updateUI(showtimes);
        }).start();
    }

    private void loadAllShowtimes() {
        new Thread(() -> {
            loadMapData();
            List<Showtime> showtimes = db.showtimeDAO().getAllShowtimes();
            updateUI(showtimes);
        }).start();
    }

    private void loadMapData() {
        List<Movie> movies = db.movieDAO().getAllMovies();
        for (Movie m : movies) {
            movieMap.put(m.id, m);
        }

        List<Theater> theaters = db.theaterDAO().getAllTheaters();
        for (Theater t : theaters) {
            theaterMap.put(t.id, t);
        }
    }

    private void updateUI(List<Showtime> showtimes) {
        runOnUiThread(() -> {
            showtimeList.clear();
            showtimeList.addAll(showtimes);
            showtimeAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public <T> void onClick(ArrayList<T> list, View view, int position) {
        if (list.get(position) instanceof Showtime) {
            Showtime showtime = (Showtime) list.get(position);
            android.content.Intent intent = new android.content.Intent(this, TicketActivity.class);
            intent.putExtra("showtimeId", showtime.id);
            startActivity(intent);
        }
    }

    @Override
    public <T> void onLongClick(ArrayList<T> list, View view, int position) {
        // Handle long click
    }
}