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
import com.bumptech.glide.request.RequestOptions;
import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.Products;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    ArrayList<Products> productList;
    Context context;
    private ItemClickListener prodListener;


    //create an interface
    public interface ItemClickListener{
         //pass the position of the item in the recyclerview
        void onItemCLick(int position);

    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        prodListener= itemClickListener;
    }


    public ProductAdapter(ArrayList<Products> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view= layoutInflater.inflate( R.layout.product_cardview,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Products products= productList.get(position);

        RequestOptions requestOptions= new RequestOptions()
                .placeholder(R.drawable.african);
        Glide.with(context)
                .load(products.getProductImage())
                .apply(requestOptions)
                .into(holder.productImage);
        holder.productTittle.setText(products.getProductName());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productTittle;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.productImage);
            productTittle= itemView.findViewById(R.id.productName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (prodListener != null){
                        //get the position of the adapter

                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){

                            prodListener.onItemCLick(position);
                        }
                    }
                }
            });
        }
    }

}
