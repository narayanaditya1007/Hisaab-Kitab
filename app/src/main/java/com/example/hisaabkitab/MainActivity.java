package com.example.hisaabkitab;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.Calendar;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hisaabkitab.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;



import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    EditText search;
    ListView listView;
    Button Sort;
    ImageButton Search_bnt;
    ArrayAdapter expenseArrayAdapter;
    DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        search =findViewById(R.id.SearchView);
        listView=findViewById(R.id.list_View);
        Search_bnt=findViewById(R.id.bnt_Search);
        Sort=findViewById(R.id.Sort);
        databaseHelper = new DatabaseHelper(MainActivity.this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expense expense=(Expense)parent.getItemAtPosition(position);
                Intent intent=new Intent(getApplicationContext(),ViewExpense.class);
                intent.putExtra("TransactionID",expense.getTransactionID());
                startActivity(intent);
            }

        });

        Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        month++;
        String today_date=day+"/"+month+"/"+year;



        Sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] options = {"Date DESC","Date AESC","Amount DESC","Amount AESC"};


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select Sorting Order");
                final String[] a = new String[1];
                final String[] b = new String[1];
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if("Date DESC".equals(options[which])){
                            a[0] ="date";
                            b[0] ="DESC";
                        }
                        else if("Date AESC".equals(options[which])){
                            a[0] ="date";
                            b[0] ="ASC";
                        }
                        else if("Amount DESC".equals(options[which])){
                            a[0] ="amount";
                            b[0] ="DESC";
                        }
                        else{
                            a[0] ="amount";
                            b[0] ="ASC";
                        }

                        databaseHelper = new DatabaseHelper(MainActivity.this);

                        try {
                            expenseArrayAdapter = new ArrayAdapter<Expense>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getAllSorted(a[0],b[0]));
                            listView.setAdapter(expenseArrayAdapter);
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this,"Exception: "+e,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });




        Search_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSearch=search.getText().toString();
                databaseHelper = new DatabaseHelper(MainActivity.this);
                try {
                    expenseArrayAdapter = new ArrayAdapter<Expense>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getSearch(toSearch));
                    listView.setAdapter(expenseArrayAdapter);
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Exception: "+e,Toast.LENGTH_SHORT);
                }
            }
        });

        try {
            expenseArrayAdapter = new ArrayAdapter<Expense>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getAll());
            listView.setAdapter(expenseArrayAdapter);
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this,"Exception: "+e,Toast.LENGTH_SHORT).show();
        }

        setSupportActionBar(binding.appBarMain.toolbar);




        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }





    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        expenseArrayAdapter = new ArrayAdapter<Expense>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getAll());
        listView.setAdapter(expenseArrayAdapter);

        //Refresh your stuff here
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }




    public void addActivity(View view) {
        Intent intent=new Intent(getApplicationContext(),AddExpense.class);
        startActivity(intent);
    }
}