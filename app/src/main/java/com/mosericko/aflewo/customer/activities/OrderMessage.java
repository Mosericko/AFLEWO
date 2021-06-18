package com.mosericko.aflewo.customer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mosericko.aflewo.R;

public class OrderMessage extends AppCompatActivity {

    LinearLayout shopMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_message);

        shopMore =findViewById(R.id.shopMore);

        shopMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderMessage.this, Index.class));
                finish();
            }
        });

    }
}