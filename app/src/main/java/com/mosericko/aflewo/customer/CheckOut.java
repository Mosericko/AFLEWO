package com.mosericko.aflewo.customer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.adapters.GoodsListAdapter;
import com.mosericko.aflewo.database.DataBaseHandler;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;
import com.mosericko.aflewo.loginregistration.MoreDetails;
import com.mosericko.aflewo.loginregistration.UserLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import static com.mosericko.aflewo.customer.fragments.CategoriesFrag.EXTRA_CATEGORY;

public class CheckOut extends AppCompatActivity {
    private static final String TAG = "log value";
    RecyclerView goodsRV;
    DataBaseHandler myDb;
    GoodsListAdapter goodsListAdapter;
    ArrayList<CartDetails> goodsArray = new ArrayList<>();
    TextView totalAmount, completeOrder;
    EditText mpesaCode;
    TextInputLayout mcode;
    String total;
    LinearLayout addMore;
    Button placeOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        goodsRV = findViewById(R.id.goodsList);
        totalAmount = findViewById(R.id.totalPrice);
        addMore = findViewById(R.id.addMoreItems);
        placeOrder = findViewById(R.id.placeOrder);
        addMore.setOnClickListener(v -> {
            startActivity(new Intent(CheckOut.this, Index.class));
            finish();
        });

        placeOrder.setOnClickListener(v -> {
            showMpesaDialog();
        });

        Intent intent = getIntent();
        total = intent.getStringExtra("Price");

        totalAmount.setText(total);

        listGoods();

    }

    private void showMpesaDialog() {
        AlertDialog.Builder mpesaDialog = new AlertDialog.Builder(this);
        // Inflate the layout created
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.place_order, null);

        // display the inflated layout
        mpesaDialog.setView(view);

        //connect the views
        mpesaCode = view.findViewById(R.id.codeMpesa);
        completeOrder = view.findViewById(R.id.completeOrder);

        AlertDialog alertDialog = mpesaDialog.create();
        alertDialog.show();

        completeOrder.setOnClickListener(v -> {
            //validations();
            sendOrderToDb();
        });


    }

    private void listGoods() {
        myDb = new DataBaseHandler(this);
        goodsRV.setHasFixedSize(true);
        goodsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        goodsArray.addAll(myDb.getCartDetails());
        goodsListAdapter = new GoodsListAdapter(CheckOut.this, goodsArray);
        goodsRV.setAdapter(goodsListAdapter);

    }

    private void sendOrderToDb() {
        //variable declarations
        String json;
        String currentDate, currentTime;
        String mpesa_code;
        String user_id;
        String orderNumber;
        Gson gson = new Gson();

        //get Json array Format of Checkout items
        ArrayList<CartDetails> totalCheckOut = myDb.getCartDetails();
        json = gson.toJson(totalCheckOut);
        Log.d(TAG, "sendOrderToDb: " + json);

        //Variables Values
        mpesa_code = mpesaCode.getText().toString().trim();
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        user_id = String.valueOf(PrefManager.getInstance(this).UserID());
        orderNumber = setOrderNumber();

        //validations
        if (mpesa_code.isEmpty()) {
            mpesaCode.setError("Cannot be Blank!");
            return;
        }
        if (mpesa_code.length() < 10) {
            mpesaCode.setError("Cannot be less than 10");
            return;
        }


        OrderAsync orderAsync = new OrderAsync(json, currentDate, currentTime, mpesa_code, user_id, orderNumber, total);
        orderAsync.execute();


    }

    private String setOrderNumber() {
        char[] myChars = "1234567890".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            char c1 = myChars[random.nextInt(myChars.length)];
            stringBuilder.append(c1);
        }

        String randomString = stringBuilder.toString();

        return ("ON-" + randomString);
    }

    public class OrderAsync extends AsyncTask<Void, Void, String> {
        String json;
        String currentDate, currentTime;
        String mpesa_code;
        String user_id;
        String orderNumber;
        String totalPrice;

        public OrderAsync(String json, String currentDate, String currentTime, String mpesa_code, String user_id, String orderNumber, String totalPrice) {
            this.json = json;
            this.currentDate = currentDate;
            this.currentTime = currentTime;
            this.mpesa_code = mpesa_code;
            this.user_id = user_id;
            this.orderNumber = orderNumber;
            this.totalPrice = totalPrice;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("json", json);
            params.put("order_num", orderNumber);
            params.put("mpesa_code", mpesa_code);
            params.put("user_id", user_id);
            params.put("total_price", totalPrice);
            params.put("current_time", currentTime);
            params.put("current_date", currentDate);

            Log.d(TAG, "doInBackground: " + params);

            return requestHandler.sendPostRequest(URLs.URL_PLACE_ORDER, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                if (!jsonObject.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    myDb.deleteCartItems();

                    startActivity(new Intent(CheckOut.this, OrderMessage.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}




























