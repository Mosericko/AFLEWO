package com.mosericko.aflewo.participants.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.database.DataBaseHandler;
import com.mosericko.aflewo.loginregistration.UserLogin;
import com.mosericko.aflewo.loginregistration.UserRegistration;

import static com.mosericko.aflewo.customer.fragments.HomeFragment.CATEGORY;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.COLOR;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.ID;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.IMAGE;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.NAME;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.PRICE;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.QUANTITY;
import static com.mosericko.aflewo.customer.fragments.HomeFragment.SIZE;

public class ProductDetail extends AppCompatActivity {
    ImageView prodImage;
    TextView name, color, price, category, size, quantity;
    String idIn, nameIntent, colorIntent, priceIntent, categoryIntent, sizeIntent, imageIntent, stockIntent;
    Button addToCart;


    DataBaseHandler myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        myDb = new DataBaseHandler(this);

        prodImage = findViewById(R.id.detail_image);
        name = findViewById(R.id.prod_name);
        color = findViewById(R.id.prod_color);
        price = findViewById(R.id.prod_price);
        category = findViewById(R.id.prod_category);
        size = findViewById(R.id.prod_size);
        addToCart = findViewById(R.id.addToCart);
        quantity = findViewById(R.id.prod_stock);

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
        quantity.setText(stockIntent);

        addToCart.setOnClickListener(v -> {

            showAlertDialog();
        });

    }

    private void showAlertDialog() {
        AlertDialog.Builder signPrompt = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.sign_prompt_dialog, null);

        signPrompt.setView(view);

        TextView register, login;

        register = view.findViewById(R.id.signUpHere);
        login = view.findViewById(R.id.loginHere);

        register.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), UserRegistration.class));
        });

        login.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), UserLogin.class));
        });

        AlertDialog alertDialog = signPrompt.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
}