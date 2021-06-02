package com.mosericko.aflewo.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.fragments.CartFragment;
import com.mosericko.aflewo.customer.fragments.CategoriesFrag;
import com.mosericko.aflewo.customer.fragments.HomeFragment;
import com.mosericko.aflewo.customer.fragments.ProfileFragment;
import com.mosericko.aflewo.customer.fragments.TransactionsFrag;
import com.mosericko.aflewo.eventsmanager.fragments.AddEventFragment;
import com.mosericko.aflewo.eventsmanager.fragments.ArchivedEvents;
import com.mosericko.aflewo.eventsmanager.fragments.EventListFragment;
import com.mosericko.aflewo.eventsmanager.fragments.EventProfileFragment;

public class Index extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        bottomNavigationView=findViewById(R.id.cus_bottom_navigation);

        FragmentTransaction fT=getSupportFragmentManager().beginTransaction();

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.customer_container, new HomeFragment()).commit();

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;

                    case R.id.categories:
                        selectedFragment = new CategoriesFrag();
                        break;

                    case R.id.cart:
                        selectedFragment = new CartFragment();
                        break;

                    case R.id.transactions:
                        selectedFragment=new TransactionsFrag();
                        break;

                    case R.id.customerProfile:
                        selectedFragment=new ProfileFragment();
                        break;

                }

                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.customer_container, selectedFragment).commit();
                return true;
            };

}