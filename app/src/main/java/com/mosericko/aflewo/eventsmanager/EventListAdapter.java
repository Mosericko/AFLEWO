package com.mosericko.aflewo.eventsmanager;

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

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    private ArrayList<Events> eventList;
    private Context context;

    public EventListAdapter(ArrayList<Events> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.event_cardview,parent,false);
        return new EventViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Events eventsHelper=eventList.get(position);
        Glide.with(context)
                .load(eventsHelper.getEventImage())
                .into(holder.eventImage);
        holder.eventName.setText(eventsHelper.getEventName());
        holder.eventDate.setText(eventsHelper.getEventDate());
        holder.startTime.setText(eventsHelper.getStartTime());
        holder.endTime.setText(eventsHelper.getEndTime());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{

        //hooks
        TextView eventName,eventDate,startTime,endTime;
        ImageView eventImage;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName=itemView.findViewById(R.id.event_name);
            eventDate=itemView.findViewById(R.id.event_Date);
            startTime=itemView.findViewById(R.id.start_time);
            endTime=itemView.findViewById(R.id.end_time);
            eventImage=itemView.findViewById(R.id.event_image);

        }
    }
}
