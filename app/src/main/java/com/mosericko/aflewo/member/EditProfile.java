package com.mosericko.aflewo.member;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.database.DataBaseHandler;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;
import com.mosericko.aflewo.loginregistration.MoreDetails;
import com.mosericko.aflewo.loginregistration.UserLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {

    EditText firstName, lastName, gender;
    Button saveChanges;
    ImageView backButton;
    TextInputLayout fname, lname, genderTil;

    private final int id = PrefManager.getInstance(this).UserID();
    DataBaseHandler myDb = new DataBaseHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        saveChanges = findViewById(R.id.saveChanges);
        backButton = findViewById(R.id.previousPage);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, UserProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        displayDetails();

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });


    }


    private void displayDetails() {

        User user = myDb.getUser(id);

        firstName = findViewById(R.id.firstName);
        firstName.setText(user.getFirstname());

        lastName = findViewById(R.id.lastName);
        lastName.setText(user.getLastname());

        gender = findViewById(R.id.gender);
        gender.setText(user.getGender());

    }

    private void editProfile() {

        final String fnameUpdate = firstName.getText().toString().trim();
        final String lnameUpdate = lastName.getText().toString().trim();
        final String genderUpdate = gender.getText().toString().trim();

        fname = findViewById(R.id.fnameTil);
        lname = findViewById(R.id.lnameTil);
        genderTil = findViewById(R.id.genderUpdateTil);




        UpdateAsync updateAsync = new UpdateAsync(String.valueOf(id), fnameUpdate, lnameUpdate, genderUpdate);
        updateAsync.execute();

        //update SQLite Database Too
        myDb.updateProfile(new User(fnameUpdate, lnameUpdate, genderUpdate),id);


    }


    private class UpdateAsync extends AsyncTask<Void, Void, String> {
        private final String id;
        private final String firstName;
        private final String lastName;
        private final String gender;


        public UpdateAsync(String id, String firstName, String lastName, String gender) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;

        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("id", String.valueOf(id));
            params.put("firstname", firstName);
            params.put("lastname", lastName);
            params.put("gender", gender);


            return requestHandler.sendPostRequest(URLs.URL_UPDATE_PROFILE, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject obj = new JSONObject(s);

                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
