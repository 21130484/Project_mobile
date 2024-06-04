package com.example.handmakeapp.listProduct;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.handmakeapp.R;

import java.util.List;

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
        listProduct2 = (ListView) findViewById(R.id.listProducts);
        String[] arr2 = {"dép","giày","mũ","ống tay","bó gối"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arr2);

        String[] option = {"Khung Ảnh HandMade","Thiệp HandMade","Đồ Dùng HandMade"};
        ArrayAdapter<String> adapterFilter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,option);
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterProduct = findViewById(R.id.filterProductList);
        filterProduct.setAdapter(adapterFilter);
//        Hành động khi chọn danh mục
        filterProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            Khi có chọn trong select
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getItemAtPosition(position).toString();
                updateListView(selection);
            }
//            Khi không có chọn gì
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                listProduct1.setAdapter(adapter);
                listProduct2.setAdapter(adapter2);
            }
        });

    }

    private void updateListView(String selection) {
        if(selection.equalsIgnoreCase("Khung Ảnh HandMade")){
            //        khi chọn khung ảnh
            String[] khunganh = {"KA A","KA B","KA C","KA D","KA E"};
            String[] khunganh2 = {"KA A1","KA B1","KA C1","KA D1","KA E1"};
            ArrayAdapter<String> newData = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,khunganh);
            ArrayAdapter<String> newData2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,khunganh2);
            listProduct1.setAdapter(newData);
            listProduct2.setAdapter(newData2);

        }else if(selection.equalsIgnoreCase("Thiệp HandMade")){
            //        khi chọn thiep
            String[] thiep = {"T A","T B","T C","T D","T E"};
            String[] thiep2 = {"T A1","T B1","T C1","T D1","T E1"};
            ArrayAdapter<String> newData = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,thiep);
            ArrayAdapter<String> newData2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,thiep2);
            listProduct1.setAdapter(newData);
            listProduct2.setAdapter(newData2);
        }else if(selection.equalsIgnoreCase("Đồ Dùng HandMade")){
            //        khi chọn đồ dùng
            String[] dodung = {"dd A","dd B","dd C","dd D","dd E"};
            String[] dodung2 = {"dd A1","dd B1","dd C1","dd D1","dd E1"};
            ArrayAdapter<String> newData = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dodung);
            ArrayAdapter<String> newData2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dodung2);
            listProduct1.setAdapter(newData);
            listProduct2.setAdapter(newData2);

        }
    }
}
