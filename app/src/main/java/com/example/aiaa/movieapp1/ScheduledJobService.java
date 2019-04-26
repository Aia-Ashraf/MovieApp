package com.example.aiaa.movieapp1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.Random;

public class ScheduledJobService extends JobService {

    String d;
    private static final String TAG = "Aiaaaaaa";
    public FavouritDatabase mDB;

//    private static final String TAG = ScheduledJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(final JobParameters params) {
        //Offloading work to a new thread.

        Intent intent = new Intent(getApplicationContext(),
                MovieAppWidget.class);
        intent.putExtra("d", "hi222222222222222");

        new Thread(new Runnable() {
            @Override

            public void run() {
                MovieAppWidget movieAppWidget = new MovieAppWidget();
                d = "ddddddddd";
                Log.d(TAG, "completeJob: " + d);

                codeYouWantToRun(params);
            }

        }).start();


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void codeYouWantToRun(final JobParameters parameters) {
        try {

            Intent intent = new Intent();

            int[] allWidgetIds = intent
                    .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

//      ComponentName thisWidget = new ComponentName(getApplicationContext(),
//              MyWidgetProvider.class);
//      int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);

            for (int widgetId : allWidgetIds) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                        .getApplicationContext());

                // create some random data
                int number = (new Random().nextInt(100));

                RemoteViews views = new RemoteViews(this.getApplicationContext().getPackageName(), R.layout.movie_app_widget);

                Log.w("WidgetExample", String.valueOf(number));
                // Set the text
//                views.setTextViewText(R.id.appwidget_text, "❥  " + "Us" + this.getApplicationContext().getDatabasePath("movies_db").toString() +
//                        "");
                // Register an onClickListener
                Intent clickIntent = new Intent(this.getApplicationContext(),
                        MovieAppWidget.class);

                clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                        allWidgetIds);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 0, clickIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                appWidgetManager.updateAppWidget(widgetId, views);
            }
            stopSelf();


            Log.d(TAG, "completeJob: " + "Done");
            Log.d(TAG, "completeJob: " + "jobStarted");
            //This task takes 2 seconds to complete.
            Thread.sleep(2000);

            Log.d(TAG, "completeJob: " + "jobFinished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //Tell the framework that the job has completed and doesnot needs to be reschedule
            jobFinished(parameters, true);
        }
    }
}



