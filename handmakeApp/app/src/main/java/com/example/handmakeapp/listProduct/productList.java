package com.example.handmakeapp.listProduct;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.handmakeapp.R;

public class productList extends AppCompatActivity {
     ListView listProduct1;
    ListView listProduct2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlist);
        AnhXa();
    }

    private void AnhXa() {
        listProduct1 = (ListView) findViewById(R.id.listProduct);
        String[] arr = {"Mũ","Tất","Kính","Quần","Áo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arr);
        listProduct1.setAdapter(adapter);
        listProduct2 = (ListView) findViewById(R.id.listProducts);
        String[] arr2 = {"dép","giày","mũ","ống tay","bó gối"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arr2);
        listProduct2.setAdapter(adapter2);
    }
}
