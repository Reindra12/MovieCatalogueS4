package com.reindra.submission4java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.reindra.submission4java.R;
import com.reindra.submission4java.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<Movie> mData = new ArrayList<>();
    private OnItemClickCallBack onItemClickCallBack;

    public MovieAdapter() {

    }

    public interface OnItemClickCallBack {
        void onItemClicked(Movie data);
    }

    public void setOnItemClickCallBack(OnItemClickCallBack onItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack;
    }

    public void setData(ArrayList<Movie> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public MovieAdapter(ArrayList<Movie> list) {
        this.mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.ViewHolder holder, int position) {
        Movie moviesItems = mData.get(position);
        Glide.with(holder.itemView.getContext())
                .load(moviesItems.getPhoto())
                .apply(new RequestOptions().override(350, 550))
                .placeholder(R.drawable.img_placeholder)
                .into(holder.imgPhoto);

        holder.title.setText(moviesItems.getTitle());
        holder.overview.setText(moviesItems.getOverview());
        holder.date.setText(moviesItems.getDate());
        holder.county.setText(moviesItems.getCountry());
        Float pa = Float.parseFloat(moviesItems.getRating());
        pa = Float.valueOf(pa * 10);
        holder.rating.setText(String.format("%s%%", pa.intValue()));
        holder.ratingBar.setRating(pa / 20f);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallBack.onItemClicked(mData.get(holder.getAdapterPosition()));

            }

        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView id;
        Integer idint;
        TextView overview;
        TextView rating;
        TextView county;
        ImageView imgPhoto, favorite;
        CardView cardView;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            date = itemView.findViewById(R.id.tv_date_movie);
            title = itemView.findViewById(R.id.tvjudul);
            imgPhoto = itemView.findViewById(R.id.imgposter);
            overview = itemView.findViewById(R.id.tv_description);
            rating = itemView.findViewById(R.id.tv_score_movie);
            county = itemView.findViewById(R.id.tv_country);
            cardView = itemView.findViewById(R.id.cv_item_movie);
            ratingBar = itemView.findViewById(R.id.rb_score_movie);

        }
    }
}
