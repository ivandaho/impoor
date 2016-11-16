package com.example.hoho.impoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ListView mainListView;
    public static ArrayList<FinanceItem> financeItems = new ArrayList<FinanceItem>();
    Intent addnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        mainListView = (ListView) findViewById(R.id.mainListView);

        financeItems.clear();
        FinanceItem fi1 = new FinanceItem("finance item 1", new Date(), 20.30);
        FinanceItem fi2 = new FinanceItem("finance item 2", new Date(), 50.64);
        FinanceItem fi3 = new FinanceItem("finance item 3", new Date(), 12.78);
        financeItems.add(fi1);
        financeItems.add(fi2);
        financeItems.add(fi3);


        String[] listItems = new String[financeItems.size()];
        for(int i = 0; i < financeItems.size(); i ++) {
            FinanceItem financeItem = financeItems.get(i);
            listItems[i] = financeItem.name;
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mainListView.setAdapter(adapter);

        addnew = new Intent(this,
                AddNewItemActivity.class);



        // FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        String[] listItems = new String[financeItems.size()];
        for(int i = 0; i < financeItems.size(); i ++) {
            FinanceItem financeItem = financeItems.get(i);
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
