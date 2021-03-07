package com.mosericko.aflewo.eventsmanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.eventsmanager.fragments.AddEventFragment;
import com.mosericko.aflewo.eventsmanager.fragments.ArchivedEvents;
import com.mosericko.aflewo.eventsmanager.fragments.EventListFragment;
import com.mosericko.aflewo.eventsmanager.fragments.EventProfileFragment;

public class EventsManager extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_events_manager);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        FragmentTransaction fT=getSupportFragmentManager().beginTransaction();

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventListFragment()).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.dashBoard:
                            selectedFragment = new EventListFragment();
                            break;

                        case R.id.addEvents:
                            selectedFragment = new AddEventFragment();
                            break;

                        case R.id.profile:
                            selectedFragment = new EventProfileFragment();
                            break;

                        case R.id.archivedEvent:
                            selectedFragment=new ArchivedEvents();
                            break;

                    }

                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    /*@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_events_manager);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventListFragment()).commit();


    }*/
}

