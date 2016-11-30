package com.example.hoho.impoor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by hoho on 11/29/16.
 */

public class FinanceItemArrayAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<FinanceItem> data;
    private final int layoutResourceId;

    public FinanceItemArrayAdapter(Context context, int layoutResourceId, ArrayList<FinanceItem> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row;
        row = inflater.inflate(R.layout.list_item_layout, parent, false);
        TextView tv_name;
        TextView tv_amount;
        ImageView iv_net_result;
        tv_name = (TextView) row.findViewById(R.id.item_tv_name);
        tv_amount = (TextView) row.findViewById(R.id.item_tv_amount);
        iv_net_result = (ImageView) row.findViewById(R.id.item_iv_net_result);

        tv_name.setText(data.get(i).name);

        tv_amount.setText(toCurrencyString(data.get(i).amount));

        if (checkIfGain(data.get(i))) {
            iv_net_result.setImageResource(R.drawable.ic_add_black_24dp);
        } else {
            iv_net_result.setImageResource(R.drawable.ic_remove_black_24dp);
        }

        return (row);
    }

    public boolean checkIfGain(FinanceItem fi) {
        if (fi.gain == true) {
            return true;
        } else if (fi.gain == false) {
            return false;
        } else {
            // TODO: make error message????
            return false;
        }
    }

    public String toCurrencyString(Double d) {
        DecimalFormat df = new DecimalFormat("$#.00");
        return df.format(d);
    }
}
