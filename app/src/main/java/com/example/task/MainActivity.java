package com.example.task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    EditText change,title;
    Button credit;
    Button debit,delete;
    private DBHelper db;
    public static double amount=10000.00;
    private LineChart line;
    public double c=0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DBHelper(this);

            line=findViewById(R.id.lineChart);
            change = findViewById(R.id.editText);
            title = findViewById(R.id.editText2);
            credit = findViewById(R.id.button);
            debit = findViewById(R.id.debit);
            delete=findViewById(R.id.myCircularButton);

            setupLineChart();
            refreshGraph();




            credit.setOnClickListener(view -> {

            String ch=change.getText().toString();
            c=Double.parseDouble(ch);
                amount+=c;
            db.credit(amount,c);

            refreshGraph();



            });

            debit.setOnClickListener(view -> {
                String ch=change.getText().toString();
                c=Double.parseDouble(ch);
                double d= -c;
                amount-=c;
                db.debit(amount,d);

                refreshGraph();

            });

            delete.setOnClickListener(view -> {
                deletedatabase();
                refreshGraph();
                amount=0;
            });

        ArrayList<TransactionModel> transactions = db.getAllTransactions();

        // Iterate over transactions using a for loop
        for (TransactionModel transaction : transactions) {
            Log.d("Transaction", "SrNo: " + transaction.getSrNo() +
                    ", Date: " + transaction.getDate() +
                    ", Timestamp: " + transaction.getTimestamp() +
                    ", Amount: " + transaction.getAmount() +
                    ", Credit/Debit: " + transaction.getCreditDebit() +
                    ", Final Amount: " + transaction.getFinalAmount());
        }





        }
        private void deletedatabase(){
        Context con=db.getcontext();
        con.deleteDatabase(DBHelper.DATABASE_NAME);
        amount=10000.00;
        }

    private void setupLineChart() {
        // Configure LineChart appearance and settings
        // This method sets up the LineChart initially
    }

    private void refreshGraph() {
        // Fetch transaction data from DBHelper
        List<TransactionModel> transactions = db.getAllTransactions();

        // Prepare data for the LineChart
        List<Entry> entries = new ArrayList<>();

        // Populate entries with data from transactions
        for (int i = 0; i < transactions.size(); i++) {
            TransactionModel transaction = transactions.get(i);
            // Add a new entry with x-coordinate as the index and y-coordinate as the final amount
            entries.add(new Entry(i, (float) transaction.getFinalAmount()));
        }

        // Create LineDataSet and LineData
        LineDataSet dataSet = new LineDataSet(entries, "Spending Over Time");
        LineData lineData = new LineData(dataSet);

        // Set data to the LineChart
        line.setData(lineData);
        line.invalidate(); // Refresh chart
    }



}



