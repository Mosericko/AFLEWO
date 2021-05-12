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
import com.mosericko.aflewo.customer.CategoryDetails;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH> {

    Context context;
    ArrayList<CategoryDetails> categArray;
    private ItemClickListener categoryListener;


    public CategoryAdapter(Context context, ArrayList<CategoryDetails> categArray) {
        this.context = context;
        this.categArray = categArray;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        categoryListener= itemClickListener;
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.category_card,parent,false);
        return new CategoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, int position) {
        CategoryDetails categoryDetails = categArray.get(position);

        holder.category.setText(categoryDetails.getName());
        Glide.with(context)
                .load(categoryDetails.getImage())
                .into(holder.imageCateg);

    }

    @Override
    public int getItemCount() {
        return categArray.size();
    }

    //create an interface
    public interface ItemClickListener{
        //pass the position of the item in the recyclerview
        void onItemCLick(int position);

    }

    public class CategoryVH extends RecyclerView.ViewHolder{
        TextView category;
        ImageView imageCateg;
        public CategoryVH(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.typeOfItem);
            imageCateg = itemView.findViewById(R.id.itemSvg);

            itemView.setOnClickListener(v -> {
                if (categoryListener != null){
                    //get the position of the adapter

                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){

                        categoryListener.onItemCLick(position);
                    }
                }
            });

        }
    }
}
