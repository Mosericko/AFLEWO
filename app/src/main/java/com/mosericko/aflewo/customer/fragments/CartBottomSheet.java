package com.mosericko.aflewo.customer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.activities.Index;
import com.mosericko.aflewo.customer.classes.CartDetails;
import com.mosericko.aflewo.database.DataBaseHandler;

public class CartBottomSheet extends BottomSheetDialogFragment {

    Button okay;
    LinearLayout add, subtract;
    ImageView modalImage;
    TextView price;
    TextView pieces;
    Context context;
    int quantity = 1;
    DataBaseHandler myDb;

    String bundleName, bundlePrice, bundleColor, bundleSize, bundleImage, bundleId, bundleCategory,bundleQuantity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cart_bottom_sheet, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();

        myDb = new DataBaseHandler(context);

        okay = view.findViewById(R.id.okay);
        add = view.findViewById(R.id.layoutInc);
        subtract = view.findViewById(R.id.layoutDec);
        modalImage = view.findViewById(R.id.modalImage);
        price = view.findViewById(R.id.modalPrice);
        pieces = view.findViewById(R.id.modalQuantity);



        Bundle bundle = getArguments();
        assert bundle != null;
        bundleId = bundle.getString("id");
        bundleName = bundle.getString("name");
        bundleColor = bundle.getString("color");
        bundleCategory = bundle.getString("category");
        bundlePrice = bundle.getString("price");
        bundleSize = bundle.getString("size");
        bundleImage = bundle.getString("image");
        bundleQuantity = bundle.getString("quantity");

        String total = bundleQuantity;

        Glide.with(context)
                .load(bundleImage)
                .into(modalImage);
        price.setText(bundlePrice);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < Integer.parseInt(bundleQuantity)) {
                    quantity++;
                    pieces.setText(String.valueOf(quantity));

                } else {
                    Toast.makeText(context, "Quantity Cannot Exceed Stock!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity == 1) {
                    pieces.setText(String.valueOf(quantity));
                    Toast.makeText(context, "Buy at least 1 item", Toast.LENGTH_SHORT).show();

                } else {
                    quantity -= 1;
                    pieces.setText(String.valueOf(quantity));
                }
            }
        });


        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedQuantity = pieces.getText().toString().trim();
                CartDetails cartDetails = new CartDetails(bundleId, bundleImage, bundleName, bundleColor, bundlePrice, bundleCategory, bundleSize, selectedQuantity,total);

                if (myDb.checkIfRowExists(cartDetails)) {
                    Toast.makeText(context, "Item already Added to Cart!", Toast.LENGTH_SHORT).show();
                } else {

                    myDb.addToCart(cartDetails);

                    startActivity(new Intent(context, Index.class));
                    Toast.makeText(context, "Successfully Added " + selectedQuantity +" Piece[s] to the Cart", Toast.LENGTH_SHORT).show();
                    dismiss();
                }


            }
        });


    }
}
