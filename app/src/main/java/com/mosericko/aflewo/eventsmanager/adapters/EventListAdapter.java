package com.mosericko.aflewo.eventsmanager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.eventsmanager.EditEvent;
import com.mosericko.aflewo.eventsmanager.Events;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    private ArrayList<Events> eventList;
    private Context context;

    //variables for intent
    public static final String EXTRA_ID = "eventId";
    public static final String EXTRA_IMAGE = "imageUrl";
    public static final String EXTRA_NAME = "eventName";
    public static final String EXTRA_VENUE = "eventVenue";
    public static final String EXTRA_THEME = "eventTheme";
    public static final String EXTRA_START = "startTime";
    public static final String EXTRA_END = "endTime";
    public static final String EXTRA_DATE = "eventDate";


    public EventListAdapter(ArrayList<Events> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.event_cardview, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Events eventsHelper = eventList.get(position);
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

    public class EventViewHolder extends RecyclerView.ViewHolder {

        //hooks
        LinearLayout edit, archive;
        TextView eventName, eventDate, startTime, endTime;
        ImageView eventImage;

        public EventViewHolder(@NonNull final View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.event_name);
            eventDate = itemView.findViewById(R.id.event_Date);
            startTime = itemView.findViewById(R.id.start_time);
            endTime = itemView.findViewById(R.id.end_time);
            eventImage = itemView.findViewById(R.id.event_image);
            edit = itemView.findViewById(R.id.edit_event);
            archive = itemView.findViewById(R.id.archive_event);


            //implementing onclick listeners on the two
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendEventIntent();
                }
            });

            archive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog();
                }
            });

        }


        private void openDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Caution")
                    .setIcon(R.drawable.archive_svg)
                    .setMessage("Are You Sure You Want to Archive?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            archiveId();

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(itemView.getContext(), "Canceled!!", Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.show();
        }

        private void archiveId() {
            int i;
            String id;
            i = getAdapterPosition();
            Events events = eventList.get(i);
            id = events.getId();

            //execute async task
            ArchiveAsync archiveAsync = new ArchiveAsync(id);
            archiveAsync.execute();

            eventList.remove(i);
            notifyItemRemoved(i);
//            notifyItemRangeRemoved(i,);




        }

        class ArchiveAsync extends AsyncTask<Void, Void, String> {
            String id;

            public ArchiveAsync(String id) {
                this.id = id;
            }


            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> param = new HashMap<>();
                param.put("id", id);

                return requestHandler.sendPostRequest(URLs.URL_ARCHIVE_EVENT, param);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {

                    JSONObject obj = new JSONObject(s);

                    Toast.makeText(itemView.getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        private void sendEventIntent() {
            int position = getAdapterPosition();
            Intent eventIntentData = new Intent(itemView.getContext(), EditEvent.class);
            Events clickedEvent = eventList.get(position);

            eventIntentData.putExtra(EXTRA_ID, clickedEvent.getId());
            eventIntentData.putExtra(EXTRA_IMAGE, clickedEvent.getEventImage());
            eventIntentData.putExtra(EXTRA_NAME, clickedEvent.getEventName());
            eventIntentData.putExtra(EXTRA_VENUE, clickedEvent.getEventVenue());
            eventIntentData.putExtra(EXTRA_THEME, clickedEvent.getEventTheme());
            eventIntentData.putExtra(EXTRA_START, clickedEvent.getStartTime());
            eventIntentData.putExtra(EXTRA_END, clickedEvent.getEndTime());
            eventIntentData.putExtra(EXTRA_DATE, clickedEvent.getEventDate());


            context.startActivity(eventIntentData);
        }
    }
}
