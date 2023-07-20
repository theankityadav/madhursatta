package com.madhuurstta.makta;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class adapter_withdraw_requests extends RecyclerView.Adapter<adapter_withdraw_requests.ViewHolder> {

    Context context;
    ArrayList<String> amount = new ArrayList<>();
    ArrayList<String> details = new ArrayList<>();
    ArrayList<String> mode = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();

    public adapter_withdraw_requests(Context context, ArrayList<String> amount, ArrayList<String> details, ArrayList<String> mode, ArrayList<String> date, ArrayList<String> status) {
        this.context = context;
        this.amount = amount;
        this.mode = mode;
        this.details = details;
        this.date = date;
        this.status = status;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.withdraw_requests, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.date.setText(date.get(position));
        holder.amount.setText(amount.get(position));
        holder.paymentMethod.setText(mode.get(position));
        holder.paymentDetails.setText(details.get(position));
        holder.status.setText(status.get(position));

    }


    @Override
    public int getItemCount() {
        return amount.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        latobold date;
        latobold amount;
        latobold status;
        latobold paymentMethod;
        latobold paymentDetails;

        public ViewHolder(View view) {
            super(view);


            this.date = itemView.findViewById(R.id.date);
            this.amount = itemView.findViewById(R.id.amount);
            this.status = itemView.findViewById(R.id.status);
            this.paymentMethod = itemView.findViewById(R.id.payment_method);
            this.paymentDetails = itemView.findViewById(R.id.payment_details);
        }
    }


}
