package com.example.hisaabkitab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

public class AddExpense extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button submit;
    EditText remarks,amount,date;
    ImageButton calender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calender=findViewById(R.id.calender);
        submit=findViewById(R.id.submit);
        remarks=findViewById(R.id.et_TID);
        amount=findViewById(R.id.et_Amount);
        date=findViewById(R.id.et_Date);

        Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        month++;
        String today_date=day+"/"+month+"/"+year;
        date.setText(today_date);
    }

    public void showPickerDialog(View view) {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");



    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        month++;
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,day);
        String Date=day+"/"+month+"/"+year;
        date.setText(Date);


    }

    public void add(View view) {

        Expense expense;
        try {
            expense= new Expense(-1,remarks.getText().toString(), Integer.parseInt(amount.getText().toString()) ,date.getText().toString());
            Toast.makeText(AddExpense.this,expense.toString(),Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Calendar c=Calendar.getInstance();
            int year=c.get(Calendar.YEAR);
            int month=c.get(Calendar.MONTH);
            int day=c.get(Calendar.DAY_OF_MONTH);
            month++;
            String today_date=day+"/"+month+"/"+year;
            //Toast.makeText(MainActivity.this,"error adding ",Toast.LENGTH_SHORT).show();
            expense= new Expense(-1,"error",0,today_date);
        }
        DatabaseHelper databaseHelper=new DatabaseHelper(AddExpense.this);
        boolean success = databaseHelper.addone(expense);
        Toast.makeText(AddExpense.this,"success="+success,Toast.LENGTH_SHORT).show();
        finish();


    }
}