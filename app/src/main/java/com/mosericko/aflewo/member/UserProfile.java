package com.mosericko.aflewo.member;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.database.DataBaseHandler;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.loginregistration.SignActivityPrompt;

public class UserProfile extends AppCompatActivity {
    TextView email, phone, gender;
    TextView firstName, lastName, firstInitial, secondInitial, backText, editText;
    ImageView editProfile, backArrow;
    Button logOut;
    String nameOne, nameTwo;
    private int id = PrefManager.getInstance(this).UserID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        logOut = findViewById(R.id.logOut);
        /*editProfile = findViewById(R.id.editProfile);
        backArrow = findViewById(R.id.backArrow);
        backText = findViewById(R.id.backText);
        editText = findViewById(R.id.editText);*/


       /* backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, EditProfile.class));
            }
        });
*/
        /*editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, EditProfile.class));
            }
        });*/
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

        nameOne = String.valueOf(user.getFirstname());
        nameTwo = String.valueOf(user.getLastname());

        firstInitial = findViewById(R.id.firstInitial);
        firstInitial.setText(String.valueOf(nameOne.charAt(0)));

        secondInitial = findViewById(R.id.secondInitial);
        secondInitial.setText(String.valueOf(nameTwo.charAt(0)));

        firstName = findViewById(R.id.first_name);
        firstName.setText(user.getFirstname());

        lastName = findViewById(R.id.last_name);
        lastName.setText(user.getLastname());

        gender = findViewById(R.id.userGender);
        gender.setText(user.getGender());

        email = findViewById(R.id.profilemail);
        email.setText(user.getEmail());

        phone = findViewById(R.id.phoneNumber);
        phone.setText(user.getPhonenumber());

    }
}