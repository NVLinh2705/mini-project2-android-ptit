package com.btl_ptit.kiemtra2_android_ptit.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl_ptit.kiemtra2_android_ptit.R;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Theater;

import java.util.List;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder> {

    private List<Theater> theaterList;

    public TheaterAdapter(List<Theater> theaterList) {
        this.theaterList = theaterList;
    }

    public void setTheaterList(List<Theater> theaterList) {
        this.theaterList = theaterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theater, parent, false);
        return new TheaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterViewHolder holder, int position) {
        Theater theater = theaterList.get(position);
        holder.tvName.setText(theater.getName());
        holder.tvAddress.setText(theater.getAddress());
    }

    @Override
    public int getItemCount() {
        return theaterList != null ? theaterList.size() : 0;
    }

    public static class TheaterViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress;

        public TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTheaterName);
            tvAddress = itemView.findViewById(R.id.tvTheaterAddress);
        }
    }
}