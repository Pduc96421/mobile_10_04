package com.tren_lop.bai1003.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.tren_lop.bai1003.R;
import com.tren_lop.bai1003.adapters.MovieAdapter;
import com.tren_lop.bai1003.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        rvMovies = view.findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));

        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList);
        rvMovies.setAdapter(movieAdapter);

        loadMovies();

        return view;
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
                            addSampleMovies();
                        }
                    } else {
                        Toast.makeText(getContext(), "Error getting movies.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addSampleMovies() {
        Movie m1 = new Movie("1", "Avengers: Endgame", "The Avengers take a final stand against Thanos.", "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg", "", 181, "2019-04-26");
        Movie m2 = new Movie("2", "Interstellar", "A team of explorers travel through a wormhole in space.", "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg", "", 169, "2014-11-05");
        
        db.collection("movies").document("1").set(m1);
        db.collection("movies").document("2").set(m2);
        
        movieList.add(m1);
        movieList.add(m2);
        movieAdapter.notifyDataSetChanged();
    }
}
