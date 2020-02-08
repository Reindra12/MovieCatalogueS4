package com.reindra.submission4java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.reindra.submission4java.R;
import com.reindra.submission4java.model.Movie;

import java.util.ArrayList;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.ViewHolder> {
    private ArrayList<Movie> ListMovie = new ArrayList<>();

    public TVAdapter(ArrayList<Movie> list){
        this.ListMovie = list;
    }
    public TVAdapter(){

    }
    public void setData(ArrayList<Movie>items){
        ListMovie.clear();
        ListMovie.addAll(items);
        notifyDataSetChanged();
    }
    private TVAdapter.OnItemClickCallBack onItemClickCallBack;

    public void setOnItemClickCallBack(TVAdapter.OnItemClickCallBack onItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack;
    }

    @NonNull
    @Override
    public TVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TVAdapter.ViewHolder holder, int position) {
        Movie movie = ListMovie.get(position);
        Glide.with(holder.itemView.getContext())
                .load(movie.getPhoto())
                .apply(new RequestOptions().override(350, 550))
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.ic_missing)
                .into(holder.imgPhototv);
        holder.titletv.setText(movie.getTitle());
        holder.overviewtv.setText(movie.getOverview());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallBack.onItemClicked(ListMovie.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return ListMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titletv;
        TextView overviewtv;
        ImageView imgPhototv;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titletv = itemView.findViewById(R.id.tv_tittletv);
            overviewtv = itemView.findViewById(R.id.tvdescriptiontv);
            imgPhototv = itemView.findViewById(R.id.img_postertv);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
    public interface OnItemClickCallBack {
        void onItemClicked(Movie data);
    }
}
