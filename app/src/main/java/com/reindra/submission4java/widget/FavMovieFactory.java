package com.reindra.submission4java.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavMovieFactory extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavMovieRemoteFactory(this.getApplicationContext());
    }
}
