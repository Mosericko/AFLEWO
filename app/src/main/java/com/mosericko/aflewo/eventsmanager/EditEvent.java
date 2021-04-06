package com.mosericko.aflewo.eventsmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import static com.mosericko.aflewo.eventsmanager.adapters.EventListAdapter.EXTRA_DATE;
import static com.mosericko.aflewo.eventsmanager.adapters.EventListAdapter.EXTRA_END;
import static com.mosericko.aflewo.eventsmanager.adapters.EventListAdapter.EXTRA_ID;
import static com.mosericko.aflewo.eventsmanager.adapters.EventListAdapter.EXTRA_IMAGE;
import static com.mosericko.aflewo.eventsmanager.adapters.EventListAdapter.EXTRA_NAME;
import static com.mosericko.aflewo.eventsmanager.adapters.EventListAdapter.EXTRA_START;
import static com.mosericko.aflewo.eventsmanager.adapters.EventListAdapter.EXTRA_THEME;
import static com.mosericko.aflewo.eventsmanager.adapters.EventListAdapter.EXTRA_VENUE;

public class EditEvent extends AppCompatActivity {

    ImageView editPhoto;
    TimePickerDialog startEvent, endEvent;
    DatePickerDialog setDate;
    EditText editName,editVenue,editTheme,editStartTime,editEndTime,editDate;
    Button saveChanges;
    String id,imageUrl,name,venue,theme,start,end,date;
    //image
    ImageView selectPhoto;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String imageString;
    private final int galleryReqCode = 1;
    private final int cameraReqCode = 2;
    Bitmap bitmap, image;
    private Uri fileTrace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        //editPhoto=findViewById(R.id.editEventPhoto);
        editName=findViewById(R.id.editEventName);
        editVenue=findViewById(R.id.editEventVenue);
        editTheme=findViewById(R.id.editEventTheme);
        editStartTime=findViewById(R.id.editStartTime);
        editEndTime=findViewById(R.id.editEndTime);
        editDate=findViewById(R.id.editEventDate);
        saveChanges=findViewById(R.id.saveEventChanges);
        selectPhoto= findViewById(R.id.editEventPhoto);

        pickerDialog();


        Intent intent= getIntent();

        id=intent.getStringExtra(EXTRA_ID);
        imageUrl=intent.getStringExtra(EXTRA_IMAGE);
        name=intent.getStringExtra(EXTRA_NAME);
        venue=intent.getStringExtra(EXTRA_VENUE);
        theme=intent.getStringExtra(EXTRA_THEME);
        start=intent.getStringExtra(EXTRA_START);
        end=intent.getStringExtra(EXTRA_END);
        date=intent.getStringExtra(EXTRA_DATE);

        if (!imageUrl.isEmpty()) {
            fileTrace = Uri.parse(imageUrl);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            try {
                URL url = new URL(imageUrl);
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(IOException e) {
                System.out.println(e);
            }

            bitmap = image;
        }

        Glide.with(this)
                .load(imageUrl)
                .into(selectPhoto);
        editName.setText(name);
        editVenue.setText(venue);
        editTheme.setText(theme);
        editStartTime.setText(start);
        editEndTime.setText(end);
        editDate.setText(date);


        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEvent();
            }
        });
        byteArrayOutputStream = new ByteArrayOutputStream();
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });

    }

    private void pickerDialog() {
        editStartTime.setInputType(InputType.TYPE_NULL);
        editEndTime.setInputType(InputType.TYPE_NULL);
        editDate.setInputType(InputType.TYPE_NULL);

        editStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                // time picker dialog
                startEvent = new TimePickerDialog(EditEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                editStartTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                startEvent.show();
            }
        });


        //timePicker Dialog
        editEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                // time picker dialog
                endEvent = new TimePickerDialog(EditEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                editEndTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                endEvent.show();
            }
        });

        //DatePicker Dialog
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                setDate = new DatePickerDialog(EditEvent.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                //grey out previous dates from the calender
                setDate.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                setDate.show();
            }

        });
    }
    private void editEvent() {
        final String eveName = editName.getText().toString().trim();
        final String eveVenue = editVenue.getText().toString().trim();
        final String eveTheme = editTheme.getText().toString().trim();
        final String eveStartTime = editStartTime.getText().toString().trim();
        final String eveEndTime = editEndTime.getText().toString().trim();
        final String eveDate = editDate.getText().toString().trim();


        if (fileTrace == null) {
            Toast.makeText(this, "Select a Photo!", Toast.LENGTH_SHORT).show();
            return;
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);


        //EditText Validations
        if (TextUtils.isEmpty(eveName)) {
            editName.setError("Please Enter Event name!");
            editName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(eveVenue)) {
            editVenue.setError("Please Enter Event Venue!");
            editVenue.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(eveTheme)) {
            editTheme.setError("Please Enter Event Theme!");
            editTheme.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(eveStartTime)) {
            editStartTime.setError("Please Enter Start Time!");
            editStartTime.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(eveEndTime)) {
            editEndTime.setError("Please Enter End Time!");
            editEndTime.requestFocus();
            return;
        }

        if (editStartTime.getText().toString().equals(editEndTime.getText().toString())) {
            editEndTime.setError("End Time Cannot Match Start Time!");
            editEndTime.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(eveDate)) {
            editDate.setError("Please Enter Date of Event!");
            editDate.requestFocus();
            return;
        }

        editEventAsync edit = new editEventAsync(id,eveName,eveVenue,eveTheme,eveStartTime,eveEndTime,eveDate,imageString);
        edit.execute();

    }
    public class editEventAsync extends AsyncTask<Void,Void,String>{
        String id,eName,eVenue,eTheme, startTime,endTime,eDate,imageStr;

        public editEventAsync(String id, String eName, String eVenue, String eTheme, String startTime, String endTime, String eDate,String imageStr) {
            this.id = id;
            this.eName = eName;
            this.eVenue = eVenue;
            this.eTheme = eTheme;
            this.startTime = startTime;
            this.endTime = endTime;
            this.eDate = eDate;
            this.imageStr= imageStr;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            HashMap<String,String> params = new HashMap<>();
            params.put("id",id);
            params.put("event_name", eName);
            params.put("event_venue", eVenue);
            params.put("event_theme", eTheme);
            params.put("start_time", startTime);
            params.put("end_time", endTime);
            params.put("event_date", eDate);
            params.put("event_image", imageStr);

            return requestHandler.sendPostRequest(URLs.URL_EDIT_EVENTS,params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject obj = new JSONObject(s);

                if (obj.getBoolean("error")){
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditEvent.this,EventsManager.class));
                    finish();
                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    //image

    private void showOptionsDialog() {
        AlertDialog.Builder optionsDialog = new AlertDialog.Builder(this);
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
            fileTrace = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filepath);

                Glide.with(this)
                        .load(bitmap)
                        .into(selectPhoto);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == cameraReqCode && resultCode == RESULT_OK && data != null && data.getData() != null) {

            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            Glide.with(this)
                    .load(bitmap)
                    .into(selectPhoto);


        }
    }
}