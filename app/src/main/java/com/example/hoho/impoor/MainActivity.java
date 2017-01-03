package com.example.hoho.impoor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mainListView;
    public Boolean colorEnabled = false;
    //public static ArrayList<FinanceItem> financeItems = new ArrayList<FinanceItem>();
    public static ArrayList<FinanceItem> financeItems;

    public static Double balance;
    final DecimalFormat decf = new DecimalFormat("$#.00");
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("preferences", MODE_PRIVATE);
        sp.getBoolean("color", false);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        mainListView = (ListView) findViewById(R.id.mainListView);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent viewItemIntent = new Intent(getApplicationContext(), ViewItemActivity.class);

                FinanceItem financeItem = MainActivity.financeItems.get(i);
                viewItemIntent.putExtra("name_value", financeItem.name.toString());

                String moneyString = decf.format(financeItem.amount);
                if (financeItem.gain) {
                    moneyString = "+ " + moneyString;
                } else {
                    moneyString = "- " + moneyString;
                }
                viewItemIntent.putExtra("amount_value", moneyString);

                SimpleDateFormat datef = new SimpleDateFormat("EEEE, MMMM d, yyyy");
                viewItemIntent.putExtra("date_value", datef.format(financeItem.date));
                viewItemIntent.putExtra("gain", financeItem.gain);
                viewItemIntent.putExtra("_id", Integer.toString(financeItem._id));

                startActivity(viewItemIntent);
            }
        });


        // FAB
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //DBHandler db = new DBHandler(MainActivity.this);
                //db.dropAll();
                DBHandler db = new DBHandler(MainActivity.this);
                // db.getBalance(getApplicationContext());
                Intent addNewItemIntent = new Intent(getApplicationContext(), AddNewItemActivity.class);

                startActivity(addNewItemIntent);
                overridePendingTransition(R.animator.to_left_in, R.animator.to_left_out);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFinanceItemAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_toggleColor) {

            SharedPreferences sp = getSharedPreferences("preferences", MODE_PRIVATE);
            Boolean colorEnabled = sp.getBoolean("color", false);
            // Toast.makeText(getApplicationContext(), colorEnabled.toString(), Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor = getSharedPreferences("preferences", MODE_PRIVATE).edit();
            if (colorEnabled == true) {
                editor.putBoolean("color", false);
            } else {
                editor.putBoolean("color", true);
            }
            editor.commit();


            setFinanceItemAdapter();
            return true;
        } else if ( id == R.id.action_sortBy) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            final String[] sortChoices = new String[4];
            sortChoices[0] = "Date";
            sortChoices[1] = "Gain/Loss then Date";
            sortChoices[2] = "Net Amount";
            sortChoices[3] = "Inserted";

            builder.setTitle("Sort Method");

            builder.setItems(sortChoices, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DBHandler.sortMethod = i;
                            setFinanceItemAdapter();
                            Toast.makeText(getApplicationContext(), "Sorting by " + sortChoices[i], Toast.LENGTH_SHORT).show();
                        }
                    });

            builder.create();
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setFinanceItemAdapter() {
        int sm = DBHandler.sortMethod;

        DBHandler db = new DBHandler(this);
        financeItems = db.getFinanceItems(sm);
        String balance_header_prefix = getResources().getString(R.string.balance_header_prefix);
        if (balance == null) {
            getSupportActionBar().setTitle(balance_header_prefix + " $0.00");
        } else {
            getSupportActionBar().setTitle(balance_header_prefix + " " + decf.format(balance));
        }
        FinanceItemArrayAdapter adapter = new FinanceItemArrayAdapter(this, R.layout.list_item_layout, financeItems);
        mainListView.setAdapter(adapter);
    }
}
