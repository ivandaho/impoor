package com.example.hoho.impoor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by hoho on 11/16/16.
 */

public class ViewItemActivity extends Activity {
    TextView name_title;
    TextView name_value;
    TextView date_title;
    TextView date_value;
    TextView amount_title;
    TextView amount_value;
    Button btn_remove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        name_title = (TextView) findViewById(R.id.tv_name_title);
        name_value = (TextView) findViewById(R.id.tv_name_value);
        date_title = (TextView) findViewById(R.id.tv_date_title);
        date_value = (TextView) findViewById(R.id.tv_date_value);
        amount_title = (TextView) findViewById(R.id.tv_amount_title);
        amount_value = (TextView) findViewById(R.id.tv_amount_value);

        final Intent thisActivity = getIntent();
        name_value.setText(thisActivity.getStringExtra("name_value"));
        date_value.setText(thisActivity.getStringExtra("date_value"));
        amount_value.setText(thisActivity.getStringExtra("amount_value"));
        btn_remove = (Button) findViewById(R.id.btn_remove);
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler db = new DBHandler(ViewItemActivity.this);
                db.removeFinanceItem(thisActivity.getStringExtra("name_value"),
                        thisActivity.getStringExtra("amount_value"));
                db.close();
                finish();

            }
        });
    }
}
