package com.mosericko.aflewo.financemanager.activities;

import android.content.Intent;
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
import com.mosericko.aflewo.customer.adapters.FeedBackAdapter;
import com.mosericko.aflewo.customer.classes.FeedBackData;
import com.mosericko.aflewo.eventsmanager.adapters.OnClickInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminFeedBack extends AppCompatActivity implements OnClickInterface {
    RecyclerView feedBackRV;
    ArrayList<FeedBackData> myFeedBack = new ArrayList<>();
    FeedBackAdapter feedBackAdapter;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feed_back);

        feedBackRV = findViewById(R.id.feedbackMessages);

        Intent intent = getIntent();
        userType = intent.getStringExtra("name");

        getFeedBackData();

    }

    private void getFeedBackData() {
        feedBackRV.setHasFixedSize(true);
        feedBackRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        String url = "http://android.officialm-devs.com/aflewoapp/SignActivity/Operations.php?signactivitycall=getAdminFeedBack";
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONArray j = new JSONArray(response);
                for (int i = 0; i < j.length(); i++) {
                    try {
                        JSONObject obj = j.getJSONObject(i);

                        String id = obj.getString("id");
                        String sender = obj.getString("sender");
                        String title = obj.getString("title");
                        String message = obj.getString("message");
                        String time = obj.getString("messagetime");
                        String date = obj.getString("messagedate");
                        String reply = obj.getString("reply");


                        myFeedBack.add(new FeedBackData(id, sender, title, message, time, date, reply));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    feedBackAdapter = new FeedBackAdapter(this, myFeedBack);
                    feedBackRV.setAdapter(feedBackAdapter);
                    feedBackAdapter.setOnItemClickListener(AdminFeedBack.this);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userType", userType);

                return params;
            }
        };
        requestQueue.add(stringRequest);


    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, Reply.class);
        FeedBackData feedBackData = myFeedBack.get(position);
        intent.putExtra("id", feedBackData.getId());
        intent.putExtra("reply", feedBackData.getReply());
        intent.putExtra("sender", feedBackData.getSender());
        startActivity(intent);
    }
}