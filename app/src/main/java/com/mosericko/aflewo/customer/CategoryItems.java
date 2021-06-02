package com.mosericko.aflewo.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.adapters.CatItemsAdapter;
import com.mosericko.aflewo.helperclasses.URLs;
import com.mosericko.aflewo.productmanager.adapters.ProductListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mosericko.aflewo.customer.fragments.CategoriesFrag.EXTRA_CATEGORY;

public class CategoryItems extends AppCompatActivity implements CatItemsAdapter.ItemClickListener {
    //Intent Extra constants
    public static final int NUM_COLUMNS = 2;
    public static final String ID_ = "id";
    public static final String NAME_ = "name";
    public static final String COLOR_ = "color";
    public static final String PRICE_ = "price";
    public static final String CATEGORY_ = "category";
    public static final String SIZE_ = "size";
    public static final String IMAGE_ = "image";
    public static final String QUANTITY_ = "quantity";
    TextView name;
    RecyclerView catProducts;
    ArrayList<CategoryInfo> details = new ArrayList<>();
    CatItemsAdapter catItemsAdapter;
    String itemType;
    //data from api
    String id, image, nameProduct, colorProduct, price, category, size,quantity;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        name = findViewById(R.id.itemCategory);
        catProducts = findViewById(R.id.cat);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        //get extras
        Intent intent = getIntent();
        itemType = intent.getStringExtra(EXTRA_CATEGORY);
        name.setText(itemType);

        //implement swipe to refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                finish();
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        fetchItems();
    }

    public void fetchItems() {
        StaggeredGridLayoutManager staggeredRecycler = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        catProducts.setLayoutManager(staggeredRecycler);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_FETCH_CATEGORIES, response -> {
            try {
                JSONArray j = new JSONArray(response);
                for (int i = 0; i < j.length(); i++) {
                    try {
                        JSONObject obj = j.getJSONObject(i);

                        id = obj.getString("id");
                        image = obj.getString("image");
                        nameProduct = obj.getString("name");
                        colorProduct = obj.getString("color");
                        price = obj.getString("price");
                        category = obj.getString("category");
                        size = obj.getString("size");
                        quantity = obj.getString("quantity");

                        details.add(new CategoryInfo(id, image, nameProduct, colorProduct, price, category, size,quantity));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    catItemsAdapter = new CatItemsAdapter(CategoryItems.this, details);
                    catProducts.setAdapter(catItemsAdapter);
                    catItemsAdapter.setItemClickListener(CategoryItems.this);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(CategoryItems.this, "Error", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Category", itemType);

                return params;
            }
        };
        requestQueue.add(stringRequest);


    }


    @Override
    public void ItemCLick(int position) {
        Intent intent = new Intent(CategoryItems.this, MoreCatInfo.class);
        CategoryInfo clickedCard = details.get(position);

        intent.putExtra(ID_, clickedCard.getId());
        intent.putExtra(NAME_, clickedCard.getName());
        intent.putExtra(COLOR_, clickedCard.getColor());
        intent.putExtra(PRICE_, clickedCard.getPrice());
        intent.putExtra(CATEGORY_, clickedCard.getCategory());
        intent.putExtra(SIZE_, clickedCard.getSize());
        intent.putExtra(IMAGE_, clickedCard.getImage());
        intent.putExtra(QUANTITY_, clickedCard.getQuantity());

        startActivity(intent);
    }
}