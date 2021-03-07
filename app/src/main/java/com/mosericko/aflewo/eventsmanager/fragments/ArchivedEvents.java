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
import com.mosericko.aflewo.eventsmanager.adapters.ArchiveListAdapter;
import com.mosericko.aflewo.eventsmanager.Events;
import com.mosericko.aflewo.helperclasses.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArchivedEvents extends Fragment {


    RecyclerView archivedEventsRecycler;
    ArrayList<Events> archivedArrayList = new ArrayList<>();
    ArchiveListAdapter archiveListAdapter;
    private DataBaseHandler myDb;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.events_archived, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();

        archivedEventsRecycler = view.findViewById(R.id.archivedEventsRV);

        listedEvents();


    }

    private void listedEvents() {

        myDb = new DataBaseHandler(context);
        archivedEventsRecycler.setHasFixedSize(true);
        archivedEventsRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLs.FETCH_ARCHIVED_EVENTS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

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

                                archivedArrayList.add(new Events(id, eventImage, eventName, eventVenue, eventTheme, eventDate, startTime, endTime));
                            }

                            archiveListAdapter = new ArchiveListAdapter(archivedArrayList, context);
                            archivedEventsRecycler.setAdapter(archiveListAdapter);

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
