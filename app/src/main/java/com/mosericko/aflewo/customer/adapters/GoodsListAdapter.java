package com.mosericko.aflewo.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.CartDetails;

import java.util.ArrayList;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.GoodsListVH> {

    Context context;
    ArrayList<CartDetails>goodsList;

    public GoodsListAdapter(Context context, ArrayList<CartDetails> goodsList) {
        this.context = context;
        this.goodsList = goodsList;
    }

    @NonNull
    @Override
    public GoodsListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.goods_card_view,parent,false);
        return new GoodsListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsListVH holder, int position) {
        CartDetails cartDetails = goodsList.get(position);

        Glide.with(context)
                .load(cartDetails.getProductImage())
                .into(holder.image);

        holder.label.setText(cartDetails.getProductName());
        holder.size.setText(cartDetails.getSize());
        holder.category.setText(cartDetails.getCategory());
        holder.quantity.setText(cartDetails.getQuantity());
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    public class GoodsListVH extends RecyclerView.ViewHolder {
        TextView label,category,size,quantity;
        ImageView image;

        public GoodsListVH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.goodsImage);
            label = itemView.findViewById(R.id.goodsLabel);
            category = itemView.findViewById(R.id.goodsCategory);
            size = itemView.findViewById(R.id.goodsSize);
            quantity = itemView.findViewById(R.id.goodsQuantity);
        }
    }
}
