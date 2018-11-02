package com.example.android.bakingfun.widgetsdata;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.example.android.bakingfun.MainActivity;
import com.example.android.bakingfun.R;
import static com.example.android.bakingfun.widgetsdata.ConfigurationWidgetActivity.KEY_RECIPE_NAME;
import static com.example.android.bakingfun.widgetsdata.ConfigurationWidgetActivity.SHARED_PREFS;
import static com.example.android.bakingfun.widgetsdata.ListWidgetService.WIDGET_POSITION_NAME;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for(int appWidgetId: appWidgetIds){
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntentSeeAll = PendingIntent.getActivity(context,0,intent,0);

            SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            String name = prefs.getString(KEY_RECIPE_NAME + appWidgetId,"Recipe Name");
            Intent serviceIntent = new Intent(context,ListWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            serviceIntent.putExtra(WIDGET_POSITION_NAME,name);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.baking_app_widget);
            views.setCharSequence(R.id.appwidget_text,"setText",name);
            views.setOnClickPendingIntent(R.id.widget_appBar,pendingIntentSeeAll);
            views.setRemoteAdapter(R.id.widget_list,serviceIntent);
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        context.startService(new Intent(context, ListWidgetService.class));

    }


}
