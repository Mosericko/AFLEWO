package com.mosericko.aflewo.loginregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mosericko.aflewo.customer.Index;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.eventsmanager.EventsManager;
import com.mosericko.aflewo.financemanager.FinanceManager;
import com.mosericko.aflewo.fundraiser.Fundraiser;
import com.mosericko.aflewo.member.MainActivity;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;
import com.mosericko.aflewo.member.User;
import com.mosericko.aflewo.database.DataBaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UserLogin extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        email = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginPass);
        login = findViewById(R.id.login);
        forgotPassword = findViewById(R.id.forgotPass);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        final String loginEmail, loginPass;
        loginEmail = email.getText().toString().trim();
        loginPass = password.getText().toString().trim();

        String email_pattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com$";

        TextInputLayout eAddress = findViewById(R.id.mailTil);
        TextInputLayout passLayout = findViewById(R.id.passTil);


        if (TextUtils.isEmpty(loginEmail)) {
            eAddress.setError("Please enter your email address!");
            email.requestFocus();
            return;
        } else {
            eAddress.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) {
            eAddress.setError("Enter a valid email address!");
            email.requestFocus();
            return;
        } else {
            eAddress.setError(null);
        }

        if (!email.getText().toString().matches(email_pattern)) {
            eAddress.setError("Enter a valid email address!");
            email.requestFocus();
            return;
        } else {
            eAddress.setError(null);
        }

        if (TextUtils.isEmpty(loginPass)) {
            passLayout.setError("Enter your password!");
            password.requestFocus();
            return;
        } else {
            passLayout.setError(null);
        }

        UserSignIn user_login = new UserSignIn(loginEmail, loginPass);
        user_login.execute();


    }


    class UserSignIn extends AsyncTask<Void, Void, String> {

        String email, password;

        UserSignIn(String email, String password) {
            this.email = email;
            this.password = password;
        }


        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);
            //returning the response
            return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);

                //if no error in response
                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    //getting the user from the response
                    JSONObject userJson = obj.getJSONObject("user");

                    String typeUser = userJson.getString("usertype");

                    //creating a new user object
                    User user = new User(
                            userJson.getInt("id"),
                            userJson.getString("firstname"),
                            userJson.getString("lastname"),
                            userJson.getString("gender"),
                            userJson.getString("email"),
                            userJson.getString("phonenumber"),
                            userJson.getString("usertype")

                    );

                    //storing the user in shared preferences
                    PrefManager.getInstance(getApplicationContext()).setUserLogin(user);
                    //finish();

                    DataBaseHandler myDb = new DataBaseHandler(UserLogin.this);

                    //storing User to SQLite DataBase
                    myDb.addUser(user);

                    switch (typeUser) {
                        case "1":
                            startActivity(new Intent(UserLogin.this, MainActivity.class));
                            finish();
                            break;
                        case "2":
                            startActivity(new Intent(UserLogin.this, EventsManager.class));
                            finish();
                            break;
                        case "3":
                            startActivity(new Intent(UserLogin.this, FinanceManager.class));
                            finish();
                            break;
                        case "4":
                            startActivity(new Intent(UserLogin.this, Index.class));
                            finish();
                            break;
                        case "5":
                            startActivity(new Intent(UserLogin.this, Fundraiser.class));
                            finish();
                            break;
                    }


                } else {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
}