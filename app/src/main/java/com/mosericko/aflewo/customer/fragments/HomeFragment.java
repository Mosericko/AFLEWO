package com.mosericko.aflewo.customer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.ProductDetails;
import com.mosericko.aflewo.customer.Products;
import com.mosericko.aflewo.customer.adapters.ProductAdapter;
import com.mosericko.aflewo.database.DataBaseHandler;
import com.mosericko.aflewo.database.PrefManager;
import com.mosericko.aflewo.eventsmanager.Events;
import com.mosericko.aflewo.eventsmanager.adapters.EventListAdapter;
import com.mosericko.aflewo.helperclasses.URLs;
import com.mosericko.aflewo.member.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ProductAdapter.ItemClickListener{


    //constants declarations for intents
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String COLOR = "color";
    public static final String PRICE = "price";
    public static final String CATEGORY = "category";
    public static final String SIZE = "size";
    public static final String IMAGE = "image";

    RecyclerView recyclerView;
    ArrayList<Products> productsArrayList = new ArrayList<>();
    Context context;
    ProductAdapter productAdapter;
    TextView introName;
    int id;
    DataBaseHandler dataBaseHandler;

    public static final int NUM_COLUMNS = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=this.getContext();

        id= PrefManager.getInstance(context).UserID();
        dataBaseHandler= new DataBaseHandler(context);
        User user = dataBaseHandler.getUser(id);

        introName= view.findViewById(R.id.introName);
        recyclerView=view.findViewById(R.id.productRecyclerview);

        introName.setText(user.getFirstname());


        productsDisplay();
    }

    private void productsDisplay() {
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(NUM_COLUMNS,LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_FETCH_PRODUCTS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

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


                                productsArrayList.add(new Products(id, image, name, color, price, category, size));
                            }

                            productAdapter = new ProductAdapter(productsArrayList, context);
                            recyclerView.setAdapter(productAdapter);
                            productAdapter.setOnItemClickListener(HomeFragment.this);

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


    @Override
    public void onItemCLick(int position) {
        Intent prodDetails = new Intent(getContext(), ProductDetails.class);
        Products clickedItem = productsArrayList.get(position);

       prodDetails.putExtra(ID,clickedItem.getId());
       prodDetails.putExtra(NAME,clickedItem.getProductName());
       prodDetails.putExtra(COLOR,clickedItem.getColor());
       prodDetails.putExtra(CATEGORY,clickedItem.getCategory());
       prodDetails.putExtra(SIZE,clickedItem.getSize());
       prodDetails.putExtra(PRICE,clickedItem.getPrice());
       prodDetails.putExtra(IMAGE,clickedItem.getProductImage());

       startActivity(prodDetails);

    }
}
