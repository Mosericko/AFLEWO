package com.mosericko.aflewo.financemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.adapters.OrderHistoryAdapter;
import com.mosericko.aflewo.customer.classes.Orders;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.AllOrdersVH> {

    Context context;
    ArrayList<Orders> pendingOrders;

    private OrderClickListener orderClickListener;

    public AllOrdersAdapter(Context context, ArrayList<Orders> pendingOrders) {
        this.context = context;
        this.pendingOrders = pendingOrders;
    }


    public void setOnItemClickListener(OrderClickListener itemClickListener) {
        orderClickListener = itemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public AllOrdersVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.orders_card_design, parent, false);
        return new AllOrdersVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllOrdersAdapter.AllOrdersVH holder, int position) {
        Orders orders = pendingOrders.get(position);
        holder.orderNo.setText(orders.getOrderNo());
        holder.mpesaCode.setText(orders.getMpesaCode());
        holder.orderDate.setText(orders.getOrderDate());
        holder.orderTime.setText(orders.getOrderTime());
        holder.amountPaid.setText(orders.getAmountPaid());
        holder.orderStatus.setText(orders.getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return pendingOrders.size();
    }

    //onclick interface
    public interface OrderClickListener {
        void onClick(int position);
    }

    public class AllOrdersVH extends RecyclerView.ViewHolder {

        TextView orderNo, mpesaCode, orderDate, orderTime, amountPaid, orderStatus;

        public AllOrdersVH(@NonNull @NotNull View itemView) {
            super(itemView);

            orderNo = itemView.findViewById(R.id.orderNo);
            mpesaCode = itemView.findViewById(R.id.mpesaCode);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderTime = itemView.findViewById(R.id.orderTime);
            amountPaid = itemView.findViewById(R.id.amountPaid);
            orderStatus = itemView.findViewById(R.id.orderStatus);

            itemView.setOnClickListener(v -> {
                if (orderClickListener != null) {
                    //get the position of the adapter

                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {

                        orderClickListener.onClick(position);
                    }
                }
            });
        }
    }
}
