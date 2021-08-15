package com.mosericko.aflewo.eventsmanager.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.eventsmanager.Bookings;
import com.mosericko.aflewo.eventsmanager.adapters.ParticipantsAdapter;
import com.mosericko.aflewo.helperclasses.RequestHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static android.content.Context.DOWNLOAD_SERVICE;

public class ParticipantsList extends Fragment {

    private static final int REQUEST_CODE = 1;
    RecyclerView participants;
    ArrayList<Bookings> participantsL = new ArrayList<>();
    ParticipantsAdapter participantsAdapter;
    Context context;
    TextView downloadReports;
    DownloadManager downloadManager;
    String receiptUrl = "https://android.officialm-devs.com/aflewo/android/participants";
    String reportName;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_participants, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();

        participants = view.findViewById(R.id.participantsRV);

        listParticipants();
        reportName = setReportName();
        ActivityCompat.requestPermissions((Activity) context, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_CODE);

        downloadReports = view.findViewById(R.id.participants);
        downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        downloadReports.setOnClickListener(v -> {
            downloadReceipt();
        });


    }

    private void listParticipants() {
        participants.setHasFixedSize(true);
        participants.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://android.officialm-devs.com/aflewoapp/SignActivity/Operations.php?signactivitycall=fetchBookings", null, response -> {

            try {
                for (int i = 0; i < response.length(); i++) {

                    JSONObject details = response.getJSONObject(i);


                    String user_id = details.getString("user_id");
                    String unique = details.getString("unique_id");
                    String firstName = details.getString("firstname");
                    String lastname = details.getString("lastname");
                    String gender = details.getString("gender");
                    String phone = details.getString("phone");


                    participantsL.add(new Bookings(user_id, firstName, lastname, gender, phone, unique));
                }

                participantsAdapter = new ParticipantsAdapter(context, participantsL);
                participants.setAdapter(participantsAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        requestQueue.add(jsonArrayRequest);
    }

    private void downloadReceipt() {

        ReceiptAsync receiptAsync = new ReceiptAsync(reportName);
        receiptAsync.execute();
    }

    public String setReportName() {

        char[] myChars = "1234567890".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 2; i++) {
            char c1 = myChars[random.nextInt(myChars.length)];
            stringBuilder.append(c1);
        }

        String randomString = stringBuilder.toString();

        return ("ParNo" + randomString);
    }


    public class ReceiptAsync extends AsyncTask<Void, Void, String> {

        String orderNum;

        public ReceiptAsync(String orderNum) {
            this.orderNum = orderNum;
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("reportName", orderNum);

            return requestHandler.sendPostRequest(receiptUrl, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);

                String receiptAddress = obj.getString("url");

                Log.d("TAG", "downloadReceipt: " + receiptAddress);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(receiptAddress));
                String title = URLUtil.guessFileName(receiptAddress, null, null);
                //Set the title of this download, to be displayed in notifications (if enabled).
                request.setTitle(title);
                //Set a description of this download, to be displayed in notifications (if enabled)
                request.setDescription("Receipt download using DownloadManager.");

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                //Set the local destination for the downloaded file to a path within the application's external files directory
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                downloadManager.enqueue(request);
                Toast.makeText(getContext(), "Download Completed", Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
