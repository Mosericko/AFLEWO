package com.mosericko.aflewo.customer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mosericko.aflewo.R;

public class ReplyMessage extends AppCompatActivity {

    TextView reply;
    String intentStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_message);

        reply = findViewById(R.id.reply);
        Intent intent = getIntent();
        intentStr = intent.getStringExtra("reply");

        reply.setText(intentStr);

    }
}