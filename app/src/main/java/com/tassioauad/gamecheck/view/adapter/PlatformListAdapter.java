package com.tassioauad.gamecheck.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tassioauad.gamecheck.R;
import com.tassioauad.gamecheck.model.entity.Platform;

import java.util.List;

public class PlatformListAdapter extends RecyclerView.Adapter<PlatformListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Platform> platformList;
    private OnItemClickListener<Platform> onItemClickListener;

    public PlatformListAdapter(List<Platform> platformList, OnItemClickListener<Platform> onItemClickListener) {
        this.platformList = platformList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_platform, parent, false);
        view.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Platform platform = platformList.get(position);
        holder.itemView.setTag(platform);

        if (platform.getImage() != null && platform.getImage().getMediumUrl() != null) {
            Picasso.with(holder.imageViewPhoto.getContext()).load(platform.getImage().getMediumUrl()).placeholder(R.drawable.nophoto).into(holder.imageViewPhoto);
        } else {
            holder.imageViewPhoto.setImageDrawable(holder.imageViewPhoto.getContext().getResources().getDrawable(R.drawable.nophoto));
        }

        holder.textViewName.setText(platform.getName());

    }


    @Override
    public int getItemCount() {
        return platformList.size();
    }

    @Override
    public void onClick(View view) {
        Platform platform = (Platform) view.getTag();
        onItemClickListener.onClick(platform);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView imageViewPhoto;

        public ViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textview_name);
            imageViewPhoto = (ImageView) view.findViewById(R.id.imageview_photo);
        }
    }
}
