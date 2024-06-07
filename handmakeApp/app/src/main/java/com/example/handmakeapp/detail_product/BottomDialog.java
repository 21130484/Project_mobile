package com.example.handmakeapp.detail_product;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.handmakeapp.R;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.ProductDetail;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class BottomDialog extends BottomSheetDialogFragment {
    private ProductDetail p;

    public void setP(ProductDetail p) {
        this.p = p;
    }

    public BottomDialog() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.raw_add_item, container, false);

        Button buyBtn = view.findViewById(R.id.doneBtn);
        TextView titleTxt = view.findViewById(R.id.titleText);
        TextView stockTxt = view.findViewById(R.id.stockText);
        TextView priceTxt = view.findViewById(R.id.priceText);
        ImageView imgView = view.findViewById(R.id.subItem);

        ImageView minus = view.findViewById(R.id.minusQuantity);
        TextView valueQuantity = view.findViewById(R.id.quantityValue);
        ImageView plus = view.findViewById(R.id.plusQuantity);
        TextView total = view.findViewById(R.id.totalValue);

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("VND"));


        if(p != null) {
            titleTxt.setText(p.getName());
            stockTxt.setText("Kho còn " + p.getStock() + " sản phẩm." );
            priceTxt.setText(format.format(p.getSellingPrice()));
            String iUrl = CallAPI.getAbsoluteURL() + "/" + p.getImageList().get(0).getPath();
            Picasso.get().load(iUrl).into(imgView);
            total.setText(format.format(p.getSellingPrice()));
        }

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //convert Vnd -> int.
              int totalPr = convertVNDtoInt(total.getText().toString());
                int quantity = Integer.parseInt(valueQuantity.getText().toString());
                if(quantity> 1) {
                    quantity--;
                    totalPr -= p.getSellingPrice();
                    valueQuantity.setText(String.valueOf(quantity));
                    total.setText(format.format(totalPr));
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(valueQuantity.getText().toString());
                int totalPr = convertVNDtoInt(total.getText().toString());

                if(quantity < p.getStock()) {
                    quantity++;
                    totalPr+=p.getSellingPrice();
                    valueQuantity.setText(String.valueOf(quantity));
                    total.setText(format.format(totalPr));
                }
            }
        });


        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
//                intent.putExtra("data", e.getText().toString());

                startActivity(intent);
            }
        });
        return view;
    }

    //Convert 70.000đ to 70000.
    public static int convertVNDtoInt(String priceVND) {
        String numerics = priceVND.replaceAll("[^\\d]", "");
        return Integer.parseInt(numerics);
    }


}
