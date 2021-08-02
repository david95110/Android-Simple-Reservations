package com.example.simplereservationsapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplereservationsapp.R;
import com.example.simplereservationsapp.adapters.CalendarViewAdapter;
import com.example.simplereservationsapp.interfaces.IReservationListener;
import com.example.simplereservationsapp.managers.AppManager;
import com.example.simplereservationsapp.modules.MyCalendar;
import com.example.simplereservationsapp.modules.Reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initialiseComponents();
        AppManager.getInstance().getReservations(this);
        displayProgressDialog();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initialiseComponents() {
        mBtnDodaj=(Button)findViewById(R.id.buttonDodaj);
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerViewCalendar);
        mMonthYearText=(TextView)findViewById(R.id.textViewMonthYear);
        mBtnNextMonth=(Button)findViewById(R.id.buttonNextMonth);
        mBtnPreviousMonth=(Button)findViewById(R.id.buttonPreviousMonth);
        mCalendar=new MyCalendar();
        mCalendar.setMonthView(this,mMonthYearText,mRecyclerView);
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonDodaj)
        {
            Log.d(TAG,"btnDodaj");
            Intent i=new Intent(this, AddReservationActivity.class);
            startActivity(i);
        }
        else if(v.getId()==R.id.buttonPreviousMonth){
            mCalendar.setSelectedDate(mCalendar.getSelectedDate().minusMonths(1));
            mCalendar.setMonthView(this,mMonthYearText,mRecyclerView);
        }
        else if(v.getId()==R.id.buttonNextMonth){
            mCalendar.setSelectedDate(mCalendar.getSelectedDate().plusMonths(1));
            mCalendar.setMonthView(this,mMonthYearText,mRecyclerView);
        }

    }

    @Override
    public void onReservationSaved(boolean saved) {

    }

    @Override
    public void onReservationsReceived(ArrayList<Reservation> reservations) {
         if(reservations!=null){
             progressDialog.dismiss();
             displayReservations=reservations;
         }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void OnItemClicked(int position, String dayText) {
        if(dayText!=""){
            String message=dayText+" "+ mCalendar.monthYearFromDate();
            Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        }
    }
}