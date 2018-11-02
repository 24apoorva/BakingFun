package com.example.android.bakingfun.widgetsdata;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingfun.Ingredient;
import com.example.android.bakingfun.MainActivity;
import com.example.android.bakingfun.R;
import com.example.android.bakingfun.Recipe;

import java.util.List;

public class ListWidgetService extends RemoteViewsService{
    public final static String WIDGET_POSITION_NAME = "NameOfRecipe";
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetListItemViewFactory(getApplicationContext(),intent);
    }

    class WidgetListItemViewFactory implements RemoteViewsFactory {
        private final Context context;
        private final int appWidgetId;
        private final String myName;
        private List<Ingredient> ingData;
        final Recipe[] allData = MainActivity.widgetData;


        WidgetListItemViewFactory(Context context, Intent intent){
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
            this.myName = intent.getStringExtra(WIDGET_POSITION_NAME);
        }



        @Override
        public void onCreate() {
            for (Recipe anAllData : allData) {
                if (anAllData.getName().equals(myName)) {
                    ingData = anAllData.getIngredients();
                    Log.d("Config", "data is ::" + ingData.get(0).getIngredient());
                }
            }
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingData.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_widget);
            String ing = ingData.get(position).getIngredient();
            views.setTextViewText(R.id.text_view_widget,ing);
            return views;
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
            return true;
        }
    }
   }