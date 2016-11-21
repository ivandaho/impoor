package com.example.hoho.impoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mainListView;
    //public static ArrayList<FinanceItem> financeItems = new ArrayList<FinanceItem>();
    public static ArrayList<FinanceItem> financeItem;
    Intent addnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        mainListView = (ListView) findViewById(R.id.mainListView);


        DBHandler db = new DBHandler(this);
        financeItem = db.getFinanceItems();


        String[] listItems = new String[financeItem.size()];
        for(int i = 0; i < financeItem.size(); i ++) {
            FinanceItem financeItem = MainActivity.financeItem.get(i);
            listItems[i] = financeItem.name;
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mainListView.setAdapter(adapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent viewItemIntent = new Intent(getApplicationContext(), ViewItemActivity.class);

                FinanceItem financeItem = MainActivity.financeItem.get(i);
                viewItemIntent.putExtra("name_value", financeItem.name.toString());
                viewItemIntent.putExtra("amount_value", financeItem.amount.toString());
                viewItemIntent.putExtra("date_value", financeItem.date.toString());

                startActivity(viewItemIntent);
            }
        });

        addnew = new Intent(this,
                AddNewItemActivity.class);



        // FAB
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(addnew);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] listItems = new String[financeItem.size()];
        for(int i = 0; i < financeItem.size(); i ++) {
            FinanceItem financeItem = MainActivity.financeItem.get(i);
            listItems[i] = financeItem.name + " $" + financeItem.amount;
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mainListView.setAdapter(adapter);
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
}
