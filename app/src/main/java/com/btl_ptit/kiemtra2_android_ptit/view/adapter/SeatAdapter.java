package com.btl_ptit.kiemtra2_android_ptit.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl_ptit.kiemtra2_android_ptit.R;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    public static class Seat {
        public int seatNumber;
        public boolean isBooked;
        public boolean isSelected;

        public Seat(int seatNumber, boolean isBooked) {
            this.seatNumber = seatNumber;
            this.isBooked = isBooked;
            this.isSelected = false;
        }
    }

    private List<Seat> seatList;
    private int maxSelectable;
    private int currentlySelected = 0;

    public SeatAdapter(List<Seat> seatList, int maxSelectable) {
        this.seatList = seatList;
        this.maxSelectable = maxSelectable;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = seatList.get(position);

        holder.tvSeatName.setText(String.valueOf(seat.seatNumber));
        holder.tvSeatName.setEnabled(!seat.isBooked);
        holder.tvSeatName.setSelected(seat.isSelected);

        holder.tvSeatName.setOnClickListener(v -> {
            if (seat.isBooked) return;

            if (seat.isSelected) {
                seat.isSelected = false;
                currentlySelected--;
                notifyItemChanged(position);
            } else {
                if (currentlySelected < maxSelectable) {
                    seat.isSelected = true;
                    currentlySelected++;
                    notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return seatList == null ? 0 : seatList.size();
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public int getCurrentlySelectedCount() {
        return currentlySelected;
    }

    public static class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView tvSeatName;

        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSeatName = itemView.findViewById(R.id.tvSeatName);
        }
    }
}
