package com.mosericko.aflewo.customer.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.adapters.CatItemsAdapter;
import com.mosericko.aflewo.customer.adapters.FeedBackAdapter;
import com.mosericko.aflewo.customer.classes.CategoryInfo;
import com.mosericko.aflewo.customer.classes.FeedBackData;
import com.mosericko.aflewo.database.DataBaseHandler;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;
import com.mosericko.aflewo.member.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FeedBack extends AppCompatActivity {

    private static final String TAG = "logcat";
    Button sendMessage;
    TextInputLayout til;
    AutoCompleteTextView recipients;
    EditText messageT;
    Button send;
    TextView feedBackMessage, sendFeedBackBtn;
    LinearLayout bgl;
    int id;
    String userEmail;
    RecyclerView feedBackRV;
    ArrayList<FeedBackData> myFeedBack = new ArrayList<>();
    FeedBackAdapter feedBackAdapter;
    private DataBaseHandler myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        sendMessage = findViewById(R.id.sendMessage);
        feedBackRV = findViewById(R.id.feedbackMessages);
        sendFeedBackBtn = findViewById(R.id.sendFeedBack);
        bgl = findViewById(R.id.backgroundLayout);

        id = PrefManager.getInstance(this).UserID();
        myDb = new DataBaseHandler(this);
        User user = myDb.getUser(id);
        userEmail = user.getEmail();

        sendMessage.setOnClickListener(v -> {
            showFeedBackDialog();
        });
        sendFeedBackBtn.setOnClickListener(v -> {
            showFeedBackDialog();
        });

        getFeedBackData();

    }

    private void getFeedBackData() {
        feedBackRV.setHasFixedSize(true);
        feedBackRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_FEEDBACK, response -> {
            try {
                JSONArray j = new JSONArray(response);
                for (int i = 0; i < j.length(); i++) {
                    try {
                        JSONObject obj = j.getJSONObject(i);

                        /*String sender = obj.getString("id");
                        String receiver= obj.getString("image");*/
                        String title = obj.getString("title");
                        String message = obj.getString("message");
                        String time = obj.getString("messagetime");
                        String date = obj.getString("messagedate");


                        myFeedBack.add(new FeedBackData(title, message, time, date));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    feedBackAdapter = new FeedBackAdapter(this, myFeedBack);
                    feedBackRV.setAdapter(feedBackAdapter);
                }

                if (myFeedBack.size()>0){
                    bgl.setVisibility(View.GONE);
                    sendFeedBackBtn.setVisibility(View.VISIBLE);
                }else{
                    bgl.setVisibility(View.VISIBLE);
                    sendFeedBackBtn.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userEmail", userEmail);

                return params;
            }
        };
        requestQueue.add(stringRequest);



    }


    private void showFeedBackDialog() {
        AlertDialog.Builder feedBackDialog = new AlertDialog.Builder(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.feedback_design, null);

        feedBackDialog.setView(view);


        til = view.findViewById(R.id.exposedTil);
        recipients = view.findViewById(R.id.recipients);
        messageT = view.findViewById(R.id.messageTitle);
        send = view.findViewById(R.id.send);
        feedBackMessage = view.findViewById(R.id.feedbackData);

        ArrayList<String> admins = new ArrayList<>();
        admins.add("Product Manager");
        admins.add("Finance Manager");

        ArrayAdapter<String> adminAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.feedback_menu_design, admins);
        recipients.setAdapter(adminAdapter);


        //create dialog
        AlertDialog alertDialog = feedBackDialog.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        send.setOnClickListener(v -> {
            sendFeedBack();

        });
    }

    private void sendFeedBack() {
        String receiver, title, message, messageTime, messageDate, sender;

        sender = userEmail;
        receiver = recipients.getText().toString();
        title = messageT.getText().toString().trim();
        message = feedBackMessage.getText().toString().trim();
        messageTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        messageDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        //Validations
        if (TextUtils.isEmpty(title)) {
            messageT.setError("Cannot be Blank!");
            messageT.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(message)) {
            feedBackMessage.setError("Cannot be Blank!");
            feedBackMessage.requestFocus();
            return;
        }

        FeedBackAsync sendToDB = new FeedBackAsync(receiver, title, message, messageTime, messageDate, sender);
        sendToDB.execute();

    }

    public class FeedBackAsync extends AsyncTask<Void, Void, String> {
        String receiver, title, message, messageTime, messageDate, sender;

        public FeedBackAsync(String receiver, String title, String message, String messageTime, String messageDate, String sender) {
            this.receiver = receiver;
            this.title = title;
            this.message = message;
            this.messageTime = messageTime;
            this.messageDate = messageDate;
            this.sender = sender;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("receiver", receiver);
            params.put("title", title);
            params.put("message", message);
            params.put("messageTime", messageTime);
            params.put("messageDate", messageDate);
            params.put("sender", sender);

            return requestHandler.sendPostRequest(URLs.SEND_FEEDBACK, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject getResponse = new JSONObject(s);

                if (!getResponse.getBoolean("error")) {
                    Toast.makeText(FeedBack.this, getResponse.getString("message"), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), FeedBack.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getResponse.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}



























