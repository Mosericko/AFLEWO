package com.mosericko.aflewo.financemanager.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.financemanager.FinanceManager;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.productmanager.ProductManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Reply extends AppCompatActivity {
    EditText message;
    TextView senderT;
    Button sendReply;
    String id, rep, sender;
    String userT = PrefManager.getInstance(this).UserType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        message = findViewById(R.id.replyData);
        sendReply = findViewById(R.id.sendReply);
        senderT = findViewById(R.id.gmail);

        Intent intent = getIntent();
        rep = intent.getStringExtra("reply");
        id = intent.getStringExtra("id");
        sender = intent.getStringExtra("sender");

        message.setText(rep);

        sendReply.setOnClickListener(v -> {
            sendFeedBackReply();
        });

        senderT.setText(sender);

    }

    private void sendFeedBackReply() {
        String data = message.getText().toString().trim();

        SendAsync sendAsync = new SendAsync(data, id);
        sendAsync.execute();


    }

    public class SendAsync extends AsyncTask<Void, Void, String> {

        String data, id;

        public SendAsync(String data, String id) {
            this.data = data;
            this.id = id;
        }


        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("reply", data);
            params.put("id", id);

            return requestHandler.sendPostRequest("http://android.officialm-devs.com/aflewoapp/SignActivity/Operations.php?signactivitycall=reply", params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("error")) {
                    switch (userT) {
                        case "3":
                            Toast.makeText(Reply.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Reply.this, FinanceManager.class));
                            finish();
                        case "5":
                            Toast.makeText(Reply.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Reply.this, ProductManager.class));
                            finish();
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}