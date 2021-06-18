package com.mosericko.aflewo.productmanager.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;
import com.mosericko.aflewo.productmanager.ProductManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class AddProductFragment extends Fragment {

    private static final String TAG = "Check Error" ;
    ImageView selectProdPhoto;
    EditText prodName,prodColor,prodPrice,productQuantity;
    RadioGroup category,size;
    Button saveProd;
    Context context;
    Bitmap bitmap;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String imageString;
    private int galleryReqCode = 1, cameraReqCode = 2;
    private Uri fileTrace;
    RadioButton selectedCat;
    RadioButton selectedSize;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_product,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=this.getContext();


        //hooks
        selectProdPhoto= view.findViewById(R.id.choose_product_image);
        prodName= view.findViewById(R.id.product_name);
        prodColor= view.findViewById(R.id.prodColor);
        prodPrice= view.findViewById(R.id.prodPrice);
        saveProd= view.findViewById(R.id.saveProduct);
        category= view.findViewById(R.id.prodCategory);
        productQuantity = view.findViewById(R.id.quantityProd);
        size= view.findViewById(R.id.size);

       /* selectedCat = view.findViewById(category.getCheckedRadioButtonId());
        selectedSize = view.findViewById(size.getCheckedRadioButtonId());*/

        byteArrayOutputStream = new ByteArrayOutputStream();

        selectProdPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });

        saveProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDetailsToDb();
            }
        });
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
            fileTrace = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), filepath);

                Glide.with(this)
                        .load(bitmap)
                        .into(selectProdPhoto);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == cameraReqCode && resultCode == RESULT_OK && data != null && data.getData() != null) {

            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            Glide.with(this)
                    .load(bitmap)
                    .into(selectProdPhoto);


        }
    }

    private void sendDetailsToDb() {

        int selectedCategory= category.getCheckedRadioButtonId();
        selectedCat = requireView().findViewById(selectedCategory);

        int selected_size = size.getCheckedRadioButtonId();
        selectedSize= getView().findViewById(selected_size);

        final String productName = prodName.getText().toString().trim();
        final String productColor = prodColor.getText().toString().trim();
        final String productPrice = prodPrice.getText().toString().trim();
        final String catRadio = selectedCat.getText().toString().trim();
        final String sizeRadio = selectedSize.getText().toString().trim();
        final String productQuanta = productQuantity.getText().toString().trim();

        Log.d("tag","radioButton.getText()" + selectedSize.getText());

        if (fileTrace == null) {
            Toast.makeText(context, "Select Product Photo!", Toast.LENGTH_SHORT).show();
            return;
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);


        Log.d(TAG, "sendDetailsToDb: ");
        if (TextUtils.isEmpty(productName)) {
            prodName.setError("Please Enter Product name!");
            prodName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(productColor)) {
            prodColor.setError("Please Enter Product Color!");
            prodColor.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(productPrice)) {
            prodPrice.setError("Please Enter Product Price!");
            prodPrice.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(productQuanta)){
            productQuantity.setError("Please Enter Product Quantity!");
            productQuantity.requestFocus();
            return;
        }

        SendDetailsAsync sendDetails= new SendDetailsAsync(imageString, productName,productColor,productPrice,catRadio,sizeRadio,productQuanta);
        sendDetails.execute();
    }


    public class SendDetailsAsync extends AsyncTask<Void,Void,String>{

        String imageStr,name,color,price,category,size,quantity;


        public SendDetailsAsync(String imageStr,String name, String color, String price, String category, String size,String quantity) {
            this.imageStr = imageStr;
            this.name = name;
            this.color = color;
            this.price = price;
            this.category = category;
            this.size = size;
            this.quantity = quantity;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler= new RequestHandler();

            HashMap<String,String> params = new HashMap<>();
            params.put("image",imageStr);
            params.put("name",name);
            params.put("color",color);
            params.put("price",price);
            params.put("category",category);
            params.put("size",size);
            params.put("quantity",quantity);

            return requestHandler.sendPostRequest(URLs.URL_ADD_PRODUCTS,params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject obj = new JSONObject(s);

                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, ProductManager.class));
                requireActivity().finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}






























