package com.example.android.bakingfun;

import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Recipe[] allRecipes;
    public static Recipe[] widgetData;
    private Parcelable listState;
    private final String LIST = "list";
    private final String RECIPES_DATA = "recipesdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(RECIPES_DATA)){
                allRecipes = (Recipe[]) savedInstanceState.getSerializable(RECIPES_DATA);
                listState = savedInstanceState.getParcelable(LIST);
                displayData(this,allRecipes);
            }

        }else{
            String URL_STRING = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
            requestDataAndDisplay(this, URL_STRING);

        }

    }
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        // Save list state
            state.putSerializable(RECIPES_DATA,allRecipes);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            state.putParcelable(LIST, Objects.requireNonNull(mRecyclerView.getLayoutManager()).onSaveInstanceState());
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            allRecipes = (Recipe[]) savedInstanceState.getSerializable(RECIPES_DATA);
            listState = savedInstanceState.getParcelable(LIST);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(listState != null){
            mLayoutManager.onRestoreInstanceState(listState);
        }
    }

    private void requestDataAndDisplay(final Context context, String url){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Recipe[] recipe = gson.fromJson(response,Recipe[].class);
                displayData(context,recipe);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"error", Toast.LENGTH_SHORT).show();
            }
        });
        //Adding request to requestQueue
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.add(request);
    }

    private void displayData(Context context,Recipe[] recipe){
        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(context, recipe);
        allRecipes = recipe;
        widgetData = recipe;
        if(recipe.length != 0){
            mRecyclerView.setAdapter(mAdapter);
        }

    }
}
