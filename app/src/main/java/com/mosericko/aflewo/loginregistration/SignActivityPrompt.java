package com.mosericko.aflewo.loginregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.activities.Index;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.eventsmanager.EventsManager;
import com.mosericko.aflewo.financemanager.FinanceManager;
import com.mosericko.aflewo.participants.Participants;
import com.mosericko.aflewo.productmanager.ProductManager;
import com.mosericko.aflewo.member.MainActivity;

public class SignActivityPrompt extends AppCompatActivity {
    Button register, login;
    LinearLayout participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_prompt);

        register = findViewById(R.id.signUp);
        login = findViewById(R.id.signIn);
       // participants = findViewById(R.id.participant);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignActivityPrompt.this, UserRegistration.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignActivityPrompt.this, UserLogin.class));
            }
        });
       /* participants.setOnClickListener(v -> {
            startActivity(new Intent(SignActivityPrompt.this, Participants.class));
        });*/

        if (PrefManager.getInstance(this).isLoggedIn()) {
            this.finish();

            switch (PrefManager.getInstance(this).UserType()) {

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
                    Intent intent = new Intent(SignActivityPrompt.this, Index.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    break;
                case "5":
                    startActivity(new Intent(SignActivityPrompt.this, ProductManager.class));
                    finish();
                    break;

            }
        }
    }
}