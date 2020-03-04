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

    }

    @Override
    public void onDataSetChanged() {
        movieHelper.open();
        tvHelper.open();
        list = movieHelper.getAllMovies();
        for (int i = 0; i < list.size(); i++){
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(list.get(i).getPhoto())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL ).get();
            } catch (Exception e) {
                e.getMessage();
            }
            Items.add(bitmap);
        }
  list = tvHelper.getdataTV();
  for (int i = 0; i <list.size(); i++){
      Bitmap bitmap = null;
      try {
          bitmap = Glide.with(context)
                  .asBitmap()
                  .load(list.get(i).getPhoto())
                  .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
      } catch (Exception e) {
          e.printStackTrace();
      }
      Items.add(bitmap);
  }
  }

    @Override
    public void onDestroy() {
        movieHelper.close();
        tvHelper.close();

    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_items);
        remoteViews.setImageViewBitmap(R.id.imageView, Items.get(position));
        Bundle bundle = new Bundle();
        bundle.putInt(ImageMovieWidget.EXTRA_ITEM, position);
        Intent intent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.imageView, intent);
        return remoteViews;

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
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
