package com.mosericko.aflewo.customer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.CartDetails;
import com.mosericko.aflewo.customer.Index;
import com.mosericko.aflewo.customer.Products;
import com.mosericko.aflewo.customer.fragments.CartFragment;
import com.mosericko.aflewo.database.DataBaseHandler;

import java.util.ArrayList;
import java.util.Objects;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<CartDetails> productsArrayList;
    DataBaseHandler myDb;
    int counter = 1;


    public CartAdapter(Context context, ArrayList<CartDetails> productsArrayList) {
        this.context = context;
        this.productsArrayList = productsArrayList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cart_cardview, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartDetails cartDetails = productsArrayList.get(position);

        Glide.with(context)
                .load(cartDetails.getProductImage())
                .into(holder.cartImage);

        holder.cartTitle.setText(cartDetails.getProductName());
        holder.cartSize.setText(cartDetails.getSize());
        holder.cartCategory.setText(cartDetails.getCategory());
        holder.cartPrice.setText(cartDetails.getPrice());
        holder.noOfItems.setText(cartDetails.getQuantity());


    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView cartImage;
        TextView cartTitle, cartSize, cartCategory, cartPrice;
        LinearLayout remove;
        LinearLayout piecesQ;
        TextView noOfItems;


        public CartViewHolder(@NonNull final View itemView) {
            super(itemView);

            cartImage = itemView.findViewById(R.id.cart_Image);
            cartTitle = itemView.findViewById(R.id.cart_title);
            cartSize = itemView.findViewById(R.id.sizeOfItem);
            cartCategory = itemView.findViewById(R.id.categoryOfItem);
            cartPrice = itemView.findViewById(R.id.priceTag);
            remove = itemView.findViewById(R.id.removeItem);
            noOfItems = itemView.findViewById(R.id.noOfItems);
            piecesQ = itemView.findViewById(R.id.piecesQuanta);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeCartItem();

                }
            });

            piecesQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOptionsDialog();
                }
            });

        }

        private void showOptionsDialog() {
            myDb = new DataBaseHandler(context);
            LinearLayout increment, decrement, saveChanges;
            final TextView quantity;

            int position = getAdapterPosition();
            final CartDetails cart = productsArrayList.get(position);
            final int id = Integer.parseInt(cart.getId());


            final AlertDialog.Builder quantityDialog = new AlertDialog.Builder(itemView.getContext());
            // Inflate the layout created
            LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
            final View view = inflater.inflate(R.layout.edit_quantity, null);

            // display the inflated layout
            quantityDialog.setView(view);

            //connect views
            quantity = view.findViewById(R.id.quantity);
            decrement = view.findViewById(R.id.decrementBtn);
            increment = view.findViewById(R.id.incrementBtn);
            saveChanges = view.findViewById(R.id.saveBtn);

            final AlertDialog alertDialog = quantityDialog.create();
            alertDialog.show();

            //increment and decrement
            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (counter < Integer.parseInt(cart.getTotalQuantity())) {
                        counter++;
                        quantity.setText(String.valueOf(counter));
                    } else {
                        Toast.makeText(context, "Pieces Cannot Exceed Stock!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (counter == 1) {
                        quantity.setText(String.valueOf(counter));
                        Toast.makeText(context, "Buy at least one Item ", Toast.LENGTH_SHORT).show();
                    } else {
                        counter -= 1;
                        quantity.setText(String.valueOf(counter));
                    }

                }
            });

            saveChanges.setOnClickListener(v -> {
                String selectedQuantity = quantity.getText().toString().trim();
                CartDetails cartDetails = new CartDetails(selectedQuantity);

                myDb.updateQuantity(cartDetails, id);
                Toast.makeText(context, "Quantity Edited ✔✔", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
//
            });


        }

        private void removeCartItem() {
            myDb = new DataBaseHandler(context);
            int position = getAdapterPosition();
            CartDetails cart = productsArrayList.get(position);

            int id = Integer.parseInt(cart.getId());

            myDb.deleteOneItem(id);
            productsArrayList.remove(position);
            notifyItemRemoved(position);

        }


    }

}
