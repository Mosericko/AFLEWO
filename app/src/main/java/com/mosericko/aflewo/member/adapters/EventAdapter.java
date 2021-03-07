package com.mosericko.aflewo.member.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.eventsmanager.Events;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventVH> {

    private ArrayList<Events> userEvents;
    private Context context;


    public EventAdapter(ArrayList<Events> userEvents, Context context) {
        this.userEvents = userEvents;
        this.context = context;
    }

    @NonNull
    @Override
    public EventVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.user_event_cardview,viewGroup,false);
        return new EventVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventVH eventVH, int i) {
        Events userEventList=userEvents.get(i);
        Glide.with(context)
                .load(userEventList.getEventImage())
                .into(eventVH.event_poster);

        eventVH.event_name.setText((userEventList.getEventName()));
        eventVH.event_time.setText((userEventList.getStartTime()));
        eventVH.event_location.setText((userEventList.getEventVenue()));

    }

    @Override
    public int getItemCount() {
        return userEvents.size();
    }


    public static class EventVH extends RecyclerView.ViewHolder{

        TextView event_name, event_time, event_location;
        ImageView event_poster;


        public EventVH(@NonNull View itemView) {
            super(itemView);

            event_name= itemView.findViewById(R.id.eventTitle);
            event_time= itemView.findViewById(R.id.event_time);
            event_location= itemView.findViewById(R.id.location);
            event_poster= itemView.findViewById(R.id.eventPoster);
        }
    }
}
