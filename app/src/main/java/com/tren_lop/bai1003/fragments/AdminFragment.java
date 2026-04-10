package com.tren_lop.bai1003.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.tren_lop.bai1003.R;
import com.tren_lop.bai1003.activities.AddMovieActivity;

public class AdminFragment extends Fragment {

    private CardView cardAddMovie;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        cardAddMovie = view.findViewById(R.id.cardAddMovie);
        cardAddMovie.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddMovieActivity.class));
        });

        return view;
    }
}
