package com.example.handmakeapp.detail_product;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.handmakeapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomDialog extends BottomSheetDialogFragment {

    public BottomDialog() {


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.raw_add_item, container, false);

        Button buyBtn = view.findViewById(R.id.doneBtn);
//        EditText e = view.findViewById(R.id.newItem);
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
}
