package com.mosericko.aflewo.participants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.activities.ProductDetails;
import com.mosericko.aflewo.customer.adapters.ProductAdapter;
import com.mosericko.aflewo.customer.classes.Products;
import com.mosericko.aflewo.customer.fragments.HomeFragment;
import com.mosericko.aflewo.database.DataBaseHandler;
import com.mosericko.aflewo.eventsmanager.Events;
import com.mosericko.aflewo.helperclasses.URLs;
import com.mosericko.aflewo.member.MainActivity;
import com.mosericko.aflewo.member.activities.EventInformation;
import com.mosericko.aflewo.member.adapters.EventAdapter;
import com.mosericko.aflewo.participants.activities.ProductDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Participants extends AppCompatActivity implements EventAdapter.EventClickListener, ProductAdapter.ItemClickListener {
    //constants declarations for intents
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String COLOR = "color";
    public static final String PRICE = "price";
    public static final String CATEGORY = "category";
    public static final String SIZE = "size";
    public static final String IMAGE = "image";
    public static final String QUANTITY = "quantity";
    public static final int NUM_COLUMNS = 2;
    RecyclerView event_recycler;
    ArrayList<Events> userEventsArrayList = new ArrayList<>();
    EventAdapter eventAdapter;
    RecyclerView recyclerView;
    ArrayList<Products> productsArrayList = new ArrayList<>();
    ProductAdapter productAdapter;
    DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        event_recycler = findViewById(R.id.event_recyclerview);
        recyclerView = findViewById(R.id.productRecyclerview);

        productsDisplay();
        userEventView();
    }

    private void userEventView() {
        event_recycler.setHasFixedSize(true);
        event_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_FETCH_EVENTS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            //loop through the event details array
                            for (int i = 0; i < response.length(); i++) {

                                //get the current Json Object
                                JSONObject eventInfo = response.getJSONObject(i);

                                String id = eventInfo.getString("id");
                                String eventImage = eventInfo.getString("event_image");
                                String eventName = eventInfo.getString("event_name");
                                String startTime = eventInfo.getString("start_time");
                                String eventVenue = eventInfo.getString("event_venue");

                                userEventsArrayList.add(new Events(id, eventImage, eventName, startTime, eventVenue));
                            }
                            eventAdapter = new EventAdapter(userEventsArrayList, Participants.this);
                            event_recycler.setAdapter(eventAdapter);
                            eventAdapter.setOnItemClickListener(Participants.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void productsDisplay() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_FETCH_PRODUCTS, null,
                response -> {

                    try {
                        //loop through the event details array
                        for (int i = 0; i < response.length(); i++) {

                            //get the current Json Object
                            JSONObject productDetails = response.getJSONObject(i);

                            String id = productDetails.getString("id");
                            String image = productDetails.getString("image");
                            String name = productDetails.getString("name");
                            String color = productDetails.getString("color");
                            String price = productDetails.getString("price");
                            String category = productDetails.getString("category");
                            String size = productDetails.getString("size");
                            String quantity = productDetails.getString("quantity");


                            productsArrayList.add(new Products(id, image, name, color, price, category, size, quantity));
                        }

                        productAdapter = new ProductAdapter(productsArrayList, Participants.this);
                        recyclerView.setAdapter(productAdapter);
                        productAdapter.setOnItemClickListener(Participants.this);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }


    @Override
    public void onEventClick(int position) {
        Intent intent = new Intent(Participants.this, EventInformation.class);

        startActivity(intent);
    }

    @Override
    public void onItemCLick(int position) {
        Intent prodDetails = new Intent(Participants.this, ProductDetail.class);
        Products clickedItem = productsArrayList.get(position);

        prodDetails.putExtra(ID, clickedItem.getId());
        prodDetails.putExtra(NAME, clickedItem.getProductName());
        prodDetails.putExtra(COLOR, clickedItem.getColor());
        prodDetails.putExtra(CATEGORY, clickedItem.getCategory());
        prodDetails.putExtra(SIZE, clickedItem.getSize());
        prodDetails.putExtra(PRICE, clickedItem.getPrice());
        prodDetails.putExtra(IMAGE, clickedItem.getProductImage());
        prodDetails.putExtra(QUANTITY, clickedItem.getQuantity());

        startActivity(prodDetails);
    }
}