package com.example.hisaabkitab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String COLUMN_TID = "TRANSACTION_ID";
    public static final String COLUMN_REMARKS = "REMARKS";
    public static final String COLUMN_AMOUNT = "AMOUNT";
    public static final String COLUMN_DATE = "DATE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "expense.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement= "CREATE TABLE " + EXPENSE_TABLE + " (" + COLUMN_TID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_REMARKS + " TEXT, " + COLUMN_AMOUNT + " INTEGER, " + COLUMN_DATE + " TEXT)";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean deleteOne(int transactionID){
        SQLiteDatabase db=this.getWritableDatabase();
        String deleteString= "DELETE FROM "+EXPENSE_TABLE+ " WHERE "+COLUMN_TID+" = "+transactionID;
        Cursor cursor = db.rawQuery(deleteString, null);
        return cursor.moveToFirst();
    }
    public Expense getOne(int ID){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+EXPENSE_TABLE+ " WHERE "+COLUMN_TID+ " = "+ID,null);
        cursor.moveToFirst();
        int transactionID=cursor.getInt(0);
        String remarks=cursor.getString(1);
        int amount=cursor.getInt(2);
        String date= cursor.getString(3);

        Expense expense=new Expense(transactionID,remarks,amount,date);
        return expense;

    }
    public boolean update(Expense expense){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_REMARKS,expense.getRemarks());
        cv.put(COLUMN_AMOUNT,expense.getAmount());
        cv.put(COLUMN_DATE,expense.getDate());
        return db.update(EXPENSE_TABLE, cv, COLUMN_TID + "="+expense.getTransactionID() ,null) >0;

    }


    public boolean addone(Expense expense){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_REMARKS,expense.getRemarks());
        cv.put(COLUMN_AMOUNT,expense.getAmount());
        cv.put(COLUMN_DATE,expense.getDate());
        long insert = db.insert(EXPENSE_TABLE, null, cv);
        db.close();
        return insert != -1;


    }

    public List<Expense> getAll(){
        List<Expense> returnList= new ArrayList<>();
        String querryStirng="SELECT * FROM "+EXPENSE_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querryStirng,null);
        if(cursor.moveToFirst()){
            do{
                int transctionID=cursor.getInt(0);
                String remarks=cursor.getString(1);
                int amount=cursor.getInt(2);
                String date= cursor.getString(3);

                Expense expense=new Expense(transctionID,remarks,amount,date);
                returnList.add(expense);
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public List<Expense> getAllSorted(String orderBy,String order){
        List<Expense> returnList= new ArrayList<>();
        String querryStirng ="SELECT * FROM "+EXPENSE_TABLE;
        if(orderBy.equals("date")){
            querryStirng="SELECT * FROM "+EXPENSE_TABLE+" ORDER BY "+COLUMN_DATE+" "+order;
        }
        else if(orderBy.equals("amount")){
            querryStirng="SELECT * FROM "+EXPENSE_TABLE+" ORDER BY "+COLUMN_AMOUNT+" "+order+" ";
        }
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querryStirng,null);
        if(cursor.moveToFirst()){
            do{
                int transctionID=cursor.getInt(0);
                String remarks=cursor.getString(1);
                int amount=cursor.getInt(2);
                String date= cursor.getString(3);

                Expense expense=new Expense(transctionID,remarks,amount,date);
                returnList.add(expense);
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public List<Expense> getSearch(String toSearch){
        List<Expense> returnList= new ArrayList<>();
        String querryStirng="SELECT * FROM "+EXPENSE_TABLE+" WHERE "+COLUMN_REMARKS+" LIKE '%"+toSearch+"%' ";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querryStirng,null);
        if(cursor.moveToFirst()){
            do{
                int transctionID=cursor.getInt(0);
                String remarks=cursor.getString(1);
                int amount=cursor.getInt(2);
                String date= cursor.getString(3);

                Expense expense=new Expense(transctionID,remarks,amount,date);
                returnList.add(expense);
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

}
