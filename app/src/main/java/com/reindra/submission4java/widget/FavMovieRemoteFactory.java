package com.reindra.submission4java.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.reindra.submission4java.R;
import com.reindra.submission4java.database.MovieHelper;
import com.reindra.submission4java.database.TVHelper;
import com.reindra.submission4java.model.Movie;

import java.util.ArrayList;
import java.util.List;

class FavMovieRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context context;
    private final List<Bitmap> Items = new ArrayList<>();
    private MovieHelper movieHelper;
    private TVHelper tvHelper;
    private ArrayList<Movie> list;

    public FavMovieRemoteFactory(Context context) {
        this.context = context;

    }

    @Override
    public void onCreate() {
        movieHelper = MovieHelper.getInstance(context);
        tvHelper = TVHelper.getInstance(context);
        movieHelper.open();
        tvHelper.open();

    }

    @Override
    public void onDataSetChanged() {
        list = movieHelper.getDataMovies();
        for (int i = 0; i < list.size(); i++) {
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(list.get(i).getPhoto())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            } catch (Exception e) {
                e.getMessage();
            }

            Items.add(bitmap);

        }
    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
   public RemoteViews getViewAt(int position){
        RemoteViews remote = new RemoteViews(context.getPackageName(), R.layout.widget_items);
        remote.setImageViewBitmap(R.id.imageView, Items.get(position));
        Bundle extra = new Bundle();
        extra.putInt(ImageMovieWidget.EXTRA_ITEM, position);
        Intent intentlagi = new Intent();
        intentlagi.putExtras(extra);
        remote.setOnClickFillInIntent(R.id.imageView, intentlagi);
        return remote;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onDestroy() {
        movieHelper.close();
        tvHelper.close();

    }

}
