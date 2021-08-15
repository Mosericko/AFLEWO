package com.mosericko.aflewo.member.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;

public class EventInformation extends AppCompatActivity {

    String imageIn, titleIn, startTimeIn, endTimeIn, dateIn, location, themeIn;
    ImageView imageView;
    TextView title, start, end, date, venue, theme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information);

        imageView = findViewById(R.id.auPoster);
        title = findViewById(R.id.title);
        start = findViewById(R.id.start_time);
        end = findViewById(R.id.end_time);
        date = findViewById(R.id.date);
        venue = findViewById(R.id.venue);
        theme = findViewById(R.id.theme);

        //get intent extras
        Intent intent = getIntent();
        imageIn = intent.getStringExtra("image");
        titleIn = intent.getStringExtra("title");
        startTimeIn = intent.getStringExtra("startTime");
        endTimeIn = intent.getStringExtra("endTime");
        dateIn = intent.getStringExtra("date");
        location = intent.getStringExtra("location");
        themeIn = intent.getStringExtra("theme");

        //set extras
        Glide.with(this)
                .load(imageIn)
                .into(imageView);
        title.setText(titleIn);
        start.setText(startTimeIn);
        end.setText(endTimeIn);
        date.setText(dateIn);
        venue.setText(location);
        theme.setText(themeIn);
    }
}