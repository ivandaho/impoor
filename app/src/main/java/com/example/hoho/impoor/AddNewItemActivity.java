package com.example.hoho.impoor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Date;


/**
 * Created by ivan on 10/23/16.
 */

public class AddNewItemActivity extends AppCompatActivity {

    EditText amountfield;
    EditText namefield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        amountfield = (EditText) findViewById(R.id.textfield_amount);
        namefield = (EditText) findViewById(R.id.textfield_name);
    }


    public void changeToSpent(View view) {
        namefield.setHint("Excuse");
        amountfield.setHint("Amount Spent");
    }
    public void changeToEarn(View view) {
        namefield.setHint("How");
        amountfield.setHint("Amount Gained");
    }
    public void addThis (View view) {
        FinanceItem item = new FinanceItem(namefield.getText().toString(),
                new Date(),
                Double.parseDouble(amountfield.getText().toString()));

        MainActivity.financeItems.add(item);
        finish();
    }

}
