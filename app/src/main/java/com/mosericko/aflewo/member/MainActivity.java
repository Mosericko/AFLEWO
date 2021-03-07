package com.mosericko.aflewo.member;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.database.DataBaseHandler;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.eventsmanager.Events;
import com.mosericko.aflewo.helperclasses.URLs;
import com.mosericko.aflewo.member.adapters.EventAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RelativeLayout account;
    TextView firstInitial,secondInitial;
    RecyclerView event_recycler;
    ArrayList<Events> userEventsArrayList = new ArrayList<>();
    EventAdapter eventAdapter;
    String nameOne, nameTwo;
    private int id = PrefManager.getInstance(this).UserID();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        account = findViewById(R.id.accountIcon);
        event_recycler = findViewById(R.id.event_recyclerview);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserProfile.class));
            }
        });

        userEventView();
        accountInitials();
    }

    private void accountInitials() {
        DataBaseHandler myDb = new DataBaseHandler(this);
        User user = myDb.getUser(id);

        nameOne = String.valueOf(user.getFirstname());
        nameTwo = String.valueOf(user.getLastname());

        firstInitial=findViewById(R.id.firstInitial);
        firstInitial.setText(String.valueOf(nameOne.charAt(0)));

        secondInitial=findViewById(R.id.secondInitial);
        secondInitial.setText(String.valueOf(nameTwo.charAt(0)));
    }

    private void userEventView() {
        event_recycler.setHasFixedSize(true);
        event_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_FETCH_EVENTS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            //loop through the event details array
                            for (int i = 0; i < response.length(); i++) {

                                //get the current Json Object
                                JSONObject eventInfo = response.getJSONObject(i);

                                String id = eventInfo.getString("id");
                                String eventImage = eventInfo.getString("event_image");
                                String eventName = eventInfo.getString("event_name");
                                String startTime = eventInfo.getString("start_time");
                                String eventVenue = eventInfo.getString("event_venue");

                                userEventsArrayList.add(new Events(id, eventImage, eventName, startTime, eventVenue));
                            }
                            eventAdapter = new EventAdapter(userEventsArrayList, MainActivity.this);
                            event_recycler.setAdapter(eventAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}