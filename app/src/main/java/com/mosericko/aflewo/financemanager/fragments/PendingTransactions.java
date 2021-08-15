package com.mosericko.aflewo.financemanager.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.classes.Orders;
import com.mosericko.aflewo.financemanager.activities.AuditionPayments;
import com.mosericko.aflewo.financemanager.activities.FinanceOrderDetails;
import com.mosericko.aflewo.financemanager.adapters.AllOrdersAdapter;
import com.mosericko.aflewo.helperclasses.URLs;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PendingTransactions extends Fragment implements AllOrdersAdapter.OrderClickListener {
    //intent extras
    public static final String ORDER_ID = "orderId";
    public static final String ORDER_NUM = "orderNo";
    public static final String MPESA_CODE = "mpesaCode";
    public static final String CUST_ID = "custId";
    public static final String TOTAL_PRICE = "total_price";
    public static final String ORDER_DATE = "orderDate";
    public static final String ORDER_TIME = "orderTime";
    public static final String ORDER_STATUS = "orderStatus";
    RecyclerView pendingT;
    Context context;
    ArrayList<Orders> pendingOrders = new ArrayList<>();
    AllOrdersAdapter rAdapter;

    LinearLayout auPayments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending_transactions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();

        pendingT = view.findViewById(R.id.pendingTRecycler);
        auPayments = view.findViewById(R.id.auPayments);

        auPayments.setOnClickListener(v -> {
            startActivity(new Intent(context, AuditionPayments.class));
        });

        displayPendingTransactions();
    }

    private void displayPendingTransactions() {
        pendingT.setHasFixedSize(true);
        pendingT.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        //fetch the items from the database using volley
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_FETCH_ALL_ORDERS, null, response -> {
            try {
                //loop through the event details array
                for (int i = 0; i < response.length(); i++) {

                    //get the current Json Object
                    JSONObject ordersPending = response.getJSONObject(i);

                    String orderId = ordersPending.getString("order_id");
                    String custId = ordersPending.getString("cust_id");
                    String orderNum = ordersPending.getString("order_num");
                    String mpesa_code = ordersPending.getString("mpesa_code");
                    String orderDate = ordersPending.getString("order_date");
                    String orderTime = ordersPending.getString("order_time");
                    String amountPaid = ordersPending.getString("total_price");
                    String orderStatus = ordersPending.getString("order_status");

                    pendingOrders.add(new Orders(orderId, orderNum, mpesa_code, custId, orderDate, orderTime, amountPaid, orderStatus));
                }

                rAdapter = new AllOrdersAdapter(context, pendingOrders);
                pendingT.setAdapter(rAdapter);
                rAdapter.setOnItemClickListener(PendingTransactions.this);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(context, FinanceOrderDetails.class);
        Orders clickedOrder = pendingOrders.get(position);

        intent.putExtra(ORDER_ID, clickedOrder.getOrderId());
        intent.putExtra(ORDER_NUM, clickedOrder.getOrderNo());
        intent.putExtra(MPESA_CODE, clickedOrder.getMpesaCode());
        intent.putExtra(CUST_ID, clickedOrder.getCust_id());
        intent.putExtra(TOTAL_PRICE, clickedOrder.getAmountPaid());
        intent.putExtra(ORDER_DATE, clickedOrder.getOrderDate());
        intent.putExtra(ORDER_TIME, clickedOrder.getOrderTime());
        intent.putExtra(ORDER_STATUS, clickedOrder.getOrderStatus());

        startActivity(intent);
    }
}
