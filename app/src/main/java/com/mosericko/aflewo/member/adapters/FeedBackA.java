package com.mosericko.aflewo.member.adapters;

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

public class FeedBackA extends RecyclerView.Adapter<FeedBackA.FeedBackVH> {
    Context context;
    ArrayList<FeedBackData> feedBackList;

    public FeedBackA(Context context, ArrayList<FeedBackData> feedBackList) {
        this.context = context;
        this.feedBackList = feedBackList;
    }

    @NonNull
    @NotNull
    @Override
    public FeedBackVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.feedback_card, parent, false);
        return new FeedBackVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FeedBackA.FeedBackVH holder, int position) {
        FeedBackData feedBackData = feedBackList.get(position);

        holder.title.setText(feedBackData.getTitle());
        holder.dateTime.setText(feedBackData.getMessageDate());
        holder.feedbackMessage.setText(feedBackData.getMessage());
    }

    @Override
    public int getItemCount() {
        return feedBackList.size();
    }


    public class FeedBackVH extends RecyclerView.ViewHolder {
        TextView title, dateTime, feedbackMessage;

        public FeedBackVH(@NonNull @NotNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.message_title);
            dateTime = itemView.findViewById(R.id.dateTime);
            feedbackMessage = itemView.findViewById(R.id.actual_message);
        }
    }
}
