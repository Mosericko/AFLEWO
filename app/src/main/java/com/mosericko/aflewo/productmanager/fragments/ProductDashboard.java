package com.mosericko.aflewo.productmanager.fragments;

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
import com.mosericko.aflewo.customer.classes.Products;
import com.mosericko.aflewo.financemanager.activities.AdminFeedBack;
import com.mosericko.aflewo.helperclasses.URLs;
import com.mosericko.aflewo.productmanager.EditProduct;
import com.mosericko.aflewo.productmanager.adapters.ProductListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductDashboard extends Fragment implements ProductListAdapter.onItemClickListener {

    RecyclerView prodRecyclerView;
    Context context;
    public static final int NUM_COLUMNS = 2;
    ProductListAdapter proAdapter;
    ArrayList<Products> prodArray = new ArrayList<>();
    TextView messages;

    //Key Constants for Intents to be sent
    public static final String ID_ = "id";
    public static final String NAME_ = "name";
    public static final String COLOR_ = "color";
    public static final String PRICE_ = "price";
    public static final String CATEGORY_ = "category";
    public static final String SIZE_ = "size";
    public static final String IMAGE_ = "image";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();

        prodRecyclerView = view.findViewById(R.id.prodRecyclerView);
        messages = view.findViewById(R.id.messages);

        messages.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminFeedBack.class);
            intent.putExtra("name", "Product Manager");
            startActivity(intent);
        });

        listAllProducts();
    }

    private void listAllProducts() {
        StaggeredGridLayoutManager staggeredRecycler = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        prodRecyclerView.setLayoutManager(staggeredRecycler);
        //volley library
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest getJsonArray = new JsonArrayRequest(Request.Method.GET, URLs.URL_FETCH_PRODUCTS, null, new Response.Listener<JSONArray>() {
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
                        String quantity = productDetails.getString("quantity");


                        prodArray.add(new Products(id, image, name, color, price, category, size, quantity));
                    }

                    proAdapter = new ProductListAdapter(prodArray, context);
                    prodRecyclerView.setAdapter(proAdapter);
                    proAdapter.setOnItemClickListener(ProductDashboard.this);

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

        requestQueue.add(getJsonArray);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(getContext(), EditProduct.class);
        Products clickedProduct = prodArray.get(position);

        detailIntent.putExtra(ID_, clickedProduct.getId());
        detailIntent.putExtra(NAME_, clickedProduct.getProductName());
        detailIntent.putExtra(COLOR_, clickedProduct.getColor());
        detailIntent.putExtra(PRICE_, clickedProduct.getPrice());
        detailIntent.putExtra(CATEGORY_, clickedProduct.getCategory());
        detailIntent.putExtra(SIZE_, clickedProduct.getSize());
        detailIntent.putExtra(IMAGE_, clickedProduct.getProductImage());

        startActivity(detailIntent);


    }
}
