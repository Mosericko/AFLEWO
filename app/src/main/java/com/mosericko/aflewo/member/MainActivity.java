package com.mosericko.aflewo.member;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.mosericko.aflewo.eventsmanager.adapters.AuditionsAdapter;
import com.mosericko.aflewo.eventsmanager.adapters.OnClickInterface;
import com.mosericko.aflewo.helperclasses.URLs;
import com.mosericko.aflewo.member.activities.AuditionDetails;
import com.mosericko.aflewo.member.activities.EventInformation;
import com.mosericko.aflewo.member.adapters.EventAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EventAdapter.EventClickListener, OnClickInterface {
    RelativeLayout account;
    TextView firstInitial, secondInitial;
    private final int id = PrefManager.getInstance(this).UserID();
    ArrayList<Events> userEventsArrayList = new ArrayList<>();
    EventAdapter eventAdapter;
    String nameOne, nameTwo;
    RecyclerView event_recycler, auditionRecycler;
    LinearLayout topLayout, auditionLayout,messages;
    ArrayList<Events> eventsArrayList = new ArrayList<>();
    AuditionsAdapter eventListAdapter;
    String status = PrefManager.getInstance(this).status();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        account = findViewById(R.id.accountIcon);
        event_recycler = findViewById(R.id.event_recyclerview);
        auditionRecycler = findViewById(R.id.auditionRecyclerView);
        topLayout = findViewById(R.id.topLayout);
        auditionLayout = findViewById(R.id.auditionLayout);
        messages = findViewById(R.id.messages);

        if (status.equals("1")) {
            topLayout.setVisibility(View.GONE);
        } else {
            auditionLayout.setVisibility(View.GONE);
        }

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserProfile.class));
            }
        });
        messages.setOnClickListener(v -> {
            startActivity(new Intent(this,Messages.class));
        });

        userEventView();
        accountInitials();
        listedEvents();
    }

    private void accountInitials() {
        DataBaseHandler myDb = new DataBaseHandler(this);
        User user = myDb.getUser(id);

        nameOne = String.valueOf(user.getFirstname());
        nameTwo = String.valueOf(user.getLastname());

        firstInitial = findViewById(R.id.firstInitial);
        firstInitial.setText(String.valueOf(nameOne.charAt(0)));

        secondInitial = findViewById(R.id.secondInitial);
        secondInitial.setText(String.valueOf(nameTwo.charAt(0)));
    }

    private void userEventView() {
        event_recycler.setHasFixedSize(true);
        event_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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
                                String event_theme = eventInfo.getString("event_theme");
                                String endTime = eventInfo.getString("end_time");
                                String date = eventInfo.getString("event_date");

                                userEventsArrayList.add(new Events(id, eventImage, eventName, eventVenue, event_theme, date, startTime, endTime));
                            }
                            eventAdapter = new EventAdapter(userEventsArrayList, MainActivity.this);
                            event_recycler.setAdapter(eventAdapter);
                            eventAdapter.setOnItemClickListener(MainActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, Throwable::printStackTrace);

        requestQueue.add(jsonArrayRequest);
    }

    private void listedEvents() {
        auditionRecycler.setHasFixedSize(true);
        auditionRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_FETCH_AUDITION, null,
                response -> {

                    try {
                        //loop through the event details array
                        for (int i = 0; i < response.length(); i++) {

                            //get the current Json Object
                            JSONObject eventDetails = response.getJSONObject(i);

                            String id = eventDetails.getString("id");
                            String eventName = eventDetails.getString("title");
                            String eventVenue = eventDetails.getString("venue");
                            String startTime = eventDetails.getString("start_time");
                            String endTime = eventDetails.getString("end_time");
                            String eventDate = eventDetails.getString("date");
                            String eventImage = eventDetails.getString("poster");

                            eventsArrayList.add(new Events(id, eventImage, eventName, eventVenue, eventDate, startTime, endTime));

                        }

                        eventListAdapter = new AuditionsAdapter(MainActivity.this, eventsArrayList);
                        auditionRecycler.setAdapter(eventListAdapter);
                        eventListAdapter.setOnClickInterface(MainActivity.this);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onEventClick(int position) {
        Intent intent = new Intent(MainActivity.this, EventInformation.class);
        Events events = userEventsArrayList.get(position);
        intent.putExtra("image", events.getEventImage());
        intent.putExtra("title", events.getEventName());
        intent.putExtra("theme", events.getEventTheme());
        intent.putExtra("startTime", events.getStartTime());
        intent.putExtra("endTime", events.getEndTime());
        intent.putExtra("date", events.getEventDate());
        intent.putExtra("location", events.getEventVenue());
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        //see more details about the upcoming audition
        Intent intent = new Intent(MainActivity.this, AuditionDetails.class);
        Events events = eventsArrayList.get(position);
        intent.putExtra("image", events.getEventImage());
        intent.putExtra("title", events.getEventName());
        intent.putExtra("startTime", events.getStartTime());
        intent.putExtra("endTime", events.getEndTime());
        intent.putExtra("date", events.getEventDate());
        intent.putExtra("location", events.getEventVenue());

        startActivity(intent);


    }
}