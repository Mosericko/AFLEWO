package com.mosericko.aflewo.financemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.eventsmanager.fragments.AddEventFragment;
import com.mosericko.aflewo.eventsmanager.fragments.EventListFragment;
import com.mosericko.aflewo.eventsmanager.fragments.EventProfileFragment;
import com.mosericko.aflewo.financemanager.fragments.ApprovedTransactions;
import com.mosericko.aflewo.financemanager.fragments.PendingTransactions;
import com.mosericko.aflewo.financemanager.fragments.ProfileFragment;

public class FinanceManager extends AppCompatActivity {
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.pendingTransactions:
                selectedFragment = new PendingTransactions();
                break;

            case R.id.approvedTransactions:
                selectedFragment = new ApprovedTransactions();
                break;

            case R.id.profile:
                selectedFragment = new ProfileFragment();
                break;
        }

        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.finance_fragment_container, selectedFragment).commit();
        return true;
    };
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_manager);
        bottomNavigationView = findViewById(R.id.finance_bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.finance_fragment_container, new PendingTransactions()).commit();
    }
}