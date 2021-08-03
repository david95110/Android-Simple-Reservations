package com.example.simplereservationsapp.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.simplereservationsapp.R;
import com.example.simplereservationsapp.modules.CalendarViewHolder;
import com.example.simplereservationsapp.modules.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalendarViewAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private final ArrayList<Reservation> mReservations;
    private final String monthYear;

    public CalendarViewAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener, ArrayList<Reservation> mReservations, String monthYear) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.mReservations = mReservations;
        this.monthYear = monthYear;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.calendar_cell,parent,false);
        ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
        layoutParams.height=(int)(parent.getHeight()*0.1666666);//velicina jende celije
        return new CalendarViewHolder(view, onItemListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
            String holderDay=daysOfMonth.get(position);
            holder.getDayOfMonth().setText(holderDay);
            Reservation reservation = null;
            if(mReservations!=null && mReservations.size()!=0 && holderDay!="") {
                try {
                    reservation = getReservation(daysOfMonth.get(position));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            holder.setReservation(reservation);
    }

    private Reservation getReservation(String day) throws ParseException {
        String holderDate=day+" "+monthYear;
        for(Reservation res : mReservations){
            if(checkIfDateBelongIn(holderDate,res.getDatumOd(),res.getDatumDo())){
                return res;
            }
        }
        return null;
    }

    private boolean checkIfDateBelongIn(String holderDate, String datumOd, String datumDo) throws ParseException {
        Date holder=new SimpleDateFormat("dd MMMM yyyy").parse(holderDate);
        Date start=new SimpleDateFormat("dd MMMM yyyy").parse(datumOd);
        Date end=new SimpleDateFormat("dd MMMM yyyy").parse(datumDo);

        if(!holder.before(start) && holder.before(end)){
            return true;
        }

        return false;





    }



    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }



    public interface OnItemListener{
        void OnItemClicked(Reservation res);
    }
}
