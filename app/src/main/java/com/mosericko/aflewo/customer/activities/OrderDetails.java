package com.mosericko.aflewo.customer.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.adapters.OrderDetailsAdapter;
import com.mosericko.aflewo.customer.classes.OrderItems;
import com.mosericko.aflewo.helperclasses.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mosericko.aflewo.customer.activities.OrderHistory.MPESA_CODE;
import static com.mosericko.aflewo.customer.activities.OrderHistory.ORDER_DATE;
import static com.mosericko.aflewo.customer.activities.OrderHistory.ORDER_NUM;
import static com.mosericko.aflewo.customer.activities.OrderHistory.ORDER_STATUS;
import static com.mosericko.aflewo.customer.activities.OrderHistory.ORDER_TIME;
import static com.mosericko.aflewo.customer.activities.OrderHistory.TOTAL_PRICE;

public class OrderDetails extends AppCompatActivity {
    RecyclerView itemsRecycler;
    ArrayList<OrderItems> myItems = new ArrayList<>();
    OrderDetailsAdapter orderDetailsAdapter;
    TextView orderNo, mpesaCode, orderDate, orderTime, amountPaid, orderStatus;
    String orderNoIn, mpesaCodeIn, orderDateIn, orderTimeIn, amountPaidIn, orderStatusIn;
    ImageView downloadReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        orderNo = findViewById(R.id.orderNo);
        mpesaCode = findViewById(R.id.mpesaCode);
        orderDate = findViewById(R.id.orderDate);
        orderTime = findViewById(R.id.orderTime);
        amountPaid = findViewById(R.id.amountPaid);
        orderStatus = findViewById(R.id.orderStatus);
        itemsRecycler = findViewById(R.id.itemsBought);
        downloadReceipt = findViewById(R.id.downloadReceipt);

        Intent getIntents = getIntent();
        orderNoIn = getIntents.getStringExtra(ORDER_NUM);
        mpesaCodeIn = getIntents.getStringExtra(MPESA_CODE);
        orderDateIn = getIntents.getStringExtra(ORDER_DATE);
        orderTimeIn = getIntents.getStringExtra(ORDER_TIME);
        amountPaidIn = getIntents.getStringExtra(TOTAL_PRICE);
        orderStatusIn = getIntents.getStringExtra(ORDER_STATUS);


        orderNo.setText(orderNoIn);
        mpesaCode.setText(mpesaCodeIn);
        orderDate.setText(orderDateIn);
        orderTime.setText(orderTimeIn);
        amountPaid.setText(amountPaidIn);
        orderStatus.setText(orderStatusIn);

        if (!orderStatusIn.equals("approved")){
            downloadReceipt.setVisibility(View.GONE);
        }

        listItems();
    }

    private void listItems() {
        itemsRecycler.setHasFixedSize(true);
        itemsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_FETCH_ORDER_DETAILS, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject orderItems = jsonArray.getJSONObject(i);

                        String name = orderItems.getString("name");
                        String color = orderItems.getString("color");
                        String price = orderItems.getString("price");
                        String category = orderItems.getString("category");
                        String size = orderItems.getString("size");
                        String quantity = orderItems.getString("quantity");

                        myItems.add(new OrderItems(name, color, price, category, size, quantity));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    orderDetailsAdapter = new OrderDetailsAdapter(this, myItems);
                    itemsRecycler.setAdapter(orderDetailsAdapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> Toast.makeText(this, "Error 500", Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_num", orderNoIn);

                return params;
            }

        };

        requestQueue.add(stringRequest);
    }
}