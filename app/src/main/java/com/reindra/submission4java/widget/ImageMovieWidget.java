package com.reindra.submission4java.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.reindra.submission4java.R;

public class ImageMovieWidget extends AppWidgetProvider {
    public static final String EXTRA_ITEM = "com.reindra.submission4java.EXTRA_TEXT";
    private static final String TOAST_ACTION = "com.reindra.submission4java.TOAST_ACTION";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context, FavMovieFactory.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.image_widget);
        remoteViews.setRemoteAdapter(R.id.stact_view, intent);
        remoteViews.setEmptyView(R.id.stact_view, R.id.empty_view);

        Intent suka = new Intent(context, ImageMovieWidget.class);
        suka.setAction(ImageMovieWidget.TOAST_ACTION);
        suka.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        suka.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntents = PendingIntent.getBroadcast(context, 0, suka, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.stact_view, pendingIntents);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() !=null){
            if (intent.getAction().equals(TOAST_ACTION)){
                int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
                Toast.makeText(context, "coba" + viewIndex, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
    }

}
