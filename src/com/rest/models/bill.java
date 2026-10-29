package com.rest.models;

import java.util.List;

// Model class representing a complete restaurant bill
public class bill {
    public int billNo;
    public String date;
    public List<billitem> items;
    public double grandTotal;

    public bill(int billNo, String date, List<billitem> items, double grandTotal) {
        this.billNo = billNo;
        this.date = date;
        this.items = items;
        this.grandTotal = grandTotal;
    }

    public int getBillNo() {
        return billNo;
    }

    public String getDate() {
        return date;
    }

    public List<billitem> getItems() {
        return items;
    }

    public double getGrandTotal() {
        return grandTotal;
    }
}
