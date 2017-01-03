package com.example.hoho.impoor;

import android.app.DatePickerDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    boolean gain = false;
    RadioButton defaultRB;
    RadioGroup rg;
    Calendar c;
    ListFragment lf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        c = Calendar.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);


        rg = (RadioGroup) findViewById(R.id.rg_transaction_type);
        rg.check(R.id.rb_tt_negative);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.animator.to_right_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_additem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            String name;

            Date date = c.getTime();

            if (nameField.getText().toString().trim().length() == 0) {
                name = "Unnamed item";
            } else {
                name = nameField.getText().toString();
            }

            if (amountField.getText().toString().trim().length() == 0) {
                // give warning about no amount entered
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddNewItemActivity.this);

                builder.setMessage("Please enter an amount");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create();
                builder.show();
            } else {
                Double amount = Double.parseDouble(amountField.getText().toString());
                addThis(name, date, amount);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void changeToSpent(View view) {
        nameField.setHint("Excuse");
        amountField.setHint("Amount Spent");
        gain = false;
    }

    public void changeToEarn(View view) {
        nameField.setHint("How");
        amountField.setHint("Amount Gained");
        gain = true;
    }

    public void addThis(String name, Date date, Double amount) {
        //FinanceItem item = new FinanceItem(name, date, amount, gain);

        DBHandler.sortMethod = 3;
        //MainActivity.financeItems.add(item);
        DBHandler db = new DBHandler(this);
        db.addFinanceItem(name, date, amount, gain);

        finish();
    }
    private void setDateTimeField() {
    }
}
