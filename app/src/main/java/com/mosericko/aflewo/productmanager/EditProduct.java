package com.mosericko.aflewo.productmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;

import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.CATEGORY_;
import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.COLOR_;
import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.ID_;
import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.IMAGE_;
import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.NAME_;
import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.PRICE_;
import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.SIZE_;

public class EditProduct extends AppCompatActivity {

    ImageView editImage;
    EditText name,color,price;
    RadioGroup category,size;

    String id,imageIn,nameIn,colorIn,priceIn,categoryIn,sizeIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        editImage= findViewById(R.id.edit_product_image);
        name=findViewById(R.id.edit_product_name);
        color=findViewById(R.id.edit_prodColor);
        price=findViewById(R.id.edit_prodPrice);
        category=findViewById(R.id.edit_prodCategory);
        size=findViewById(R.id.edit_size);

        Intent getIntents = getIntent();
        id= getIntents.getStringExtra(ID_);
        imageIn= getIntents.getStringExtra(IMAGE_);
        nameIn= getIntents.getStringExtra(NAME_);
        colorIn= getIntents.getStringExtra(COLOR_);
        priceIn= getIntents.getStringExtra(PRICE_);
        categoryIn= getIntents.getStringExtra(CATEGORY_);
        sizeIn= getIntents.getStringExtra(SIZE_);


        Glide.with(this)
                .load(imageIn)
                .into(editImage);
        name.setText(nameIn);
        color.setText(colorIn);
        price.setText(priceIn);
        /*category.setOnCheckedChangeListener(nameIn);
        name.setText(nameIn);
        name.setText(nameIn);*/



    }
}