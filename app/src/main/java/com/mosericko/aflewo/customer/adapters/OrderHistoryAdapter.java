package com.mosericko.aflewo.customer.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.classes.Orders;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryVH> {

    Context context;
    ArrayList<Orders> ordersArrayList;
    //create interface object
    private OrderClickListener orderClickListener;

    public OrderHistoryAdapter(Context context, ArrayList<Orders> ordersArrayList) {
        this.context = context;
        this.ordersArrayList = ordersArrayList;
    }


    @NonNull
    @NotNull
    @Override
    public OrderHistoryVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.orders_card_design, parent, false);
        return new OrderHistoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderHistoryAdapter.OrderHistoryVH holder, int position) {
        Orders orders = ordersArrayList.get(position);

        holder.orderNo.setText(orders.getOrderNo());
        holder.mpesaCode.setText(orders.getMpesaCode());
        holder.orderDate.setText(orders.getOrderDate());
        holder.orderTime.setText(orders.getOrderTime());
        holder.amountPaid.setText(orders.getAmountPaid());
        holder.orderStatus.setText(orders.getOrderStatus());
        if (orders.getOrderStatus().equals("pending")) {
            holder.orderStatus.setTextColor(Color.RED);
        }


    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }

    public void setOnItemClickListener(OrderClickListener itemClickListener) {
        orderClickListener = itemClickListener;
    }

    //onclick interface
    public interface OrderClickListener {
        void onClick(int position);
    }

    public class OrderHistoryVH extends RecyclerView.ViewHolder {
        TextView orderNo, mpesaCode, orderDate, orderTime, amountPaid, orderStatus;

        public OrderHistoryVH(@NonNull @NotNull View itemView) {
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
