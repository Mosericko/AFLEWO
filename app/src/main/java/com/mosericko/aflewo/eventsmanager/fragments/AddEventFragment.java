package com.mosericko.aflewo.eventsmanager.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.eventsmanager.EventsManager;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class AddEventFragment extends Fragment {
    //variable declaration
    private int galleryReqCode = 1, cameraReqCode = 2;
    TimePickerDialog startEvent, endEvent;
    DatePickerDialog setDate;
    ImageView selectPhoto;
    Button saveEvent;
    EditText eventName, eventVenue, eventTheme, startTime, endTime, eventDate;
    Bitmap bitmap;
    Context context;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String imageString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addevents, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();

        //connect Interface with the backend
        selectPhoto = view.findViewById(R.id.choose_photo);
        saveEvent = view.findViewById(R.id.saveEvent);
        eventName = view.findViewById(R.id.eventName);
        eventVenue = view.findViewById(R.id.eventVenue);
        eventDate = view.findViewById(R.id.eventDate);
        eventTheme = view.findViewById(R.id.eventTheme);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);

        //helps to prevent the editText from calling the keyboard -inputType=null
        startTime.setInputType(InputType.TYPE_NULL);
        endTime.setInputType(InputType.TYPE_NULL);
        eventDate.setInputType(InputType.TYPE_NULL);

        //timePicker dialog
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                // time picker dialog
                startEvent = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                startTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                startEvent.show();
            }
        });


        //timePicker Dialog
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                // time picker dialog
                endEvent = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                endTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                endEvent.show();
            }
        });

        //DatePicker Dialog
        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                setDate = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eventDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                //grey out previous dates from the calender
                setDate.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                setDate.show();
            }

        });

        byteArrayOutputStream = new ByteArrayOutputStream();

        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });

        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadEvent();

            }
        });
    }

    private void nextFrag() {
        //calling the eventList Fragment
        EventListFragment eventListFragment=new EventListFragment();
        FragmentTransaction fragmentTransaction= Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,eventListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showOptionsDialog() {
        AlertDialog.Builder optionsDialog = new AlertDialog.Builder(context);
        String[] options = {"Take photo", "Choose Existing Photo"};

        optionsDialog.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        takePhoto();
                        break;

                    case 1:
                        choosePhoto();
                        break;
                }
            }
        });

        optionsDialog.show();

    }

    private void choosePhoto() {

        Intent gallery = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(gallery, galleryReqCode);
    }

    private void takePhoto() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, cameraReqCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryReqCode && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filepath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), filepath);

                Glide.with(this)
                        .load(bitmap)
                        .into(selectPhoto);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == cameraReqCode && resultCode == RESULT_OK && data != null && data.getData() != null) {

            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            Glide.with(this)
                    .load(bitmap)
                    .into(selectPhoto);


        }
    }

    private void uploadEvent() {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);


        final String eveName = eventName.getText().toString().trim();
        final String eveVenue = eventVenue.getText().toString().trim();
        final String eveTheme = eventTheme.getText().toString().trim();
        final String eveStartTime = startTime.getText().toString().trim();
        final String eveEndTime = endTime.getText().toString().trim();
        final String eveDate = eventDate.getText().toString().trim();

        //EditText Validations

        if (imageString.length()==0){
            Toast.makeText(context, "Upload Image", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(eveName)) {
            eventName.setError("Please Enter Event name!");
            eventName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(eveVenue)) {
            eventVenue.setError("Please Enter Event Venue!");
            eventVenue.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(eveTheme)) {
            eventTheme.setError("Please Enter Event Theme!");
            eventTheme.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(eveStartTime)) {
            startTime.setError("Please Enter Start Time!");
            startTime.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(eveEndTime)) {
            endTime.setError("Please Enter End Time!");
            endTime.requestFocus();
            return;
        }

        if (startTime.getText().toString().equals(endTime.getText().toString())) {
            endTime.setError("End Time Cannot Match Start Time!");
            endTime.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(eveDate)) {
            eventDate.setError("Please Enter Date of Event!");
            eventDate.requestFocus();
            return;
        }

        UploadEventsAsync uploadEventsAsync = new UploadEventsAsync(imageString, eveName, eveVenue, eveTheme, eveStartTime, eveEndTime, eveDate);
        uploadEventsAsync.execute();

    }

    class UploadEventsAsync extends AsyncTask<Void, Void, String> {

        private String imageStr, eName, eVenue, eTheme, eStartT, eEndT, eDate;

        public UploadEventsAsync(String imageStr, String eName, String eVenue, String eTheme, String eStartT, String eEndT, String eDate) {
            this.imageStr = imageStr;
            this.eName = eName;
            this.eVenue = eVenue;
            this.eTheme = eTheme;
            this.eStartT = eStartT;
            this.eEndT = eEndT;
            this.eDate = eDate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("event_name", eName);
            params.put("event_venue", eVenue);
            params.put("event_theme", eTheme);
            params.put("start_time", eStartT);
            params.put("end_time", eEndT);
            params.put("event_date", eDate);
            params.put("event_image", imageStr);

            return requestHandler.sendPostRequest(URLs.URL_ADD_EVENTS, params);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.i("Add events", "" + s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                if (!jsonObject.getBoolean("error")) {
                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    nextFrag();
                } else {
                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
