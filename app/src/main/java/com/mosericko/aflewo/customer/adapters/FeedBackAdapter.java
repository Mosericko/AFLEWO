package com.mosericko.aflewo.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.customer.classes.FeedBackData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.FeedBackVH> {
    Context context;
    ArrayList<FeedBackData> feedBackList;

    public FeedBackAdapter(Context context, ArrayList<FeedBackData> feedBackList) {
        this.context = context;
        this.feedBackList = feedBackList;
    }

    @NonNull
    @NotNull
    @Override
    public FeedBackVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.message_card_design, parent, false);
        return new FeedBackVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FeedBackAdapter.FeedBackVH holder, int position) {
        FeedBackData feedBackData = feedBackList.get(position);

        holder.title.setText(feedBackData.getTitle());
        holder.messageDate.setText(feedBackData.getMessageDate());
        holder.messageTime.setText(feedBackData.getMessageTime());
        holder.feedbackMessage.setText(feedBackData.getMessage());

    }

    @Override
    public int getItemCount() {
        return feedBackList.size();
    }

    public static class FeedBackVH extends RecyclerView.ViewHolder {
        TextView title, messageDate, messageTime, feedbackMessage;

        public FeedBackVH(@NonNull @NotNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.message_title);
            messageDate = itemView.findViewById(R.id.message_date);
            messageTime = itemView.findViewById(R.id.message_time);
            feedbackMessage = itemView.findViewById(R.id.actual_message);
        }
    }
}
