package com.example.simplereservationsapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplereservationsapp.R;
import com.example.simplereservationsapp.enums.ReservationMode;
import com.example.simplereservationsapp.adapters.CalendarViewAdapter;
import com.example.simplereservationsapp.interfaces.IReservationListener;
import com.example.simplereservationsapp.managers.AppManager;
import com.example.simplereservationsapp.modules.MyCalendar;
import com.example.simplereservationsapp.modules.Reservation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CalendarActivity extends AppCompatActivity implements View.OnClickListener, IReservationListener, CalendarViewAdapter.OnItemListener {

    private static final String TAG="CalendarActivity";
    ArrayList<Reservation> displayReservations;
    MyCalendar mCalendar;
    Button mBtnDodaj,mBtnPreviousMonth,mBtnNextMonth;
    ProgressDialog progressDialog;
    RecyclerView mRecyclerView;
    TextView mMonthYearText;

    ChildEventListener reservationChildEventListener=new ChildEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            if(displayReservations!=null && snapshot!=null){
                Reservation res=snapshot.getValue(Reservation.class);
                if(res!=null) {
                    res.setReservationID(snapshot.getKey());
                    if (!displayReservations.contains(res)) {
                        displayReservations.add(res);
                        setMonthView();
                    }
                }
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            if(displayReservations!=null && snapshot!=null){
                Reservation res=snapshot.getValue(Reservation.class);
                if(res!=null) {
                    res.setReservationID(snapshot.getKey());
                    if (displayReservations.contains(res)) {
                        displayReservations.remove(res);
                    }
                    displayReservations.add(res);
                    setMonthView();
                }
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
              if(displayReservations!=null && snapshot!=null){
                  Reservation res=snapshot.getValue(Reservation.class);
                  if(res!=null) {
                      res.setReservationID(snapshot.getKey());
                      if (displayReservations.contains(res)) {
                          displayReservations.remove(res);
                          setMonthView();
                      }
                  }
              }
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initialiseComponents();
        refreshActivity();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initialiseComponents() {
        mBtnDodaj=(Button)findViewById(R.id.buttonDodaj);
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerViewCalendar);
        mMonthYearText=(TextView)findViewById(R.id.textViewMonthYear);
        mBtnNextMonth=(Button)findViewById(R.id.buttonNextMonth);
        mBtnPreviousMonth=(Button)findViewById(R.id.buttonPreviousMonth);

        mCalendar=new MyCalendar();
        AppManager.getInstance().getReservationsReference().addChildEventListener(reservationChildEventListener);
        mBtnDodaj.setOnClickListener(this);
        mBtnPreviousMonth.setOnClickListener(this);
        mBtnNextMonth.setOnClickListener(this);
        
    }

    private void displayProgressDialog()
    {
        progressDialog=new ProgressDialog(CalendarActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

    }
    private void refreshActivity() {
        AppManager.getInstance().getReservations(this);
        displayProgressDialog();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setMonthView() {
        String monthYear=mCalendar.monthYearFromDate();
        mMonthYearText.setText(monthYear);
        ArrayList<String> daysOfMonth=mCalendar.getDaysOfMonth();

        CalendarViewAdapter calendarViewAdapter=new CalendarViewAdapter(daysOfMonth,this, displayReservations, monthYear);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager((Context) this,7);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(calendarViewAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonDodaj)
        {
            Log.d(TAG,"btnDodaj");
            Intent i=new Intent(this, AddReservationActivity.class);
            i.putExtra("mode", ReservationMode.CREATE_MODE.getValue());
            AppManager.getInstance().setReservationToDisplay(null);
            startActivity(i);
        }
        else if(v.getId()==R.id.buttonPreviousMonth){
            mCalendar.setSelectedDate(mCalendar.getSelectedDate().minusMonths(1));
            setMonthView();
        }
        else if(v.getId()==R.id.buttonNextMonth){
            mCalendar.setSelectedDate(mCalendar.getSelectedDate().plusMonths(1));
            setMonthView();
        }

    }

    @Override
    public void onReservationSaved(boolean saved) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReservationsReceived(ArrayList<Reservation> reservations) {
        progressDialog.dismiss();
         if(reservations!=null){
             displayReservations=reservations;
             setMonthView();
         }
         else
         {
             Toast.makeText(this,"Doslo je do greske.",Toast.LENGTH_LONG).show();
         }
    }

    @Override
    public void onReservationDeleted() {
    }

    @Override
    public void onReservationUpdated() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void OnItemClicked(Reservation res) {
        if(res!=null){
            Intent i=new Intent(this, AddReservationActivity.class);
            i.putExtra("mode", ReservationMode.PREVIEW_MODE.getValue());
            AppManager.getInstance().setReservationToDisplay(res);
            startActivity(i);

        }else{
            Toast.makeText(this,"Ovaj datum je slobodan.",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                refreshActivity();
                return true;
            case (MotionEvent.ACTION_MOVE) :
                return true;
            case (MotionEvent.ACTION_UP) :
                refreshActivity();
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }


}