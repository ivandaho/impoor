package com.example.hoho.impoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mainListView;
    //public static ArrayList<FinanceItem> financeItems = new ArrayList<FinanceItem>();
    public static ArrayList<FinanceItem> financeItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        mainListView = (ListView) findViewById(R.id.mainListView);
        setFinanceItemAdapter();

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent viewItemIntent = new Intent(getApplicationContext(), ViewItemActivity.class);

                FinanceItem financeItem = MainActivity.financeItems.get(i);
                viewItemIntent.putExtra("name_value", financeItem.name.toString());
                viewItemIntent.putExtra("amount_value", financeItem.amount.toString());
                SimpleDateFormat df = new SimpleDateFormat("EEEE, MMMM d, yyyy");
                viewItemIntent.putExtra("date_value", df.format(financeItem.date));

                startActivity(viewItemIntent);
            }
        });


        // FAB
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //DBHandler db = new DBHandler(MainActivity.this);
                //db.dropAll();
                Intent addNewItemIntent = new Intent(getApplicationContext(), AddNewItemActivity.class);

                startActivity(addNewItemIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFinanceItemAdapter();
    }

    /*
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
    public void setFinanceItemAdapter() {
        DBHandler db = new DBHandler(this);
        financeItems = db.getFinanceItems();
        String[] listItems = new String[financeItems.size()];
        for(int i = 0; i < financeItems.size(); i ++) {
            FinanceItem fi = MainActivity.financeItems.get(i);
            listItems[i] = fi.name + " $" + fi.amount;
            if (fi.gain == true) {
                listItems[i] += " +";
            } else if (fi.gain == false) {
                listItems[i] += " -";
            } else {
                listItems[i] += " ?";
            }
        }
        FinanceItemArrayAdapter adapter = new FinanceItemArrayAdapter(this, R.layout.list_item_layout, financeItems);
        mainListView.setAdapter(adapter);
    }
}
