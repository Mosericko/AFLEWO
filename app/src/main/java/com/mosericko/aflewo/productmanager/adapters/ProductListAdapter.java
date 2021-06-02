package com.mosericko.aflewo.productmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.Products;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    ArrayList<Products> productList;
    Context context;
    private onItemClickListener prodClickListener;


    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        prodClickListener = onItemClickListener;
    }


    public ProductListAdapter(ArrayList<Products> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.manager_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Products product = productList.get(position);

//        RequestOptions reqOptions = new RequestOptions().placeholder(R.drawable.ic_undraw_empty_cart);
        Glide.with(context)
                .load(product.getProductImage())
//                .apply(reqOptions)
                .into(holder.imageOfProd);
        holder.nameOfProd.setText(product.getProductName());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView nameOfProd;
        ImageView imageOfProd;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            nameOfProd = itemView.findViewById(R.id.nameOfProd);
            imageOfProd = itemView.findViewById(R.id.imageOfProd);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (prodClickListener != null) {

                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {

                            prodClickListener.onItemClick(position);
                        }
                    }

                }
            });

        }
    }
}
