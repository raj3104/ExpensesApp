package com.example.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "transactionsDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "transactions";
    private static final String COLUMN_SRNO = "SrNo";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_TIMESTAMP = "Timestamp";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_CREDIT_DEBIT = "credit_debit";
    private static final String COLUMN_FINAL_AMOUNT = "final_amount";
    public Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

    }
    public Context getcontext(){
        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_SRNO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TIMESTAMP + " TEXT,"
                + COLUMN_AMOUNT + " DOUBLE,"
                + COLUMN_CREDIT_DEBIT + " DOUBLE,"
                + COLUMN_FINAL_AMOUNT + " DOUBLE"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void credit(double amount, double creditDebit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, getCurrentDate());
        values.put(COLUMN_TIMESTAMP, getCurrentTimeStamp());
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_CREDIT_DEBIT, creditDebit);
        values.put(COLUMN_FINAL_AMOUNT, amount + creditDebit);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void debit(double amount, double creditDebit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, getCurrentDate());
        values.put(COLUMN_TIMESTAMP, getCurrentTimeStamp());
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_CREDIT_DEBIT, creditDebit);
        values.put(COLUMN_FINAL_AMOUNT, amount - creditDebit);
        db.insert(TABLE_NAME, null, values);
        db.close();

    }


    public ArrayList<TransactionModel> getAllTransactions() {
        ArrayList<TransactionModel> transactionList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TransactionModel transaction = new TransactionModel();
                transaction.setSrNo(cursor.getInt(0));
                transaction.setDate(cursor.getString(1));
                transaction.setTimestamp(cursor.getString(2));
                transaction.setAmount(cursor.getDouble(3));
                transaction.setCreditDebit(cursor.getDouble(4));
                transaction.setFinalAmount(cursor.getDouble(5));
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transactionList;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getCurrentTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}