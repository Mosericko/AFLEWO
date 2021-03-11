package com.mosericko.aflewo.productmanager.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.database.DataBaseHandler;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.loginregistration.SignActivityPrompt;
import com.mosericko.aflewo.member.User;

public class AdminProfileFragment extends Fragment {

    int id;
    DataBaseHandler db;
    Context context;
    EditText firstName, lastName, gender,email, phoneNum;
    Button logOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context= this.getContext();

        firstName= view.findViewById(R.id.name1);
        lastName= view.findViewById(R.id.name2);
        gender= view.findViewById(R.id.adminGender);
        email= view.findViewById(R.id.adminEmail);
        phoneNum= view.findViewById(R.id.mobileNumber);
        logOut=view.findViewById(R.id.signOut);

        displayDetails();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager.getInstance(context).logout();
                Intent intent = new Intent(context, SignActivityPrompt.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void displayDetails() {
        db= new DataBaseHandler(context);
        id= PrefManager.getInstance(context).UserID();
        User user = db.getUser(id);

        firstName.setText(user.getFirstname());
        lastName.setText(user.getLastname());
        gender.setText(user.getGender());
        email.setText(user.getEmail());
        phoneNum.setText(user.getPhonenumber());
    }
}
