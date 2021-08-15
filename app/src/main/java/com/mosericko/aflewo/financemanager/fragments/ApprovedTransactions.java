package com.mosericko.aflewo.financemanager.fragments;

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
import com.mosericko.aflewo.customer.activities.FeedBack;
import com.mosericko.aflewo.financemanager.activities.AdminFeedBack;
import com.mosericko.aflewo.financemanager.activities.Approved;

import org.jetbrains.annotations.NotNull;

public class ApprovedTransactions extends Fragment {
    LinearLayout approvedT, replyFeedback;
    Context context;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_approved_transactions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = this.getContext();
        approvedT = view.findViewById(R.id.approvedTransactions);
        replyFeedback = view.findViewById(R.id.replyFeedback);


        approvedT.setOnClickListener(v -> {
            startActivity(new Intent(context, Approved.class));
        });
        replyFeedback.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminFeedBack.class);
            intent.putExtra("name", "Finance Manager");
            startActivity(intent);
        });
    }
}
