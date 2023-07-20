package com.madhuurstta.wbvjkmatka;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cn.iwgang.countdownview.CountdownView;

class adapter_delhi_markets extends RecyclerView.Adapter<adapter_delhi_markets.ViewHolder> {

    Context context;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> is_open = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> result = new ArrayList<>();
    ArrayList<String> market_name = new ArrayList<>();

    public adapter_delhi_markets(Context context, ArrayList<String> name, ArrayList<String> is_open, ArrayList<String> time, ArrayList<String> result,ArrayList<String> market_name) {
        this.context = context;
        this.name = name;
        this.is_open = is_open;
        this.time = time;
        this.result = result;
        this.market_name = market_name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_delhi_layout, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        holder.name.setText(time.get(position));
        holder.result.setText(result.get(position));
        holder.market_name.setText(market_name.get(position));
        holder.chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, charts.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("href",constant.prefix+"chart2/getChart.php?market="+name.get(position)));
                Log.e("curl",constant.prefix+"chart2/getChart.php?market="+name.get(position));
            }
        });

//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        String formattedDate = df.format(c);
//        String givenDateString = formattedDate+" "+time.get(position);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
//        try {
//            Date mDate = sdf.parse(givenDateString);
//            long timeInMilliseconds = mDate.getTime();
//            //System.out.println(timeInMilliseconds);
//            holder.timer.start(timeInMilliseconds-System.currentTimeMillis());
//            //System.out.println("started");
//            holder.timer.setOnCountdownIntervalListener(1, new CountdownView.OnCountdownIntervalListener() {
//                @Override
//                public void onInterval(CountdownView cv, long remainTime) {
//
//                    //System.out.println(name.get(position)+" - "+cv.getRemainTime());
//                }
//            });
//            // System.out.println(timeInMilliseconds-System.currentTimeMillis());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        if (is_open.get(position).equals("1")) {
            holder.status.setTextColor(context.getResources().getColor(R.color.md_black_1000));
            holder.status.setText("Betting is Running for today");

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,DelhiGames.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("market",name.get(position)).putExtra("timing",time.get(position)));
                }
            });

        } else {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Market is already closed", Toast.LENGTH_SHORT).show();
                }
            });

            holder.status.setTextColor(context.getResources().getColor(R.color.md_black_1000));
            holder.status.setText("Betting is CLOSED for today");
        }

        holder.setIsRecyclable(false);
    }


    @Override
    public int getItemCount() {
        return time.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,result,status,market_name;
        RelativeLayout layout;
        CountdownView timer;
        LinearLayout chart;

        public ViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.layout);
            name = view.findViewById(R.id.name);
            result = view.findViewById(R.id.result);
            status = view.findViewById(R.id.status);
            timer = view.findViewById(R.id.timer);
            market_name = view.findViewById(R.id.market_name);
            chart = view.findViewById(R.id.chart);


        }
    }



}
