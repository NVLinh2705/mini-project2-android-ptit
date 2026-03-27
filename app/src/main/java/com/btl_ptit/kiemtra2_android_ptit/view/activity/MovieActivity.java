package com.btl_ptit.kiemtra2_android_ptit.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.btl_ptit.kiemtra2_android_ptit.database.AppDatabase;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Movie;
import com.btl_ptit.kiemtra2_android_ptit.databinding.ActivityMovieBinding;
import com.btl_ptit.kiemtra2_android_ptit.listener.ItemListener;
import com.btl_ptit.kiemtra2_android_ptit.view.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements ItemListener {

    private ActivityMovieBinding binding;
    private AppDatabase db;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getInstance(this);
        

        movieAdapter = new MovieAdapter(movieArrayList, this);
        binding.recyclerViewMovie.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewMovie.setAdapter(movieAdapter);


        loadMovies();
    }

    private void loadMovies() {
        new Thread(() -> {
            List<Movie> list = db.movieDAO().getAllMovies();
            runOnUiThread(() -> {
                movieArrayList.clear();
                movieArrayList.addAll(list);
                movieAdapter.notifyDataSetChanged();
            });
        }).start();
    }

    @Override
    public <T> void onClick(ArrayList<T> list, View view, int position) {
        if (list.get(position) instanceof Movie) {
            Movie movie = (Movie) list.get(position);
            android.content.Intent intent = new android.content.Intent(this, ShowtimeActivity.class);
            intent.putExtra("movieId", movie.id);
            startActivity(intent);
        }
    }

    @Override
    public <T> void onLongClick(ArrayList<T> list, View view, int position) {
        // Xử lý khi nhấn giữ
    }
}
