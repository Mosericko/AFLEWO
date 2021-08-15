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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.eventsmanager.Events;
import com.mosericko.aflewo.eventsmanager.adapters.AuditionsAdapter;
import com.mosericko.aflewo.helperclasses.URLs;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashBoardFragment extends Fragment {

    RecyclerView eventsRecycler;
    ArrayList<Events> eventsArrayList = new ArrayList<>();
    AuditionsAdapter eventListAdapter;
    Context context;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();
        eventsRecycler = view.findViewById(R.id.auditionRecyclerView);
        listedEvents();
    }

    private void listedEvents() {
        eventsRecycler.setHasFixedSize(true);
        eventsRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        RequestQueue requestQueue = Volley.newRequestQueue(context);

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

                        eventListAdapter = new AuditionsAdapter(context, eventsArrayList);
                        eventsRecycler.setAdapter(eventListAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        requestQueue.add(jsonArrayRequest);
    }

}
