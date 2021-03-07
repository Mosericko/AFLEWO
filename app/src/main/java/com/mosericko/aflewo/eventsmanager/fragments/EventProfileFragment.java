package com.mosericko.aflewo.eventsmanager.fragments;

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
import com.mosericko.aflewo.member.UserProfile;

public class EventProfileFragment extends Fragment {

    EditText firstName, lastName, gender, emailAddress, phoneNumber;
    Button logOut;
    Context context;
    int id;
    private DataBaseHandler myDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();

        firstName = view.findViewById(R.id.firstJina);
        lastName = view.findViewById(R.id.lastJina);
        gender = view.findViewById(R.id.genderPick);
        emailAddress = view.findViewById(R.id.profileEmailEvents);
        phoneNumber = view.findViewById(R.id.phoneContact);
        logOut = view.findViewById(R.id.signOut);

        loadEventMangerProfile();

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

    private void loadEventMangerProfile() {
        id = PrefManager.getInstance(context).UserID();
        myDb = new DataBaseHandler(context);
        User user = myDb.getUser(id);

        firstName.setText(user.getFirstname());
        lastName.setText(user.getLastname());
        emailAddress.setText(user.getEmail());
        gender.setText(user.getGender());
        phoneNumber.setText(user.getPhonenumber());

    }
}
