package com.btl_ptit.kiemtra2_android_ptit.view.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btl_ptit.kiemtra2_android_ptit.R;
import com.btl_ptit.kiemtra2_android_ptit.database.AppDatabase;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Showtime;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Ticket;
import com.btl_ptit.kiemtra2_android_ptit.view.adapter.SeatAdapter;

import java.util.ArrayList;
import java.util.List;

public class SeatActivity extends AppCompatActivity {

    private RecyclerView rcvSeatMap;
    private AppCompatButton btnContinue;
    private SeatAdapter adapter;
    private AppDatabase db;

    private int showtimeId;
    private int userId;
    private int quantity;
    private double unitPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seat);

        rcvSeatMap = findViewById(R.id.rcvSeatMap);
        btnContinue = findViewById(R.id.btnContinue);

        showtimeId = getIntent().getIntExtra("showtimeId", -1);
        userId = getIntent().getIntExtra("userId", -1);
        quantity = getIntent().getIntExtra("quantity", 1);
        unitPrice = getIntent().getDoubleExtra("unitPrice", 0);

        db = AppDatabase.getInstance(this);

        rcvSeatMap.setLayoutManager(new GridLayoutManager(this, 5));

        loadSeats();

        btnContinue.setOnClickListener(v -> confirmBooking());
    }

    private void loadSeats() {
        new Thread(() -> {
            Showtime showtime = db.showtimeDAO().getShowtimeById(showtimeId);
            if (showtime == null) return;

            int totalSeats = showtime.seatNum;
            if (totalSeats <= 0) totalSeats = 50; 

            List<Ticket> bookedTickets = db.ticketDAO().getTicketsByShowtime(showtimeId);
            List<Integer> bookedSeatNumbers = new ArrayList<>();
            for (Ticket t : bookedTickets) {
                bookedSeatNumbers.add(t.seat);
            }

            List<SeatAdapter.Seat> seats = new ArrayList<>();
            for (int i = 1; i <= totalSeats; i++) {
                boolean isBooked = bookedSeatNumbers.contains(i);
                seats.add(new SeatAdapter.Seat(i, isBooked));
            }

            runOnUiThread(() -> {
                adapter = new SeatAdapter(seats, quantity);
                rcvSeatMap.setAdapter(adapter);
            });
        }).start();
    }

    private void confirmBooking() {
        if (adapter == null) return;

        if (adapter.getCurrentlySelectedCount() < quantity) {
            Toast.makeText(this, "Vui lòng chọn đủ " + quantity + " ghế", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String createdAtStr = now.format(formatter);

            List<SeatAdapter.Seat> seats = adapter.getSeatList();
            ArrayList<Integer> selectedSeats = new ArrayList<>();
            for (SeatAdapter.Seat seat : seats) {
                if (seat.isSelected) {
                    Ticket ticket = new Ticket();
                    ticket.userId = userId;
                    ticket.showtimeId = showtimeId;
                    ticket.seat = seat.seatNumber;
                    ticket.price = unitPrice;
                    ticket.createdAt = now;
                    db.ticketDAO().insertTicket(ticket);
                    selectedSeats.add(seat.seatNumber);
                }
            }
            double totalPrice = selectedSeats.size() * unitPrice;

            runOnUiThread(() -> {
                Toast.makeText(this, "Đặt vé thành công!", Toast.LENGTH_LONG).show();
                android.content.Intent intent = new android.content.Intent(SeatActivity.this, InvoiceActivity.class);
                intent.putExtra("showtimeId", showtimeId);
                intent.putExtra("userId", userId);
                intent.putIntegerArrayListExtra("seats", selectedSeats);
                intent.putExtra("totalPrice", totalPrice);
                intent.putExtra("createdAt", createdAtStr);
                startActivity(intent);
                finish();
            });
        }).start();
    }
}