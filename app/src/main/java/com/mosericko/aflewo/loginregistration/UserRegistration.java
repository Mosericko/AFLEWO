package com.mosericko.aflewo.loginregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.mosericko.aflewo.R;

public class UserRegistration extends AppCompatActivity {
    EditText firstName, lastName, email, password, passConfirm, phone;
    Button nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);


        firstName = findViewById(R.id.fName);
        lastName = findViewById(R.id.lName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        nextPage = findViewById(R.id.nextPage);
        passConfirm = findViewById(R.id.confirmPass);


        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDetails();
            }
        });


    }

    private void validateDetails() {

        final String firstJina = firstName.getText().toString().trim();
        final String lastJina = lastName.getText().toString().trim();
        final String gmail = email.getText().toString().trim();
        final String passCode = password.getText().toString().trim();
        final String confirmPass = passConfirm.getText().toString().trim();
        final String mobileNum = phone.getText().toString().trim();


        //email and phoneNumber Validations

        String mobile_pattern = "^07[0-9]{8}$";
        String email_pattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com$";

        TextInputLayout fname = findViewById(R.id.fName_til);
        TextInputLayout lname = findViewById(R.id.lName_til);
        TextInputLayout mail = findViewById(R.id.admin_mailTil);
        TextInputLayout pass = findViewById(R.id.admin_passTil);
        TextInputLayout phoneNum = findViewById(R.id.admin_numberTil);
        TextInputLayout confirm = findViewById(R.id.admin_passConfirmTill);

        //Validate inputs

        if (TextUtils.isEmpty(firstJina)) {
            fname.setError("Please enter your first name!");
            firstName.requestFocus();
            return;
        } else {
            fname.setError(null);
        }

        if (TextUtils.isEmpty(lastJina)) {
            lname.setError("Please enter your last name!");
            lastName.requestFocus();
            return;
        } else {
            lname.setError(null);
        }

        if (TextUtils.isEmpty(gmail)) {
            mail.setError("Please enter your email address!");
            email.requestFocus();
            return;
        } else {
            mail.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(gmail).matches()) {
            mail.setError("Enter a valid email address!");
            email.requestFocus();
            return;
        } else {
            mail.setError(null);
        }

        if (!email.getText().toString().matches(email_pattern)) {
            mail.setError("Enter a valid email address!");
            email.requestFocus();
            return;
        } else {
            mail.setError(null);
        }

        if (passCode.length() < 5) {
            pass.setError("Password cannot be less than 5 characters");
            password.requestFocus();
            return;
        } else {
            pass.setError(null);
        }

        if (!password.getText().toString().equals(passConfirm.getText().toString())) {
            confirm.setError("Passwords Do not Match!!");
            passConfirm.requestFocus();
            return;
        } else {
            confirm.setError(null);
        }

        if (mobileNum.length() < 10) {
            phoneNum.setError("Phone number cannot be less than 10 digits");
            phone.requestFocus();
            return;
        } else {
            phoneNum.setError(null);
        }
        if (!phone.getText().toString().matches(mobile_pattern)) {
            phoneNum.setError("Enter a valid phone number");
            phone.requestFocus();
            return;
        } else {
            phoneNum.setError(null);
        }
        if (!android.util.Patterns.PHONE.matcher(mobileNum).matches()) {
            phoneNum.setError("Enter a valid phone number");
            phone.requestFocus();
            return;
        } else {
            phoneNum.setError(null);
        }

        if (TextUtils.isEmpty(mobileNum)) {
            phoneNum.setError("Please enter your phone number!");
            phone.requestFocus();
            return;
        } else {
            phoneNum.setError(null);
        }

        //if it passes all validations

        Intent intentData = new Intent(UserRegistration.this, MoreDetails.class);
        intentData.putExtra("firstName", firstJina);
        intentData.putExtra("lastName", lastJina);
        intentData.putExtra("email", gmail);
        intentData.putExtra("password", passCode);
        intentData.putExtra("phoneNumber", mobileNum);


        startActivity(intentData);


    }
}