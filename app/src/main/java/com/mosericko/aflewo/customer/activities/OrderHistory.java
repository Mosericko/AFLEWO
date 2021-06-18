package com.mosericko.aflewo.customer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.adapters.CatItemsAdapter;
import com.mosericko.aflewo.customer.adapters.OrderHistoryAdapter;
import com.mosericko.aflewo.customer.adapters.ProductAdapter;
import com.mosericko.aflewo.customer.classes.CategoryInfo;
import com.mosericko.aflewo.customer.classes.Orders;
import com.mosericko.aflewo.customer.classes.Products;
import com.mosericko.aflewo.customer.fragments.HomeFragment;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.helperclasses.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderHistory extends AppCompatActivity implements OrderHistoryAdapter.OrderClickListener {

    //intent extras
    public static final String ORDER_NUM = "orderNo";
    public static final String MPESA_CODE = "mpesaCode";
    public static final String TOTAL_PRICE = "total_price";
    public static final String ORDER_DATE = "orderDate";
    public static final String ORDER_TIME = "orderTime";
    public static final String ORDER_STATUS = "orderStatus";
    int id;
    RecyclerView ordersHistoryRV;
    OrderHistoryAdapter orderHistoryAdapter;
    ArrayList<Orders> myOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        id = PrefManager.getInstance(this).UserID();
        ordersHistoryRV = findViewById(R.id.ordersRecycler);
        getAllOrders();

    }

    private void getAllOrders() {
        ordersHistoryRV.setHasFixedSize(true);
        ordersHistoryRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FETCH_ORDERS, response -> {
            try {
                JSONArray j = new JSONArray(response);
                for (int i = 0; i < j.length(); i++) {
                    try {
                        JSONObject orders = j.getJSONObject(i);

                        String orderNum = orders.getString("order_num");
                        String mpesa_code = orders.getString("mpesa_code");
                        String orderDate = orders.getString("order_date");
                        String orderTime = orders.getString("order_time");
                        String amountPaid = orders.getString("total_price");
                        String orderStatus = orders.getString("order_status");

                        myOrders.add(new Orders(orderNum, mpesa_code, orderDate, orderTime, amountPaid, orderStatus));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    orderHistoryAdapter = new OrderHistoryAdapter(OrderHistory.this, myOrders);
                    ordersHistoryRV.setAdapter(orderHistoryAdapter);
                    orderHistoryAdapter.setOnItemClickListener(OrderHistory.this);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "Error500", Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(OrderHistory.this,OrderDetails.class);
        Orders clickedOrder = myOrders.get(position);

        intent.putExtra(ORDER_NUM, clickedOrder.getOrderNo());
        intent.putExtra(MPESA_CODE, clickedOrder.getMpesaCode());
        intent.putExtra(TOTAL_PRICE, clickedOrder.getAmountPaid());
        intent.putExtra(ORDER_DATE, clickedOrder.getOrderDate());
        intent.putExtra(ORDER_TIME, clickedOrder.getOrderTime());
        intent.putExtra(ORDER_STATUS, clickedOrder.getOrderStatus());

        startActivity(intent);


    }
}