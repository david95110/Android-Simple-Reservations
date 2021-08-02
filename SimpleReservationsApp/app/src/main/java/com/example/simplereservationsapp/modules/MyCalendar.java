package com.example.simplereservationsapp.modules;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplereservationsapp.adapters.CalendarViewAdapter;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MyCalendar {
    LocalDate selectedDate;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public  MyCalendar(){
        selectedDate=LocalDate.now();
    }

    public LocalDate getSelectedDate(){return selectedDate;}
    public void setSelectedDate(LocalDate date){selectedDate=date;}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setMonthView(CalendarViewAdapter.OnItemListener activity, TextView mMonthYearText,RecyclerView recyclerView) {
        mMonthYearText.setText(monthYearFromDate());
        ArrayList<String> daysOfMonth=getDaysOfMonth();

        CalendarViewAdapter calendarViewAdapter=new CalendarViewAdapter(daysOfMonth,activity);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager((Context) activity,7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(calendarViewAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> getDaysOfMonth() {
        ArrayList<String> daysOfMonth=new ArrayList<>();
        YearMonth yearMonth=YearMonth.from(selectedDate);
        int dayOfMonth=yearMonth.lengthOfMonth();
        LocalDate firstOfMonth=selectedDate.withDayOfMonth(1);
        int dayOfWeek=firstOfMonth.getDayOfWeek().getValue();
        for(int i=1;i<=42;i++){
            if(i<=dayOfWeek || i>dayOfMonth+dayOfWeek){
                daysOfMonth.add("");
            }else
                daysOfMonth.add(String.valueOf(i-dayOfWeek));
        }
        return daysOfMonth;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String monthYearFromDate(){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("MMMM yyyy");
        return selectedDate.format(formatter);
    }
}
