package com.mosericko.aflewo.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.database.DataBaseHandler;

import static com.mosericko.aflewo.customer.fragments.HomeFragment.CATEGORY;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.COLOR;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.ID;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.IMAGE;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.NAME;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.PRICE;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.QUANTITY;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.SIZE;

public class MoreCatInfo extends AppCompatActivity {

    ImageView prodImage;
    TextView name, color, price, category, size,stock;
    String idIn, nameIntent, colorIntent, priceIntent, categoryIntent, sizeIntent, imageIntent,stockIntent;
    Button addToCart;

    DataBaseHandler myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_cat_info);

        myDb = new DataBaseHandler(this);

        prodImage = findViewById(R.id.detail_image);
        name = findViewById(R.id.prod_name);
        color = findViewById(R.id.prod_color);
        price = findViewById(R.id.prod_price);
        category = findViewById(R.id.prod_category);
        stock = findViewById(R.id.prod_stock);
        size = findViewById(R.id.prod_size);
        addToCart = findViewById(R.id.addToCart);

        Intent getDetailsIntent = getIntent();
        idIn = getDetailsIntent.getStringExtra(ID);
        nameIntent = getDetailsIntent.getStringExtra(NAME);
        colorIntent = getDetailsIntent.getStringExtra(COLOR);
        priceIntent = getDetailsIntent.getStringExtra(PRICE);
        categoryIntent = getDetailsIntent.getStringExtra(CATEGORY);
        sizeIntent = getDetailsIntent.getStringExtra(SIZE);
        imageIntent = getDetailsIntent.getStringExtra(IMAGE);
        stockIntent = getDetailsIntent.getStringExtra(QUANTITY);

        Glide.with(this)
                .load(imageIntent)
                .into(prodImage);
        name.setText(nameIntent);
        color.setText(colorIntent);
        price.setText(priceIntent);
        category.setText(categoryIntent);
        size.setText(sizeIntent);
        stock.setText(stockIntent);


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartBottomSheet cartBottomSheet = new CartBottomSheet();
                Bundle bundle = new Bundle();
                bundle.putString("id", idIn);
                bundle.putString("name", nameIntent);
                bundle.putString("color", colorIntent);
                bundle.putString("price", priceIntent);
                bundle.putString("category", categoryIntent);
                bundle.putString("size", sizeIntent);
                bundle.putString("image", imageIntent);
                bundle.putString("quantity", stockIntent);
                cartBottomSheet.setArguments(bundle);
                cartBottomSheet.show(getSupportFragmentManager(), "cartBottomSheet");
            }
        });



    }
}