package com.mosericko.aflewo.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;

import static com.mosericko.aflewo.customer.fragments.HomeFragment.CATEGORY;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.COLOR;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.IMAGE;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.NAME;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.PRICE;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.SIZE;

public class ProductDetails extends AppCompatActivity {
    ImageView prodImage;
    TextView name,color,price,category,size;
    String nameIntent,colorIntent,priceIntent,categoryIntent,sizeIntent,imageIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_details);

        prodImage=findViewById(R.id.detail_image);
        name=findViewById(R.id.prod_name);
        color=findViewById(R.id.prod_color);
        price=findViewById(R.id.prod_price);
        category=findViewById(R.id.prod_category);
        size=findViewById(R.id.prod_size);

        Intent getDetailsIntent=getIntent();
        nameIntent = getDetailsIntent.getStringExtra(NAME);
        colorIntent = getDetailsIntent.getStringExtra(COLOR);
        priceIntent = getDetailsIntent.getStringExtra(PRICE);
        categoryIntent = getDetailsIntent.getStringExtra(CATEGORY);
        sizeIntent = getDetailsIntent.getStringExtra(SIZE);
        imageIntent = getDetailsIntent.getStringExtra(IMAGE);

        Glide.with(this)
                .load(imageIntent)
                .into(prodImage);
        name.setText(nameIntent);
        color.setText(colorIntent);
        price.setText(priceIntent);
        category.setText(categoryIntent);
        size.setText(sizeIntent);


    }
}