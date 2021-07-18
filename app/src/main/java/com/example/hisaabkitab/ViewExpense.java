package com.example.hisaabkitab;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ViewExpense extends AppCompatActivity {

    ImageView theme;
    TextView TID;
    EditText Amount,Remarks,Date;
    DatabaseHelper databaseHelper;
    int TransactionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);
        theme=findViewById(R.id.theme);
        TID=findViewById(R.id.tid);
        Amount=findViewById(R.id.Amount);
        Remarks=findViewById(R.id.Remarks);
        Date=findViewById(R.id.Date);
        databaseHelper = new DatabaseHelper(ViewExpense.this);
        Intent intent=getIntent();
        TransactionID=intent.getIntExtra("TransactionID",-1);
        Expense expense= databaseHelper.getOne(TransactionID);
        TID.setText(String.valueOf(expense.getTransactionID()));
        Amount.setText(String.valueOf(expense.getAmount()));
        Date.setText(String.valueOf(expense.getDate()));
        Remarks.setText(expense.getRemarks());
        int r= 1+(int)(7*Math.random());
        switch (r){
            case 1:
                theme.setImageResource(R.drawable.pic1);
                break;
            case 2:
                theme.setImageResource(R.drawable.pic2);
                break;
            case 3:
                theme.setImageResource(R.drawable.pic3);
                break;
            case 4:
                theme.setImageResource(R.drawable.pic4);
                break;
            case 5:
                theme.setImageResource(R.drawable.pic5);
                break;
            case 6:
                theme.setImageResource(R.drawable.pic6);
                break;
            case 7:
                theme.setImageResource(R.drawable.pic7);
                break;
        }
    }

    public void Edit(View view) {
        Expense expense=databaseHelper.getOne(TransactionID);
        expense.setAmount(Integer.parseInt(Amount.getText().toString()));
        expense.setDate(Date.getText().toString());
        expense.setRemarks(Remarks.getText().toString());
        boolean success = databaseHelper.update(expense);
        Toast.makeText(ViewExpense.this,"success= "+success,Toast.LENGTH_SHORT).show();
        finish();
    }

    public void Delete(View view) {
//        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(intent);

        boolean b = databaseHelper.deleteOne(TransactionID);
        finish();
    }
}