package com.mosericko.aflewo.financemanager.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.database.DataBaseHandler;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.loginregistration.SignActivityPrompt;
import com.mosericko.aflewo.member.User;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {


    Context context;
    Button logout;
    TextView firstName, lastName, phoneNumber, emailAddress, userType,gender;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_finance_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = this.getContext();

        logout = view.findViewById(R.id.logout);
        firstName = view.findViewById(R.id.nameFirst);
        lastName = view.findViewById(R.id.nameSecond);
        gender = view.findViewById(R.id.gender);
        phoneNumber = view.findViewById(R.id.phoneNum);
        emailAddress = view.findViewById(R.id.emailAddress);


        displayDetails();

        logout.setOnClickListener(v -> {
            PrefManager.getInstance(context).logout();
            Intent signOut = new Intent(context, SignActivityPrompt.class);
            signOut.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(signOut);
        });
    }

    private void displayDetails() {
        int id = PrefManager.getInstance(context).UserID();
        DataBaseHandler myDb = new DataBaseHandler(context);
        User user = myDb.getUser(id);

        firstName.setText(user.getFirstname());
        lastName.setText(user.getLastname());
        gender.setText(user.getGender());
        phoneNumber.setText(user.getPhonenumber());
        emailAddress.setText(user.getEmail());

    }
}
