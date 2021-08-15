package com.mosericko.aflewo.loginregistration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ForgotPassword extends AppCompatActivity {
    EditText rPass, rEmail;
    Button recover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        rPass = findViewById(R.id.recoveryPass);
        rEmail = findViewById(R.id.recoveryEmail);
        recover = findViewById(R.id.recover);
        recover.setOnClickListener(v -> sendToDb());
    }

    private void sendToDb() {
        String rec_email = rEmail.getText().toString().trim();
        String rec_pass = rPass.getText().toString().trim();

        TextInputLayout eAddress = findViewById(R.id.mailTil);
        TextInputLayout passLayout = findViewById(R.id.passTil);

        String email_pattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com$";


        if (TextUtils.isEmpty(rec_email)) {
            eAddress.setError("Please enter your email address!");
            rEmail.requestFocus();
            return;
        } else {
            eAddress.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(rec_email).matches()) {
            eAddress.setError("Enter a valid email address!");
            rEmail.requestFocus();
            return;
        } else {
            eAddress.setError(null);
        }

        if (!rEmail.getText().toString().matches(email_pattern)) {
            eAddress.setError("Enter a valid email address!");
            rEmail.requestFocus();
            return;
        } else {
            eAddress.setError(null);
        }

        if (TextUtils.isEmpty(rec_pass)) {
            passLayout.setError("Enter your password!");
            rPass.requestFocus();
            return;
        } else {
            passLayout.setError(null);
        }

        RecoverAsync recoverAsync = new RecoverAsync(rec_email, rec_pass);
        recoverAsync.execute();

    }

    class RecoverAsync extends AsyncTask<Void, Void, String> {

        String email, password;

        RecoverAsync(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Toast.makeText(ForgotPassword.this, "Loading...Please Wait", Toast.LENGTH_SHORT).show();
            recover.setVisibility(View.INVISIBLE);
        }


        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("recovery_email", email);
            params.put("password", password);
            //returning the response
            return requestHandler.sendPostRequest(URLs.REC_PASS, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(ForgotPassword.this, UserLogin.class));
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