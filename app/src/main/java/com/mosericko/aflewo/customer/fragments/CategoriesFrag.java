package com.mosericko.aflewo.customer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.classes.CategoryDetails;
import com.mosericko.aflewo.customer.activities.CategoryItems;
import com.mosericko.aflewo.customer.adapters.CategoryAdapter;

import java.util.ArrayList;

public class CategoriesFrag extends Fragment implements CategoryAdapter.ItemClickListener {

    public static final String EXTRA_CATEGORY = "category";
    RecyclerView categoryRV;
    Context context;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryDetails> categList = new ArrayList<>();
    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();
        categoryRV = view.findViewById(R.id.categoryRV);

        requestQueue = Volley.newRequestQueue(context);

        listCategories();

    }

    private void listCategories() {
        categoryRV.setHasFixedSize(true);
        categoryRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        // add the static files into the arrayList
        categList.add(new CategoryDetails(1, "Scarfs", R.drawable.ic_scarf));
        categList.add(new CategoryDetails(2, "T-Shirts", R.drawable.ic_tshirt));
        categList.add(new CategoryDetails(3, "Mugs", R.drawable.ic_mug));
        categList.add(new CategoryDetails(4, "Hoodies", R.drawable.ic_sweatshirt));
        categList.add(new CategoryDetails(5, "Hats", R.drawable.ic_hatflat));
        categList.add(new CategoryDetails(6, "Napkins", R.drawable.ic_square_handkerchief));

        categoryAdapter = new CategoryAdapter(context, categList);
        categoryRV.setAdapter(categoryAdapter);
        categoryAdapter.setOnItemClickListener(CategoriesFrag.this);
    }

    @Override
    public void onItemCLick(int position) {
        Intent intent = new Intent(getContext(), CategoryItems.class);
        CategoryDetails clickedItem = categList.get(position);

        intent.putExtra(EXTRA_CATEGORY, clickedItem.getName());

        startActivity(intent);

    }

}
