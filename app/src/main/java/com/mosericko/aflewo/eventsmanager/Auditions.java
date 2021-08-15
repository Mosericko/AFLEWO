package com.mosericko.aflewo.eventsmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.eventsmanager.fragments.DashBoardFragment;
import com.mosericko.aflewo.eventsmanager.fragments.ParticipantsList;
import com.mosericko.aflewo.eventsmanager.fragments.SetAudition;

public class Auditions extends AppCompatActivity {
    private final NavigationBarView.OnItemSelectedListener navigationListener = item -> {
        Fragment selectedFragment = null;


        switch (item.getItemId()) {
            case R.id.au_dashboard:
                selectedFragment = new DashBoardFragment();
                break;

            case R.id.addAuditions:
                selectedFragment = new SetAudition();
                break;

            case R.id.attendants:
                selectedFragment = new ParticipantsList();
                break;
        }

        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.auditions_container, selectedFragment).commit();
        return true;
    };
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditions);

        bottomNavigationView = findViewById(R.id.au_nav);


        bottomNavigationView.setOnItemSelectedListener(navigationListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.auditions_container, new DashBoardFragment()).commit();
    }
}