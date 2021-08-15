package com.mosericko.aflewo.financemanager.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.classes.Orders;
import com.mosericko.aflewo.financemanager.adapters.AllOrdersAdapter;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Approved extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    RecyclerView pendingT;
    ArrayList<Orders> pendingOrders = new ArrayList<>();
    AllOrdersAdapter rAdapter;
    TextView downloadReports;
    DownloadManager downloadManager;
    String receiptUrl = "https://android.officialm-devs.com/aflewo/android/orders";
    String reportName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved);

        pendingT = findViewById(R.id.pendingTRecycler);
        downloadReports = findViewById(R.id.reports);

        displayApprovedTransactions();

        reportName = setReportName();
        ActivityCompat.requestPermissions((Activity) this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_CODE);

        downloadManager = (DownloadManager) this.getSystemService(DOWNLOAD_SERVICE);

        downloadReports.setOnClickListener(v -> {
            downloadReceipt();
        });
    }

    private void displayApprovedTransactions() {
        pendingT.setHasFixedSize(true);
        pendingT.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //fetch the items from the database using volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_FETCH_ALL_APP, null, response -> {
            try {
                //loop through the event details array
                for (int i = 0; i < response.length(); i++) {

                    //get the current Json Object
                    JSONObject ordersPending = response.getJSONObject(i);

                    String orderId = ordersPending.getString("order_id");
                    String custId = ordersPending.getString("cust_id");
                    String orderNum = ordersPending.getString("order_num");
                    String mpesa_code = ordersPending.getString("mpesa_code");
                    String orderDate = ordersPending.getString("order_date");
                    String orderTime = ordersPending.getString("order_time");
                    String amountPaid = ordersPending.getString("total_price");
                    String orderStatus = ordersPending.getString("order_status");

                    pendingOrders.add(new Orders(orderId, orderNum, mpesa_code, custId, orderDate, orderTime, amountPaid, orderStatus));
                }

                rAdapter = new AllOrdersAdapter(this, pendingOrders);
                pendingT.setAdapter(rAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(jsonArrayRequest);

    }

    private void downloadReceipt() {

        ReceiptAsync receiptAsync = new ReceiptAsync(reportName);
        receiptAsync.execute();
    }

    public String setReportName() {

        char[] myChars = "1234567890".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 2; i++) {
            char c1 = myChars[random.nextInt(myChars.length)];
            stringBuilder.append(c1);
        }

        String randomString = stringBuilder.toString();

        return ("ParNo" + randomString);
    }


    public class ReceiptAsync extends AsyncTask<Void, Void, String> {

        String orderNum;

        public ReceiptAsync(String orderNum) {
            this.orderNum = orderNum;
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("reportName", orderNum);

            return requestHandler.sendPostRequest(receiptUrl, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);

                String receiptAddress = obj.getString("url");

                Log.d("TAG", "downloadReceipt: " + receiptAddress);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(receiptAddress));
                String title = URLUtil.guessFileName(receiptAddress, null, null);
                //Set the title of this download, to be displayed in notifications (if enabled).
                request.setTitle(title);
                //Set a description of this download, to be displayed in notifications (if enabled)
                request.setDescription("Receipt download using DownloadManager.");

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                //Set the local destination for the downloaded file to a path within the application's external files directory
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                downloadManager.enqueue(request);
                Toast.makeText(Approved.this, "Download Completed", Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}