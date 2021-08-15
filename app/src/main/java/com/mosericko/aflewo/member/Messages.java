package com.mosericko.aflewo.member;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.classes.FeedBackData;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.member.adapters.FeedBackA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Messages extends AppCompatActivity {

    RecyclerView feedBackRV;
    ArrayList<FeedBackData> feedBackDataArrayList = new ArrayList<>();
    FeedBackA feedBackAdapter;
    int id = PrefManager.getInstance(this).UserID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        feedBackRV = findViewById(R.id.feedBackRv);

        getFeedBackData();
    }

    private void getFeedBackData() {

        feedBackRV.setHasFixedSize(true);
        feedBackRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://android.officialm-devs.com/aflewoapp/SignActivity/Operations.php?signactivitycall=fetchNotifs", response -> {
            try {
                JSONArray j = new JSONArray(response);
                for (int i = 0; i < j.length(); i++) {
                    try {
                        JSONObject obj = j.getJSONObject(i);

                        String title = obj.getString("title");
                        String message = obj.getString("message");
                        String date = obj.getString("date");
                        //String reply = obj.getString("reply");


                        feedBackDataArrayList.add(new FeedBackData(title, message, date));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    feedBackAdapter = new FeedBackA(this, feedBackDataArrayList);
                    feedBackRV.setAdapter(feedBackAdapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(id));

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}