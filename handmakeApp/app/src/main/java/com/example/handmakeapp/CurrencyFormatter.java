package com.example.handmakeapp;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
    public static String formatCurrency(double number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        numberFormat.setGroupingUsed(true);
        return numberFormat.format(number) + " VND";
    }
}