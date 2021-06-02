package com.mosericko.aflewo.eventsmanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.mosericko.aflewo.eventsmanager.adapters.EventListAdapter;
import com.mosericko.aflewo.eventsmanager.Events;
import com.mosericko.aflewo.helperclasses.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventListFragment extends Fragment {

    RecyclerView eventsRecycler;
    ArrayList<Events> eventsArrayList = new ArrayList<>();
    EventListAdapter eventListAdapter;
    private DataBaseHandler myDb;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.eventlist_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();

        eventsRecycler = view.findViewById(R.id.eventRecyclerView);

        listedEvents();


    }

    private void listedEvents() {

        myDb = new DataBaseHandler(context);
        eventsRecycler.setHasFixedSize(true);
        eventsRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_FETCH_EVENTS, null,
                response -> {

                    try {
                        //loop through the event details array
                        for (int i = 0; i < response.length(); i++) {

                            //get the current Json Object
                            JSONObject eventDetails = response.getJSONObject(i);

                            String id = eventDetails.getString("id");
                            String eventName = eventDetails.getString("event_name");
                            String eventVenue = eventDetails.getString("event_venue");
                            String eventTheme = eventDetails.getString("event_theme");
                            String startTime = eventDetails.getString("start_time");
                            String endTime = eventDetails.getString("end_time");
                            String eventDate = eventDetails.getString("event_date");
                            String eventImage = eventDetails.getString("event_image");

                            eventsArrayList.add(new Events(id, eventImage, eventName, eventVenue, eventTheme, eventDate, startTime, endTime));
                            myDb.addEvents(new Events(id, eventImage, eventName, eventVenue, eventTheme, eventDate, startTime, endTime));
                        }

                        eventListAdapter = new EventListAdapter(eventsArrayList, context);
                        eventsRecycler.setAdapter(eventListAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());

        requestQueue.add(jsonArrayRequest);
    }
}
