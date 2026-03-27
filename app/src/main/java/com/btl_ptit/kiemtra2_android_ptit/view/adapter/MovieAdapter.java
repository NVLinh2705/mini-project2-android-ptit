package com.btl_ptit.kiemtra2_android_ptit.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl_ptit.kiemtra2_android_ptit.R;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Movie;
import com.btl_ptit.kiemtra2_android_ptit.listener.ItemListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> movieArrayList;
    private ItemListener itemListener;

    public MovieAdapter(ArrayList<Movie> movieArrayList, ItemListener itemListener) {
        this.movieArrayList = movieArrayList;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieArrayList.get(position);
        holder.movieTitleTxt.setText(movie.getTitle());
        holder.movieDescriptionTxt.setText(movie.getDescription());

        Glide.with(holder.itemView.getContext())
                .load(movie.getImageUrl())
                .fitCenter()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.movieImg);
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView movieTitleTxt;
        private TextView movieDescriptionTxt;
        private ImageView movieImg;


        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitleTxt = itemView.findViewById(R.id.movieTitleTxt);
            movieDescriptionTxt = itemView.findViewById(R.id.movieDescriptionTxt);
            movieImg = itemView.findViewById(R.id.movieImg);
            movieImg.setOnClickListener(this);
            movieTitleTxt.setOnClickListener(this);
            movieDescriptionTxt.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener != null){
                int position = getAbsoluteAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    itemListener.onClick(movieArrayList, v, position);
                }
            }
        }
    }
}
