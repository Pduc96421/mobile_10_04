package com.tren_lop.bai1003.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.tren_lop.bai1003.R;
import com.tren_lop.bai1003.adapters.TicketAdapter;
import com.tren_lop.bai1003.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketsFragment extends Fragment {

    private RecyclerView rvTickets;
    private TicketAdapter ticketAdapter;
    private List<Ticket> ticketList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        rvTickets = view.findViewById(R.id.rvTickets);
        tvEmpty = view.findViewById(R.id.tvEmptyTickets);
        
        rvTickets.setLayoutManager(new LinearLayoutManager(getContext()));
        ticketList = new ArrayList<>();
        ticketAdapter = new TicketAdapter(ticketList);
        rvTickets.setAdapter(ticketAdapter);

        loadTickets();

        return view;
    }

    private void loadTickets() {
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("tickets")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        ticketList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Ticket ticket = document.toObject(Ticket.class);
                            ticketList.add(ticket);
                        }
                        ticketAdapter.notifyDataSetChanged();
                        
                        if (ticketList.isEmpty()) {
                            tvEmpty.setVisibility(View.VISIBLE);
                        } else {
                            tvEmpty.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
