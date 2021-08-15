package com.mosericko.aflewo.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.classes.OrderItems;
import com.mosericko.aflewo.database.PrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsVH> {
    Context context;
    ArrayList<OrderItems> itemsList;

    public OrderDetailsAdapter(Context context, ArrayList<OrderItems> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @NotNull
    @Override
    public OrderDetailsVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.order_items, parent, false);
        return new OrderDetailsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderDetailsAdapter.OrderDetailsVH holder, int position) {

        OrderItems orderItems = itemsList.get(position);

        holder.name.setText(orderItems.getName());
        holder.color.setText(orderItems.getColor());
        holder.price.setText(orderItems.getPrice());
        holder.category.setText(orderItems.getCategory());
        holder.size.setText(orderItems.getSize());
        holder.quantity.setText(orderItems.getQuantity());
        holder.stock.setText(orderItems.getStock());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class OrderDetailsVH extends RecyclerView.ViewHolder {
        TextView name,color,price,category,size,quantity,stock;
        LinearLayout stockLay;

        public OrderDetailsVH(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            color = itemView.findViewById(R.id.color);
            price = itemView.findViewById(R.id.price);
            category = itemView.findViewById(R.id.category);
            size = itemView.findViewById(R.id.sizeOfProduct);
            quantity = itemView.findViewById(R.id.itemQuanta);
            stock = itemView.findViewById(R.id.stock);
            stockLay = itemView.findViewById(R.id.stockLay);

            if (PrefManager.getInstance(context).UserType().equals("4")){
                stockLay.setVisibility(View.GONE);
            }



        }
    }
}
