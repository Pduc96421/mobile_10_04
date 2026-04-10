package com.tren_lop.bai1003.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tren_lop.bai1003.R;
import com.tren_lop.bai1003.models.Movie;
import com.tren_lop.bai1003.models.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingActivity extends AppCompatActivity {

    private TextView tvMovieName;
    private TextInputEditText etSeats;
    private Button btnConfirmBooking;

    private Movie movie;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        movie = (Movie) getIntent().getSerializableExtra("movie");

        tvMovieName = findViewById(R.id.tvMovieName);
        etSeats = findViewById(R.id.etSeats);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);

        if (movie != null) {
            tvMovieName.setText(movie.getTitle());
        }

        btnConfirmBooking.setOnClickListener(v -> confirmBooking());
    }

    private void confirmBooking() {
        if (mAuth.getCurrentUser() == null) return;
        
        String seatsStr = etSeats.getText().toString();
        if (seatsStr.isEmpty()) {
            etSeats.setError("Required");
            return;
        }

        int seatCount = Integer.parseInt(seatsStr);
        double pricePerSeat = 10.0; // Mock price
        double totalAmount = seatCount * pricePerSeat;
        
        List<String> mockSeats = new ArrayList<>();
        for (int i=1; i<=seatCount; i++) {
            mockSeats.add("A" + i);
        }

        Ticket ticket = new Ticket(
                UUID.randomUUID().toString(),
                mAuth.getCurrentUser().getUid(),
                movie.getId() + "_show01", // Mock showtime
                mockSeats,
                totalAmount,
                System.currentTimeMillis(),
                movie.getTitle(),
                String.join(", ", mockSeats),
                "2026-04-10" // Mock date
        );

        db.collection("tickets").document(ticket.getId())
                .set(ticket)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(BookingActivity.this, "Ticket Booked Successfully!", Toast.LENGTH_LONG).show();
                    finish(); // return to detail
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(BookingActivity.this, "Booking Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
