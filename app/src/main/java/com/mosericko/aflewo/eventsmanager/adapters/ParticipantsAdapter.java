package com.mosericko.aflewo.eventsmanager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.eventsmanager.Bookings;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ParticipantsVH> {

    Context context;
    ArrayList<Bookings> bookingsArrayList;

    String notification = "Congratulations! You have been accepted as a Member in Aflewo\n" +
            "Thank You For Participating";
    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    String notifTitle = "Approved";

    String notifMessage = "We regret to inform you that you did not qualify as a Member in Aflewo\n" +
            "Thank You For Participating";
    String title = "Audition Failed";

    public ParticipantsAdapter(Context context, ArrayList<Bookings> bookingsArrayList) {
        this.context = context;
        this.bookingsArrayList = bookingsArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ParticipantsVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.booking_card, parent, false);
        return new ParticipantsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ParticipantsAdapter.ParticipantsVH holder, int position) {
        Bookings bookings = bookingsArrayList.get(position);
        holder.firstName.setText(bookings.getFirstName());
        holder.lastName.setText(bookings.getLastName());
        holder.gender.setText(bookings.getGender());
        holder.phone.setText(bookings.getPhone());
        holder.uniqueId.setText(bookings.getUniqueId());

    }

    @Override
    public int getItemCount() {
        return bookingsArrayList.size();
    }

    public class ParticipantsVH extends RecyclerView.ViewHolder {
        TextView uniqueId, firstName, lastName, gender, phone;

        public ParticipantsVH(@NonNull @NotNull View itemView) {
            super(itemView);


            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            gender = itemView.findViewById(R.id.gender);
            phone = itemView.findViewById(R.id.phone);
            uniqueId = itemView.findViewById(R.id.pUid);

            itemView.setOnClickListener(v -> openDialog());
        }

        private void openDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Caution")
                    .setMessage(" Approve Participant?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        approveId();
                    })
                    .setNegativeButton("NO", (dialog, which) -> {
                        notApproved();
                    });
            builder.show();
        }

        private void notApproved() {

            int i;
            String id;
            i = getAdapterPosition();
            Bookings booking = bookingsArrayList.get(i);
            id = booking.getUser_id();

            NotApp notApp = new NotApp(id,title,notifMessage,currentDate);
            notApp.execute();

            bookingsArrayList.remove(i);
            notifyItemRemoved(i);
        }

        private void approveId() {
            int i;
            String id;
            i = getAdapterPosition();
            Bookings booking = bookingsArrayList.get(i);
            id = booking.getUser_id();

            ApproveAsync approve = new ApproveAsync(id,notifTitle,notification,currentDate);
            approve.execute();

            bookingsArrayList.remove(i);
            notifyItemRemoved(i);

        }

        class ApproveAsync extends AsyncTask<Void, Void, String> {
            String id, title, message, date;

            public ApproveAsync(String id, String title, String message, String date) {
                this.id = id;
                this.title = title;
                this.message = message;
                this.date = date;
            }


            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> param = new HashMap<>();
                param.put("user_id", id);
                param.put("notif_title", notifTitle);
                param.put("notif_message", notification);
                param.put("date", currentDate);

                return requestHandler.sendPostRequest(URLs.APPROVE_AUD, param);
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

        class NotApp extends AsyncTask<Void, Void, String> {
            String id, title, message, date;

            public NotApp(String id, String title, String message, String date) {
                this.id = id;
                this.title = title;
                this.message = message;
                this.date = date;
            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> param = new HashMap<>();
                param.put("user_id", id);
                param.put("notif_title", title);
                param.put("notif_message", notifMessage);
                param.put("date", currentDate);

                return requestHandler.sendPostRequest(URLs.DISAPPROVE, param);
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
