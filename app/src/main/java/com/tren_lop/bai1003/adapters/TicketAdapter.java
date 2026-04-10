package com.tren_lop.bai1003.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tren_lop.bai1003.R;
import com.tren_lop.bai1003.models.Ticket;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<Ticket> ticketList;

    public TicketAdapter(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);
        holder.tvTitle.setText(ticket.getMovieTitle());
        holder.tvSub.setText("Seats: " + ticket.getSeatsDisplay() + " | Date: " + ticket.getShowtimeDate());
        
        // Customizing the default simple_list_item_2 for dark theme if needed
        holder.tvTitle.setTextColor(holder.itemView.getContext().getColor(R.color.white));
        holder.tvSub.setTextColor(holder.itemView.getContext().getColor(R.color.white));
        holder.tvSub.setAlpha(0.7f);
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSub;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(android.R.id.text1);
            tvSub = itemView.findViewById(android.R.id.text2);
        }
    }
}
