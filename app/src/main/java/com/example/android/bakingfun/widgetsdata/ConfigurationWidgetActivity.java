package com.example.android.bakingfun.widgetsdata;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.android.bakingfun.Ingredient;
import com.example.android.bakingfun.MainActivity;
import com.example.android.bakingfun.R;
import com.example.android.bakingfun.Recipe;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingfun.widgetsdata.ListWidgetService.WIDGET_POSITION_NAME;

public class ConfigurationWidgetActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "prefs";
    public static final String KEY_RECIPE_NAME = "keyRecipeName";
    private String sendName = "Recipe Name";
    private Button finishButton;
    private Recipe[] dataNeeded;

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_widget);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED, resultValue);

        if(appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();
        }

        dataNeeded = MainActivity.widgetData;
        ListView listView = (ListView) findViewById(R.id.config_list);
        List<String> fullData = getIngredientsFull(dataNeeded);
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_view,R.id.list_textView, fullData);
        listView.setAdapter(mArrayAdapter);
        finishButton = (Button)findViewById(R.id.finish_button);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                sendName = dataNeeded[position].getName();
            }
        });

    }

    private List<String> getIngredientsFull(Recipe[] data) {
        List<String> names = new ArrayList<>();
        for(int i=0;i<data.length;i++){
            names.add(data[i].getName());
        }
        return names;
    }

    public void onClickFinish(View v) {

        if(sendName.equals("Recipe Name")){
            Toast.makeText(this,"Select a Recipe",Toast.LENGTH_SHORT).show();
        }else{
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            Intent serviceIntent = new Intent(this,ListWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            serviceIntent.putExtra(WIDGET_POSITION_NAME,sendName);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));


            RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.baking_app_widget);

            views.setOnClickPendingIntent(R.id.widget_appBar, pendingIntent);
            views.setCharSequence(R.id.appwidget_text, "setText", sendName);
            views.setRemoteAdapter(R.id.widget_list,serviceIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);

            SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_RECIPE_NAME + appWidgetId, sendName);
            editor.apply();

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }

    }
}