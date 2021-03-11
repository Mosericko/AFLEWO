package com.mosericko.aflewo.productmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.productmanager.fragments.AddProductFragment;
import com.mosericko.aflewo.productmanager.fragments.AdminProfileFragment;
import com.mosericko.aflewo.productmanager.fragments.ProductDashboard;

public class ProductManager extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manager);


        bottomNavigationView=findViewById(R.id.prod_bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.products_container,new ProductDashboard()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){

                case R.id.prodDash:
                    selectedFragment= new ProductDashboard();
                    break;

                case R.id.addProd:
                    selectedFragment= new AddProductFragment();
                    break;

                case R.id.admin_profile:
                    selectedFragment= new AdminProfileFragment();
                    break;


            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.products_container, selectedFragment).commit();
            return true;
        }
    };
}