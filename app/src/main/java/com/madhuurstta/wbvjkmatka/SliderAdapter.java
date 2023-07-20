package com.madhuurstta.wbvjkmatka;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        SliderItem sliderItem = mSliderItems.get(position);

        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImageUrl())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.imageViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sliderItem.getRefer().equals("")){
                    if (sliderItem.getRefer().equals("url")){
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(sliderItem.getData()));
                        viewHolder.itemView.getContext().startActivity(i);
                    } else if (sliderItem.getRefer().equals("refer")){
                        viewHolder.itemView.getContext().startActivity(new Intent(viewHolder.itemView.getContext(), earn.class));
                    } else if (sliderItem.getRefer().equals("market")){
                        context.startActivity(new Intent(context, games.class)
                                .putExtra("market", sliderItem.getParams().get("market"))
                                .putExtra("is_open", sliderItem.getParams().get("is_open"))
                                .putExtra("is_close", sliderItem.getParams().get("is_close"))
                                .putExtra("timing", sliderItem.getParams().get("open_time") + "-" + sliderItem.getParams().get("close_time"))
                        );
                    }
                }
            }
        });


    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}
