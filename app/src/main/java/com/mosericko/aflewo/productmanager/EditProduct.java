package com.mosericko.aflewo.productmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.CATEGORY_;
import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.COLOR_;
import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.ID_;
import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.IMAGE_;
import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.NAME_;
import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.PRICE_;
import static com.mosericko.aflewo.productmanager.fragments.ProductDashboard.SIZE_;

public class EditProduct extends AppCompatActivity {

    ImageView editImage;
    EditText name, color, price;
    AutoCompleteTextView category, size;
    Button archive,save;
    String id, imageIn, nameIn, colorIn, priceIn, categoryIn, sizeIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        editImage = findViewById(R.id.edit_product_image);
        name = findViewById(R.id.edit_product_name);
        color = findViewById(R.id.edit_prodColor);
        price = findViewById(R.id.edit_prodPrice);
        category = findViewById(R.id.productCat);
        archive = findViewById(R.id.archive);
        size = findViewById(R.id.productSizes);
        save = findViewById(R.id.saveEdits);
        
        save.setOnClickListener(v -> {
            Toast.makeText(this, "Under Construction. Coming Soon", Toast.LENGTH_SHORT).show();
        });

        Intent getIntents = getIntent();
        id = getIntents.getStringExtra(ID_);
        imageIn = getIntents.getStringExtra(IMAGE_);
        nameIn = getIntents.getStringExtra(NAME_);
        colorIn = getIntents.getStringExtra(COLOR_);
        priceIn = getIntents.getStringExtra(PRICE_);
        categoryIn = getIntents.getStringExtra(CATEGORY_);
        sizeIn = getIntents.getStringExtra(SIZE_);


        Glide.with(this)
                .load(imageIn)
                .into(editImage);
        name.setText(nameIn);
        color.setText(colorIn);
        price.setText(priceIn);
        category.setText(categoryIn);
        size.setText(sizeIn);


        ArrayList<String> categ = new ArrayList<>();
        categ.add("T-Shirts");
        categ.add("Hoodies");
        categ.add("Scarfs");
        categ.add("Hats");
        categ.add("Mugs");
        categ.add("Napkins");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, R.layout.feedback_menu_design, categ);
        category.setAdapter(categoryAdapter);


        ArrayList<String> sizes = new ArrayList<>();
        sizes.add("Extra Small [XS]");
        sizes.add("Small [S]");
        sizes.add("Medium [M]");
        sizes.add("Large [L]");
        sizes.add("Extra Large [XL]");
        sizes.add("Standard");

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, R.layout.feedback_menu_design, sizes);
        size.setAdapter(sizeAdapter);


        archive.setOnClickListener(v -> {
            archiveId();
        });

    }

    private void archiveId() {
        //execute async task
        ArchiveAsync archiveAsync = new ArchiveAsync(id);
        archiveAsync.execute();


    }

    class ArchiveAsync extends AsyncTask<Void, Void, String> {
        String id;

        public ArchiveAsync(String id) {
            this.id = id;
        }


        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> param = new HashMap<>();
            param.put("id", id);

            return requestHandler.sendPostRequest(URLs.URL_ARCHIVE_PRODUCT, param);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject obj = new JSONObject(s);

                Toast.makeText(EditProduct.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProduct.this, ProductManager.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}