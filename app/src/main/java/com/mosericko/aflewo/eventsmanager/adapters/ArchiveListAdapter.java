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

public class ArchiveListAdapter extends RecyclerView.Adapter<ArchiveListAdapter.ArchiveViewHolder> {
    private ArrayList<Events> archiveList;
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



    public ArchiveListAdapter(ArrayList<Events> archiveList, Context context) {
        this.archiveList = archiveList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArchiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.archive_cardview, parent, false);
        return new ArchiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArchiveViewHolder holder, int position) {

        Events eventsHelper = archiveList.get(position);
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
        return archiveList.size();
    }

    public class ArchiveViewHolder extends RecyclerView.ViewHolder {

        LinearLayout edit, unArchive;
        TextView eventName, eventDate, startTime, endTime;
        ImageView eventImage;

        public ArchiveViewHolder(@NonNull View itemView) {
            super(itemView);


            eventName = itemView.findViewById(R.id.archiveName);
            eventDate = itemView.findViewById(R.id.archiveDate);
            startTime = itemView.findViewById(R.id.archiveStartTime);
            endTime = itemView.findViewById(R.id.archiveEndTime);
            eventImage = itemView.findViewById(R.id.archiveImage);
            edit = itemView.findViewById(R.id.archive_edit_event);
            unArchive = itemView.findViewById(R.id.unArchiveEvent);


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendEventIntent();
                }
            });

            unArchive.setOnClickListener(new View.OnClickListener() {
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
                    .setMessage("Are You Sure You Want to Restore Event?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            unArchiveId();

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

        private void unArchiveId() {

            int i;
            String id;
            i = getAdapterPosition();
            Events events = archiveList.get(i);
            id = events.getId();

            UnArchiveAsync unArchiveAsync = new UnArchiveAsync(id);
            unArchiveAsync.execute();

            archiveList.remove(i);
            notifyItemRemoved(i);

        }

        private void sendEventIntent() {
            int position = getAdapterPosition();
            Intent eventIntentData = new Intent(itemView.getContext(), EditEvent.class);
            Events clickedEvent = archiveList.get(position);

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


        class UnArchiveAsync extends AsyncTask<Void, Void, String> {
            String id;

            public UnArchiveAsync(String id) {
                this.id = id;
            }


            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> param = new HashMap<>();
                param.put("id", id);

                return requestHandler.sendPostRequest(URLs.URL_UNARCHIVE_EVENT, param);
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


    }


}





























