package com.madhuurstta.makta;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

class adapter_chat extends RecyclerView.Adapter<adapter_chat.ViewHolder> {

    Context context;
    ArrayList<String> msg = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> sender = new ArrayList<>();

    public adapter_chat(Context context, ArrayList<String> msg, ArrayList<String> time, ArrayList<String> sender) {
        this.context = context;
        this.msg = msg;
        this.time = time;
        this.sender = sender;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if (sender.get(position).equals("admin")){
            holder.msgReceived.setText(msg.get(position));
            holder.receivedTime.setText(time.get(position));
            holder.received.setVisibility(View.VISIBLE);
            holder.sent.setVisibility(View.GONE);
        } else {
            holder.msgSent.setText(msg.get(position));
            holder.sentTime.setText(time.get(position));
            holder.sent.setVisibility(View.VISIBLE);
            holder.received.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return msg.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        latobold msgSent;
        latonormal sentTime;
        RelativeLayout sent;
        latobold msgReceived;
        latonormal receivedTime;
        RelativeLayout received;

        public ViewHolder(View view) {
            super(view);

            this.msgSent = itemView.findViewById(R.id.msgSent);
            this.sentTime = itemView.findViewById(R.id.sentTime);
            this.sent = itemView.findViewById(R.id.sent);
            this.msgReceived = itemView.findViewById(R.id.msgReceived);
            this.receivedTime = itemView.findViewById(R.id.receivedTime);
            this.received = itemView.findViewById(R.id.received);
        }
    }


}
