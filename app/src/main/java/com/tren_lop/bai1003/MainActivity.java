package com.tren_lop.bai1003;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.tren_lop.bai1003.activities.LoginActivity;
import com.tren_lop.bai1003.adapters.MovieAdapter;
import com.tren_lop.bai1003.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        db = FirebaseFirestore.getInstance();
        rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList);
        rvMovies.setAdapter(movieAdapter);

        setupToolbar();
        loadMovies();
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_logout) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private void loadMovies() {
        db.collection("movies")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        movieList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            movie.setId(document.getId());
                            movieList.add(movie);
                        }
                        movieAdapter.notifyDataSetChanged();
                        
                        if (movieList.isEmpty()) {
                            // Optionally, write some sample data if DB is empty
                            addSampleMovies();
                        }
                    } else {
                        Toast.makeText(this, "Error getting movies.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addSampleMovies() {
        // Sample data for demonstration
        Movie m1 = new Movie("1", "Avengers: Endgame", "The Avengers take a final stand against Thanos.", "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg", "", 181, "2019-04-26");
        Movie m2 = new Movie("2", "Interstellar", "A team of explorers travel through a wormhole in space.", "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg", "", 169, "2014-11-05");
        
        db.collection("movies").document("1").set(m1);
        db.collection("movies").document("2").set(m2);
        
        movieList.add(m1);
        movieList.add(m2);
        movieAdapter.notifyDataSetChanged();
    }
}