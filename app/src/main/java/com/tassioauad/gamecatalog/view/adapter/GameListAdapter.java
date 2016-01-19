package com.tassioauad.gamecatalog.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tassioauad.gamecatalog.R;
import com.tassioauad.gamecatalog.model.entity.Game;

import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Game> gameList;
    private OnItemClickListener<Game> onItemClickListener;

    public GameListAdapter(List<Game> gameList, OnItemClickListener<Game> onItemClickListener) {
        this.gameList = gameList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_game, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.itemView.setTag(game);

        if (game.getImage() != null && game.getImage().getMediumUrl() != null) {
            Picasso.with(holder.imageViewPhoto.getContext()).load(game.getImage().getMediumUrl()).placeholder(R.drawable.nophoto).into(holder.imageViewPhoto);
        }

        holder.textViewName.setText(game.getName());
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    @Override
    public void onClick(View v) {
        Game game = (Game) v.getTag();
        onItemClickListener.onClick(game);
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
