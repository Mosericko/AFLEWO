package com.mosericko.aflewo.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.adapters.GoodsListAdapter;
import com.mosericko.aflewo.database.DataBaseHandler;

import java.util.ArrayList;

import static com.mosericko.aflewo.customer.fragments.CategoriesFrag.EXTRA_CATEGORY;

public class CheckOut extends AppCompatActivity {
    RecyclerView goodsRV;
    DataBaseHandler myDb;
    GoodsListAdapter goodsListAdapter;
    ArrayList<CartDetails>goodsArray = new ArrayList<>();
    TextView totalAmount;
    String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        goodsRV = findViewById(R.id.goodsList);
        totalAmount = findViewById(R.id.totalPrice);

        Intent intent = getIntent();
        total = intent.getStringExtra("Price");

        totalAmount.setText(total);

        listGoods();
    }

    private void listGoods() {
        myDb = new DataBaseHandler(this);
        goodsRV.setHasFixedSize(true);
        goodsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        goodsArray.addAll(myDb.getCartDetails());
        goodsListAdapter = new GoodsListAdapter(CheckOut.this,goodsArray);
        goodsRV.setAdapter(goodsListAdapter);


    }
}