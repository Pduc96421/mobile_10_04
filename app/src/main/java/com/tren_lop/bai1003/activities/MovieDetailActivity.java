package com.tren_lop.bai1003.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.tren_lop.bai1003.R;
import com.tren_lop.bai1003.models.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView ivBackdrop;
    private TextView tvMovieTitle, tvMovieInfo, tvMovieDesc;
    private ExtendedFloatingActionButton btnBookTicket;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        ivBackdrop = findViewById(R.id.ivBackdrop);
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvMovieInfo = findViewById(R.id.tvMovieInfo);
        tvMovieDesc = findViewById(R.id.tvMovieDesc);
        btnBookTicket = findViewById(R.id.btnBookTicket);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        if (movie != null) {
            populateUI();
        } else {
            Toast.makeText(this, "Failed to load movie details", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnBookTicket.setOnClickListener(v -> {
            Intent intent = new Intent(MovieDetailActivity.this, BookingActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        });
    }

    private void populateUI() {
        tvMovieTitle.setText(movie.getTitle());
        tvMovieInfo.setText(movie.getDuration() + " mins • " + movie.getReleaseDate());
        tvMovieDesc.setText(movie.getDescription());

        // Assuming posterUrl is also usable as backdrop for now
        Glide.with(this)
                .load(movie.getPosterUrl())
                .placeholder(android.R.color.darker_gray)
                .into(ivBackdrop);
    }
}
