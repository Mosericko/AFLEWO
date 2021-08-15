package com.mosericko.aflewo.member.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;

import java.util.Random;

public class AuditionDetails extends AppCompatActivity {
    String imageIn, titleIn, startTimeIn, endTimeIn, dateIn, location;
    ImageView imageView;
    TextView title, start, end, date, venue;
    Button bookAudition;
    String participant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audition_details);

        imageView = findViewById(R.id.auPoster);
        title = findViewById(R.id.title);
        start = findViewById(R.id.start_time);
        end = findViewById(R.id.end_time);
        date = findViewById(R.id.date);
        venue = findViewById(R.id.venue);
        bookAudition = findViewById(R.id.bookAudition);

        //get intent extras
        Intent intent = getIntent();
        imageIn = intent.getStringExtra("image");
        titleIn = intent.getStringExtra("title");
        startTimeIn = intent.getStringExtra("startTime");
        endTimeIn = intent.getStringExtra("endTime");
        dateIn = intent.getStringExtra("date");
        location = intent.getStringExtra("location");

        //set extras
        Glide.with(this)
                .load(imageIn)
                .into(imageView);
        title.setText(titleIn);
        start.setText(startTimeIn);
        end.setText(endTimeIn);
        date.setText(dateIn);
        venue.setText(location);
        participant = setTicketNumber();

        bookAudition.setOnClickListener(v -> {
            Intent data = new Intent(AuditionDetails.this, Deposit.class);
            data.putExtra("name",titleIn);
            data.putExtra("uid",participant);
            startActivity(data);
        });


    }

    private String setTicketNumber() {
        char[] myChars = "1234567890".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            char c1 = myChars[random.nextInt(myChars.length)];
            stringBuilder.append(c1);
        }

        String randomString = stringBuilder.toString();
        return ("#ParNo" + randomString);
    }
}