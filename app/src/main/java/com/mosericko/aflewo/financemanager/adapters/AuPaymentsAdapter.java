package com.mosericko.aflewo.financemanager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mosericko.aflewo.R;
import com.mosericko.aflewo.eventsmanager.adapters.OnClickInterface;
import com.mosericko.aflewo.financemanager.classes.PaymentDetails;
import com.mosericko.aflewo.helperclasses.RequestHandler;
import com.mosericko.aflewo.helperclasses.URLs;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AuPaymentsAdapter extends RecyclerView.Adapter<AuPaymentsAdapter.AuVH> {
    Context context;
    ArrayList<PaymentDetails> payments;
    OnClickInterface onClickInterface;


    public AuPaymentsAdapter(Context context, ArrayList<PaymentDetails> payments) {
        this.context = context;
        this.payments = payments;
    }

    public void setItemClickListener(OnClickInterface onClickInterface) {
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @NotNull
    @Override
    public AuVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.au_payments_card, parent, false);
        return new AuVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AuPaymentsAdapter.AuVH holder, int position) {
        PaymentDetails paymentDetails = payments.get(position);
        holder.mpesa.setText(paymentDetails.getMpesa());
        holder.amount.setText(paymentDetails.getAmount());
        holder.date.setText(paymentDetails.getDate());
        holder.name1.setText(paymentDetails.getFirstName());
        holder.name2.setText(paymentDetails.getLastName());

        if (paymentDetails.getStatus().equals("0")) {
            holder.status.setText("pending");
        } else {
            holder.status.setText("approved");
        }


    }

    @Override
    public int getItemCount() {
        return payments.size();
    }


    public class AuVH extends RecyclerView.ViewHolder {
        TextView amount, mpesa, date, status, name1, name2;

        public AuVH(@NonNull @NotNull View itemView) {
            super(itemView);
            mpesa = itemView.findViewById(R.id.mpesaTCode);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.paymentStatus);
            amount = itemView.findViewById(R.id.amountPaid);
            name1 = itemView.findViewById(R.id.name1);
            name2 = itemView.findViewById(R.id.name2);

            itemView.setOnClickListener(v -> {
               /* if (onClickInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onClickInterface.onItemClick(position);
                    }
                }*/

                openDialog();
            });

        }

        private void openDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Caution")
                    .setMessage(" Approve Payment?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        approveId();
                    })
                    .setNegativeButton("NO", (dialog, which) -> Toast.makeText(itemView.getContext(), "Not Approved!", Toast.LENGTH_SHORT).show());
            builder.show();
        }

        private void approveId() {
            int i;
            String id;
            i = getAdapterPosition();
            PaymentDetails paymentDetails = payments.get(i);
            id = paymentDetails.getUserId();

            ApproveAsync archiveAsync = new ApproveAsync(id);
            archiveAsync.execute();

            payments.remove(i);
            notifyItemRemoved(i);

        }

        class ApproveAsync extends AsyncTask<Void, Void, String> {
            String id;

            public ApproveAsync(String id) {
                this.id = id;
            }


            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> param = new HashMap<>();
                param.put("user_id", id);

                return requestHandler.sendPostRequest(URLs.APPROVE_PAY, param);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject obj = new JSONObject(s);
                    Toast.makeText(itemView.getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
