package com.mosericko.aflewo.loginregistration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRegistration extends AppCompatActivity {
    EditText firstName, lastName, email, password, passConfirm, phone;
    Button createAccount;
    AutoCompleteTextView gender, userType;
    TextView user_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);


        firstName = findViewById(R.id.fName);
        user_login = findViewById(R.id.user_login);
        lastName = findViewById(R.id.lName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        createAccount = findViewById(R.id.nextPage);
        passConfirm = findViewById(R.id.confirmPass);
        gender = findViewById(R.id.gender);
        userType = findViewById(R.id.userCat);


        createAccount.setOnClickListener(view -> validateDetails());


        user_login.setOnClickListener(v -> startActivity(new Intent(UserRegistration.this, UserLogin.class)));

        //gender and user choices

        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("Male");
        genderList.add("Female");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, R.layout.feedback_menu_design, genderList);
        gender.setAdapter(genderAdapter);

        //user categories

        ArrayList<String> userTypes = new ArrayList<>();
        userTypes.add("Applicants");
        userTypes.add("Staff");
        userTypes.add("Customer");


        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, R.layout.feedback_menu_design, userTypes);
        userType.setAdapter(categoryAdapter);


    }

    private void validateDetails() {
        String typeUser;
        final String firstJina = firstName.getText().toString().trim();
        final String lastJina = lastName.getText().toString().trim();
        final String gmail = email.getText().toString().trim();
        final String passCode = password.getText().toString().trim();
        final String confirmPass = passConfirm.getText().toString().trim();
        final String mobileNum = phone.getText().toString().trim();

        final String genderCat = gender.getText().toString().trim();
        final String userCategory = userType.getText().toString().trim();


        typeUser = null;
        switch (userCategory) {

            case "Applicants":
                typeUser = "1";
                break;

            case "Staff":
                typeUser = "6";
                break;

            case "Customer":
                typeUser = "4";
                break;

        }


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

        /*Intent intentData = new Intent(UserRegistration.this, MoreDetails.class);
        intentData.putExtra("firstName", firstJina);
        intentData.putExtra("lastName", lastJina);
        intentData.putExtra("email", gmail);
        intentData.putExtra("password", passCode);
        intentData.putExtra("phoneNumber", mobileNum);*/


//        startActivity(intentData);

        RegisterUser registerUser = new RegisterUser(firstJina, lastJina, gmail, passCode, mobileNum, genderCat, typeUser);
        registerUser.execute();
    }

    private class RegisterUser extends AsyncTask<Void, Void, String> {

        private final String firstName;
        private final String lastName;
        private final String email;
        private final String password;
        private final String phoneNumber;
        private final String gender;
        private final String typeUser;

        public RegisterUser(String firstName, String lastName, String email, String password, String phoneNumber, String gender, String typeUser) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.phoneNumber = phoneNumber;
            this.gender = gender;
            this.typeUser = typeUser;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("firstname", firstName);
            params.put("lastname", lastName);
            params.put("gender", gender);
            params.put("email", email);
            params.put("password", password);
            params.put("phonenumber", phoneNumber);
            params.put("usertype", typeUser);

            //returning the response
            return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("SignUp", "sign up : " + s);

            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(UserRegistration.this, UserLogin.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}