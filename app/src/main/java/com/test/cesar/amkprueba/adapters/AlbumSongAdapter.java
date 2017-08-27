package com.test.cesar.amkprueba.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.cesar.amkprueba.utils.ICommunication;
import com.test.cesar.amkprueba.R;
import com.test.cesar.amkprueba.model.Constants;
import com.test.cesar.amkprueba.model.Result;

import java.util.ArrayList;

/**
 * Created by Cesar on 26/08/2017.
 */

public class AlbumSongAdapter extends RecyclerView.Adapter<AlbumSongAdapter.ViewHolder> {

    private ArrayList<Result> items;
    public ICommunication mCallBack;
    Context ctx;
    String origin;

    public AlbumSongAdapter(ArrayList<Result> items, ICommunication mCallBack, Context ctx, String origin) {
        this.items = items;
        this.mCallBack = mCallBack;
        this.ctx = ctx;
        this.origin = origin;
    }


    @Override
    public AlbumSongAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false);

        return new AlbumSongAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result item = items.get(position);
        holder.tvArtist.setText(item.getArtistName());
        holder.tvAlbum.setText(item.getCollectionName());
        holder.tvPriceDisc.setText(String.valueOf(item.getCollectionPrice()));

        if (item.getTrackPrice() != null) {
            holder.tvPriceTrack.setText(String.valueOf(item.getTrackPrice()));

        } else {
            holder.tvPriceTrack.setVisibility(View.GONE);
            holder.tvSongPriceLabel.setVisibility(View.GONE);
        }

        holder.tvCountry.setText(item.getCountry());
        holder.tvCurrency.setText(item.getCurrency());
        Picasso.with(ctx).load(item.getArtworkUrl100()).into(holder.ivImage);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivImage;
        private TextView tvArtist;
        private TextView tvAlbum;
        private TextView tvPriceDisc;
        private TextView tvPriceTrack;
        private TextView tvCountry;
        private TextView tvCurrency;
        private TextView tvSongPriceLabel;


        ViewHolder(View v) {
            super(v);
            ivImage = v.findViewById(R.id.iv_artist_image);
            tvArtist = v.findViewById(R.id.tv_artist);
            tvAlbum = v.findViewById(R.id.tv_album);
            tvPriceDisc = v.findViewById(R.id.tv_price_disc);
            tvPriceTrack = v.findViewById(R.id.tv_price_track);
            tvCountry = v.findViewById(R.id.tv_country);
            tvCurrency = v.findViewById(R.id.tv_currency);
            tvSongPriceLabel = v.findViewById(R.id.tv_song_price_label);

            v.setOnClickListener(this);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onClick(View view) {
            if (origin.equals(Constants.ARTIST_ITEM))
                mCallBack.onResponse(Constants.ARTIST_ITEM, items.get(getAdapterPosition()));
            else {
                mCallBack.onResponse(Constants.SONG_ITEM, items.get(getAdapterPosition()));
            }
        }
    }
}