package com.example.hoho.impoor;

import java.util.Date;

/**
 * Created by hoho on 10/22/2016.
 */

public class FinanceItem {
    public String name;
    public Date date;
    public Double amount;
    public String category;

    public FinanceItem(String name, Date date, Double amount) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.category = "unsorted";
    }
}
