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
import com.mosericko.aflewo.customer.CategoryInfo;

import java.util.ArrayList;

public class CatItemsAdapter extends RecyclerView.Adapter<CatItemsAdapter.CatItemsVH> {
    Context context;
    ArrayList<CategoryInfo> details;
    private ItemClickListener catItemClick;

    public CatItemsAdapter(Context context, ArrayList<CategoryInfo> details) {
        this.context = context;
        this.details = details;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        catItemClick = itemClickListener;
    }

    @NonNull
    @Override
    public CatItemsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.category_cardview, parent, false);
        return new CatItemsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatItemsAdapter.CatItemsVH holder, int position) {

        CategoryInfo categoryInfo = details.get(position);
        Glide.with(context)
                .load(categoryInfo.getImage())
                .into(holder.image);
        holder.name.setText(categoryInfo.getName());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public interface ItemClickListener {
        void ItemCLick(int position);
    }

    public class CatItemsVH extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public CatItemsVH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.productName);
            image = itemView.findViewById(R.id.productImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (catItemClick != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            catItemClick.ItemCLick(position);
                        }
                    }
                }
            });
        }
    }
}
