package com.btl_ptit.kiemtra2_android_ptit.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.btl_ptit.kiemtra2_android_ptit.R;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Movie;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Showtime;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Theater;
import com.btl_ptit.kiemtra2_android_ptit.listener.ItemListener;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {

    private ArrayList<Showtime> showtimeList;
    private Map<Integer, Movie> movieMap;
    private Map<Integer, Theater> theaterMap;
    private ItemListener itemListener;

    public ShowtimeAdapter(ArrayList<Showtime> showtimeList, Map<Integer, Movie> movieMap, Map<Integer, Theater> theaterMap, ItemListener itemListener) {
        this.showtimeList = showtimeList;
        this.movieMap = movieMap;
        this.theaterMap = theaterMap;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        Showtime showtime = showtimeList.get(position);
        Movie movie = movieMap.get(showtime.movieId);
        Theater theater = theaterMap.get(showtime.theaterId);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        if (showtime.startTime != null) {
            holder.tvStartTime.setText(showtime.startTime.format(timeFormatter));
        }
        if (showtime.endTime != null) {
            holder.tvEndTime.setText("~ " + showtime.endTime.format(timeFormatter));
        }

        if (movie != null) {
            holder.tvMovieName.setText(movie.title);
        }
        if (theater != null) {
            holder.tvTheaterName.setText(theater.name);
        }

        holder.btnBook.setOnClickListener(v -> {
            if (itemListener != null) {
                itemListener.onClick(showtimeList, v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return showtimeList == null ? 0 : showtimeList.size();
    }

    public static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvStartTime, tvEndTime, tvMovieName, tvTheaterName;
        AppCompatButton btnBook;

        public ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvTheaterName = itemView.findViewById(R.id.tvTheaterName);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}
