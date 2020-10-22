package com.mosericko.aflewo.loginregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.eventsmanager.EventsManager;
import com.mosericko.aflewo.financemanager.FinanceManager;
import com.mosericko.aflewo.fundraiser.Fundraiser;
import com.mosericko.aflewo.marketing.Marketing;
import com.mosericko.aflewo.member.MainActivity;

public class SignActivityPrompt extends AppCompatActivity {
    Button register,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_prompt);

        register=findViewById(R.id.signUp);
        login=findViewById(R.id.signIn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignActivityPrompt.this,UserRegistration.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignActivityPrompt.this,UserLogin.class));
            }
        });

        if (PrefManager.getInstance(this).isLoggedIn()){
            this.finish();

            switch(PrefManager.getInstance(this).UserType()){

                case "1":
                    startActivity(new Intent(SignActivityPrompt.this, MainActivity.class));
                    finish();
                    break;
                case "2":
                    startActivity(new Intent(SignActivityPrompt.this, EventsManager.class));
                    finish();
                    break;
                case "3":
                    startActivity(new Intent(SignActivityPrompt.this, FinanceManager.class));
                    finish();
                    break;
                case "4":
                    startActivity(new Intent(SignActivityPrompt.this, Marketing.class));
                    finish();
                    break;
                case "5":
                    startActivity(new Intent(SignActivityPrompt.this, Fundraiser.class));
                    finish();
                    break;

            }
        }
    }
}