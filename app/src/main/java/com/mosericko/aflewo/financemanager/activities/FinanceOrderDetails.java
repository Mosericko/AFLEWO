package com.mosericko.aflewo.financemanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.adapters.OrderDetailsAdapter;
import com.mosericko.aflewo.customer.classes.CartDetails;
import com.mosericko.aflewo.customer.classes.OrderItems;
import com.mosericko.aflewo.financemanager.FinanceManager;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mosericko.aflewo.financemanager.fragments.PendingTransactions.CUST_ID;
import static com.mosericko.aflewo.financemanager.fragments.PendingTransactions.MPESA_CODE;
import static com.mosericko.aflewo.financemanager.fragments.PendingTransactions.ORDER_DATE;
import static com.mosericko.aflewo.financemanager.fragments.PendingTransactions.ORDER_ID;
import static com.mosericko.aflewo.financemanager.fragments.PendingTransactions.ORDER_NUM;
import static com.mosericko.aflewo.financemanager.fragments.PendingTransactions.ORDER_STATUS;
import static com.mosericko.aflewo.financemanager.fragments.PendingTransactions.ORDER_TIME;
import static com.mosericko.aflewo.financemanager.fragments.PendingTransactions.TOTAL_PRICE;


public class FinanceOrderDetails extends AppCompatActivity {

    private static final String TAG = "json";
    RecyclerView itemsRecycler;
    ArrayList<OrderItems> myItems = new ArrayList<>();
    OrderDetailsAdapter orderDetailsAdapter;
    TextView orderNo, mpesaCode, orderDate, orderTime, amountPaid, orderStatus;
    String orderNoIn, mpesaCodeIn, orderDateIn, orderTimeIn, amountPaidIn, orderStatusIn, orderId, custId;
    String name, color, price, category, size, stock, quantity;
    LinearLayout approve_order;
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_order_details);

        orderNo = findViewById(R.id.orderNo);
        mpesaCode = findViewById(R.id.mpesaCode);
        orderDate = findViewById(R.id.orderDate);
        orderTime = findViewById(R.id.orderTime);
        amountPaid = findViewById(R.id.amountPaid);
        orderStatus = findViewById(R.id.orderStatus);
        itemsRecycler = findViewById(R.id.itemsBought);

        Intent getIntents = getIntent();
        orderNoIn = getIntents.getStringExtra(ORDER_NUM);
        mpesaCodeIn = getIntents.getStringExtra(MPESA_CODE);
        orderDateIn = getIntents.getStringExtra(ORDER_DATE);
        orderTimeIn = getIntents.getStringExtra(ORDER_TIME);
        amountPaidIn = getIntents.getStringExtra(TOTAL_PRICE);
        orderStatusIn = getIntents.getStringExtra(ORDER_STATUS);
        orderId = getIntents.getStringExtra(ORDER_ID);
        custId = getIntents.getStringExtra(CUST_ID);

        approve_order = findViewById(R.id.approve_order);

        approve_order.setOnClickListener(v -> {
            approveOrder();

//            Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();
        });

        orderNo.setText(orderNoIn);
        mpesaCode.setText(mpesaCodeIn);
        orderDate.setText(orderDateIn);
        orderTime.setText(orderTimeIn);
        amountPaid.setText(amountPaidIn);
        orderStatus.setText(orderStatusIn);

        listItems();


    }

    private void approveOrder() {

        ApproveAsync approveAsync = new ApproveAsync(orderNoIn);
        approveAsync.execute();


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

                        name = orderItems.getString("name");
                        color = orderItems.getString("color");
                        price = orderItems.getString("price");
                        category = orderItems.getString("category");
                        size = orderItems.getString("size");
                        stock = orderItems.getString("stock");
                        quantity = orderItems.getString("quantity");

                        myItems.add(new OrderItems(name, color, price, category, size, quantity, stock));

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

    public class ApproveAsync extends AsyncTask<Void, Void, String> {

        String orderNum;

        public ApproveAsync(String orderNum) {
            this.orderNum = orderNum;
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("order_num", orderNoIn);

            return requestHandler.sendPostRequest(URLs.URL_APPROVE_ORDERS, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);

                //if no error in response
                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FinanceOrderDetails.this, FinanceManager.class));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}