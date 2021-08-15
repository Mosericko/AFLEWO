package com.mosericko.aflewo.customer.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.helperclasses.URLs;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.DOWNLOAD_SERVICE;

public class ReceiptBottomSheet extends BottomSheetDialogFragment {

    private static final int REQUEST_CODE=1;
    TextView download;
    Context context;
    String bundleOrderNum;
    DownloadManager downloadManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.receipt_bottom_sheet, container, false);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();


        assert context != null;
        ActivityCompat.requestPermissions((Activity) context, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_CODE);

        download = view.findViewById(R.id.download);
        downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);

        Bundle bundle = getArguments();
        assert bundle != null;
        bundleOrderNum = bundle.getString("orderNo");
        Log.d("TAG", "onViewCreated: "+bundleOrderNum);

        download.setOnClickListener(v -> {
            fetchAndDownload();
        });
    }

    private void fetchAndDownload() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_FETCH_LINK, response -> {

            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject receiptUrl = jsonArray.getJSONObject(i);
                        String receipt_url = receiptUrl.getString("receipt_url");

                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(receipt_url));
                        String title = URLUtil.guessFileName(receipt_url,null,null);
                        //Set the title of this download, to be displayed in notifications (if enabled).
                        request.setTitle(title);
                        //Set a description of this download, to be displayed in notifications (if enabled)
                        request.setDescription("Receipt download using DownloadManager.");

                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                        //Set the local destination for the downloaded file to a path within the application's external files directory
                        request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS,title);

                        downloadManager.enqueue(request);
                        Toast.makeText(context, "Download Completed: Check Notification Bar", Toast.LENGTH_SHORT).show();

                        dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        },error ->Toast.makeText(context, "Error 500", Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_num", bundleOrderNum);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
