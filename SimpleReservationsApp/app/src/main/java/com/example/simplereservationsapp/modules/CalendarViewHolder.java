package com.example.simplereservationsapp.modules;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import com.example.simplereservationsapp.R;
import com.example.simplereservationsapp.adapters.CalendarViewAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setReservation(Reservation res){
        reservation=res;
        if(reservation!=null) {
            dayOfMonth.setBackgroundTintList( ColorStateList.valueOf( reservation.getBoja()));
        }
    }

    public TextView getDayOfMonth(){return dayOfMonth;}


    @Override
    public void onClick(View v) {
        //itemListener.OnItemClicked(getAdapterPosition(),(String)dayOfMonth.getText());
        itemListener.OnItemClicked(reservation);
    }
}
