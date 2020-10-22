package com.mosericko.aflewo.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.database.DataBaseHandler;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.loginregistration.SignActivityPrompt;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {
    EditText fName, lName, email, phone,gender;
    private int id = PrefManager.getInstance(this).UserID();
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        logOut=findViewById(R.id.logOut);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager.getInstance(UserProfile.this).logout();
                finish();
                Intent intent = new Intent(UserProfile.this, SignActivityPrompt.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        profile();
    }

    private void profile() {

        DataBaseHandler myDb = new DataBaseHandler(this);
        User user = myDb.getUser(id);
        //User user=PrefManager.getUser();

        fName = findViewById(R.id.firstName);
        fName.setText(user.getFirstname());

        lName = findViewById(R.id.lastName);
        lName.setText(user.getLastname());

        gender=findViewById(R.id.userGender);
        gender.setText(user.getGender());

        email = findViewById(R.id.profilemail);
        email.setText(user.getEmail());

        phone = findViewById(R.id.phoneNumber);
        phone.setText(user.getPhonenumber());

    }
}