package com.example.hoho.impoor;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by ivan on 10/23/16.
 */

public class AddNewItemActivity extends AppCompatActivity {

    EditText amountField;
    EditText nameField;
    TextView tv_datePicker;
    final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        amountField = (EditText) findViewById(R.id.textfield_amount);
        nameField = (EditText) findViewById(R.id.textfield_name);
        tv_datePicker = (TextView) findViewById(R.id.tv_datePicker);
        tv_datePicker.setText(dateFormat.format(c.getTime()));

        tv_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddNewItemActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        c.set(y, m, d);
                        tv_datePicker.setText(dateFormat.format(c.getTime()));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }


    public void changeToSpent(View view) {
        nameField.setHint("Excuse");
        amountField.setHint("Amount Spent");
    }

    public void changeToEarn(View view) {
        nameField.setHint("How");
        amountField.setHint("Amount Gained");
    }

    public void addThis(View view) {
        FinanceItem item = new FinanceItem(nameField.getText().toString(),
                new Date(),
                Double.parseDouble(amountField.getText().toString()));

        //MainActivity.financeItems.add(item);
        DBHandler db = new DBHandler(this);
        db.addFinanceItem(item);

        finish();
    }

    private void setDateTimeField() {
    }
}
