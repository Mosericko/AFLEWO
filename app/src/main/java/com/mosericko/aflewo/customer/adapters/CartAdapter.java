package com.mosericko.aflewo.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.CartDetails;
import com.mosericko.aflewo.customer.Products;
import com.mosericko.aflewo.database.DataBaseHandler;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<CartDetails> productsArrayList;
    DataBaseHandler myDb ;


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
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeCartItem();

                }
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
