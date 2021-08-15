package com.mosericko.aflewo.eventsmanager.adapters;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AuditionsAdapter extends RecyclerView.Adapter<AuditionsAdapter.AuditionsVH> {

    Context context;
    ArrayList<Events> auditionList;
    OnClickInterface onClickInterface;

    public AuditionsAdapter(Context context, ArrayList<Events> auditionList) {
        this.context = context;
        this.auditionList = auditionList;
    }

    public void setOnClickInterface (OnClickInterface onClickInterface){
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @NotNull
    @Override
    public AuditionsVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.audition_card, parent, false);
        return new AuditionsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AuditionsAdapter.AuditionsVH holder, int position) {
        Events events = auditionList.get(position);
        Glide.with(context)
                .load(events.getEventImage())
                .into(holder.image);
        holder.title.setText(events.getEventName());
        holder.time.setText(events.getStartTime());
        holder.date.setText(events.getEventDate());
        holder.location.setText(events.getEventVenue());

    }

    @Override
    public int getItemCount() {
        return auditionList.size();
    }

    public class AuditionsVH extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, time, location, date;

        public AuditionsVH(@NonNull @NotNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.eventPoster);
            title = itemView.findViewById(R.id.eventTitle);
            time = itemView.findViewById(R.id.event_time);
            date = itemView.findViewById(R.id.event_date);
            location = itemView.findViewById(R.id.location);

            itemView.setOnClickListener(v -> {
                if (onClickInterface != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {

                        onClickInterface.onItemClick(position);
                    }
                }
            });


        }
    }
}
