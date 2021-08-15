package com.mosericko.aflewo.member.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.database.DataBaseHandler;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;
import com.mosericko.aflewo.member.MainActivity;
import com.mosericko.aflewo.member.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Deposit extends AppCompatActivity {
    DataBaseHandler myDb = new DataBaseHandler(this);
    EditText mpesaCode, amount;
    String firstName, lastName, gender, phoneNumber, mpesa_code, idStr, currentDate;
    String notification;
    Button confirmBooking;
    String auName, puid;
    int id = PrefManager.getInstance(this).UserID();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        mpesaCode = findViewById(R.id.mpesaCode);
        amount = findViewById(R.id.amount);
        confirmBooking = findViewById(R.id.confirmB);

        Intent intent = getIntent();
        auName = intent.getStringExtra("name");
        puid = intent.getStringExtra("uid");
        Log.d("TAG", "onCreate: " + puid);
        Log.d("TAG", "onCreate: " + auName);

        confirmBooking.setOnClickListener(v -> {

            sendToDb();
        });


    }

    private void sendToDb() {
        //get user details
        User user = myDb.getUser(id);

        idStr = String.valueOf(id);
        firstName = user.getFirstname();
        lastName = user.getLastname();
        gender = user.getGender();
        phoneNumber = user.getPhonenumber();
        mpesa_code = mpesaCode.getText().toString().trim();
        String amountStr = amount.getText().toString().trim();
        String notifTitle = "Down Payment";
        notification = "Your Payment of " + amountStr + " and Transaction id " + mpesa_code + " Has Been Received.\n" +
                "Please await confirmation before proceeding to auditions";
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        //validate editText
        if (amountStr.isEmpty()) {
            amount.setError("Cannot be Blank!");
            return;
        }
        if (mpesa_code.isEmpty()) {
            mpesaCode.setError("Cannot be Blank!");
            return;
        }
        if (mpesa_code.length() < 10) {
            mpesaCode.setError("Cannot be less than 10");
            return;
        }

        //send async

        BookAudition bookAudition = new BookAudition(idStr, firstName, lastName, gender, phoneNumber, mpesa_code, notifTitle, notification, currentDate, amountStr, auName, puid);
        bookAudition.execute();


    }

    public class BookAudition extends AsyncTask<Void, Void, String> {
        String idStr, firstName, lastName, gender, phoneNumber, mpesa_code, notifTitle, notification, currentDate, amount, audition_name, parUid;

        public BookAudition(String idStr, String firstName, String lastName, String gender, String phoneNumber, String mpesa_code,
                            String notifTitle, String notification, String currentDate, String amount, String audition_name, String parUid) {
            this.idStr = idStr;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.phoneNumber = phoneNumber;
            this.mpesa_code = mpesa_code;
            this.notifTitle = notifTitle;
            this.notification = notification;
            this.currentDate = currentDate;
            this.amount = amount;
            this.audition_name = audition_name;
            this.parUid = parUid;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("id", idStr);
            params.put("firstName", firstName);
            params.put("lastName", lastName);
            params.put("gender", gender);
            params.put("phone", phoneNumber);
            params.put("mpesa", mpesa_code);
            params.put("notifTitle", notifTitle);
            params.put("notif", notification);
            params.put("date", currentDate);
            params.put("amount", amount);
            params.put("audition_name", audition_name);
            params.put("parUid", parUid);
            return requestHandler.sendPostRequest(URLs.BOOK, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Deposit.this, MainActivity.class));
                    finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}