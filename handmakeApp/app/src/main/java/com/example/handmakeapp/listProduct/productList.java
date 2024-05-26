package com.example.handmakeapp.listProduct;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.handmakeapp.R;

public class productList extends AppCompatActivity {
     ListView listProduct1;
    ListView listProduct2;
    Spinner filterProduct;

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

        String[] option = {"Khung Ảnh HandMade","Thiệp HandMade","Đồ Dùng HandMade"};
        ArrayAdapter<String> adapterFilter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,option);
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterProduct = findViewById(R.id.filterProductList);
        filterProduct.setAdapter(adapterFilter);
//        filterProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selectOption = option[position];
//                System.out.println(selectOption);
//            }
//        });

    }
}
