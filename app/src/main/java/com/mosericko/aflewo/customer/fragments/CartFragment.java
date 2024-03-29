package com.mosericko.aflewo.customer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.classes.CartDetails;
import com.mosericko.aflewo.customer.activities.CheckOut;
import com.mosericko.aflewo.customer.adapters.CartAdapter;
import com.mosericko.aflewo.database.DataBaseHandler;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    Context context;
    TextView clearAll;
    RecyclerView recyclerView;
    ImageView imageView;
    RelativeLayout hiddenLay, hiddenLay2;
    CartAdapter cartAdapter;
    ArrayList<CartDetails> cartArray = new ArrayList<>();
    DataBaseHandler myDb;
    TextView totalPrice;
    Button checkOut;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();
        imageView = view.findViewById(R.id.cartBackgroundImage);
        hiddenLay = view.findViewById(R.id.hiddenLayout);
        hiddenLay2 = view.findViewById(R.id.hiddenLayout2);
        recyclerView = view.findViewById(R.id.cartRecycler);
        clearAll = view.findViewById(R.id.clearItems);
        totalPrice = view.findViewById(R.id.totalPrice);
        checkOut = view.findViewById(R.id.checkOut);


        clearAll.setOnClickListener(v -> {
            myDb.deleteCartItems();
            clearAllItems();
            Toast.makeText(context, "Items Cleared Successfully!", Toast.LENGTH_SHORT).show();
        });

        loadCart();
        grandTotal();
    }

    public void loadCart() {

        myDb = new DataBaseHandler(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        cartArray.addAll(myDb.getCartDetails());

        cartAdapter = new CartAdapter(context, cartArray);
        recyclerView.setAdapter(cartAdapter);

        if (cartArray.isEmpty()) {
            imageView.setVisibility(View.VISIBLE);
            hiddenLay.setVisibility(View.GONE);
            hiddenLay2.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.GONE);
            hiddenLay.setVisibility(View.VISIBLE);
            hiddenLay2.setVisibility(View.VISIBLE);
        }


    }

    private void grandTotal() {
        int position;
        int priceTotal = 0;
        CartDetails cartDetails;


        for (position = 0; position < cartArray.size(); position++) {
            cartDetails = cartArray.get(position);
            priceTotal = priceTotal + (Integer.parseInt(cartDetails.getPrice()) * Integer.parseInt(cartDetails.getQuantity()));

            totalPrice.setText(String.valueOf(priceTotal));

            int finalPriceTotal = priceTotal;

            checkOut.setOnClickListener(v -> {
                Intent intent = new Intent(context,CheckOut.class);
                intent.putExtra("Price",String.valueOf(finalPriceTotal));

                startActivity(intent);
            });

        }
    }

    public void clearAllItems() {
        int size = cartArray.size();
        if (size > 0) {
            cartArray.subList(0, size).clear();

            cartAdapter.notifyItemRangeRemoved(0, size);
            hiddenLay.setVisibility(View.GONE);
            hiddenLay2.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);


        }
    }
    
}
//SwipeRefreshLayout swipeRefreshLayout;
//swipeRefreshLayout = view.findViewById(R.id.cartRefresh);
/*swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction().detach(CartFragment.this).attach(CartFragment.this).commit();
                swipeRefreshLayout.setRefreshing(false);
            }
        });*/
