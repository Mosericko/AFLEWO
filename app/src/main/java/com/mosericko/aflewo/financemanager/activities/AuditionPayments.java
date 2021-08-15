package com.mosericko.aflewo.financemanager.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.financemanager.adapters.AuPaymentsAdapter;
import com.mosericko.aflewo.financemanager.classes.PaymentDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AuditionPayments extends AppCompatActivity {

    RecyclerView auPayments;
    ArrayList<PaymentDetails> paymentList = new ArrayList<>();
    AuPaymentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audition_payments);
        auPayments = findViewById(R.id.auPaymentsRV);

        listPayments();
    }

    private void listPayments() {
        auPayments.setHasFixedSize(true);
        auPayments.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://android.officialm-devs.com/aflewoapp/SignActivity/Operations.php?signactivitycall=fetchAuditionPayments", null, response -> {

            try {
                for (int i = 0; i < response.length(); i++) {

                    JSONObject details = response.getJSONObject(i);

                    String id = details.getString("id");
                    String user_id = details.getString("user_id");
                    String amount = details.getString("amount");
                    String mpesa_code = details.getString("mpesa_code");
                    String date = details.getString("date");
                    String status = details.getString("status");
                    String firstName = details.getString("firstname");
                    String lastName = details.getString("lastname");


                    paymentList.add(new PaymentDetails(id, user_id, amount, mpesa_code, date, status, firstName, lastName));
                }

                adapter = new AuPaymentsAdapter(AuditionPayments.this, paymentList);
                auPayments.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        requestQueue.add(jsonArrayRequest);
    }
}