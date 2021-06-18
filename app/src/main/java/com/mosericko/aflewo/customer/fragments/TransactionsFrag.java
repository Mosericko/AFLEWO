package com.mosericko.aflewo.customer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.activities.FAQs;
import com.mosericko.aflewo.customer.activities.FeedBack;
import com.mosericko.aflewo.customer.activities.OrderHistory;

import org.jetbrains.annotations.NotNull;

public class TransactionsFrag extends Fragment {
    Context context;
    LinearLayout orderHistory;
    LinearLayout feedBack;
    LinearLayout faqs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transactions,container,false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();

        orderHistory = view.findViewById(R.id.orderHistory);
        feedBack = view.findViewById(R.id.feedback);
        faqs = view.findViewById(R.id.faqs);

        orderHistory.setOnClickListener(v->{
            startActivity(new Intent(context, OrderHistory.class));
        });

        feedBack.setOnClickListener(v->{
            startActivity(new Intent(context, FeedBack.class));
        });

        faqs.setOnClickListener(v->{
            startActivity(new Intent(context, FAQs.class));
        });
    }
}
