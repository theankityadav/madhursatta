package com.madhuurstta.makta;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.util.Log;

import cn.iwgang.countdownview.CountdownView;

class adapter_result extends RecyclerView.Adapter<adapter_result.ViewHolder> {

    Context context;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> result = new ArrayList<>();

    ArrayList<String> is_open = new ArrayList<>();
    ArrayList<String> open_time = new ArrayList<>();
    ArrayList<String> close_time = new ArrayList<>();
    private ArrayList<String> open_av = new ArrayList<>();

    public adapter_result(Context context,  ArrayList<String> name, ArrayList<String> result, ArrayList<String> is_open, ArrayList<String> open_time, ArrayList<String> close_time,ArrayList<String> open_av) {
        this.context = context;
        this.name = name;
        this.result = result;
        this.is_open = is_open;
        this.open_time = open_time;
        this.close_time = close_time;
        this.open_av = open_av;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_layout, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.name.setText(name.get(position));
        holder.result.setText(result.get(position));


        holder.open_time.setText(open_time.get(position));
        holder.close_time.setText(close_time.get(position));

        if (context.getSharedPreferences(constant.prefs,Context.MODE_PRIVATE).getString("verify","0").equals("1")){
            holder.play_game.setVisibility(View.VISIBLE);
        } else {
            holder.play_game.setVisibility(View.GONE);
        }
        holder.chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, charts.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("href",constant.prefix+"chart2/getChart.php?market="+name.get(position)));
                Log.e("curl",constant.prefix+"chart2/getChart.php?market="+name.get(position));
            }
        });

        if (open_av.get(position).equals("1") || is_open.get(position).equals("1")) {
            holder.status.setText("Betting is running for today");
            holder.status.setTextColor(context.getResources().getColor(R.color.md_green_800));
         //   Glide.with(context).load(R.drawable.play_button).into(holder.play_image);
            holder.play_game.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,games.class)
                            .putExtra("market",name.get(position))
                            .putExtra("is_open",is_open.get(position))
                            .putExtra("is_close",open_av.get(position))
                            .putExtra("timing",open_time.get(position)+"-"+close_time.get(position))
                    );
                }
            });

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            String givenDateString = formattedDate+" "+close_time.get(position);

            System.out.println(name.get(position));
            System.out.println(givenDateString);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
            try {
                Date mDate = sdf.parse(givenDateString);
                long timeInMilliseconds = mDate.getTime();
                //System.out.println(timeInMilliseconds);
                holder.timer.start(timeInMilliseconds-System.currentTimeMillis());
                //System.out.println("started");
                holder.timer.setOnCountdownIntervalListener(1, new CountdownView.OnCountdownIntervalListener() {
                    @Override
                    public void onInterval(CountdownView cv, long remainTime) {

                        //System.out.println(name.get(position)+" - "+cv.getRemainTime());
                    }
                });
                // System.out.println(timeInMilliseconds-System.currentTimeMillis());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // timer.start((Long.parseLong(jsonObject1.getString("end_time")) * 1000) - System.currentTimeMillis());
            //


        } else {
            holder.status.setText("Betting is closed for today");
            // holder.status.setText("CLOSED");
        //    Glide.with(context).load(R.drawable.play_red).into(holder.play_image);
            holder.status.setTextColor(context.getResources().getColor(R.color.md_red_600));

            holder.play_game.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new androidx.appcompat.app.AlertDialog.Builder(context)
                            .setTitle("Market Close")
                            .setMessage("Betting is already closed for this market")
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                }
            });
        }

        if(open_time.get(position).equals("HOLIDAY")){
      //      Glide.with(context).load(R.drawable.play_red).into(holder.play_image);
            holder.status.setText("Betting is closed because today is holiday");
            holder.status.setTextColor(context.getResources().getColor(R.color.md_red_600));
        }

    }


    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,result,open_time,close_time,status;
        LinearLayout chart;
        //        LinearLayout play_game;
        LinearLayout layout;
        LinearLayout play_game;
        ImageView play_image;
        CountdownView timer;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            play_image = view.findViewById(R.id.play_image);
            result = view.findViewById(R.id.result);
            open_time = view.findViewById(R.id.open_time);
            close_time = view.findViewById(R.id.close_time);
            play_game = view.findViewById(R.id.play_game);
            layout = view.findViewById(R.id.layout);
            status = view.findViewById(R.id.status);
            timer = view.findViewById(R.id.timer);
            chart = view.findViewById(R.id.chart);
        }
    }



}
