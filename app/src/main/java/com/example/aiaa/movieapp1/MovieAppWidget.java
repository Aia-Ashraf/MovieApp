package com.example.aiaa.movieapp1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation off App Widget functionality.
 */
public class MovieAppWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        ScheduledJobService scheduledJobService;
        scheduledJobService = new ScheduledJobService();
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        FavouritDatabase mDB;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.movie_app_widget);

        Intent intent = new Intent(context.getApplicationContext(), ScheduledJobService.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetId);

        views.setTextViewText(R.id.appwidget_text, "❥  " + "Us" );

        views.setTextViewText(R.id.appwidget_text2, "❥  " + "Glass");
        views.setTextViewText(R.id.appwidget_text3, "❥  " + "Cars");
        views.setTextViewText(R.id.appwidget_text4, "❥  " + "The Mule");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

//        Intent intent = new Intent(context.getApplicationContext(),
//                ScheduledJobService.class);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//        String d = intent.getStringExtra("d");


        // Construct the RemoteViews object
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);


        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}