package com.example.simplereservationsapp.modules;

import android.view.View;
import android.widget.TextView;
import com.example.simplereservationsapp.R;
import com.example.simplereservationsapp.adapters.CalendarViewAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView dayOfMonth;
    private Reservation reservation;
    private  final CalendarViewAdapter.OnItemListener itemListener;
    public CalendarViewHolder(@NonNull View itemView, CalendarViewAdapter.OnItemListener itemListener) {
        super(itemView);
        dayOfMonth=itemView.findViewById(R.id.textViewCellDay);
        this.itemListener = itemListener;
        itemView.setOnClickListener(this);
    }

    public Reservation getReservation(){return reservation;}
    public void setReservation(Reservation res){reservation=res;}

    public TextView getDayOfMonth(){return dayOfMonth;}

    @Override
    public void onClick(View v) {
        itemListener.OnItemClicked(getAdapterPosition(),(String)dayOfMonth.getText());
    }
}
